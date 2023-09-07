package org.custom.appcontext.testobj.web.allcontrollers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.custom.appcontext.testobj.web.Product;
import org.custom.web.annotations.Delete;
import org.custom.web.annotations.Get;
import org.custom.web.annotations.PathVariable;
import org.custom.web.annotations.Post;
import org.custom.web.annotations.Put;
import org.custom.web.annotations.RequestBody;
import org.custom.web.annotations.RequestParam;
import org.custom.web.annotations.RestController;
import org.custom.web.model.HttpStatusCode;
import org.custom.web.model.RestResponse;

@RestController
public class ProductController {

  @Get(url = "/products")
  public RestResponse<List<Product>> getAllProducts(@RequestParam String category) {
    return new RestResponse<>(
        getProducts().stream().filter(x -> x.getCategory().equals(category)).toList(),
        HttpStatusCode.OK);
  }

  private List<Product> getProducts() {
    var list = IntStream.rangeClosed(0, 20).mapToObj(x -> new Product(x, "men.jeans", "Levis"))
        .collect(
            Collectors.toList());
    list.addAll(
        IntStream.rangeClosed(21, 40).mapToObj(x -> new Product(x, "women.jeans", "Wrangler"))
            .toList());
    return list;
  }

  @Get(url = "/products/{productId}")
  public RestResponse<Product> getProductById(@PathVariable long productId) {
    return new RestResponse<>(
        getProducts().stream().filter(x -> x.getId() == productId).findFirst().orElseThrow(),
        HttpStatusCode.OK);
  }

  @Post(url = "/products")
  public RestResponse<Product> createProduct(@RequestBody Product newProduct) {
    return new RestResponse<>(newProduct, HttpStatusCode.CREATED);
  }

  @Put(url = "/products/{productId}")
  public Product updateProduct(@PathVariable long productId, @RequestBody Product updatedProduct) {
    return updatedProduct;
  }

  @Delete(url = "/products/{productId}")
  public RestResponse<String> deleteProduct(@PathVariable long productId) {
    return new RestResponse<>("Deleted: " + productId, HttpStatusCode.OK);
  }

  @Get(url = "/products/name")
  public RestResponse<List<Product>> searchProductsByName(@RequestParam String name) {
    return new RestResponse<>(
        getProducts().stream().filter(x -> x.getName().equals(name)).toList(), HttpStatusCode.OK);
  }

  @Put(url = "/products/{productId}")
  public RestResponse<Product> updateProductProperties(
      @PathVariable long productId, @RequestParam String category, @RequestParam String name,
      @RequestBody Product product) {

    var productToUpdate =
        getProducts().stream().filter(
            x -> x.getId() == productId && x.getCategory().equals(category) && x.getName()
                .equals(name)).findFirst().orElseThrow();
    productToUpdate.setCategory(product.getCategory());
    productToUpdate.setName(product.getName());
    return new RestResponse<>(product, HttpStatusCode.OK);
  }
}
