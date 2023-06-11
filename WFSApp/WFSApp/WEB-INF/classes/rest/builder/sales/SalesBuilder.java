package rest.builder.sales;

import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import rest.builder.Built;
import rest.model.sales.in.ISalesData;
import rest.model.sales.out.ISalesModel;

public class SalesBuilder {
    
    @Inject @Default
    private ISalesModel salesModel;
    
    @Inject @Default
    private ISalesData salesRepos;

    @Produces @Built
    public ISalesModel buildModel(){
        salesModel.setDataSource(salesRepos);
        return salesModel;
    }
}