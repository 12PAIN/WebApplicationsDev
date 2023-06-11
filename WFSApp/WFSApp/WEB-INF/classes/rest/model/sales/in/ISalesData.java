package rest.model.sales.in;

import rest.model.dto.Sale;
import java.util.ArrayList;

public interface ISalesData {
    public Sale addSale(Sale sale);
    public ArrayList<Sale> getSales();
}

/* 
package rest.model.products.in;

import java.util.ArrayList;

import rest.model.dto.Product;

public interface IProductsData {
    public Product addRow(Product product);
    public Integer deleteRows(ArrayList<Integer> toDeleteIds);
    public ArrayList<Product> getProductsList(); 
}
*/