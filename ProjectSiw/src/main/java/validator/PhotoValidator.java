package validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import models.Photo;

@Component
public class PhotoValidator implements Validator{
	
	@Override
	public boolean supports(Class<?> c) {
		return Photo.class.equals(c);
	}
	
	@Override
	public void validate(Object toValid,Errors e) {
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "name", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "extension", "required");
	}

}
