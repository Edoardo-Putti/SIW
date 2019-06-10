package controller;

import java.io.File;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class FolderController {

	private static String UPLOADED_FOLDER = "/home/edoardo/Downloads/spring-boot-file-upload-example/src/main/resources/static/uploaded/";

	@GetMapping("/folder")
	public String folder() {
		return "folder";
	}
	
    @RequestMapping(value = "/folder", method = RequestMethod.POST)
    @Secured("USER")
    public String createFolder(String name) {
    	String path = UPLOADED_FOLDER +'/'+name;
    	File file = new File(path);
        if (!file.exists()) {
            if (file.mkdir()) {
            	return "Directory is created!";
            	
            } else {
            	return "Failed to create directory!";
            	
            }
        }
        return "yea";
    }
    
}
