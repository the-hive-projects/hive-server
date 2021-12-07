package org.thehive.hiveserver.security;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SecurityUser extends User {

    private final int id;
    private final String email;

    public SecurityUser(@NonNull Integer id, @NonNull String username, @NonNull String password, @NonNull String email) {
        super(username, password, Collections.emptyList());
        this.id = id;
        this.email = email;
    }

}
