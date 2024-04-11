package gm.inventarios.service;

import gm.inventarios.model.Product;
import java.util.List;

public interface IProductService {
    public List<Product> listProducts();

    public Product searchProductById(Integer idProduct);

    public Product saveProduct(Product product);

    public void deleteProductById(Integer idProduct);
}
