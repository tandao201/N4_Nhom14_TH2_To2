package Product;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@RequiredArgsConstructor
@Getter
@Setter
@Entity
public class Product {
	@Id
	@NotBlank(message = "Vui lòng nhập code!")
	private String code;
	@NotBlank(message = "Vui lòng nhập description!")
	private String description;
	@Min(value = 0, message = "Nhập lớn hơn 0!")
	private int price;
}
