package pack.model;


import java.util.List;

import pack.model.api.IModel;
import pack.model.api.dto.Student;


public class Model implements IModel {           
    @Override
    public void run(List<Student> students) {
              
       for (Student student : students ) {
		      student.setId(student.getId()+5);		  		 		 		 
      }
    } 
}