package com.siw.project.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.siw.project.models.Photo;
import com.siw.project.repository.PhotoRepository;

@Controller
public class UserCartController {

	@Autowired
	PhotoRepository photoRepository;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cart/add/{photoId}", method = RequestMethod.POST)
	public ResponseEntity<String> addPhoto(@PathVariable("photoId") Long photoId, HttpSession session) {
		String body = "";
		Optional<Photo> opt = photoRepository.findById(photoId);
		if(opt.isPresent()) {
			Photo photo = opt.get();
			String name = photo.getName();
			Set<Long> photosCart;
			if(session.getAttribute("photos")==null) {
				photosCart = new HashSet<>();
			} else {
				photosCart = (Set<Long>) session.getAttribute("photos");
			}
			if(photosCart.contains(photoId)) {
				return new ResponseEntity<String>(body, HttpStatus.NOT_ACCEPTABLE);
			}
			photosCart.add(photoId);
			session.setAttribute("photos", photosCart);
			
			body = "{\"name\":\"" + name + "\"}";
			return new ResponseEntity<String>(body, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(body, HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@RequestMapping(value = "/cart/remove/{photoId}", method = RequestMethod.POST)
	public @ResponseBody byte[] removePhoto(@PathVariable("photoId") Long photoId, HttpSession session) {
		String body = "";
		
		//Session handler here
		@SuppressWarnings("unchecked")
		Set<Long> photosCart = (Set<Long>) session.getAttribute("photos");
		photosCart.remove(photoId);
		session.setAttribute("photos", photosCart);
		
		
		return body.getBytes();
	}
}
