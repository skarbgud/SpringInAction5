package tacos.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tacos.Order;

@Service
public class KafkaOrderMessagingService
                                  implements OrderMessagingService {
  
  private KafkaTemplate<String, Order> kafkaTemplate;
  
  @Autowired
  public KafkaOrderMessagingService(
          KafkaTemplate<String, Order> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  // KafakaTemplate의 send() 메서드를 사용해서 tacocloud.orders.topic 라는 이름의 토픽으로 Order 객체 전송
  @Override
  public void sendOrder(Order order) {
    kafkaTemplate.sendDefault(order);
  }
  
}
