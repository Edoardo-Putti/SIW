package com.siw.project.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.siw.project.models.UsageRequest;

@Component
public class UsageRequestValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return UsageRequest.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UsageRequest r = (UsageRequest) target;
		if(r.getPhotos().isEmpty()) {
			errors.rejectValue("photos", "required");
		}
		
	}
	
	

}
