package pl.gielzak.spa_creator.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import pl.gielzak.spa_creator.domain.View;

public interface ViewRepository extends MongoRepository<View, String> {

}
