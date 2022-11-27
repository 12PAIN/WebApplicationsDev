package pack.model;


import java.util.List;

import pack.model.api.IModel;
import pack.model.api.dto.Student;


//JDBC (Begin)
import java.sql.*;
//JDBC (End)

//Pool (Begin)
import jakarta.annotation.Resource;   
import javax.sql.DataSource;
   
import javax.naming.InitialContext;   
//Pool (End)   


//JPA (Begin)
import jakarta.persistence.*;
import jakarta.transaction.*;
//JPA (End)   



public class Model implements IModel {           
   
    //Pool (Begin)
    //@Resource(name = "jdbc/local_pg_test")
    private DataSource ds;
    //Pool (End)   

	  //JPA (Begin)
	  @PersistenceUnit(unitName = "local_pg_test_PersistenceUnit")
    private EntityManagerFactory entityManagerFactory;
   
    @Resource
    private UserTransaction userTransaction;
	  //JPA (End)   
	
	
   	private int retrieveRowsCountBySimpleJDBC() throws Exception {				
		 try {	
      Class.forName("org.postgresql.Driver");            
			String url = "jdbc:postgresql://localhost:5432/test";
      String login = "postgres";
      String password = "123456";			
      Connection con = DriverManager.getConnection(url, login, password);			
      try {			
        Statement stmt = con.createStatement();                			
				ResultSet rs = stmt.executeQuery("SELECT * FROM \"persons\"");								
				int count = 0;
        while (rs.next()) {                					
					count++;
        }				
        rs.close();
        stmt.close();				
				return count;
      } finally {
         con.close();
      }			
     } 
		 catch (Exception e) {		
        throw new Exception("Error while JDBC operating: " + e.getMessage());
     }
	  }  
	
	
    private int retrieveRowsCountByPoolConnection() throws Exception {				
		 try {		            
		  try {	        
	         InitialContext initialContext = new InitialContext();
           ds = (DataSource) initialContext.lookup("jdbc/local_pg_test");
	    }	
	    catch(Exception e) {	        		      
		      throw new Exception("Error while Data Source initializing: " + e.getMessage());
	    }
			
      Connection con = ds.getConnection();		  
      try {			
        Statement stmt = con.createStatement();                			
				ResultSet rs = stmt.executeQuery("SELECT * FROM \"persons\"");								
				int count = 0;
        while (rs.next()) {                					
					count++;
        }				
        rs.close();
        stmt.close();				
				return count;
      } finally {
         con.close();
      }			
     } 
		 catch (Exception e) {		
        throw new Exception("Error while JDBC operating: " + e.getMessage());
     }
	  } 
	
	
	  private int retrieveRowsCountByJPA() throws Exception {	   
	     EntityManager entityManager;
	     try {
	      entityManager = entityManagerFactory.createEntityManager();
	     }
       catch (Exception e) {
		    throw new Exception("Error while Entity Manager initializing: " + e.getMessage()); 
	     }	   
	          
       try {
         userTransaction.begin();
         entityManager.joinTransaction();
		 
		     List<EPerson> persons = entityManager.createQuery("SELECT p FROM EPerson p",EPerson.class).getResultList();                                                                                                   		 		 
		 
		     /*
         EPerson personFind = entityManager.find(EPerson.class,new Integer(2));		 
		     personFind.setPersonName("Person_Find");
		     entityManager.merge(personFind);
		 
		     EPerson personPersist = new EPerson();
		     //personPersist.setPersonID(new Integer(3));
		     personPersist.setPersonName("Person_Persist");		 
		     entityManager.persist(personPersist);
		     */
		 
         userTransaction.commit();
		 		 
		     return persons.size(); 
       }
	     catch (Exception e) {
         throw new Exception("Error while JPA operating: " + e.getMessage());
       }	   
    }
 




    private int retrieveRowsCount() throws Exception {	
	    //return 1*retrieveRowsCountBySimpleJDBC();
		  return 2*retrieveRowsCountByPoolConnection();
		  //return 3*retrieveRowsCountByJPA();
	  }


    @Override
    public void run(List<Student> students) throws Exception {
	   int count = retrieveRowsCount();	
       for (Student student : students ) {
		     student.setId((student.getId()) + count);		  		 		 		 
      }
    } 
}