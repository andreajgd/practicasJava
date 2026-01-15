package model;

import config.IUsuario;
import config.RestrictionType;
import config.RestrictivaUsuario;
import jakarta.persistence.*;

@Entity
@NamedQuery(
        name="Ventas.all",query = "select e from Ventas e"
)
@RestrictivaUsuario(
        campo = "idUsuario"
        ,tipo = RestrictionType.CURRENT_USER
)
public class Ventas extends BaseEntity implements IUsuario {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idUsuario",referencedColumnName = "id")
    private Usuario usuario;

    private String producto;

    private double totalVenta;

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }



    @Override
    public String toString() {
        return "Ventas{" +
                "producto='" + producto + '\'' +
                ", totalVenta=" + totalVenta +
                '}';
    }
}