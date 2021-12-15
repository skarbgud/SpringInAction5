package tacos;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Order {
	
	private Long id;
	private Date placedAt;

	/*
	 * 
	 * @NotEmpty : null 과 "" 둘 다 허용하지 않게 합니다. @NotNull 에서 "" validation 이 추가된 것입니다.		
	   즉, @NotEmpty 는 null 과 "" 은 막히되, " " 은 허용이 됩니다.
	   
	 * @NotBlank 는 null 과 "" 과 " " 모두 허용하지 않습니다.
	   @NotEmpty 에서 " " validation 이 추가된 것입니다.
	   즉, 세개 중 가장 validation 강도가 높은 것으로,@NotBlank 는 null 과 "" 과 " " 모두 허용하지 않습니다.
	   
	 */
	@NotBlank(message = "Name is required")
	private String deliveryName;
	
	@NotBlank(message = "Street is required")
	private String deliveryStreet;
	
	@NotBlank(message = "City is required")
	private String deliveryCity;
	
	@NotBlank(message = "State is required")
	private String deliveryState;
	
	@NotBlank(message = "Zip code is required")
	private String deliveryzip;
	
	// 값이 있는지 확인, 입력 값이 유효한 신용 카드 번호인지 확인
//	@CreditCardNumber(message = "Not a valid credit card number")
	private String ccNumber;
	
	// MM/YY(두자리 수의 월과 연도 정규 표현식) 
	@Pattern(regexp = "^(0[1-9]|1[0-2])([\\\\/])([1-9][0-9])$",
			message = "Must be formatted MM/YY")
	private String ccExpiration;
	
	// 대상 수가 지정된 정수와 소수 자리수보다 적을 경우 통과 가능 (정수3, 소수0 자리)
	@Digits(integer = 3, fraction = 0, message = "Invalid CVV")
	@NotBlank(message = "Name is required")
	private String ccCVV;
	
	private List<Taco> tacos = new ArrayList<>();

	public void addDesign(Taco design) {
		this.tacos.add(design);
		
	}
}
