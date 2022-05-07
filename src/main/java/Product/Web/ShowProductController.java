package Product.Web;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Product.Product;
import Product.data.ProductRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/show")
public class ShowProductController {
	private final ProductRepository productRepo;

	@Autowired
	public ShowProductController(ProductRepository productRepository) {
		this.productRepo = productRepository;
	}

	@ModelAttribute
	public void addProductToModel(Model model) {
		List<Product> products = (List<Product>) productRepo.findAll();
		model.addAttribute("products", products);
	}

	@GetMapping()
	public String showProduct(Model model) {
		log.info("Show product");
		return "product_details";
	}

	@RequestMapping(value = "/add")
	public String addProduct(Model model) {
		model.addAttribute("product", new Product());
		return "addProduct";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editProduct(@RequestParam("code") String code, Model model) {
		Optional<Product> productX = productRepo.findById(code);
		productX.ifPresent(product -> model.addAttribute("product", product));
		return "editProduct";
	}

	@RequestMapping(value = "/preDelete", method = RequestMethod.GET)
	public String preDeleteProduct(@RequestParam("code") String code, Model model) {
		Optional<Product> productX = productRepo.findById(code);
		productX.ifPresent(product -> model.addAttribute("product", productX));
		return "confirm";
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String deleteProduct(@RequestParam("code") String code, RedirectAttributes model) {
		productRepo.deleteById(code);
		model.addFlashAttribute("success", "Xóa thành công!");
		return "redirect:/show";
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@Valid Product product, Errors errors, RedirectAttributes model) {
		if (errors.hasErrors()) {
			return "addProduct";
		} else {
			productRepo.save(product);
			model.addFlashAttribute("success", "Thêm mới thành công!");
			return "redirect:/show";
		}
	}

	@RequestMapping(value = "saveUpdate", method = RequestMethod.POST)
	public String saveUpdate(@Valid Product product, Errors errors, RedirectAttributes model) {
		if (errors.hasErrors()) {
			return "addProduct";
		} else {
			productRepo.save(product);
			model.addFlashAttribute("success", "Cập nhật thành công!");
			return "redirect:/show";
		}
	}
}
