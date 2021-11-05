package org.thehive.hiveserver.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.thehive.hiveserver.entity.Session;
import org.thehive.hiveserver.entity.User;
import org.thehive.hiveserver.security.SecurityUtils;
import org.thehive.hiveserver.service.SessionService;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/session")
public class SessionApi {

    private final SessionService sessionService;

    @GetMapping("/{id}")
    @Operation(security = @SecurityRequirement(name = "generalSecurity"))
    public Session get(@PathVariable("id") String id) {
        return sessionService.findById(id);
    }

    @PostMapping
    @Operation(security = @SecurityRequirement(name = "generalSecurity"))
    public Session save(@RequestBody Session session, HttpServletRequest request) {
        var securityUser = SecurityUtils.extractSecurityUser(request);
        session.setUser(new User());
        session.getUser().setId(securityUser.getId());
        return sessionService.save(session);
    }

}
