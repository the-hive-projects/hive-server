package org.thehive.hiveserver.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "imgae")
@Table(name = "images")
public class Image extends BaseEntity {

    @Transient
    private byte[] content;

}
