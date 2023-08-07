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