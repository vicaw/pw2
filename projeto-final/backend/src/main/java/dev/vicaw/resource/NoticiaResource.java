package dev.vicaw.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        try {
            return Response.status(Status.OK).entity(noticiaController.list()).build();
        } catch (Exception e) {
            throw new WebApplicationException(Status.BAD_REQUEST);
        }

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(Noticia noticia) {
        try {
            return Response.status(Status.OK).entity(noticiaController.save(noticia)).build();
        } catch (Exception e) {
            throw new WebApplicationException(Status.BAD_REQUEST);
        }

    }
}
