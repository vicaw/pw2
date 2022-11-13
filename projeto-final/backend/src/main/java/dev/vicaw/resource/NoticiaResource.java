package dev.vicaw.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import dev.vicaw.controller.NoticiaController;
import dev.vicaw.model.Noticia;

@Path("/noticias")
public class NoticiaResource {

    @Inject
    NoticiaController noticiaController;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Noticia save(Noticia noticia) {
        try {
            return noticiaController.save(noticia);
        } catch (Exception e) {
            throw new WebApplicationException(Status.BAD_REQUEST);
        }

    }
}
