package com.phoenix.shortLink.controller.api;

import com.phoenix.shortLink.dto.Response;
import com.phoenix.shortLink.impl.ShortLinkCreateBean;
import com.phoenix.shortLink.remote.CreateShortLink;
import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

@Path("api")
public class Create {

    @EJB
    CreateShortLink shortLink;

    @Context
    HttpServletRequest req;

    @GET
    @Path("/create/{link}")
    public Response createShortLinkPathParam(@PathParam("link") String link) {
        return createResponse(link.trim());
    }

    @GET
    @Path("/create")
    public Response createShortLinkQueryParam(@QueryParam("l") String link) {
        return createResponse(link.trim());
    }


    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createShortLinkPost(@FormParam("link") String link) {
        return createResponse(link.trim());
    }

    private Response createResponse(String link) {

        Response response=new Response();

        try {
            String shortId = shortLink.create(link);
            String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort();
            response.setData(baseUrl + req.getContextPath() + "/" + shortId);
            response.setStatus("success");
            response.setMessage("Success");

        }catch (Exception e){
            response.setStatus("Failed");
            response.setMessage(e.getMessage());
        }
        return response;
    }

}
