package dev.vicaw;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class Filter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException {

        System.out.println(
                "Request Method: " + requestContext.getMethod() + "\n Au: "
                        + requestContext.getHeaders().toString());

        // if (requestContext.getMethod().equalsIgnoreCase("OPTIONS")) {

        // }
    }
}
