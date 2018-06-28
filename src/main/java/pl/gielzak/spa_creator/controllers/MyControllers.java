package pl.gielzak.spa_creator.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.gielzak.spa_creator.dao.BasicConfRepository;
import pl.gielzak.spa_creator.dao.PageRepository;
import pl.gielzak.spa_creator.dao.ViewRepository;
import pl.gielzak.spa_creator.domain.BasicConf;
import pl.gielzak.spa_creator.domain.FormattedView;
import pl.gielzak.spa_creator.domain.Page;
import pl.gielzak.spa_creator.domain.PageView;
import pl.gielzak.spa_creator.domain.View;

@Controller
public class MyControllers {
	
	final String ICON_SAVE_PATH = "D:\\spa_creator\\icon\\";
	
	@Autowired
	BasicConfRepository basicConfRepo;
	
	@Autowired
	PageRepository pageRepo;
	
	@Autowired
	ViewRepository viewRepo;
	
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
	
	public List<View> getAllViews(){
		return viewRepo.findAll();
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
	
	@GetMapping("/login")
	public String loginPage(Model model){
		model.addAttribute("basicConf", getBasicConfiguration());
		return "login";
	}
	
	//some kind of menu with few options
	@GetMapping("/admin")
	public String adminPage(Model model){
		model.addAttribute("basicConf", getBasicConfiguration());
		return "admin";
	}
	
	//configure title of website, icon, add fonts etc.
	@GetMapping("/admin/basic-configuration")
	public String basicConfPage(Model model){
		model.addAttribute("basicConf", getBasicConfiguration());
		return "admin/basic";
	}
	
	@PostMapping("/admin/basic-configuration")
	public String basicConfSavePage(Model model, 
			@RequestParam("title") String title, 
			@RequestParam("description") String description,
			@RequestParam("icon") String icon,
			@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes){
		
		//if both params are not empty we can add document to DB
		if(!title.trim().isEmpty()&&!description.trim().isEmpty()){
			BasicConf bc = new BasicConf();
			bc.title = title;
			bc.description = description;
			bc.icon = icon;
			bc.createdAt = new Date();
			if(!file.isEmpty() && file.getOriginalFilename().substring(file.getOriginalFilename().length()-3).equals("ico")){
				try {
					String fileName = "favicon-" + new Date().getTime() + ".ico";
					file.transferTo(new File(ICON_SAVE_PATH + fileName));
					bc.icon = fileName;
				} catch (IOException e) {
					e.printStackTrace();
				}				
			}
			basicConfRepo.insert(bc);
		}
		else{
			redirectAttributes.addFlashAttribute("errorNotEnoughParameters", "You need to define at least title and description");
		}
		return "redirect:/admin/basic-configuration";
	}
	
	//edit the look of website (colors, content[text], images)
	@GetMapping("/admin/editor")
	public String editorPage(Model model){
		model.addAttribute("basicConf", getBasicConfiguration());
		model.addAttribute("page", getLatestPage());
		model.addAttribute("views", getAllViews());
		return "admin/editor";
	}
	
	@PostMapping("/admin/editor")
	@ResponseBody
	public String editorSavePage(@RequestBody Page page){
		
		if(page.createdAt==null)
			page.createdAt = new Date();
		
		pageRepo.insert(page);
		
		/* in ajax call we use 'dataType':'json'
		 * because of that we need to return json formated data
		 * otherwise javascript will call 'error' callback every single time
		 */
		return "{\"status\":\"ok\"}";
	}
	
	//add/delete modules (cookies, facebook on the side, etc.)
	@GetMapping("/admin/modules")
	public String modulesPage(Model model){
		model.addAttribute("basicConf", getBasicConfiguration());
		return "admin/modules";
	}

}
