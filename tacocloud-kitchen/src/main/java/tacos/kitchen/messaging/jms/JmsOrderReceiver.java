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
    private MessageConverter converter;

    @Autowired
    public JmsOrderReceiver(JmsTemplate jms, MessageConverter converter) {
        this.jms = jms;
        this.converter = converter;
    }

    @Override
    public Order receiveOrder() throws JMSException {
        /* 주문 데이터를 가져올 도착지를 문자열로 지정
           receive() 메서드는 변환되지 않은 메세지를 반환한다.
           메세지 변환기를 사용하여 receive 메서드가 반환한 수신 메세지를 Order 객체로 변환한다. (Order로 캐스팅)
         */
        Message message = jms.receive("tacocloud.order.queue");
        return (Order) converter.fromMessage(message);
    }
}
