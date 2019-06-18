package validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import models.Photographer;

@Component
public class PhotographerValidator implements Validator{

	@Override
	public boolean supports(Class<?> c) {
		return Photographer.class.equals(c);
		
	}

	@Override
	public void validate(Object toValid, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "required");
		
	}

	
}
