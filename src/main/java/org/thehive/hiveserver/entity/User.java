package org.thehive.hiveserver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static org.thehive.hiveserver.validation.ValidationConstraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user")
@Table(name = "users")
public class User extends BaseEntity {

    @Length(min = USER_USERNAME_LENGTH_MIN, max = USER_USERNAME_LENGTH_MAX, message = "{user.username.length}")
    @Pattern(regexp = USER_USERNAME_PATTERN_REGEXP, message = "{user.username.pattern}")
    @Column(unique = true, nullable = false, length = 64)
    private String username;

    @Email(message = "{user.email.email}")
    @Column(unique = true, nullable = false, length = 256)
    private String email;

    @Length(min = USER_USERNAME_LENGTH_MIN, max = USER_PASSWORD_LENGTH_MAX, message = "{user.password.length}")
    @Pattern(regexp = USER_PASSWORD_PATTERN_REGEXP, message = "{user.password.pattern}")
    @Column(nullable = false, length = 256)
    private String password;

    @Valid
    @NotNull(message = "{user.userinfo}")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_info_id", nullable = false)
    private UserInfo userInfo;

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

}
