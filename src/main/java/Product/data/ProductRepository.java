package Product.data;

import org.springframework.data.repository.CrudRepository;

import Product.Product;

public interface ProductRepository extends CrudRepository<Product, String> {

}
