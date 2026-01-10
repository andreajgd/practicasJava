package model;

import config.IRestrictiva;
import config.Restrictiva;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@NamedQuery(name = "Libro.all", query = "select e from Libro e")
@Table(name = "Libro")
@Restrictiva(campo= "restrictiva", value= "false")
public class Libro extends BaseEntity implements IRestrictiva {
    private String name;
    private String isbn;
    private boolean restrictiva;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public boolean isRestrictiva() {
        return restrictiva;
    }

    public void setRestrictiva(boolean restrictiva) {
        this.restrictiva = restrictiva;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "name = '" + name + '\''+
                ", isbn='" + isbn + '\'' +
                ", restrictiva=" + restrictiva +
                '}';
    }
}
