package com.siw.project.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.siw.project.controller.validator.CustomerValidator;
import com.siw.project.controller.validator.UsageRequestValidator;
import com.siw.project.models.Customer;
import com.siw.project.models.Photo;
import com.siw.project.models.UsageRequest;
import com.siw.project.services.CartService;
import com.siw.project.services.CustomerService;
import com.siw.project.services.PhotoService;
import com.siw.project.services.UsageRequestService;

@Controller
public class UserRequestForm {
	
	@Autowired
	private UsageRequestService urs;
	
	@Autowired
	private UsageRequestValidator urv;
	
	@Autowired 
	private CustomerValidator customerValidator;
	
	@Autowired
	private CustomerService cs;
	
	@Autowired
	private PhotoService ps;
	
	@Autowired
	private CartService cartS;
	
	@RequestMapping(value="/request",method= RequestMethod.POST)
	public String newRequest(@Valid @ModelAttribute("customer") Customer customer, 
			Model model, BindingResult bindingResult, HttpSession session) {
		this.customerValidator.validate(customer, bindingResult);
		UsageRequest request = new UsageRequest();

		Object obj = session.getAttribute("photos");
		if(obj != null) {
			List<Photo> photos = (List<Photo>) ps.findAllById((Set<Long>)obj);

			request.setPhotos(photos);
			request.setCustomer(customer);
			this.urv.validate(request, bindingResult);
			if(!bindingResult.hasErrors()) {
				this.cs.add(customer);
				this.urs.add(request);
				model.addAttribute("request", request);
				return "requests/requestConfirm";
			}
		}
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof OAuth2User) {
			model.addAttribute("confirm", "/googleRequest");
		} else {
			model.addAttribute("confirm", "/oauth2/authorization/google");
		}
		return "requests/requestForm";
	}

	@RequestMapping(value="/googleRequest",method= RequestMethod.GET)
	public String newGoogleRequest(Model model, HttpSession session) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof OAuth2User) {
			OAuth2User user = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			Customer customer = new Customer();
			customer.setFirstName((String) user.getAttributes().get("given_name"));
			customer.setLastName((String) user.getAttributes().get("family_name"));
			customer.setMail((String) user.getAttributes().get("email"));

			BindingResult customerBindingResult = new BindException(customer, "Customer");
			this.customerValidator.validate(customer, customerBindingResult);

			UsageRequest request = new UsageRequest();
			Object obj = session.getAttribute("photos");
			if(obj != null) {
				List<Photo> photos = (List<Photo>) ps.findAllById((Set<Long>)obj);
				request.setPhotos(photos);
				request.setCustomer(customer);

				BindingResult usageRequestBindingResult = new BindException(request, "UsageRequest");
				this.urv.validate(request, usageRequestBindingResult);

				if(!customerBindingResult.hasErrors() && !usageRequestBindingResult.hasErrors()) {
					this.cs.add(customer);
					this.urs.add(request);
					model.addAttribute("customer", customer);
					model.addAttribute("request", request);
					session.removeAttribute("photos");
					return "requests/requestConfirm";
				}
			}
		}
		if(principal instanceof OAuth2User) {
			model.addAttribute("confirm", "/googleRequest");
		} else {
			model.addAttribute("confirm", "/oauth2/authorization/google");
		}
		model.addAttribute("customer", new Customer());
		return "requests/requestForm";
	}
	
	@RequestMapping("/newRequest")
	public ModelAndView addRequest(HttpSession session){
		ModelAndView mav = new ModelAndView("requests/requestForm");
		Customer customer = new Customer();
		mav.addObject("customer", customer);
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof OAuth2User) {
			mav.addObject("confirm", "/googleRequest");
		} else {
			mav.addObject("confirm", "/oauth2/authorization/google");
		}
		
		return cartS.photoSelection(mav, session);
	}
}
