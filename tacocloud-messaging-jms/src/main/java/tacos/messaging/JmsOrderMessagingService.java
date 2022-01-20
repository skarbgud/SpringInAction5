package tacos.messaging;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Service;

import sun.security.krb5.internal.crypto.Des;
import tacos.Order;

import java.util.HashMap;
import java.util.Map;

@Service
public class JmsOrderMessagingService implements OrderMessagingService {

  private JmsTemplate jms;

//  @Bean
//  public Destination orderQueue() {
//    return new ActiveMQQueue("tacocloud.order.queue");
//  }

  public MappingJackson2MessageConverter messageConverter() {
    MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
    // 이 메세지 변환기의 인스턴스를 반환. 수신된 메세지의 변환 타입을 메세지 수신자가 알아야 하기 때문에 이 부분이 중요하다.
    messageConverter.setTypeIdPropertyName("_typeId");
    // 유연성을 높이기 위해 setTypeIdMappings를 통해 실제 타입에 임의의 타입 이름을 매핑시킬 수 있다.
    // 아래 코드에서는 order라는 타입Id로 Order클래스를 order라는 타입 ID로 매핑 하도록 변환기 빈을 변경
    Map<String, Class<?>> typeIdMappings = new HashMap<>();
    typeIdMappings.put("order", Order.class);
    messageConverter.setTypeIdMappings(typeIdMappings);
    
    return messageConverter;
  }

//  private Destination orderQueue;

  @Autowired
  public JmsOrderMessagingService(JmsTemplate jms) {
    this.jms = jms;
//    this.orderQueue = orderQueue;
  }

  @Override
  public void sendOrder(Order order) {
//    jms.send(orderQueue, session -> session.createObjectMessage(order));
    // 실제로는 도착지 이름 외에 다른것을 지정하는 일이 거의 없으므로 Destination 객체 대신 도착지 이름만 지정하는 것이 더 쉽다.
    // 두번째 인자에는 MessageCreator 객체[람다를 사용]
    /* 람다를 쓰지 않은 코드
    jms.send("tacocloud.order.queue", new MessageCreator() {
              @Override
              public Message createMessage(Session session) throws JMSException {
                return session.createObjectMessage(order);
              }
            }
    );
     */
//    jms.send("tacocloud.order.queue", session -> session.createObjectMessage(order));
    // 메세지 변환하고 전송하기(p.267) -> MessageCreator를 제공하지 않아도 되므로 메세지 전송이 더 간단해진다. (Order 객체는 Message 객체로 변한된 후 전송된다.)
    // MessageConverter 인터페이스 -> [SimpleMessageConverter 클래스]
    // 하지만 이 경우 전송될 객체가 Serializable(직렬화) 인터페이스를 구현하는 것이어야 한다. (이를 피하기 위해 MappingJackson2MessageConverter와 같은 다른 메세지 변환기를 사용할 수도 있다.) [p.268 하단 참조]
    jms.convertAndSend("tacocloud.order.queue", messageConverter());
  }
  
  private Message addOrderSource(Message message) throws JMSException {
    message.setStringProperty("X_ORDER_SOURCE", "WEB");
    return message;
  }

}
