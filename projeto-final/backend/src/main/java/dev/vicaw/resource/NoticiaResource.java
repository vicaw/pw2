package dev.vicaw.resource;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import dev.vicaw.service.NoticiaService;
import dev.vicaw.model.Noticia;

@Path("/api/noticias")
public class NoticiaResource {

    @Inject
    NoticiaService noticiaController;

    @GET
    // @RolesAllowed({ "USER" })
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        return Response.status(Status.OK).entity(noticiaController.list()).build();
    }

    @Path("/{noticiaId}")
    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("noticiaId") long noticiaId) {
        System.out.println(noticiaId);
        return Response.status(Status.OK).entity(noticiaController.getNoticiaById(noticiaId)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(Noticia noticia) {
        return Response.status(Status.OK).entity(noticiaController.save(noticia)).build();
    }
}
