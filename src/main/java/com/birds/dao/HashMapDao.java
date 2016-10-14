package com.birds.dao;

import com.birds.model.Bird;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class HashMapDao implements BirdsDao {

    Map<BigInteger, Bird> birdsMap = new HashMap<>();

    @Override
    public Bird get(BigInteger id) {
        return birdsMap.get(id);
    }

    @Override
    public Collection<Bird> values() {
        return birdsMap.values();
    }

    @Override
    public void put(BigInteger id, Bird bird) {
        birdsMap.put(id,bird);
    }

    @Override
    public void remove(BigInteger id) {
        birdsMap.remove(id);
    }
}
