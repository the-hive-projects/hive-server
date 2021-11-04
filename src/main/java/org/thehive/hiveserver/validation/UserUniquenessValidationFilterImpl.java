package org.thehive.hiveserver.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.thehive.hiveserver.entity.User;
import org.thehive.hiveserver.service.UserService;

@RequiredArgsConstructor
public class UserUniquenessValidationFilterImpl implements UserUniquenessValidationFilter {

    private final UserService userService;
    private final String usernameUniquenessErrorMessage;
    private final String emailUniquenessErrorMessage;

    @Override
    public void uniquenessFilter(User user, BindingResult bindingResult)  throws BindException {
        if (bindingResult.hasErrors()) {
            if ((!bindingResult.hasFieldErrors("username")) && userService.existsByUsername(user.getUsername())) {
                bindingResult.addError(new ObjectError("username", usernameUniquenessErrorMessage));
            }
            if ((!bindingResult.hasFieldErrors("email")) && userService.existsByEmail(user.getEmail())) {
                bindingResult.addError(new ObjectError("email", emailUniquenessErrorMessage));
            }
            throw new BindException(bindingResult);
        } else {
            if (userService.existsByUsername(user.getUsername())) {
                bindingResult.addError(new ObjectError("username", usernameUniquenessErrorMessage));
            }
            if (userService.existsByEmail(user.getEmail())) {
                bindingResult.addError(new ObjectError("email", emailUniquenessErrorMessage));
            }
            if (bindingResult.hasErrors())
                throw new BindException(bindingResult);
        }
    }

}
