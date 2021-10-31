package org.thehive.hiveserver.entity;

import lombok.*;
import lombok.experimental.WithBy;

import javax.persistence.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user")
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false, length = 64)
    private String username;

    @Column(unique = true, nullable = false, length = 256)
    private String email;

    @Column(nullable = false, length = 256)
    private String password;

}
