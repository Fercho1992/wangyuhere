/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jobservice.restful;

import com.jobservice.util.FileSystem;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.MediaType;


/**
 * REST Web Service
 *
 * @author wangyu
 */

public class TranscriptResource {
    private String name;
    /** Creates a new instance of TranscriptResource */
    private TranscriptResource(String name) {
        this.name=name;
    }

    /** Get instance of the TranscriptResource */
    public static TranscriptResource getInstance(String name) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of TranscriptResource class.
        return new TranscriptResource(name);
    }

    /**
     * Retrieves representation of an instance of com.jobservice.restful.TranscriptResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getXml() {
        String result = FileSystem.readFile("university\\"+name+".xml");
        if(result.equals("")) result = "<Transcript><error>Student doesn't exist</error></Transcript>";
        return result;
    }

    /**
     * PUT method for updating or creating an instance of TranscriptResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    public void putXml(String content) {
        FileSystem.writeFile("university\\"+name+".xml", content);
    }

    /**
     * DELETE method for resource TranscriptResource
     */
    @DELETE
    public void delete() {
        FileSystem.deleteFile("university\\"+name+".xml");
    }
}
