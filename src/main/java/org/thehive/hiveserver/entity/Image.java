package org.thehive.hiveserver.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "imgae")
@Table(name = "images")
public class Image extends BaseEntity {

    @Transient
    private byte[] content;

}
