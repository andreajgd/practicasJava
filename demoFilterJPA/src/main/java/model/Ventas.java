package model;

import config.FilterEntityRestrictiva;
import jakarta.persistence.*;
import jdk.jfr.Name;

@Entity
@NamedQuery(name = "Ventas.all", query = "select e from Ventas e")
public class Ventas extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY) //muchas instancias de esta entidad pueden estar asociadas a un solo Usuario.
    @JoinColumn(name = "idUsuario", referencedColumnName = "id")
    private Usuario usuario;
    private String producto;
    private String venta;


    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getVenta() {
        return venta;
    }

    public void setVenta(String venta) {
        this.venta = venta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Ventas{" +
                "usuario=" + usuario +
                ", producto='" + producto + '\'' +
                ", venta='" + venta + '\'' +
                '}';
    }
}
