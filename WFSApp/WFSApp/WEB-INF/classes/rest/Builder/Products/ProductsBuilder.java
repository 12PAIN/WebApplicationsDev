package rest.builder.products;

import rest.builder.Built;
import rest.model.products.in.IProductsData;
import rest.model.products.out.IProductsModel;
import jakarta.inject.Inject;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.Default;

public class ProductsBuilder {
    
    @Inject @Default
    private IProductsModel productsModel;

    @Inject @Default
    private IProductsData productsRepos;

    @Produces @Built
    public IProductsModel buildModel(){
        productsModel.setDataSource(productsRepos);
        return productsModel;
    }

}
