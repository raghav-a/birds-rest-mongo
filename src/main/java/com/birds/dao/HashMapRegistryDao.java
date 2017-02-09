package com.birds.dao;

import com.birds.model.Bird;
import org.bson.types.ObjectId;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class HashMapRegistryDao implements BirdsRegistryDao {

    Map<ObjectId, Bird> birdsMap = new HashMap<>();

    @Override
    public Bird get(ObjectId id) {
        return birdsMap.get(id);
    }

    @Override
    public Collection<Bird> getAll() {
        return birdsMap.values();
    }

    @Override
    public void save(Bird bird) {
        birdsMap.put(bird.id(),bird);
    }

    @Override
    public void remove(ObjectId id) {
        birdsMap.remove(id);
    }
}
