package org.thehive.hiveserver.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.thehive.hiveserver.entity.User;
import org.thehive.hiveserver.security.SecurityUtils;
import org.thehive.hiveserver.service.UserService;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserApi {

    private final UserService userService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable("id") int id) {
        return userService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public User updateUser(@RequestBody User user, HttpServletRequest request) {
        var securityUser = SecurityUtils.extractSecurityUser(request);
        return userService.update(securityUser.getId(), user);
    }

}
