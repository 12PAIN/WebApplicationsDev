package pack.model;


import java.util.List;

import pack.model.api.in.IModel;
import pack.model.api.out.IRepository;
import pack.model.api.dto.Student;

//import jakarta.inject.Inject;

public class Model implements IModel { 

   
    //@Inject
    IRepository repository;

    
    @Override
    public void injectRepository(IRepository repository) {
      this.repository = repository;
    } 
    

    @Override
    public void run(List<Student> students) throws Exception {
	   int count = repository.retrieveCount();	
       for (Student student : students ) {
		     student.setId((student.getId()) + count);		  		 		 		 
      }
    } 
}