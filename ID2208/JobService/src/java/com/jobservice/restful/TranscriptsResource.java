/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jobservice.restful;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author wangyu
 */

@Path("/transcripts")
public class TranscriptsResource {
    @Context
    private UriInfo context;

    /** Creates a new instance of TranscriptsResource */
    public TranscriptsResource() {
    }

    /**
     * Retrieves representation of an instance of com.jobservice.restful.TranscriptsResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getXml() {
        return "<Transcripts></Transcripts>";
    }

    /**
     * POST method for creating an instance of TranscriptResource
     * @param content representation for the new resource
     * @return an HTTP response with content of the created resource
     */
    @POST
    @Consumes("application/xml")
    @Produces("application/xml")
    public Response postXml(String content) {
        //TODO
        return Response.created(context.getAbsolutePath()).build();
    }

    /**
     * Sub-resource locator method for {name}
     */
    @Path("{name}")
    public TranscriptResource getTranscriptResource(@PathParam("name")
    String name) {
        return TranscriptResource.getInstance(name);
    }
}
