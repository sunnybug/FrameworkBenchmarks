package com.example.helloworld.db.mongo;

import com.example.helloworld.db.WorldDAO;
import com.example.helloworld.db.model.World;
import org.mongojack.DBProjection;
import org.mongojack.DBQuery;
import org.mongojack.DBUpdate;
import org.mongojack.JacksonDBCollection;

public class WorldMongoImpl implements WorldDAO {

    private final JacksonDBCollection<World, Integer> worldCollection;

    public WorldMongoImpl(JacksonDBCollection<World, Integer> worlds) {
        worldCollection = worlds;
    }

    @Override
    public World findById(int id) {
        return worldCollection.findOneById(id, DBProjection.include("_id", "randomNumber"));
    }

    @Override
    public World findAndModify(int worldId, int newRandomNumber) {
        return worldCollection.findAndModify(
                DBQuery.is("_id", worldId),
                DBProjection.include("_id", "randomNumber"),
                null,
                false,
                DBUpdate.set("randomNumber", newRandomNumber),
                true,
                false);

    }
}
