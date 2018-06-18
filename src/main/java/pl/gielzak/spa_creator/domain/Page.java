package pl.gielzak.spa_creator.domain;

import java.util.List;

import org.bson.types.BSONTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Document(collection="page")
@NoArgsConstructor
@AllArgsConstructor
public class Page {
	
	@Id
	public String id;
	
	@Field("created_at")
	public BSONTimestamp createdAt;
	
	public List<PageView> views;

}
