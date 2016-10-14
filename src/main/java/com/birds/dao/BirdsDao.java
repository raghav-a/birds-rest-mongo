package com.birds.dao;

import com.birds.model.Bird;

import java.math.BigInteger;
import java.util.Collection;

public interface BirdsDao {
    Bird get(BigInteger id);

    Collection<Bird> values();

    void put(BigInteger id, Bird bird);

    void remove(BigInteger id);
}
