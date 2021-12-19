package tacos;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tacos.data.OrderCustomRepository;

@SpringBootTest
class TacoCloudApplicationTests {
	
	@Autowired
	private OrderCustomRepository orderRepo;
	
		
	@Test
	public void testQuerydsl() {
		
		List<Order> orders = orderRepo.getOrderFromDeliveryCity("seoul");
		
		if(orders.size() != 0)
		{
			System.out.println("없음");
		}
		else {
			for (Order order : orders)
			{
				System.out.println(order.getId());
				System.out.println(order.getDeliveryName());
				System.out.println(order.getDeliveryCity());
			}
		}
	}

}
