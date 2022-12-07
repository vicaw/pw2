package dev.vicaw.resource;

import java.io.IOException;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.vicaw.model.noticia.Noticia;
import dev.vicaw.model.noticia.input.ArticleInput;
import dev.vicaw.model.noticia.input.MultipartInput;
import dev.vicaw.service.NoticiaService;

@Path("/api/noticias")
public class NoticiaResource {

    @Inject
    NoticiaService noticiaService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(@QueryParam("authorId") int authorId) {
        return Response.status(Status.OK).entity(noticiaService.list(authorId)).build();
    }

    @Path("/{noticiaId}")
    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("noticiaId") Long noticiaId) {
        return Response.status(Status.OK).entity(noticiaService.getById(noticiaId)).build();
    }

    @Path("/slugs/{noticiaSlug}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBySlug(@PathParam("noticiaSlug") String noticiaSlug) {
        return Response.status(Status.OK).entity(noticiaService.getBySlug(noticiaSlug)).build();
    }

    @POST
    @RolesAllowed({ "EDITOR", "ADMIN" })
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@MultipartForm MultipartInput body) {
        return Response.status(Status.OK).entity(noticiaService.create(body)).build();
    }

    @PUT
    @RolesAllowed({ "EDITOR", "ADMIN" })
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response edit(@MultipartForm MultipartInput body) {
        return Response.status(Status.OK).entity(noticiaService.edit(body)).build();
    }

    @Path("/feedinfo")
    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFeedInfo(@DefaultValue("10") @QueryParam("pagesize") int pagesize,
            @QueryParam("page") int page, @QueryParam("category") String categorySlug) {
        return Response.status(Status.OK).entity(noticiaService.getFeedInfo(pagesize, page, categorySlug)).build();
    }

    @Path("/search")
    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchArticle(@QueryParam("q") String query) {
        System.out.println(query);
        return Response.status(Status.OK).entity(noticiaService.searchArticle(query)).build();
    }
}
