package dev.vicaw.resource;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import dev.vicaw.service.UserService;
import dev.vicaw.model.User;

@Path("/api/users")
public class UserResource {

    @Inject
    UserService userService;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        return Response.status(Status.OK).entity(userService.list()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@Valid User user) {
        return Response.status(Status.OK).entity(userService.save(user)).build();
    }
}
