package com.birds.dao;

import com.birds.model.Bird;
import org.bson.types.ObjectId;

import java.math.BigInteger;
import java.util.Collection;

public interface BirdsRegistryDao {
    Bird get(ObjectId id);

    Collection<Bird> getAll();

    void save(Bird bird);

    void remove(ObjectId id);
}
