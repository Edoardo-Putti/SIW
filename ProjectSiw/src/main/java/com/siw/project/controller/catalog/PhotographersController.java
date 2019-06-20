package com.siw.project.controller.catalog;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.siw.project.models.Photo;
import com.siw.project.models.Photographer;
import com.siw.project.services.CartService;
import com.siw.project.services.PhotographerService;

@Controller
public class PhotographersController {

	@Autowired
	private CartService cs;

	@Autowired
	private PhotographerService ps;

	@RequestMapping(value = "/catalog/photographers", method = RequestMethod.GET)
	public ModelAndView displayAlbums(HttpSession session) {
		ModelAndView mav = new ModelAndView("catalog/photographers");
		mav.addObject("photographers", ps.all());
		return cs.photoSelection(mav, session);
	}

	@RequestMapping(value = "/catalog/photographer/{photographerId}/{pageNumber}", method = RequestMethod.GET)
	public ModelAndView displayAlbum(@PathVariable("photographerId") Long photographerId, @PathVariable("pageNumber") Integer pageNumber, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("catalog/photographer");

		return getPhotographerPage(cs.photoSelection(mav, httpSession), ps.findById(photographerId), pageNumber);
	}

	@RequestMapping(value = "/catalog/photographer/{photographerId}", method = RequestMethod.GET)
	public ModelAndView displayAlbum(@PathVariable("photographerId") Long photographerId, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("catalog/photographer");

		return getPhotographerPage(cs.photoSelection(mav, httpSession), ps.findById(photographerId), 0);
	}


	ModelAndView getPhotographerPage(ModelAndView mav, Photographer photographer, Integer pageNumber) {
		List<Photo> photos = photographer.getPhotos();
		PagedListHolder<Photo> page = new PagedListHolder<>(photos);
		page.setPageSize(9);
		page.setPage(pageNumber);
		mav.addObject("photographer", photographer);
		mav.addObject("catalog", page.getPageList());
		mav.addObject("page", page.getPage());
		mav.addObject("pages", page.getPageCount());
		return mav;
	}
}
