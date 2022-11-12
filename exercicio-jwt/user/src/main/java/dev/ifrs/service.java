package dev.ifrs;

import java.util.Arrays;
import java.util.HashSet;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.jwt.Claims;

import io.smallrye.jwt.build.Jwt;

@Path("/jwt")
public class service {
    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)

    public String generate(@FormParam("name") String name, @FormParam("email") String email) {
        return Jwt
                .issuer("http://localhost:8080")
                .upn(email)
                .groups(new HashSet<>(Arrays.asList("User", "Admin")))
                .claim(Claims.full_name, name)
                .claim(Claims.email, email)
                .sign();
    }
    
}
