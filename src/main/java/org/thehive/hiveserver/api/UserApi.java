package org.thehive.hiveserver.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thehive.hiveserver.entity.User;
import org.thehive.hiveserver.security.SecurityUtils;
import org.thehive.hiveserver.service.UserService;
import org.thehive.hiveserver.validation.UserUniquenessValidationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserApi {

    private final UserService userService;
    private final UserUniquenessValidationFilter userUniquenessValidationFilter;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(security = @SecurityRequirement(name = "generalSecurity"))
    public User getUser(HttpServletRequest request) {
        var securityUser = SecurityUtils.extractSecurityUser(request);
        return userService.findById(securityUser.getId());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(security = @SecurityRequirement(name = "generalSecurity"))
    public User getUser(@PathVariable("id") int id) {
        return userService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@Valid @RequestBody User user, BindingResult bindingResult) throws BindException {
        userUniquenessValidationFilter.uniquenessFilter(user, bindingResult);
        return userService.save(user);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(security = @SecurityRequirement(name = "generalSecurity"))
    public User updateUser(@Valid @RequestBody User user, HttpServletRequest request) {
        var securityUser = SecurityUtils.extractSecurityUser(request);
        return userService.update(securityUser.getId(), user);
    }

}
