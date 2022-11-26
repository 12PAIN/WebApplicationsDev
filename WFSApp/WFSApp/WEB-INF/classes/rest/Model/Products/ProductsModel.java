package rest.Model.Products;

import java.util.ArrayList;

import rest.Model.DTO.Product;
import rest.Repository.Products.IProductsData;

//TEMPORARY

public class ProductsModel implements IProductsModel{


    IProductsData datasource;

    public void setDataSource(IProductsData datasource){
        this.datasource = datasource;
    }

    @Override
    public Integer addRow(Product newProduct){

        return null;
    };


    @Override
    public Integer deleteRows(ArrayList<Integer> toDelete){

        return null;
    };

    @Override
    public ArrayList<Product> getProductsList(){


        return null;
    };

}
