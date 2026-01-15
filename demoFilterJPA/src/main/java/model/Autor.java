package model;

import config.RestrictionType;
import config.Restrictiva;
import config.RestrictivaUsuario;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@NamedQuery(
        name="Autor.all"
        ,query="select e from Autor e"
)
@Table(name="Autor")
@Restrictiva(campo = "id")
@RestrictivaUsuario(
        campo = "id",
        tipo = RestrictionType.CURRENT_USER
)

public class Autor extends BaseEntity {
    private String nombre;
    private String apellido;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

}
