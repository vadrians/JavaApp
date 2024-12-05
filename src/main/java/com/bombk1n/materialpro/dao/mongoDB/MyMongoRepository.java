package com.bombk1n.materialpro.dao.mongoDB;

import com.bombk1n.materialpro.model.MovieDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MyMongoRepository extends MongoRepository<MovieDocument, String>  {
}
