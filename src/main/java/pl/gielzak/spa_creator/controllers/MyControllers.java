package pl.gielzak.spa_creator.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.gielzak.spa_creator.dao.BasicConfRepository;
import pl.gielzak.spa_creator.dao.PageRepository;
import pl.gielzak.spa_creator.domain.BasicConf;
import pl.gielzak.spa_creator.domain.FormattedView;
import pl.gielzak.spa_creator.domain.Page;
import pl.gielzak.spa_creator.domain.PageView;

@Controller
public class MyControllers {
	
	@Autowired
	BasicConfRepository basicConfRepo;
	
	@Autowired
	PageRepository pageRepo;
	
	public BasicConf getBasicConfiguration(){
		//sort configuration by recent first
		List<BasicConf> bcList = basicConfRepo.findAll(new Sort(Sort.Direction.DESC, "created_at"));
		//get the latest configuration
		if(bcList.size()!=0)
			return bcList.get(0);
		else
			return null;
	}

	public Page getLatestPage(){
		List<Page> pList = pageRepo.findAll(new Sort(Sort.Direction.DESC, "created_at"));
		if(pList.size()!=0)
			return pList.get(0);
		else
			return null;
	}
	
	public List<FormattedView> getFormattedViews(){
		Page page = getLatestPage();
		List<FormattedView> formattedViews = new ArrayList<FormattedView>();
		for(PageView pv : page.views){
			formattedViews.add(new FormattedView(pv));
		}
		return formattedViews;
	}
	
	@GetMapping("/")
	public String index(Model model){
		model.addAttribute("basicConf", getBasicConfiguration());
		model.addAttribute("views", getFormattedViews());
		return "index";
	}
	
	@GetMapping("/page")
	@ResponseBody
	public Page getPage(){
		return getLatestPage();
	}
	
	//some kind of menu with few options
	@GetMapping("/admin")
	public String adminPage(){
		return "admin";
	}
	
	//configure title of website, icon, add fonts etc.
	@GetMapping("/admin/basic-configuration")
	public String basicConfPage(){
		return "admin/basic";
	}
	
	//edit the look of website (colors, content[text], images)
	@GetMapping("/admin/editor")
	public String editorPage(){
		return "admin/editor";
	}
	
	//add/delete modules (cookies, facebook on the side, etc.)
	@GetMapping("/admin/modules")
	public String modulesPage(){
		return "admin/modules";
	}
	
	//
	//for now commented, i think whole configuration and editing should be done at once at '/editor' page
	//
	//changing order and adding/deleting views
	/*@GetMapping("/admin/views")
	public String viewsPage(){
		return "admin/views";
	}*/

}
