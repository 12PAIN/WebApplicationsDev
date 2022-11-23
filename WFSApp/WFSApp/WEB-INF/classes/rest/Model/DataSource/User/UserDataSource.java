package rest.Model.DataSource.User;

import java.util.List;
import java.util.ArrayList;

import jakarta.annotation.Resource;
import jakarta.persistence.*;
import jakarta.transaction.*;

public class UserDataSource implements IUserData {
    

    @PersistenceUnit(unitName = "test-resource_PersistenceUnit")
    private EntityManagerFactory entityManagerFactory;
   
    @Resource
    private UserTransaction userTransaction;

    @Override
    public ArrayList<String> checkUser(String login, String password){
        EntityManager entityManager;
        
        try {
            entityManager = entityManagerFactory.createEntityManager();
        
            userTransaction.begin();
            entityManager.joinTransaction();

            List<EUser> user = entityManager.createQuery("SELECT p FROM EUser p WHERE p.login = " + login, EUser.class).getResultList();
            

            ArrayList<String> resolve = new ArrayList<>();

            if(user.size() == 1){
                if(user.get(0).getPassword().equals(password)){
                    resolve.add(0, "true");
                    resolve.add(1, user.get(0).getEmail());
                }else{
                    resolve.add(0, "false");
                }
            }else{
                resolve.add(0, "false");
            }

            userTransaction.commit();

            return resolve;
        }
        catch (Exception ex){
            System.out.println(ex);
            return null;
        }
        
    }

}
