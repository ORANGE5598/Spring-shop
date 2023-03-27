package com.shop.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class AbstractValidator<T> implements Validator {

	@Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public void validate(Object target, Errors errors) {
        try{
            doValidate((T) target, errors); // 유효성 검증 로직
        } catch (IllegalStateException e){
            log.error("중복 검증 에러", e);
            throw e;
        }
    }

    /**
     * 유효성 검증 로직
     **/
    protected abstract void doValidate(final T dto, final Errors errors);
}

