package dev.vicaw.resource;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import dev.vicaw.model.Image;
import dev.vicaw.model.MultipartBody;
import dev.vicaw.service.ImageService;

@Path("/images")
public class ImageResource {

    @Inject
    ImageService imageService;

    @POST
    @PermitAll
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response upload(@MultipartForm MultipartBody body) {
        return Response.status(Status.OK).entity(imageService.save(body)).build();
    }

    @Path("/{fileName}")
    @GET
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("image/jpg")
    public Response getImagebyName(@PathParam("fileName") String name) {
        Image image = imageService.findByFileName(name);
        return Response.status(Status.OK).entity(image.getData()).build();
    }

    @Path("{width}/{height}/{fileName}")
    @GET
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("image/jpg")
    public Response getImageByNameAndScale(@PathParam("fileName") String name, @PathParam("width") int w,
            @PathParam("height") int h) {
        Image image = imageService.findByFileNameAndScale(name, w, h);
        return Response.status(Status.OK).entity(image.getData()).build();
    }

    @PUT
    @PermitAll
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@MultipartForm MultipartBody body) {
        return Response.status(Status.OK).entity(imageService.update(body)).build();
    }

    @Path("/articles/{articleId}")
    @GET
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("image/jpg")
    public Response getImagebyArticleId(@PathParam("articleId") Long articleId) {
        Image image = imageService.findByArticleId(articleId);
        return Response.status(Status.OK).entity(image.getData()).build();
    }

    @Path("/articles/{width}/{height}/{articleId}")
    @GET
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("image/jpg")
    public Response getImageArticleIdAndScale(@PathParam("articleId") Long articleId, @PathParam("width") int w,
            @PathParam("height") int h) {
        Image image = imageService.findByArticleIdAndScale(articleId, w, h);
        return Response.status(Status.OK).entity(image.getData()).build();
    }

}
