package com.siw.project.controller.catalog;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.siw.project.models.Photo;
import com.siw.project.sevices.CartService;
import com.siw.project.sevices.PhotoService;

@Controller
public class PhotosController {

	@Autowired
	private PhotoService ps;
	
	@Autowired
	private CartService cs;
	
	@RequestMapping(value = "/catalog/photos/{pageNumber}", method = RequestMethod.GET)
	public ModelAndView displayPhotos(@PathVariable("pageNumber") Integer pageNumber, HttpSession session) {
		ModelAndView mav = new ModelAndView("catalog/photos");

		return getPhotoPage(cs.photoSelection(mav, session), pageNumber);
	}
	
	@RequestMapping(value = "/catalog/photos", method = RequestMethod.GET)
	public ModelAndView displayPhotos(HttpSession session) {
		ModelAndView mav = new ModelAndView("catalog/photos");

		return getPhotoPage(cs.photoSelection(mav, session), 0);
	}
	
	 ModelAndView getPhotoPage(ModelAndView mav, Integer pageNumber) {
		Page<Photo> page = ps.photoPage(pageNumber);
        mav.addObject("catalog", page.getContent());
        mav.addObject("page", page.getNumber());
        mav.addObject("pages", page.getTotalPages());
		return mav;
	}
}
