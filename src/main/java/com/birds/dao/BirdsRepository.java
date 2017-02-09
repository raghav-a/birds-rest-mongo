package com.birds.dao;

import com.birds.model.Bird;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BirdsRepository extends MongoRepository<Bird, ObjectId> {


}
