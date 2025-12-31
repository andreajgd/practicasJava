package model;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@NamedQuery(name="Estudiante.all", query = "select e from Estudiante")
@Table(name = "Estudiante")
public class Estudiante extends BaseEntity implements IRestrictiva{

    private String nombre;
    private String apellido;
    private boolean restrictiva;

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

    public boolean isRestrictiva() {
        return restrictiva;
    }

    public void setRestrictiva(boolean restrictiva) {
        this.restrictiva = restrictiva;
    }
}
