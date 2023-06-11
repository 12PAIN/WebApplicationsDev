package rest.model.sales;

import java.util.ArrayList;

import rest.model.dto.Sale;
import rest.model.sales.in.ISalesData;
import rest.model.sales.out.ISalesModel;

public class SalesModel implements ISalesModel{

    ISalesData datasource;

    @Override
    public void setDataSource(ISalesData datasource) {
        this.datasource = datasource;
    }

    @Override
    public ArrayList<Sale> getSales() {
        return datasource.getSales();
    }

    @Override
    public Sale addSale(Sale sale) {
        return datasource.addSale(sale);
    }
    
}