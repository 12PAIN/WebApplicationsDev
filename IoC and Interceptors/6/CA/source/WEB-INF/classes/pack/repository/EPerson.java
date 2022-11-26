package pack.repository;


import java.io.Serializable;

//JPA (Begin)
import jakarta.persistence.*;
//JPA (End)   
  

@Entity
@Table(name = "\"persons\"")
public class EPerson implements Serializable {
  @Id
  @Column(name = "\"id\"")
  private Integer personID;
  @Column(name = "\"name\"")
  private String personName;
  
  public Integer getPersonID() {
    return personID;
  }  
  public void setPersonID(Integer pID) {
    personID = pID;
  }  
  
  public String getPersontName() {
    return personName;
  }  
  public void setPersonName(String pName) {
    personName = pName;
  }
}