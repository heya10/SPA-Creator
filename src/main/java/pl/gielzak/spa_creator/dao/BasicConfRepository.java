package pl.gielzak.spa_creator.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import pl.gielzak.spa_creator.domain.BasicConf;

public interface BasicConfRepository extends MongoRepository<BasicConf, String>{
	
}
