package rest.model.products.in;

import java.util.ArrayList;

import rest.model.dto.Product;

public interface IProductsData {
    public Product addRow(Product product);
    public Integer deleteRows(ArrayList<Integer> toDeleteIds);
    public ArrayList<Product> getProductsList(); 
}
