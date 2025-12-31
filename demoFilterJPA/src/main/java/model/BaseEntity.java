package model;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.UuidGenerator;

@MappedSuperclass

public class BaseEntity {
    @Id
    @UuidGenerator
    private String id;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
