package gm.inventarios.controller;

import gm.inventarios.exception.ResourceNotFoundException;
import gm.inventarios.model.Product;
import gm.inventarios.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
// http://localhost:8080/inventario-app
@RequestMapping("inventario-app")
@CrossOrigin(value = "http://localhost:4200")

public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    
    @Autowired
    private ProductService productService;

    //http://localhost:8080/inventario-app/products
    @GetMapping("/products")
    public List<Product> getProducts(){
        List<Product> products = this.productService.listProducts();
        logger.info("Productos Obtenidos: ");
        products.forEach((product -> logger.info(product.toString())));
        return products;
    }

    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product){
        logger.info("Product to add: " + product);
        return this.productService.saveProduct(product);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Product product = this.productService.searchProductById(id);
        if(product != null)
            return ResponseEntity.ok(product);
        else
            throw new ResourceNotFoundException("The resource was not found.");
    }

    @PutMapping("products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product productReceived) {
        Product product = this.productService.searchProductById(id);

        if(product == null)
            throw new ResourceNotFoundException("ID not found: " + id);

        product.setDescription(productReceived.getDescription());
        product.setPrice(productReceived.getPrice());
        product.setStock(productReceived.getStock());
        this.productService.saveProduct(product);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteProduct(@PathVariable int id) {
        Product product = productService.searchProductById(id);

        if(product == null)
            throw new ResourceNotFoundException("ID not found: " + id);

        this.productService.deleteProductById(product.getIdProduct());
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
