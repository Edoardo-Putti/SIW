package com.siw.project.controller.catalog;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.siw.project.models.Album;
import com.siw.project.models.Photo;
import com.siw.project.sevices.AlbumService;
import com.siw.project.sevices.CartService;

@Controller
public class AlbumsController {

	@Autowired
	private AlbumService as;
	
	@Autowired
	private CartService cs;
	
	@RequestMapping(value = "/catalog/albums/{pageNumber}",method = RequestMethod.GET)
	public ModelAndView displayAlbums(@PathVariable("pageNumber") Integer pN,HttpSession s) {
		ModelAndView mav = new ModelAndView("catalog/albums");
		return getAlbumsPage(cs.photoSelection(mav, s),pN);
	}
	
	@RequestMapping(value = "/catalog/albums", method = RequestMethod.GET)
	public ModelAndView displayAlbums(HttpSession session) {
		ModelAndView mav = new ModelAndView("catalog/albums");

		return getAlbumsPage(cs.photoSelection(mav, session),0);
	}

	@RequestMapping(value = "/catalog/album/{albumId}/{pageNumber}", method = RequestMethod.GET)
	public ModelAndView displayAlbum(@PathVariable("albumId") Long albumId, @PathVariable("pageNumber") Integer pageNumber, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("catalog/album");

		return getAlbumPage(cs.photoSelection(mav, httpSession), as.findById(albumId), pageNumber);
	}

	@RequestMapping(value = "/catalog/album/{albumId}", method = RequestMethod.GET)
	public ModelAndView displayAlbum(@PathVariable("albumId") Long albumId, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("catalog/album");

		return getAlbumPage(cs.photoSelection(mav, httpSession), as.findById(albumId), 0);
	}

	ModelAndView getAlbumPage(ModelAndView mav, Album album, Integer pageNumber) {
		List<Photo> photos = album.getPhotos();
		PagedListHolder<Photo> page = new PagedListHolder<>(photos);
		page.setPageSize(9);
		page.setPage(pageNumber);
		mav.addObject("album", album);
		mav.addObject("catalog", page.getPageList());
		mav.addObject("page", page.getPage());
		mav.addObject("pages", page.getPageCount());
		return mav;
	}

	ModelAndView getAlbumsPage(ModelAndView mav, Integer pageNumber) {
		Page<Album> page = as.albumPage(pageNumber);
		mav.addObject("catalog", page.getContent());
		mav.addObject("page", page.getNumber());
		mav.addObject("pages", page.getTotalPages());
		return mav;
	}
}
