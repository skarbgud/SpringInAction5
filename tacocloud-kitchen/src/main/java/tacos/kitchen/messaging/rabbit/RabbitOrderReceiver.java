package tacos.kitchen.messaging.rabbit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tacos.Order;
import tacos.kitchen.OrderReceiver;

@Component
public class RabbitOrderReceiver {

  private RabbitTemplate rabbit;
  private MessageConverter converter;

  @Autowired
  public RabbitOrderReceiver(RabbitTemplate rabbit) {
    this.rabbit = rabbit;
    this.converter = rabbit.getMessageConverter();
  }
  
  public Order receiveOrder() {
    // timeoutMillis 에 대한 값을 인자로 전달하지 않았으므로 바로 Message 객체 또는 null 값 반환 => 두번째 인자에 값을 넣으면 해당 시간동안 기다린다(ex> 30000밀리초(30초) 대기)
    Message message = rabbit.receive("tacocloud.orders", 30000);
    // 메세지가 반환이 되면 Order 객체로 캐스팅 : null 일 경우 null 값 반환
    return message != null ? (Order) converter.fromMessage(message) : null;
  }
}
