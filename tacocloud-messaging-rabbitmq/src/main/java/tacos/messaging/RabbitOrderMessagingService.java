package tacos.messaging;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import tacos.Order;

@Service
public class RabbitOrderMessagingService
       implements OrderMessagingService {

  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  private RabbitTemplate rabbit;
  
  @Autowired
  public RabbitOrderMessagingService(RabbitTemplate rabbit) {
    this.rabbit = rabbit;
  }
  
  public void sendOrder(Order order) {
    // convertAndSend 를 사용할 경우 MessageProperties 객체를 직접 사용 할 수 없으므로 MessagePostProcessor 를 사용해야한다.
    rabbit.convertAndSend("tacocloud.order.queue", order,
            new MessagePostProcessor() {  // MessagePostProcessor 를  구현한 내부 클래스 인스턴스를 convertAndSend 인자로 전달
              @Override
              public Message postProcessMessage(Message message) throws AmqpException {
                // Message 객체의 MessageProperties 를 가져온 후 setHeader()를 호출하여 X_ORDER_SOURCE 헤더를 설정한다.
                MessageProperties props = message.getMessageProperties();
                props.setHeader("X_ORDER_SOURCE", "WEB");
                return message;
              }
            });
  }
}
