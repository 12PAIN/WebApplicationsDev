package rest.repository.entities.products;

import java.io.Serializable;
import jakarta.persistence.*;
import rest.model.dto.Product;

@Entity
@Table(name = "\"products\"")
public class EProduct  implements Serializable{

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "\"id\"")
    private Integer id;

    @Column(name = "\"name\"")
    private String name;

    @Column(name = "\"price\"")
    private Integer price;

    @Column(name = "\"description\"")
    private String description;

    @Column(name = "\"saled\"")
    private int saled;

    public int getSaled() {
        return saled;
    }

    public void setSaled(int saled) {
        this.saled = saled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    protected EProduct(){}

    public EProduct(Product product){
        this.setName(product.getName());
        this.setPrice(product.getPrice());
        this.setDescription(product.getDescription());
        this.setSaled(product.getSaled());
    }
    
    public Product castToProduct(){
        Product product = new Product();

        product.setId(this.getId());
        product.setName(this.getName());
        product.setPrice(this.getPrice());
        product.setDescription(this.getDescription());
        product.setSaled(this.saled);

        return product;
    }
}
