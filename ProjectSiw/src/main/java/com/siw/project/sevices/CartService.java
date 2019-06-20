package com.siw.project.sevices;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.siw.project.models.Photo;

@Service
public class CartService {

	@Autowired
	private PhotoService ps;
	
	public ModelAndView photoSelection(ModelAndView mav,HttpSession s) {
		Object selection = s.getAttribute("photos");
		if(selection != null) {
			@SuppressWarnings("unchecked")
			List<Photo> l = (List<Photo>) ps.findAllById((Set<Long>)selection);
			mav.addObject("selection", l);
		}
		return mav;
	}
	
	
}
