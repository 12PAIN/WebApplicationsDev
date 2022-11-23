package rest.Model.DataSource.User;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "\"users\"")
public class EUser implements Serializable {
    @Id
    @Column(name = "\"id\"")
    private Integer userId;
    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name = "\"login\"")
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name = "\"password\"")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "\"email\"")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
