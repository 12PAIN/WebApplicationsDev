package rest.model.products.out;

import java.util.ArrayList;

import rest.model.dto.Product;
import rest.model.products.in.IProductsData;

public interface IProductsModel {
    public void setDataSource(IProductsData datasource);
    Integer deleteRows(ArrayList<Integer> toDelete);
    ArrayList<Product> getProductsList();
    Product addRow(Product newProduct);
}
