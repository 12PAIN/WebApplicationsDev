package rest.Model.Products;

import java.util.ArrayList;

import rest.Model.DTO.Product;

public interface IProductsModel {
    Integer deleteRows(ArrayList<Integer> toDelete);
    ArrayList<Product> getProductsList();
    Integer addRow(Product newProduct);
}
