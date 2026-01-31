package org.uam.demospringboot.model;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.UuidGenerator;

@MappedSuperclass
public class EntityBase {
    @Id
    @UuidGenerator
    private String id;
}
