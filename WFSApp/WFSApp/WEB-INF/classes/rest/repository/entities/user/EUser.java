package rest.repository.entities.user;

import java.io.Serializable;
import jakarta.persistence.*;
import rest.model.dto.User;

@Entity
@Table(name = "\"users\"")
public class EUser implements Serializable {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "\"id\"")
    private Integer id;

    @Column(name = "\"login\"")
    private String login;

    @Column(name = "\"password\"")
    private String password;

    @Column(name = "\"email\"")
    private String email;

    @Column(name = "\"name\"")
    private String name;

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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User castToUser(){
        
        User user = new User();

        user.setLogin(this.getLogin());
        user.setEmail(this.getEmail());
        user.setPassword(this.getPassword());
        user.setName(this.getName());
    

        return user;
    }

    protected EUser(){}
    
    public EUser(User user){
        this.login = user.getLogin();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
    }

}
