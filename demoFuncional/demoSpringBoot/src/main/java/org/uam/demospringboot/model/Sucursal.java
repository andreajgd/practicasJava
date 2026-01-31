package org.uam.demospringboot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "sucursal")
public class Sucursal extends EntityBase {
    private String nombre;
    private String ubicacion;
    private String direccion;



}
