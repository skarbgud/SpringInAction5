package tacos.kitchen.messaging.jms;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import tacos.Order;
import tacos.kitchen.OrderReceiver;

@Component
public class JmsOrderReceiver implements OrderReceiver {

    private JmsTemplate jms;
//    private MessageConverter converter;

    @Autowired
    public JmsOrderReceiver(JmsTemplate jms, MessageConverter converter) {
        this.jms = jms;
//        this.converter = converter;
    }

    @Override
    public Order receiveOrder() throws JMSException {
        /* 주문 데이터를 가져올 도착지를 문자열로 지정
           receive() 메서드는 변환되지 않은 메세지를 반환한다.
           메세지 변환기를 사용하여 receive 메서드가 반환한 수신 메세지를 Order 객체로 변환한다. (Order로 캐스팅)
         */
//        Message message = jms.receive("tacocloud.order.queue");
//        return (Order) converter.fromMessage(message);
        /*
            메세지의 속성과 헤더를 살펴볼때는 Message 객체 자체로 수신을 하지만 메타데이터가 필요없고 메세지에 적재된 순수한 데이터만 필요할때는
            receiveAndConvert()를 사용하는게 더 간단하다. (모든 메세지 변환이 내부적으로 receiveAndConvert안에서 수행되기 때문에 가능하다)
         */
        return (Order) jms.receiveAndConvert("tacocloud.order.queue");
    }
}
