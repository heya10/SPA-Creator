package pl.gielzak.spa_creator.domain;

import java.util.HashMap;
import java.util.Map;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FormattedView {

	public String viewType;
	public String name;
	public Integer order;
	public Map<String, String> props;
	
	public FormattedView(PageView pageView){
		this.viewType = pageView.viewType;
		this.name = pageView.name;
		this.order = pageView.order;
		
		this.props = new HashMap<>();
		
		for(Property prop : pageView.props){
			this.props.put(prop.name, prop.value);
		}
	}
}
