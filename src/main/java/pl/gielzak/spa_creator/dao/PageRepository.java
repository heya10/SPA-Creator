package pl.gielzak.spa_creator.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import pl.gielzak.spa_creator.domain.Page;

public interface PageRepository extends MongoRepository<Page, String> {

}
