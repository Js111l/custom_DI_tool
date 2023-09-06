package org.custom.appcontext.testobj.web;

public class Product {
  private int id;
  private String category;
  private String name;
  public Product(int id, String category,String name){
    this.category=category;
    this.name=name;
    this.id=id;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setId(int id) {
    this.id = id;
  }
}
