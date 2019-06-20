package com.siw.project.controller.admin;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.siw.project.controller.validator.PhotoValidator;
import com.siw.project.models.Photo;
import com.siw.project.models.Photographer;
import com.siw.project.services.PhotoService;
import com.siw.project.services.PhotographerService;

@Controller
public class UploadController {

    @Autowired
    private PhotoService ps;
    
    @Autowired
    private PhotoValidator pv;
    
    @Autowired
    private PhotographerService pgs;
    
    @RequestMapping(value = "/admin/photo/new",method = RequestMethod.POST)
    public String uploadPhoto(@Valid @RequestParam("image") MultipartFile[] image, @Valid @RequestParam("photographer") Long id, Model model) {
    	Photo p = new Photo();
    	Photographer pg = pgs.findById(id);
    	try {
    		if(image[0].getContentType().contains("image/")) {
    			p.setName(image[0].getOriginalFilename());
    			p.setImage(image[0].getBytes());
    			p.setExtension(image[0].getContentType().replace("image/", ""));
    			p.setPhotographer(pg);
    			
    			BindingResult br =  new BindException(p,"photo");
    			this.pv.validate(p, br);
    			if(!br.hasErrors()) {
    				this.ps.salva(p);
    				model.addAttribute("photo",p);
    				return "admin/photo/photoUploadConfirm";
    			}
    			
    		}
    	}
    	catch(IOException e) {
    		e.printStackTrace();
    	}
    	return "admin/photo/newPhoto";
    }
    
    @RequestMapping(value="/admin/photo/new",method= RequestMethod.GET)
	public String newPhoto(Model model) {
    	model.addAttribute("photographers", pgs.all());
		return "admin/photo/newPhoto";
	}

}