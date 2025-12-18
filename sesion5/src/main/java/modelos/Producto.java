package modelos;

public class Producto {

    private Integer id;
    private String nombre;
    private Integer precio;
    private Categoria categoria;

    public Producto() {
    }

    public Producto(Integer id, String nombre, Integer precio, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getPrecio() {
        return precio;
    }

    public Categoria getCategoria() {
        return categoria;
    }
}
