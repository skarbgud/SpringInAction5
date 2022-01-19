package tacos.messaging;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import sun.security.krb5.internal.crypto.Des;
import tacos.Order;

@Service
public class JmsOrderMessagingService implements OrderMessagingService {

  private JmsTemplate jms;

  @Bean
  public Destination orderQueue() {
    return new ActiveMQQueue("tacocloud.order.queue");
  }

  private Destination orderQueue;

  @Autowired
  public JmsOrderMessagingService(JmsTemplate jms, Destination orderQueue) {
    this.jms = jms;
    this.orderQueue = orderQueue;
  }

  @Override
  public void sendOrder(Order order) {
    jms.send(orderQueue, session -> session.createObjectMessage(order));
  }
  
  private Message addOrderSource(Message message) throws JMSException {
    message.setStringProperty("X_ORDER_SOURCE", "WEB");
    return message;
  }

}
