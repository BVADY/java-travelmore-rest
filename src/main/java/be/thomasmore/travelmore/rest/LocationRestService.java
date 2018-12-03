package be.thomasmore.travelmore.rest;

import be.thomasmore.travelmore.domain.Location;
import be.thomasmore.travelmore.service.LocationService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by freman on 18/10/2018.
 */
@Path("/locations")
public class LocationRestService{

    @Inject
    private LocationService locationService;

    @GET
    @Path("/getlocation")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Location getLocationById(@QueryParam("id") int id) {
        return locationService.findLocationById(id);
    }

    @POST
    @Path("/addlocation")
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
    @Path("/deletelocation")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public  Response deleteLocation(int id) {
        if(null ==  locationService.findLocationById(id)){
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Location you provided is not found on our system").build();
        }
        locationService.delete(id);
        return Response.status(Response.Status.NO_CONTENT).entity("Location deleted").build();
    }

}
