package tacos.data;

import java.util.List;

import tacos.Order;

public interface OrderCustomRepository {
	List<Order> getOrderFromDeliveryCity(String deliveryCity);
}
