package com.example.helloworld.resources;

import com.example.helloworld.db.WorldDAO;
import com.example.helloworld.db.model.World;
import com.google.common.base.Optional;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/db")
@Produces(MediaType.APPLICATION_JSON)
public class WorldResource {
    private final WorldDAO worldDAO;

    public WorldResource(WorldDAO worldDAO) {
        this.worldDAO = worldDAO;
    }

    @GET
    @UnitOfWork
    public Object dbTest(@QueryParam("queries") Optional<String> queries) {
        int totalQueries = Helper.getQueries(queries);
        final World[] worlds = new World[totalQueries];

        for (int i = 0; i < totalQueries; i++) {
            worlds[i] = worldDAO.findById(Helper.randomWorld());
        }
        if (!queries.isPresent()) {
        	return worlds[0];
        } else {
        	return worlds;
        }
    }

    @GET
    @Path("/update")
    @UnitOfWork
    public World[] updateTest(@QueryParam("queries") Optional<String> queries) {
        int totalQueries = Helper.getQueries(queries);
        final World[] worlds = new World[totalQueries];

        for (int i = 0; i < totalQueries; i++) {
            worlds[i] = worldDAO.findAndModify(Helper.randomWorld(), Helper.randomWorld());
        }
        return worlds;
    }
}
