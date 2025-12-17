package modelos;

public class Producto {

    private int id;
    private String nombre;
    private Integer precio;
    private Categoria categoria;

    public Producto() {
    }

    public Producto(int id, String nombre, Integer precio, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
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