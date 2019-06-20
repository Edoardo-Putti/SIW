package com.siw.project.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.siw.project.sevices.UsageRequestService;

@Controller
public class RequestController {

	@Autowired
	private UsageRequestService us;
	
	@RequestMapping(value = "/admin/requests", method = RequestMethod.GET)
	public ModelAndView displayRequests() {
		ModelAndView mav = new ModelAndView("admin/requests/usageRequests");
		mav.addObject("requests", us.all());
		return mav;
	}
	
	@RequestMapping(value= "/admin/request/{id}", method = RequestMethod.GET)
	public String getUsageRequest(@PathVariable ("id") Long id, Model model) {
		if(id!=null) {
			model.addAttribute("request", this.us.findById(id));
			return "admin/requests/usageRequest";
		}
		else {
			model.addAttribute("requests", this.us.all());
			return "admin/requests/usageRequests";	
		}	
	}

}
