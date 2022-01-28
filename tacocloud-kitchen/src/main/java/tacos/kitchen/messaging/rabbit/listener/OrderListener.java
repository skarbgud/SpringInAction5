package tacos.kitchen.messaging.rabbit.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
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

  // JMS 처럼 RabbitMQ도 매핑된 queue 메세지 리스너이다.
  // 메세지가 큐에 도착할 때 메서드가 자동 호출되도록 지정하기 위해 @RabbitListener 애노테이션을 빈의 메서드에 지정
  // JMSListener 와 동일하게 작동해서 프로그래밍 모델 자체가 동일하다.
  @RabbitListener(queues = "tacocloud.order.queue")
  public void receiveOrder(Order order) {
    ui.displayOrder(order);
  }
  
}
