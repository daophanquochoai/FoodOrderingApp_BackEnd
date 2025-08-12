"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __await = (this && this.__await) || function (v) { return this instanceof __await ? (this.v = v, this) : new __await(v); }
var __asyncValues = (this && this.__asyncValues) || function (o) {
    if (!Symbol.asyncIterator) throw new TypeError("Symbol.asyncIterator is not defined.");
    var m = o[Symbol.asyncIterator], i;
    return m ? m.call(o) : (o = typeof __values === "function" ? __values(o) : o[Symbol.iterator](), i = {}, verb("next"), verb("throw"), verb("return"), i[Symbol.asyncIterator] = function () { return this; }, i);
    function verb(n) { i[n] = o[n] && function (v) { return new Promise(function (resolve, reject) { v = o[n](v), settle(resolve, reject, v.done, v.value); }); }; }
    function settle(resolve, reject, d, v) { Promise.resolve(v).then(function(v) { resolve({ value: v, done: d }); }, reject); }
};
var __asyncGenerator = (this && this.__asyncGenerator) || function (thisArg, _arguments, generator) {
    if (!Symbol.asyncIterator) throw new TypeError("Symbol.asyncIterator is not defined.");
    var g = generator.apply(thisArg, _arguments || []), i, q = [];
    return i = Object.create((typeof AsyncIterator === "function" ? AsyncIterator : Object).prototype), verb("next"), verb("throw"), verb("return", awaitReturn), i[Symbol.asyncIterator] = function () { return this; }, i;
    function awaitReturn(f) { return function (v) { return Promise.resolve(v).then(f, reject); }; }
    function verb(n, f) { if (g[n]) { i[n] = function (v) { return new Promise(function (a, b) { q.push([n, v, a, b]) > 1 || resume(n, v); }); }; if (f) i[n] = f(i[n]); } }
    function resume(n, v) { try { step(g[n](v)); } catch (e) { settle(q[0][3], e); } }
    function step(r) { r.value instanceof __await ? Promise.resolve(r.value.v).then(fulfill, reject) : settle(q[0][2], r); }
    function fulfill(value) { resume("next", value); }
    function reject(value) { resume("throw", value); }
    function settle(f, v) { if (f(v), q.shift(), q.length) resume(q[0][0], q[0][1]); }
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
const runtime_1 = require("@copilotkit/runtime");
const dotenv_1 = __importDefault(require("dotenv"));
const node_fetch_1 = __importDefault(require("node-fetch"));
const messages_1 = require("@langchain/core/messages");
dotenv_1.default.config();
const app = (0, express_1.default)();
const PORT = process.env.PORT;
const BASE_URL = process.env.BASE_URL;
/*
    Chuyển một AsyncGenerator<T> thành một ReadableStream tương thích với LangChain
    vì LangChain yêu cầu vừa là ReadableStream vừa là AsyncIterable.
*/
function toReadableStream(source) {
    const stream = new ReadableStream({
        pull(controller) {
            return __awaiter(this, void 0, void 0, function* () {
                // Đọc từng phần tử từ generator và đẩy vào stream
                const { done, value } = yield source.next();
                if (done) {
                    controller.close(); // Kết thúc stream
                }
                else {
                    controller.enqueue(value); // Thêm chunk vào stream
                }
            });
        },
        cancel() {
            var _a;
            // Khi stream bị huỷ, đảm bảo đóng generator
            (_a = source.return) === null || _a === void 0 ? void 0 : _a.call(source, undefined);
        }
    });
    // Ép kiểu thủ công để stream cũng là AsyncIterable
    stream[Symbol.asyncIterator] = () => source;
    return stream;
}
/*
    Adapter kết nối giữa CopilotKit và LangChain.
    Trả về luồng dữ liệu dưới dạng stream các AIMessageChunk.
*/
const serviceAdapter = new runtime_1.LangChainAdapter({
    // Nhận vào các messages và tools từ CopilotKit
    chainFn: (_a) => __awaiter(void 0, [_a], void 0, function* ({ messages, tools }) {
        /*
            Generator trả về từng AIMessageChunk, tương ứng với từng dòng/chunk
            dữ liệu từ response của RAG API
        */
        function generator() {
            return __asyncGenerator(this, arguments, function* generator_1() {
                var _a, e_1, _b, _c;
                var _d;
                // Lấy message cuối cùng mà user nhập vào
                const lastMessage = (_d = messages[messages.length - 1]) === null || _d === void 0 ? void 0 : _d.content;
                const url = `${BASE_URL}?message=${lastMessage}`;
                // Gọi API RAG
                const response = yield __await((0, node_fetch_1.default)(url));
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
                let buffer = ''; // Dùng để lưu trữ tạm thời chuỗi phản hồi nhận được từ các chunk
                let inThink = false; // Cờ đánh dấu đang ở trong đoạn <think>...</think>
                try {
                    for (var _e = true, _f = __asyncValues(reader), _g; _g = yield __await(_f.next()), _a = _g.done, !_a; _e = true) {
                        _c = _g.value;
                        _e = false;
                        const chunk = _c;
                        const text = chunk.toString('utf-8');
                        buffer += text;
                        while (buffer.length > 0) {
                            if (!inThink) {
                                const thinkStart = buffer.indexOf('<think>');
                                if (thinkStart === -1) {
                                    // Không có <think>, gửi toàn bộ buffer
                                    if (buffer.trim()) {
                                        // Mỗi chunk sẽ được đóng gói vào AIMessageChunk và yield
                                        yield yield __await(new messages_1.AIMessageChunk({ content: buffer }));
                                    }
                                    buffer = '';
                                }
                                else if (thinkStart > 0) {
                                    // Có <think> ở giữa, gửi phần trước <think>
                                    const sendPart = buffer.slice(0, thinkStart);
                                    if (sendPart.trim()) {
                                        yield yield __await(new messages_1.AIMessageChunk({ content: sendPart }));
                                    }
                                    buffer = buffer.slice(thinkStart);
                                }
                                else {
                                    // <think> ở đầu buffer
                                    inThink = true;
                                    buffer = buffer.slice(7); // Bỏ '<think>'
                                }
                            }
                            else {
                                const thinkEnd = buffer.indexOf('</think>');
                                if (thinkEnd === -1) {
                                    // Chưa kết thúc <think>, tiếp tục đợi chunk tiếp theo
                                    buffer = ''; // Xoá buffer để chờ thêm chunk mới
                                    break;
                                }
                                else {
                                    // Đã kết thúc </think>, bỏ qua nội dung trong <think>
                                    buffer = buffer.slice(thinkEnd + 8); // Bỏ '</think>'
                                    inThink = false;
                                }
                            }
                        }
                    }
                }
                catch (e_1_1) { e_1 = { error: e_1_1 }; }
                finally {
                    try {
                        if (!_e && !_a && (_b = _f.return)) yield __await(_b.call(_f));
                    }
                    finally { if (e_1) throw e_1.error; }
                }
            });
        }
        // Chuyển generator thành stream để trả về cho CopilotKit
        return toReadableStream(generator());
    })
});
/*
    Endpoint của CopilotKit được mount vào /copilotkit
    Mỗi request sẽ được xử lý bởi CopilotRuntime kết hợp với serviceAdapter.
*/
app.use('/copilotkit', (req, res, next) => {
    (() => __awaiter(void 0, void 0, void 0, function* () {
        const runtime = new runtime_1.CopilotRuntime();
        const handler = (0, runtime_1.copilotRuntimeNodeHttpEndpoint)({
            endpoint: '/copilotkit', // endpoint gốc
            runtime, // runtime xử lý logic CopilotKit
            serviceAdapter, // Adapter gọi chainFn
        });
        // Gọi handler tương ứng
        return handler(req, res);
    }))().catch(next); // Bắt lỗi nếu có
});
// Khởi động server
app.listen(PORT, () => {
    console.log(`Listening at http://localhost:${PORT}/copilotkit`);
});
