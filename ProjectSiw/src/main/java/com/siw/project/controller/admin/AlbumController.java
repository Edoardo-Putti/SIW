package com.siw.project.controller.admin;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.siw.project.controller.validator.AlbumValidator;
import com.siw.project.models.Album;
import com.siw.project.models.Photo;
import com.siw.project.sevices.AlbumService;
import com.siw.project.sevices.PhotoService;

@Controller
public class AlbumController {

	@Autowired
	private PhotoService ps;
	
	@Autowired
	private AlbumService as;
	
	@Autowired
	private AlbumValidator av;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/admin/album/new", method= RequestMethod.POST)
	public String createAlbum(@Valid @ModelAttribute("album") Album a, Model model, HttpSession session) {
		a.setPhotos((List<Photo>)ps.findAllById((Set<Long>)session.getAttribute("albumSelection")));
		BindingResult bindingResult = new BindException(a, "Album");
		this.av.validate(a, bindingResult);
		if(!bindingResult.hasErrors()) {
			this.as.inserisci(a);
			model.addAttribute(a);
			return "admin/album/albumConfirm";
		}
		else {
			return "admin/album/newAlbum";
		}
	}
    
	@RequestMapping(value="/admin/album/new",method= RequestMethod.GET)
	public String newAlbum(Model model, HttpSession session) {
		model.addAttribute("album", new Album());
		session.removeAttribute("albumSelection");
		return "admin/album/newAlbum";
	}
}
