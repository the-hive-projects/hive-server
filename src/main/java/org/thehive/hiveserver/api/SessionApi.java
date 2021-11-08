package org.thehive.hiveserver.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.thehive.hiveserver.entity.Session;
import org.thehive.hiveserver.service.SessionService;

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
    public Session save(@RequestBody Session session) {
        return sessionService.save(session);
    }

}