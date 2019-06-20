package com.siw.project.controller.admin.album;

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
public class CartController {
	
	@Autowired
	private PhotoRepository pr;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "admin/album/selection/add/{photoId}",method = RequestMethod.POST)
	public ResponseEntity<String> addPhoto(@PathVariable("photoId") Long pId,HttpSession session){
		String body= "";
		Optional<Photo> opt = pr.findById(pId);
		if(opt.isPresent()) {
			Photo p = opt.get();
			String n = p.getName();
			Set<Long> photoCart;
			if(session.getAttribute("albumSelection")==null) {
				photoCart = new HashSet<>();
			} else {
				photoCart = (Set<Long>) session.getAttribute("albumSelection");
			}
			if(photoCart.contains(pId)) {
				return new ResponseEntity<String>(body,HttpStatus.NOT_ACCEPTABLE);
			}
			photoCart.add(pId);
			session.setAttribute("albumSelction", photoCart);
			
			body = "{\"name\":\""+ n + "\"}";
			return new ResponseEntity<String>(body, HttpStatus.OK);
		}
		 else {
				return new ResponseEntity<String>(body, HttpStatus.NOT_ACCEPTABLE);
			}
	}

	
	@RequestMapping(value = "admin/album/selection/remove/{photoId}", method = RequestMethod.POST)
	public @ResponseBody byte[] removePhoto(@PathVariable("photoId") Long photoId, HttpSession session) {
		String body = "";
		
		//Session handler here
		@SuppressWarnings("unchecked")
		Set<Long> photosCart = (Set<Long>) session.getAttribute("albumSelection");
		photosCart.remove(photoId);
		session.setAttribute("albumSelection", photosCart);
		
		return body.getBytes();
	}
}
