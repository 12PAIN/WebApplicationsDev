package rest.controller.service.sales;

import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;

import rest.builder.Built;
import rest.controller.interceptor.IdRequired;
import rest.model.dto.Sale;
import rest.model.sales.out.ISalesModel;

@Path("/sales")
public class ServiceSales {

    @Context
    ContainerRequestContext requestContext;

    @Inject @Built
    ISalesModel model;

    @GET
    @IdRequired
    @Path("/")
    @Produces("application/json")
    public Response getSalesList(){

        Jsonb jsonb = JsonbBuilder.create();
        String resultJSON = jsonb.toJson("undefinedError");

        try{
            
            resultJSON = jsonb.toJson(model.getSales());
            if(resultJSON == null) return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(jsonb.toJson("Unavailable DataBase Connection")).build();

        }catch (JsonbException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();	             
        }
        catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();	             
        }    
        return Response.ok(resultJSON).build(); 
    }

    @POST
    @IdRequired
    @Path("/")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addProduct(String newProduct){

        Sale sale;
        Jsonb jsonb = JsonbBuilder.create();
        String resultJSON = jsonb.toJson("undefinedError");

        try{

            sale = jsonb.fromJson(newProduct, new Sale(){}.getClass().getGenericSuperclass());
        
            Sale saleNew = model.addSale(sale);
            if(saleNew != null) resultJSON = jsonb.toJson(saleNew);
            else{
                return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(jsonb.toJson("Unavailable DataBase Connection")).build();
            }
            
        }catch (JsonbException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();	             
        }
        catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();	             
        }    
        return Response.ok(resultJSON).build(); 
    }

    
}