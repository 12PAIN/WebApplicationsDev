package rest.model.products;

import java.util.ArrayList;

import rest.model.dto.Product;
import rest.model.products.in.IProductsData;
import rest.model.products.out.IProductsModel;

//TEMPORARY

public class ProductsModel implements IProductsModel{


    IProductsData datasource;

    @Override
    public void setDataSource(IProductsData datasource){
        this.datasource = datasource;
    }

    @Override
    public Product addRow(Product newProduct){
        return datasource.addRow(newProduct);
    };


    @Override
    public Integer deleteRows(ArrayList<Integer> toDelete){
        return datasource.deleteRows(toDelete);
    };

    @Override
    public ArrayList<Product> getProductsList(){
        return datasource.getProductsList();
    };

}
