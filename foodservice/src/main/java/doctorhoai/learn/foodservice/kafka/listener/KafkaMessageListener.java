package doctorhoai.learn.foodservice.kafka.listener;

import doctorhoai.learn.basedomain.kafka.order.EStatusOrder;
import doctorhoai.learn.basedomain.kafka.order.EventOrder;
import doctorhoai.learn.foodservice.kafka.model.OrderDto;
import doctorhoai.learn.foodservice.kafka.proceducer.KafkaMessageSender;
import doctorhoai.learn.foodservice.service.voucher.VoucherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageListener {

    private final KafkaMessageSender kafkaMessageSender;
    @Value("${spring.kafka.topic.order_rollback}")
    private String rollback;
    private final VoucherService voucherService;

    @Value("${spring.kafka.topic.inventory}")
    private String inventory;

    @RetryableTopic(attempts = "4", backoff = @Backoff( delay = 3000, multiplier = 1.5, maxDelay = 15000))
    @KafkaListener(topics = "food", groupId = "food")
    public void receiveOrder(EventOrder<OrderDto> eventOrder){
        try{
            String message = voucherService.updateVoucher(eventOrder.getVoucher());
            eventOrder.setMessage(message);
            if(message.isEmpty() ){
                System.out.println("Sending to inventory...");
                kafkaMessageSender.sendRollBackOrder(eventOrder, inventory);
                return;
            }
            System.out.println("Roll back...");
            eventOrder.setStatus(EStatusOrder.ROLL_BACK);
            eventOrder.setMessage("Voucher not found");
            kafkaMessageSender.sendRollBackOrder(eventOrder, rollback);
        } catch ( Exception e){
            System.out.println("Roll back...");
            eventOrder.setStatus(EStatusOrder.ROLL_BACK);
            eventOrder.setMessage(e.getMessage());
            kafkaMessageSender.sendRollBackOrder(eventOrder, rollback);
        }
    }

    @RetryableTopic(attempts = "4", backoff = @Backoff( delay = 3000, multiplier = 1.5, maxDelay = 15000))
    @KafkaListener(topics = "food_rollback", groupId = "food_rollback")
    public void receiveRollback(EventOrder<OrderDto> eventOrder){
        try{
            System.out.println("Roll back...");
            String message = voucherService.updateRollbackVoucher(eventOrder.getVoucher());
            eventOrder.setMessage(message);
            kafkaMessageSender.sendRollBackOrder(eventOrder, rollback);
        } catch ( Exception e){
            System.out.println("Roll back...");
            eventOrder.setStatus(EStatusOrder.ROLL_BACK);
            eventOrder.setMessage(e.getMessage());
            kafkaMessageSender.sendRollBackOrder(eventOrder, rollback);
        }
    }

    @DltHandler
    public void listenDLT(EventOrder eventOrder) {
        System.out.println(eventOrder.toString());
    }
}
