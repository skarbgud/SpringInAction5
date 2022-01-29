package tacos.kitchen.messaging.kafka.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tacos.Order;
import tacos.kitchen.KitchenUI;


@Component
public class OrderListener {
  
  private KitchenUI ui;

  @Autowired
  public OrderListener(KitchenUI ui) {
    this.ui = ui;
  }

  // tacocloud.orders.topic 이름의 토픽에 메세지가 도착할 때 까지 자동호출되어야 한다는 것을 나타내기 위해 @KafkaListener 지정
  @KafkaListener(topics="tacocloud.orders.topic")
  public void handle(Order order) { // 페이로드인 Order 만 인자로 받는다
    ui.displayOrder(order);
  }
}
