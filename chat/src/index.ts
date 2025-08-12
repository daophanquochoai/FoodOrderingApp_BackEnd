import express, { Request, Response, NextFunction } from 'express';
import { CopilotRuntime, LangChainAdapter, copilotRuntimeNodeHttpEndpoint } from '@copilotkit/runtime';
import dotenv from 'dotenv';
import fetch from "node-fetch";
import { AIMessageChunk } from "@langchain/core/messages";
import { IterableReadableStreamInterface } from '@langchain/core/utils/stream';

dotenv.config();
const app = express();

const PORT = process.env.PORT;
const BASE_URL = process.env.BASE_URL;

/*
    Chuyển một AsyncGenerator<T> thành một ReadableStream tương thích với LangChain
    vì LangChain yêu cầu vừa là ReadableStream vừa là AsyncIterable.
*/
function toReadableStream<T>(source: AsyncGenerator<T>): IterableReadableStreamInterface<T> {
    const stream = new ReadableStream<T>({
    async pull(controller) {
        // Đọc từng phần tử từ generator và đẩy vào stream
        const { done, value } = await source.next();
        if (done) {
        controller.close(); // Kết thúc stream
        } else {
        controller.enqueue(value);  // Thêm chunk vào stream
        }
    },
    cancel() {
        // Khi stream bị huỷ, đảm bảo đóng generator
        source.return?.(undefined);
    }
    });

    // Ép kiểu thủ công để stream cũng là AsyncIterable
    (stream as any)[Symbol.asyncIterator] = () => source;

    return stream as IterableReadableStreamInterface<T>;
}

/*
    Adapter kết nối giữa CopilotKit và LangChain.
    Trả về luồng dữ liệu dưới dạng stream các AIMessageChunk.
*/
const serviceAdapter = new LangChainAdapter({
    // Nhận vào các messages và tools từ CopilotKit
    chainFn: async ({ messages, tools }) => {
        /*
            Generator trả về từng AIMessageChunk, tương ứng với từng dòng/chunk
            dữ liệu từ response của RAG API
        */
        async function* generator(): AsyncGenerator<AIMessageChunk> {
            // Lấy message cuối cùng mà user nhập vào
            const lastMessage = messages[messages.length - 1]?.content;
            const url = `${BASE_URL}?message=${lastMessage}`;

            // Gọi API RAG
            const response = await fetch(url);
            if (!response.ok || !response.body) {
                throw new Error(`Failed to call /chat: ${response.statusText}`);
            }

            // Duyệt qua từng chunk dữ liệu trong response body
            const reader = response.body;

            // Nếu không cần xử lý <think> thì có thể dùng đoạn code này:
            // for await (const chunk of reader as any) {
            //     const text = chunk.toString('utf-8');
            //     if (text.trim()) {
            //         // Mỗi chunk sẽ được đóng gói vào AIMessageChunk và yield
            //         yield new AIMessageChunk({ content: text });
            //     }
            // }

            // Nếu cần xử lý <think> thì dùng đoạn code này:
            let buffer = '';        // Dùng để lưu trữ tạm thời chuỗi phản hồi nhận được từ các chunk
            let inThink = false;    // Cờ đánh dấu đang ở trong đoạn <think>...</think>

            for await (const chunk of reader as any) {
                const text = chunk.toString('utf-8');
                buffer += text;

                while (buffer.length > 0) {
                    if (!inThink) {
                        const thinkStart = buffer.indexOf('<think>');
                        if (thinkStart === -1) {
                            // Không có <think>, gửi toàn bộ buffer
                            if (buffer.trim()) {
                                // Mỗi chunk sẽ được đóng gói vào AIMessageChunk và yield
                                yield new AIMessageChunk({ content: buffer });
                            }
                            buffer = '';
                        } else if (thinkStart > 0) {
                            // Có <think> ở giữa, gửi phần trước <think>
                            const sendPart = buffer.slice(0, thinkStart);
                            if (sendPart.trim()) {
                                yield new AIMessageChunk({ content: sendPart });
                            }
                            buffer = buffer.slice(thinkStart);
                        } else {
                            // <think> ở đầu buffer
                            inThink = true;
                            buffer = buffer.slice(7); // Bỏ '<think>'
                        }
                    } else {
                        const thinkEnd = buffer.indexOf('</think>');
                        if (thinkEnd === -1) {
                            // Chưa kết thúc <think>, tiếp tục đợi chunk tiếp theo
                            buffer = ''; // Xoá buffer để chờ thêm chunk mới
                            break;
                        } else {
                            // Đã kết thúc </think>, bỏ qua nội dung trong <think>
                            buffer = buffer.slice(thinkEnd + 8); // Bỏ '</think>'
                            inThink = false;
                        }
                    }
                }
            }
        }

        // Chuyển generator thành stream để trả về cho CopilotKit
        return toReadableStream(generator());
    }
});

/* 
    Endpoint của CopilotKit được mount vào /copilotkit
    Mỗi request sẽ được xử lý bởi CopilotRuntime kết hợp với serviceAdapter.
*/
app.use('/copilotkit', (req: Request, res: Response, next: NextFunction) => {
    (async () => {
        const runtime = new CopilotRuntime();
        const handler = copilotRuntimeNodeHttpEndpoint({
            endpoint: '/copilotkit',    // endpoint gốc
            runtime,                    // runtime xử lý logic CopilotKit
            serviceAdapter,             // Adapter gọi chainFn
        });

        // Gọi handler tương ứng
        return handler(req, res);
    })().catch(next);   // Bắt lỗi nếu có
});

// Khởi động server
app.listen(PORT, () => {
    console.log(`Listening at http://localhost:${PORT}/copilotkit`);
});