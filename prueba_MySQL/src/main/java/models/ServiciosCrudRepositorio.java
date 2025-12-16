package models;

import repository.CrudRepositorio;
import util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class ServiciosCrudRepositorio implements CrudRepositorio<Producto> {

    private final Connection connection = Conexion.getConnection();

    public ServiciosCrudRepositorio() {
    }

    @Override
    public List<Producto> listar() {
        return List.of();
    }

    @Override
    public Producto guardar(Producto producto) {
        String query;
        if (producto.getId() > 0) {
            query = "UPDATE productos SET nombre=?, precio=?, id_categoria=? WHERE id=?";
        } else {
            query = "INSERT INTO productos (nombre, precio, id_categoria) VALUES (?,?,?,?)";
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, producto.getNombre());
            preparedStatement.setInt(2, producto.getPrecio());
            preparedStatement.setInt(3,producto.getCategoria().getId());
            if(producto.getId()>0){
                try{
                    ResultSet resultSet = preparedStatement.getGeneratedKeys();
                    if(resultSet.next()){
                        //result set devielve una fila
                        new Producto(resultSet.getInt(1, ));
                    }

                }catch(Exception e){
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Producto buscarPorId(Integer id) {
        return null;
    }

    @Override
    public void eliminar(Integer id) {

    }
}
