package model;

import config.IUsuario;
import config.RestrictionType;
import config.Restrictiva;
import config.RestrictivaUsuario;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@NamedQuery(
        name="Usuario.all"
        ,query="select e from Usuario e"
)
@Table(name="Usuario")
@Restrictiva(campo = "id")
@RestrictivaUsuario(
        campo = "",
        tipo = RestrictionType.CURRENT_USER
)
public class Usuario extends BaseEntity implements IUsuario {
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
