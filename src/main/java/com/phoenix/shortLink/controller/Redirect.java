package com.phoenix.shortLink.controller;

import com.phoenix.shortLink.impl.ShortLinkCreateBean;
import com.phoenix.shortLink.remote.CreateShortLink;
import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;

@Path("")
public class Redirect {

    @EJB
    CreateShortLink shortLink;

    @Context
    HttpServletRequest req;

    @GET
    @Path("/{id}/{linkId}")
    public Response getShortLink(@PathParam("id") int id,@PathParam("linkId") String linkId){
        String link=shortLink.getRealLink(id,linkId);
        if (link == null || link.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Link not found").build();
        }
        return Response.status(Response.Status.FOUND)
                .location(java.net.URI.create(link))
                .build();
    }
}
