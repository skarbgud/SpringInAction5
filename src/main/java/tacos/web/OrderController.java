package tacos.web;

import javax.validation.Valid;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import lombok.extern.slf4j.Slf4j;
import tacos.Order;
import tacos.User;
import tacos.data.OrderRepository;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
//@ConfigurationProperties(prefix = "taco.orders") // pageSize 구성 속성 값을 설정할때는 taco.orders.pageSize라는 이름을 사용해야 한다.
public class OrderController {
	
//	private int pageSize = 20;
	
//	public void setPageSize(int pageSize) {
//		this.pageSize = pageSize;
//	}
	
	private OrderProps props;

	private OrderRepository orderRepo;
	
	public OrderController(OrderRepository orderRepo, OrderProps props) {
		this.orderRepo = orderRepo;
		this.props = props;
	}
	
	@GetMapping("/current")
	public String orderForm(@AuthenticationPrincipal User user, @ModelAttribute Order order) {
		
		if (order.getDeliveryName() == null) {
			order.setDeliveryName(user.getFullname());
		}
		if (order.getDeliveryStreet() == null) {
			order.setDeliveryStreet(user.getStreet());
		}
		if (order.getDeliveryCity() == null) {
			order.setDeliveryCity(user.getCity());
		}
		if (order.getDeliveryState() == null) {
			order.setDeliveryState(user.getState());
		}
		if (order.getDeliveryzip() == null) {
			order.setDeliveryzip(user.getZip());
		}
		
//		model.addAttribute("order", new Order());
		return "orderForm";
	}
	
	@PostMapping
	public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus, @AuthenticationPrincipal User user) {
		if (errors.hasErrors()) {
			return "orderForm";
		}
//		log.info("Order submitted: " + order);
		
		order.setUser(user);
		
		orderRepo.save(order);
		sessionStatus.setComplete();
		return "redirect:/";
	}
	
	@GetMapping
	public String ordersForUser(@AuthenticationPrincipal User user, Model model) {
//		model.addAttribute("orders", orderRepo.findByUserOrderByPlacedAtDesc(user));
		
		// 최근 20개 주문만 페이징 처리
		Pageable pageable = PageRequest.of(0, props.getPageSize());
		model.addAttribute("orders", orderRepo.findByUserOrderByPlacedAtDesc(user, pageable));
		
		return "orderList";
	}
}
