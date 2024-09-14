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
        if(!(link.startsWith("http://") ||link.startsWith("https://"))){
            response.setMessage("Invalid Url (Url Should contain http:// or https://) Eg:-https://www.google.com");
            response.setStatus("error");
            return response;
        }else{
            try {
                String shortId = shortLink.create(link);
                String baseUrl = req.getScheme() + "://" + req.getServerName();
                response.setData(baseUrl + req.getContextPath() + "/" + shortId);
                response.setStatus("success");
                response.setMessage("Link Created Successfully");

            }catch (Exception e){
                response.setStatus("Failed");
                response.setMessage(e.getMessage());
            }
        }
        return response;
    }

}
