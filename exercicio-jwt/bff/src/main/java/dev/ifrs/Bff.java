package dev.ifrs;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/bff")
public class Bff {

    @Inject
    @RestClient
    BackendClient backend;

    @POST
    @Path("/sum")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @RolesAllowed({ "Admin" })
    public int sum(@FormParam("a") int a, @FormParam("b") int b) {
        return backend.sum(a, b);
    }

}
