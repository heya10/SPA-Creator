package pl.gielzak.spa_creator.domain;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PageView {

	@Field("view-type")
	public String viewType;
	public String name;
	public Integer order;
	public List<Property> props;
	
}
