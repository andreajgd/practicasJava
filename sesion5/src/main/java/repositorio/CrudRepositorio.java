package repositorio;

import java.util.List;

public interface CrudRepositorio<T> {

    List<T> listar();

    T guardar(T t);

    T buscarPorId(Integer id);

    void eliminar(Integer id);
}
