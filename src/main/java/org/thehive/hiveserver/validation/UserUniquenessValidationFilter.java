package org.thehive.hiveserver.validation;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.thehive.hiveserver.entity.User;

public interface UserUniquenessValidationFilter {

    void uniquenessFilter(User user, BindingResult bindingResult) throws BindException;

}
