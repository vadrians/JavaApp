package com.bombk1n.materialpro.config;

import com.bombk1n.materialpro.dao.IMovieRepository;
import com.bombk1n.materialpro.dao.mongoDB.MovieMongoRepository;
import com.bombk1n.materialpro.dao.mongoDB.MyMongoRepository;
import com.bombk1n.materialpro.dao.postgres.MoviePostgresRepository;
import com.bombk1n.materialpro.dao.postgres.MyJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableJpaRepositories(basePackages = "com.bombk1n.materialpro.dao.postgres")
@EnableMongoRepositories(basePackages = "com.bombk1n.materialpro.dao.mongoDB")
@Configuration
public class RepositoryConfig {

}