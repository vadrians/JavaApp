package com.bombk1n.materialpro.dao.mongoDB;

import com.bombk1n.materialpro.model.MovieEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


public interface MyMongoRepository extends MongoRepository<MovieEntity, String>  {
}
