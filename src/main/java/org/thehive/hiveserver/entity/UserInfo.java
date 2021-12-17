package org.thehive.hiveserver.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import static org.thehive.hiveserver.validation.ValidationConstraints.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "userInfo")
@Table(name = "user_infos")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Length(min = USERINFO_FIRSTNAME_LENGTH_MIN, max = USERINFO_FIRSTNAME_LENGTH_MAX, message = "{userinfo.firstname.length}")
    @Pattern(regexp = USERINFO_FIRSTNAME_PATTERN_REGEXP, message = "{userinfo.firstname.pattern}")
    private String firstname;

    @Length(min = USERINFO_LASTNAME_LENGTH_MIN, max = USERINFO_LASTNAME_LENGTH_MAX, message = "{userinfo.lastname.length}")
    @Pattern(regexp = USERINFO_LASTNAME_PATTERN_REGEXP, message = "{userinfo.lastname.pattern}")
    private String lastname;

    @CreatedDate
    private Long creationTime;

}
