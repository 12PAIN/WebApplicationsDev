package rest;


import jakarta.ws.rs.Path;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;

import jakarta.ws.rs.core.Response;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;

import java.util.ArrayList;
import java.util.List;



@Path("/")
public class Service {
 
 @GET
 @Path("/")
 @Produces("text/plain")
 public String ping() {   
  return "OK";
 } 
 
 
 @POST   
 @Path("/test")
 @Consumes("application/json")
 @Produces("application/json")
 public Response test(String studentsJSON) 
 {            
   Jsonb jsonb = JsonbBuilder.create();          
   List<Student> students;      
   String resultJSON;
   try {  
      try { 
       students = jsonb.fromJson(studentsJSON,new ArrayList<Student>(){}.getClass().getGenericSuperclass());                    
      }
      catch (Exception e) {
        throw new Exception("Error while JSON transforming.");  
      }
      for (Student student : students ) {
		  student.setId(student.getId()+1);
      }		  
	  resultJSON = jsonb.toJson(students);	  	 
   }
   catch (JsonbException e) {
    return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();	             
   }
   catch (Exception e) {
    return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();	             
   }    
   return Response.ok(resultJSON).build();           
 }
 
}