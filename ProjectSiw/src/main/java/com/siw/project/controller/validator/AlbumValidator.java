package com.siw.project.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.siw.project.models.Album;

@Component
public class AlbumValidator implements Validator{
	
	@Override
	public boolean supports(Class<?> c) {
		return Album.class.equals(c);
	}
	
	@Override
	public void validate(Object toValid,Errors e) {
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "name", "required");
		Album a =(Album) toValid;
		if(a.getPhotos().isEmpty()) {
			e.rejectValue("photos","required");
		}
	}

}
