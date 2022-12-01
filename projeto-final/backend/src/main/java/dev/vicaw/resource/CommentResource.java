package dev.vicaw.resource;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.jwt.JsonWebToken;

import dev.vicaw.model.comment.input.PostCommentInput;

import dev.vicaw.service.CommentService;

@Path("/api/comments")
@RolesAllowed({ "USER", "EDITOR", "ADMIN" })
public class CommentResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    CommentService commentService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        return Response.status(Status.OK).entity(commentService.list()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(PostCommentInput commentInput) {
        return Response.status(Status.OK).entity(commentService.create(commentInput)).build();
    }

    @Path("/article/{articleId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArticleComments(@PathParam("articleId") Long articleId, @QueryParam("page") int page) {
        return Response.status(Status.OK).entity(commentService.getArticleComments(articleId, page)).build();
    }

    @Path("/{commentId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("commentId") Long commentId) {
        return Response.status(Status.OK).entity(commentService.getById(commentId)).build();
    }

    @Path("/{commentId}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "ADMIN" })
    public Response delete(@PathParam("commentId") Long commentId) {
        commentService.delete(commentId);
        return Response.status(Status.OK).build();
    }

    /*
     * @Path("/{commentId}/children")
     * 
     * @GET
     * 
     * @Produces(MediaType.APPLICATION_JSON)
     * public Response getChildren(@PathParam("commentId") Long commentId) {
     * return
     * Response.status(Status.OK).entity(commentService.getChildren(commentId)).
     * build();
     * }
     */
}
