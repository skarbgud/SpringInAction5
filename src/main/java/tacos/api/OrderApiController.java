package tacos.api;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tacos.Order;
import tacos.data.OrderRepository;

@RestController
@RequestMapping(path = "/ordres", produces = "application/json")
@CrossOrigin("*")
@RequiredArgsConstructor
public class OrderApiController {

	private final OrderRepository orderRepository;
	
	@GetMapping(produces = "application/json")
	public Iterable<Order> allOrders() {
		return orderRepository.findAll();
	}
	
	@PostMapping(consumes = "application/json")
	public Order postOrder(@RequestBody Order order) {
		return orderRepository.save(order);
	}
	
	@PatchMapping(path = "/{/orderId}", consumes = "application/json")
	public Order patchOrder(@PathVariable("orderId") Long orderId, @RequestBody Order patch) {
		
		Order order = orderRepository.findById(orderId).get();
		if (patch.getDeliveryName() != null) {
            order.setDeliveryName(patch.getDeliveryName());
        }
        if (patch.getDeliveryStreet() != null) {
            order.setDeliveryStreet(patch.getDeliveryStreet());
        }
        if (patch.getDeliveryCity() != null) {
            order.setDeliveryCity(patch.getDeliveryCity());
        }
        if (patch.getDeliveryState() != null) {
            order.setDeliveryState(patch.getDeliveryState());
        }
        if (patch.getDeliveryZip() != null) {
            order.setDeliveryZip(patch.getDeliveryState());
        }
        if (patch.getCcNumber() != null) {
            order.setCcNumber(patch.getCcNumber());
        }
        if (patch.getCcExpiration() != null) {
            order.setCcExpiration(patch.getCcExpiration());
        }
        if (patch.getCcCVV() != null) {
            order.setCcCVV(patch.getCcCVV());
        }
		return orderRepository.save(order);
	}
	
	@DeleteMapping("/{orderId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteOrder(@PathVariable("orderId") Long orderId) {
		try {
			orderRepository.deleteById(orderId);
		} catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
		}
	}
}