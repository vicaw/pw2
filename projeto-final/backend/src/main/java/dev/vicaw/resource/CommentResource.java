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

import dev.vicaw.model.comment.Comment;
import dev.vicaw.model.comment.input.PostCommentInput;
import dev.vicaw.model.noticia.Noticia;
import dev.vicaw.service.CommentService;
import dev.vicaw.service.NoticiaService;

@Path("/api/comments")
public class CommentResource {

    @Inject
    CommentService commentService;

    @GET
    // @RolesAllowed({ "USER" })
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        return Response.status(Status.OK).entity(commentService.list()).build();
    }

    @Path("/article/{articleId}")
    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArticleComments(@PathParam("articleId") Long articleId) {
        return Response.status(Status.OK).entity(commentService.getArticleComments(articleId)).build();
    }

    @Path("/{commentId}")
    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("commentId") Long commentId) {
        return Response.status(Status.OK).entity(commentService.getById(commentId)).build();
    }

    @Path("/{commentId}/children")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getChildren(@PathParam("commentId") Long commentId) {
        return Response.status(Status.OK).entity(commentService.getChildren(commentId)).build();
    }

    @POST
    @RolesAllowed({ "USER" })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(PostCommentInput commentInput) {
        return Response.status(Status.OK).entity(commentService.create(commentInput)).build();
    }
}
