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
import org.springframework.stereotype.Service;

import sun.security.krb5.internal.crypto.Des;
import tacos.Order;

@Service
public class JmsOrderMessagingService implements OrderMessagingService {

  private JmsTemplate jms;

//  @Bean
//  public Destination orderQueue() {
//    return new ActiveMQQueue("tacocloud.order.queue");
//  }

  private Destination orderQueue;

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
    jms.send("tacocloud.order.queue", session -> session.createObjectMessage(order));
  }
  
  private Message addOrderSource(Message message) throws JMSException {
    message.setStringProperty("X_ORDER_SOURCE", "WEB");
    return message;
  }

}
