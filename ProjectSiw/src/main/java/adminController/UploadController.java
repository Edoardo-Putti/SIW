package adminController;

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

import models.Photo;
import services.PhotoService;
import validator.PhotoValidator;

@Controller
public class UploadController {

    @Autowired
    private PhotoService ps;
    
    @Autowired
    private PhotoValidator pv;
    
    @RequestMapping(value = "/admin/photo/new",method = RequestMethod.POST)
    public String uploadPhoto(@Valid @RequestParam("image") MultipartFile[] image, Model model) {
    	Photo p = new Photo();
    	try {
    		if(image[0].getContentType().contains("image/")) {
    			p.setName(image[0].getOriginalFilename());
    			p.setImage(image[0].getBytes());
    			p.setExtension(image[0].getContentType().replace("image/", ""));
    			
    			BindingResult br =  new BindException(p,"photo");
    			this.pv.validate(p, br);
    			if(!br.hasErrors()) {
    				this.ps.salva(p);
    				model.addAttribute("photo",p);
    				return "admin/photo/photoUploadConfirm";
    			}
    			else {
    				return "error";
    			}
    		}
    	}
    	catch(IOException e) {
    		e.printStackTrace();
    	}
    	return "error";
    }
    
    @RequestMapping(value="/admin/photo/new",method= RequestMethod.GET)
	public String newPhoto() {
		return "admin/photo/newPhoto";
	}

}