package org.thehive.hiveserver.validation.filter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.thehive.hiveserver.entity.User;
import org.thehive.hiveserver.service.UserService;

@RequiredArgsConstructor
public class UserUniquenessValidationFilterUnit implements ValidationFilterUnit<User> {

    private static final String USERNAME_FIELD_NAME = "username";
    private static final String EMAIL_FIELD_NAME = "email";
    private static final String MESSAGE_VALUE_PLACEHOLDER = "{}";

    private final UserService userService;
    private final String usernameErrorMessage;
    private final String emailErrorMessage;

    @Override
    public void applyFilter(@NonNull User user, @NonNull BindingResult bindingResult) {
        if ((!bindingResult.hasFieldErrors(USERNAME_FIELD_NAME)) && userService.existsByUsername(user.getUsername()))
            bindingResult.addError(new ObjectError(USERNAME_FIELD_NAME, injectValueInMessage(usernameErrorMessage, user.getUsername())));
        if ((!bindingResult.hasFieldErrors(EMAIL_FIELD_NAME)) && userService.existsByEmail(user.getEmail()))
            bindingResult.addError(new ObjectError(EMAIL_FIELD_NAME, injectValueInMessage(emailErrorMessage, user.getEmail())));
    }

    private String injectValueInMessage(@NonNull String message, @NonNull String value) {
        return message.replaceAll(MESSAGE_VALUE_PLACEHOLDER, value);
    }

}
