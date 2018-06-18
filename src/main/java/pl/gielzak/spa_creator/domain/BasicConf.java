package pl.gielzak.spa_creator.domain;

import org.bson.types.BSONTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Document(collection="basic_conf")
@NoArgsConstructor
@AllArgsConstructor
public class BasicConf {
	
	@Id
	public String id;
	
	public String title;
	public String description;
	public String icon;
	@Field("created_at")
	public BSONTimestamp createdAt;
	
}
