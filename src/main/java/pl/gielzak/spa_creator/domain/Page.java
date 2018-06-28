package pl.gielzak.spa_creator.domain;

import java.util.Date;
import java.util.List;

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
	public Date createdAt;
	
	public List<PageView> views;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public List<PageView> getViews() {
		return views;
	}

	public void setViews(List<PageView> views) {
		this.views = views;
	}
	
}
