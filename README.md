### Custom DI tool

This project aims to implement from scratch a tool that provides basic, core dependency
injection
functionality. The tool can be described as a
subset of popular dependency injection frameworks' core functionality.
Currently, the tool supports constructor dependency injection and field injection using @Wired
annotation, as well as configuration classes, that are known from Spring. Tool registers @Item
annotated
classes as components of the IOC container.
Below is a short comparison of tool functionality compared to popular frameworks:

* **@Item** = like Spring's **@Component**

```
@Item
class Person(){
private int age;
}
```

* **@Wired** = like Spring's **@Autowired** or Guice **@Inject**

```
@Item
class Person(){
private int age;
@Wired
private Address address;
}
```

* **@ConfigBeanDefinitions** = like Spring's **@Configuration**
* **@BeanDef** = like Spring's **@Bean**

```
@ConfigBeanDefinitions
class Config(){
@BeanDef
public Person getPerson(){
return new Person();
}
```

* **@Default** = like Spring's **@Primary** or Guice **@Primary**

```
@ConfigBeanDefinitions
class Config(){
@BeanDef
public Person getPerson(){
return new Person("Bob");
}

@BeanDef
@Default
public Person getPerson(){
return new Person("John");
}
```

### Web

The tool has been extended to support simple basic REST API creation.
Currently, it uses an http server from Java's standard library.
Everything is annotation-based like in popular web frameworks.
Tool handles path variables, request parameters, and request bodies. The examples below
illustrate the usage:

```

@RestController
public class PriceController {

  @Get(url = "/prices/{productId}")
  public RestResponse<Price> getPriceByProductId(@PathVariable long productId) {
    return new RestResponse<>(new Price(BigDecimal.valueOf(29.99), productId), HttpStatusCode.OK);
  }

  @Post(url = "/prices")
  public RestResponse<String> createPrice(@RequestBody Price newPrice) {
    return new RestResponse<>(newPrice.toString(), HttpStatusCode.OK);
  }

  @Put(url = "/prices/{productId}")
  public RestResponse<String> updatePrice(
      @PathVariable long productId, @RequestBody Price updatedPrice) {
    return new RestResponse<>(updatedPrice.toString(), HttpStatusCode.OK);
  }

  @Delete(url = "/prices/{productId}")
  public String deletePrice(@PathVariable() long productId) {
    return "Deleted: " + String.valueOf(productId);
  }
}

```

```

@RestController
public class UserController {

  @Get(url = "/users/{id}")
  public int getUserById(@PathVariable int id) {
    return id;
  }

  @Post(url = "/users")
  public String createUser(@RequestBody User newUser) {
    return "User created: " + newUser.name();
  }

  @Put(url = "/users/{id}")
  public String updateUser(@PathVariable int id, @RequestBody User updatedUser) {
    return "User updated: " + updatedUser.name();
  }

  @Delete(url = "/users/{id}")
  public String deleteUser(@PathVariable int id) {
    return "User deleted: " + id;
  }

  @Patch(url = "/users/{id}")
  public String partialUpdateUser(@PathVariable int id, @RequestBody Map<String, Object> updates) {
    return updates.values().toString();
  }
}

```

```

@RestController
public class ProductController {

  @Get(url = "/products")
  public RestResponse<List<Product>> getAllProducts(@RequestParam String category) {
    return new RestResponse<>(
        getProducts().stream().filter(x -> x.getCategory().equals(category)).toList(),
        HttpStatusCode.OK);
  }

  private List<Product> getProducts() {
    var list = IntStream.of(0, 20).mapToObj(x -> new Product(x, "men.jeans", "Levis")).toList();
    list.addAll(
        IntStream.of(21, 40).mapToObj(x -> new Product(x, "women.jeans", "Wrangler")).toList());
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

  @Get(url = "/products/search")
  public RestResponse<List<Product>> searchProductsByName(@RequestParam String name) {
    return new RestResponse<>(
        getProducts().stream().filter(x -> x.getName().equals(name)).toList(), HttpStatusCode.OK);
  }

  @Put(url = "/products/{productId}")
  public RestResponse<Product> updateProductProperties(
      @PathVariable long productId, @RequestParam String category, @RequestParam String name) {
    var product =
        getProducts().stream().filter(x -> x.getId() == productId).findFirst().orElseThrow();
    product.setCategory(category);
    product.setName(name);
    return new RestResponse<>(product, HttpStatusCode.OK);
  }
}

```

```
@RestController
public class CatController {

  @Wired
  CatService catService;

  @Get(url = "/cats/{id}")
  public Cat getCat(@PathVariable long id) {
    return catService.findCatById(id);
  }
}

```

```
@RestController
public class CatController {

  private CatService catService;
  
  public CatController(CatService service){
  this.catService=service;
  }

  @Get(url = "/cats/{id}")
  public Cat getCat(@PathVariable long id) {
    return this.catService.findCatById(id);
  }
}

```