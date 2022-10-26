package rest.Controller;

import jakarta.ws.rs.Path;

import jakarta.ws.rs.POST;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;

import jakarta.ws.rs.core.Response;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;

import rest.Model.User;
import rest.Model.IModel;
import jakarta.inject.Inject;

@Path("/users")
public class ServiceUser {

    @Inject
    IModel model;

    @POST
    @Path("/auth")
    @Consumes("application/json")
    @Produces("application/json")
    public Response authUser(String userJson) {

        User user;
        Jsonb jsonb = JsonbBuilder.create();
        String resultJSON = jsonb.toJson("undefinedError");

        try{

            user = jsonb.fromJson(userJson, new User(){}.getClass().getGenericSuperclass());
            Boolean usrTrue = model.checkUser(user);

            
            if(usrTrue == null){
                return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("Unavailable DataBase Connection").build();
            }

            if(usrTrue == true){

                Token token = TokenTools.generateToken(TokenTools.generatePayload(user.getLogin(), user.getEmail()));
                resultJSON = jsonb.toJson(token);

            }else{
                return Response.status(Response.Status.UNAUTHORIZED).entity("UserNotFound").build();
            }

            resultJSON = jsonb.toJson(usrTrue);

        }catch (JsonbException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();	             
        }
        catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();	             
        }    
        return Response.ok(resultJSON).build();
    }
    

    @POST
    @Path("/")
    @Consumes("application/json")
    @Produces("application/json")
    public Response createUser(String User){
        Jsonb jsonb = JsonbBuilder.create();             
        User newUser;
        String resultJSON = "undefinedError";
        try {  
            
            newUser = jsonb.fromJson(User,new User(){}.getClass().getGenericSuperclass());    
            
            Boolean userCreated = model.addUser(newUser);

            if(userCreated == null){
                return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("Unavailable DataBase Connection").build();
            }

            if(userCreated == false){
                return Response.status(Response.Status.UNAUTHORIZED).entity("UserAlreadyExist").build();
            }
            
            resultJSON = jsonb.toJson("userCreated");

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
