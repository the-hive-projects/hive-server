package org.thehive.hiveserver.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "session")
@Table(name = "sessions")
public class Session {

    @Id
    @GenericGenerator(name = "session_id_generator",strategy = "org.thehive.hiveserver.session.SessionIdGenerator")
    @GeneratedValue(generator = "session_id_generator")
    private String id;

    private String name;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Long createdAt;

}
