package rest.repository.sales;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Resource;
import jakarta.persistence.*;

import jakarta.transaction.*;
import rest.model.dto.Sale;
import rest.model.sales.in.ISalesData;
import rest.repository.entities.products.EProduct;
import rest.repository.entities.sales.ESales;

public class SalesData implements ISalesData{

    @PersistenceUnit(unitName = "test-resource_PersistenceUnit")
    private EntityManagerFactory entityManagerFactory;
   
    @Resource
    private UserTransaction userTransaction;

    @Override
    public Sale addSale(Sale sale) {
        EntityManager entityManager;

        try{

            entityManager = entityManagerFactory.createEntityManager();

            userTransaction.begin();
            entityManager.joinTransaction();

            ESales newESale = new ESales(sale);

            List<EProduct> eProducts = entityManager.createQuery("SELECT p FROM EProduct p WHERE p.id=\'" + sale.getProductId().getId() + "\'", EProduct.class).getResultList();

            newESale.setProductId(eProducts.get(0));
            eProducts.get(0).setSaled(1);
            entityManager.persist(newESale);

            userTransaction.commit();

            return newESale.castToSale();

        }
        catch(Exception ex){
            return null;
        }
    }

    @Override
    public ArrayList<Sale> getSales() {
        EntityManager entityManager;

        try{
            entityManager = entityManagerFactory.createEntityManager();

            userTransaction.begin();
            entityManager.joinTransaction();

            ArrayList<Sale> salesList = new ArrayList<>();

            List<ESales> eSales = entityManager.createQuery("SELECT s FROM ESales s", ESales.class).getResultList();

            for(ESales eSale : eSales){
                salesList.add(eSale.castToSale());
            }

            userTransaction.commit();

            return salesList;

        }catch(Exception ex){
            return null;
        }
    }
    
}