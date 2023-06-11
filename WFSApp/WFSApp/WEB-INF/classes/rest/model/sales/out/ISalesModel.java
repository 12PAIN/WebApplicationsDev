package rest.model.sales.out;

import rest.model.sales.in.ISalesData;
import java.util.ArrayList;
import rest.model.dto.Sale;

public interface ISalesModel {
    public void setDataSource(ISalesData datasource);
    ArrayList<Sale> getSales();
    Sale addSale(Sale sale);
}

/*

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
 */