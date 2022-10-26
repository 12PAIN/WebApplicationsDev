package rest.Model;

import java.util.ArrayList;

public class Product {
    private int id;
    private int price;
    private String description;
    private String name;

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public static ArrayList<Product> GenerateProductList(ArrayList<ArrayList<String>> products){
        ArrayList<Product> productList = new ArrayList<>();

        for(ArrayList<String> product: products){
            Product newProduct = new Product();
            newProduct.setId(Integer.parseInt(product.get(0))); 
            newProduct.setName(product.get(1)); 
            newProduct.setPrice(Integer.parseInt(product.get(2))); 
            newProduct.setDescription(product.get(3)); 

            productList.add(newProduct);
        }

        return productList;
    }

    public static Product createProduct(int id, String name, int price, String description){
        Product newProduct = new Product();

        newProduct.setDescription(description);
        newProduct.setId(id);
        newProduct.setName(name);
        newProduct.setPrice(price);

        return newProduct;
    }

}
