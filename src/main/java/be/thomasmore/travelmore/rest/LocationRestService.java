package be.thomasmore.travelmore.rest;

import be.thomasmore.travelmore.domain.Location;
import be.thomasmore.travelmore.service.LocationService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by freman on 18/10/2018.
 */
@Path("/locations")
public class LocationRestService{

    @Inject
    private LocationService locationService;

    @GET
    @Path("/")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Location> getLocations() {
        return locationService.findAllLocations();
    }

    @GET
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Location getLocationById(@PathParam("id") int id) {
        return locationService.findLocationById(id);
    }

    @POST
    @Path("/")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response addLocation(Location location) {
        if (null != locationService.findLocationById(location.getId())) {
            return Response
                    .status(Response.Status.NOT_MODIFIED)
                    .entity("location id should not be set.").build();
        }

        locationService.insert(location);
        return Response.status(Response.Status.CREATED).entity(location).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public  Response deleteLocation(@PathParam("id") int id, @HeaderParam("Authorization") String authHeader) {
        try {

            if(!authHeader.equals("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")){
                return  Response
                        .status(Response.Status.UNAUTHORIZED)
                        .entity("Your are not permitted deleting this resource").build();
            }
        }catch (Exception err){
            return  Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity("Your are not permitted deleting this resource").build();
        }
        if(null ==  locationService.findLocationById(id)){
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Location you provided is not found on our system").build();
        }
        locationService.delete(id);
        return Response.status(Response.Status.NO_CONTENT).entity("Location deleted").build();
    }

}
