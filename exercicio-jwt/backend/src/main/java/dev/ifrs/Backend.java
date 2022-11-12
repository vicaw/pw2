package dev.ifrs;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/backend")
public class Backend {

    @POST
    @Path("/sum")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @RolesAllowed({ "Admin" })
    public int getSum(@FormParam("a") int a, @FormParam("b") int b) {
        return a + b;
    }

}