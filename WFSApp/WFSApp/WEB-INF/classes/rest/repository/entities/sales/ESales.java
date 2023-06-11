package rest.repository.entities.sales;

import java.io.Serializable;
import jakarta.persistence.*;
import rest.model.dto.Sale;
import rest.repository.entities.products.EProduct;

@Entity
@Table(name = "\"sales\"")
public class ESales implements Serializable{

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "\"id\"")
    private Integer id;

    @Column(name = "\"customerName\"")
    private String customerName;

    @Column(name = "\"customerLastName\"")
    private String customerLastName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "\"productId\"", referencedColumnName = "\"id\"")
    private EProduct productId;

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public EProduct getProductId() {
        return productId;
    }

    public void setProductId(EProduct productId) {
        this.productId = productId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    protected ESales(){}

    public ESales(Sale sale){
        this.id = sale.getId();
        this.customerName = sale.getCustomerName();
        this.customerLastName = sale.getCustomerLastName();
        this.productId = new EProduct(sale.getProductId());
    }

    public Sale castToSale(){

        Sale newSale = new Sale();

        newSale.setId(this.id);
        newSale.setCustomerName(this.customerName);
        newSale.setCustomerLastName(this.customerLastName);
        newSale.setProductId(this.productId.castToProduct());

        return newSale;

    }

}
