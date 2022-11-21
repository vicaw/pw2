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

import org.eclipse.microprofile.jwt.JsonWebToken;

import dev.vicaw.model.noticia.Noticia;
import dev.vicaw.service.NoticiaService;

@Path("/api/noticias")
public class NoticiaResource {

    @Inject
    NoticiaService noticiaService;

    @GET
    // @RolesAllowed({ "USER" })
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        return Response.status(Status.OK).entity(noticiaService.list()).build();
    }

    @Path("/{noticiaId}")
    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("noticiaId") long noticiaId) {
        return Response.status(Status.OK).entity(noticiaService.getById(noticiaId)).build();
    }

    @Path("/slugs/{noticiaSlug}")
    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("noticiaSlug") String noticiaSlug) {
        return Response.status(Status.OK).entity(noticiaService.getBySlug(noticiaSlug)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(Noticia noticia) {
        return Response.status(Status.OK).entity(noticiaService.create(noticia)).build();
    }

    @Path("/feedinfo")
    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllFeedInfo() {
        return Response.status(Status.OK).entity(noticiaService.getAllFeedInfo()).build();
    }
}
