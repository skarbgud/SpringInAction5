package tacos.kitchen.messaging.jms.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
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

  /*
    @JmsListener는 @GetMapping이나 @PostMapping처럼 MVC 요청 매핑 애노테이션과 유사하다.
    @JmsListener가 지정된 메서드들은 지정된 도착지에 들어오는 메세지에 반응한다.
    메세지 리스너는 중단 없이 다수의 메세지를 빠르게 처리 할 수 있지만 빠르고 계속해서 메세지가 들어오게 되면 병목 현상이 생길 수 있기 때문에 JmsTemplate가 제공하는 풀모델(pull model)이 더 적합하다.
   */
  @JmsListener(destination = "tacocloud.order.queue")
  public void receiveOrder(Order order) { // tacocloud.order.queue의 도착 메세지를 '리스닝'하기 위해 메세지가 도착할 때까지 기다렸다가 도착하면 메세지 인자로 전달되며 receiveOrder메서드가 자동 호출된다.
    ui.displayOrder(order);
  }
  
}
