package tacos.data;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.JPQLQuery;

import tacos.Order;
import tacos.QOrder;

@Repository
public class OrderCustomRepositoryImpl extends QuerydslRepositorySupport implements OrderCustomRepository{
    public OrderCustomRepositoryImpl(){
        super(Order.class);
    }

    @Override
    public List<Order> getOrderFromDeliveryCity(String deliveryCity) {

        JPQLQuery<Order> query =
                from(QOrder.order)
                        .select(
                                QOrder.order
                        )
                        .where(QOrder.order.deliveryCity.eq(deliveryCity));

        /*JPQLQuery<DeliveryInfo> query =
                from(QOrder.order)
                        .select(
                                new QDeliveryInfo(
                                        QOrder.order.deliveryName,
                                        QOrder.order.deliveryStreet,
                                        QOrder.order.deliveryState)
                        )
                        .where(QOrder.order.deliveryCity.eq(deliveryCity));*/

       return query.fetch();
    }
}