package rest;

import com.google.gson.Gson;
import utils.EMF_Creator;
import utils.HttpUtils;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;

@Path("card")
public class CardResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    Gson GSON = new Gson();
    @Context
    private UriInfo context;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNewDeckShuffle() throws IOException {
        return Response.ok(GSON.toJson(HttpUtils.fetchNewDeck())).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("shuffle/{id}")
    public Response shuffleCurrentDeck(@PathParam("id") String id) throws IOException {
        return Response.ok(GSON.toJson(HttpUtils.shuffleCurrentDeck(id))).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("draw/{id}")
    public Response drawCard(@PathParam("id") String id) throws IOException {
        return Response.ok(GSON.toJson(HttpUtils.drawCard(id))).build();
    }
}
