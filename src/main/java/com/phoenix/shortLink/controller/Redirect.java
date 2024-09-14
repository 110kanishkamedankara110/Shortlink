package com.phoenix.shortLink.controller;

import com.phoenix.shortLink.impl.ShortLinkCreateBean;
import com.phoenix.shortLink.remote.CreateShortLink;
import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.server.mvc.Viewable;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Path("")
public class Redirect {

    @EJB
    CreateShortLink shortLink;

    @Context
    HttpServletRequest req;

    @GET
    @Path("/{linkId}")
    public Response getShortLink(@PathParam("linkId") String linkId){
        String link=shortLink.getRealLink(linkId);
        if (link == null || link.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Link not found").build();
        }
        return Response.status(Response.Status.FOUND)
                .location(java.net.URI.create(link))
                .build();
    }
    @GET
    @Path("")
    public Viewable login() {
        return new Viewable("/views/index.jsp");
    }
}
