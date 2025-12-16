package repository;
import java.util.List;

public interface CrudRepositorio<Producto> {
    List<Producto> listar();
    Producto guardar(Producto producto);
    Producto buscarPorId(Integer id);
    void eliminar(Integer id);
}
