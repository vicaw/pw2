package dev.vicaw.resource;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import dev.vicaw.model.category.input.CategoryCreateInput;
import dev.vicaw.service.CategoryService;

@Path("/api/categories")
public class CategoryResource {

    @Inject
    CategoryService categoryService;

    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        return Response.status(Status.OK).entity(categoryService.list()).build();
    }

    @GET
    @Path("/{categoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("categoryId") Long categoryId) {
        return Response.status(Status.OK).entity(categoryService.getById(categoryId)).build();
    }

    @GET
    @Path("/slugs/{categorySlug}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBySlug(@PathParam("categorySlug") String categorySlug) {
        return Response.status(Status.OK).entity(categoryService.getBySlug(categorySlug)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@Valid CategoryCreateInput input) {
        return Response.status(Status.OK).entity(categoryService.create(input)).build();
    }
}
