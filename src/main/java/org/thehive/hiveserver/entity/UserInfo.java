package org.thehive.hiveserver.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "userInfo")
@Table(name = "user_infos")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String firstname;

    private String lastname;

    private Long createdAt;

}
