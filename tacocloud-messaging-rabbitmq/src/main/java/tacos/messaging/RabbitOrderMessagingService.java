package tacos.messaging;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tacos.Order;

@Service
public class RabbitOrderMessagingService
       implements OrderMessagingService {
  
  private RabbitTemplate rabbit;
  
  @Autowired
  public RabbitOrderMessagingService(RabbitTemplate rabbit) {
    this.rabbit = rabbit;
  }
  
  public void sendOrder(Order order) {
      MessageConverter converter = rabbit.getMessageConverter();
      MessageProperties props = new MessageProperties();
      // MessageConverter 로 Order 객체를 Message 객체로 변환하여 send()로 전송
      Message message = converter.toMessage(order, props);
      // 라우팅 키 => tacocloud.order [기본 거래소 사용]
      rabbit.send("tacocloud.order", message);
  }
}
