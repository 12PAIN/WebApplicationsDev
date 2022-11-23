package rest.Model;

import java.util.ArrayList;

import rest.Model.DTO.Product;
import rest.Model.DTO.User;

public interface IModel {
    ArrayList<Product> GenerateProductList(ArrayList<ArrayList<String>> products);
    ArrayList<String> checkUser(User user);
    Boolean addUser(User newUser);
    Integer deleteRows(ArrayList<Integer> toDelete);
    ArrayList<Product> getProductsList();
    Integer addRow(Product newProduct);
}
