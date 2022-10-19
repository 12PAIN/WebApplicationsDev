package rest;
import jakarta.ws.rs.Path;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.DELETE;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;

import jakarta.ws.rs.HeaderParam;

import jakarta.ws.rs.core.Response;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Generated;

@Path("/")
public class Service {

  @GET
  @Path("/")
  @Produces("text/plain")
  public String ping() {   
    return "OK";
  } 

  @POST
  @Path("/auth")
  @Consumes("application/json")
  @Produces("application/json")
  public Response authUser(String userJson) {
    User user;
    Jsonb jsonb = JsonbBuilder.create();
    String resultJSON = jsonb.toJson("undefinedError");
    try{

      try{
        user = jsonb.fromJson(userJson, new User(){}.getClass().getGenericSuperclass());
      }catch (Exception e) {
        resultJSON = jsonb.toJson("Error while JSON transforming.");
        throw new Exception("Error while JSON transforming.");  
      }

      Boolean usrTrue = null;

      try{

        DataBase.initDataBase();
        usrTrue = DataBase.isUserCorrect(user.getLogin(), user.getPassword());

      }catch (SQLException sqle){ return Response.ok(jsonb.toJson("dataBaseError")).build();}
      catch (Exception ex){};
      
      if(usrTrue == true){
        Token token = Token.generateToken(user);
        
        resultJSON = jsonb.toJson(token);
      }else{
        resultJSON = jsonb.toJson("false");
      }

      

    }catch (JsonbException e) {
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();	             
    }
    catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();	             
    }    
    return Response.ok(resultJSON).build(); 
  }

  @GET
  @Path("/productList")
  @Produces("application/json")
  public Response getProductList(@HeaderParam("User-token") String userToken){
    Token token;
    Jsonb jsonb = JsonbBuilder.create();
    String resultJSON = jsonb.toJson("undefinedError");
    try{

      try{
        token = jsonb.fromJson(userToken, new Token(){}.getClass().getGenericSuperclass());
      }catch (Exception e) {
        resultJSON = "Error while JSON transforming.";
        throw new Exception("Error while JSON transforming.");  
      }

      if(Token.verifyToken(token)){
        try{
          DataBase.initDataBase();
          ArrayList<Product> productList = DataBase.selectProducts();
          resultJSON = jsonb.toJson(productList);
        } catch(SQLException e){
          resultJSON = jsonb.toJson("dataBaseError");
        }
        catch(Exception e){};
      }else{
        resultJSON = jsonb.toJson("tokenError");
      }

    }catch (JsonbException e) {
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();	             
    }
    catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();	             
    }    
    return Response.ok(resultJSON).build(); 
  }

  @POST
  @Path("/product")
  @Consumes("application/json")
  @Produces("application/json")
  public Response addProducts(@HeaderParam("User-token") String userToken, String newProduct){
    Token token;
    Product product;
    Jsonb jsonb = JsonbBuilder.create();
    String resultJSON = jsonb.toJson("undefinedError");
    try{

      try{
        token = jsonb.fromJson(userToken, new Token(){}.getClass().getGenericSuperclass());
        product = jsonb.fromJson(newProduct, new Product(){}.getClass().getGenericSuperclass());
      }catch (Exception e) {
        resultJSON = "Error while JSON transforming.";
        throw new Exception("Error while JSON transforming.");  
      }

      Boolean usrTrue = null;

      if(Token.verifyToken(token)){
        try{
          DataBase.initDataBase();
          DataBase.addRow(product.getName(), product.getPrice(), product.getDescription());
          resultJSON = jsonb.toJson("row_added");
        } catch(SQLException e){}
        catch(Exception e){};
      }else{
        resultJSON = jsonb.toJson("tokenError");
      }

    }catch (JsonbException e) {
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();	             
    }
    catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();	             
    }    
    return Response.ok(resultJSON).build(); 
  }

  @DELETE
  @Path("/products")
  @Produces("application/json")
  public Response deleteProducts(@HeaderParam("User-token") String userToken,@HeaderParam("To-delete-rows") String toDeleteJSON){
    Token token;
    ArrayList<Integer> toDelete;
    Jsonb jsonb = JsonbBuilder.create();
    String resultJSON = jsonb.toJson("undefinedError");
    try{

      try{
        token = jsonb.fromJson(userToken, new Token(){}.getClass().getGenericSuperclass());
        toDelete = jsonb.fromJson(toDeleteJSON, new ArrayList<Integer>(){}.getClass().getGenericSuperclass());
      }catch (Exception e) {
        resultJSON = "Error while JSON transforming.";
        throw new Exception("Error while JSON transforming.");  
      }

      Boolean usrTrue = null;

      if(Token.verifyToken(token)){
        try{
          DataBase.initDataBase();
          DataBase.deleteRows(toDelete);
          resultJSON = jsonb.toJson("rows_deleted");
        } catch(SQLException e){}
        catch(Exception e){};
      }else{
        resultJSON = jsonb.toJson("tokenError");
      }

    }catch (JsonbException e) {
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();	             
    }
    catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();	             
    }    
    return Response.ok(resultJSON).build(); 
  }

  @POST
  @Path("/user")
  @Consumes("application/json")
  @Produces("application/json")
  public Response createUser(String User){
    Jsonb jsonb = JsonbBuilder.create();             
    User newUser;
    String resultJSON = "undefinedError";
    try {  
        try { 
          newUser = jsonb.fromJson(User,new User(){}.getClass().getGenericSuperclass());                    
        }
        catch (Exception e) {
          resultJSON = "Error while JSON transforming.";
          throw new Exception("Error while JSON transforming.");  
        }
        try{

          Boolean userCreated = false;
          DataBase.initDataBase();
          userCreated = DataBase.createUser(newUser.getLogin(), newUser.getPassword(), newUser.getEmail());
          if(userCreated == true) resultJSON = "createUser_Ok_status";
          else if (userCreated == false) resultJSON = "userIsExistStatus";
          
        }catch (java.sql.SQLException sqle){return Response.ok(jsonb.toJson("dataBaseError")).build();}
        catch (Exception ex){};
    }
    catch (JsonbException e) {
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();	             
    }
    catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();	             
    }    
    return Response.ok(jsonb.toJson(resultJSON)).build();  
  }
}