
package pack.controller.interceptor;


import java.io.IOException;

import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;

//Example:        
/*
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.HttpHeaders;
*/

@Provider
@IdRequired
public class Interceptor implements ContainerRequestFilter {
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        requestContext.setProperty("id",Integer.valueOf(6));		

        //Example:        
        /*
        String header = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (header == null) {
            throw new NotAuthorizedException("Authorization header must be provided");           
        }
        */         
    }
}
