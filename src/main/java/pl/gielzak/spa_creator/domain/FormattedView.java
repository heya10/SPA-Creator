package pl.gielzak.spa_creator.domain;

import java.util.HashMap;
import java.util.Map;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FormattedView {

	public String viewType;
	public String viewName;
	public Integer order;
	public Map<String, String> props;
	
	public FormattedView(PageView pageView){
		this.viewType = pageView.viewType;
		this.viewName = pageView.viewName;
		this.order = pageView.order;
		
		this.props = new HashMap<>();
		
		for(Property prop : pageView.props){
			this.props.put(prop.name, prop.value);
		}
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Map<String, String> getProps() {
		return props;
	}

	public void setProps(Map<String, String> props) {
		this.props = props;
	}
	
	
}
