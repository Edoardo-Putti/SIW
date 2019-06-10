package controller;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FolderController {

	private static String UPLOADED_FOLDER = "/home/edoardo/Downloads/spring-boot-file-upload-example/src/main/resources/static/uploaded/";

	@GetMapping("/folder")
	public String folder() {
		return "folder";
	}
	
    @PostMapping("/folder")
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
