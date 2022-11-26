package pack.controller.path;


import jakarta.ws.rs.Path;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;

import jakarta.ws.rs.core.Response;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.container.ContainerRequestContext;


import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;


import jakarta.inject.Inject;


import pack.controller.interceptor.IdRequired;


import pack.builder.Built;


import java.util.ArrayList;
import java.util.List;


import pack.model.api.dto.Student;
import pack.model.api.in.IModel;



@Path("/")
public class Controller {
 
 @Context
 ContainerRequestContext requestContext;
 

 //@Inject
 @Inject @Built
 IModel model;
 
 
 @GET
 @IdRequired
 @Path("/")
 @Produces("text/plain")
 public String ping() {
  Integer id = (Integer)requestContext.getProperty("id");   
  return "OK " + id.toString();
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
	  

	  model.run(students);	  	  
	  
    Object obj = students;
	  resultJSON = jsonb.toJson(obj);	  	 
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