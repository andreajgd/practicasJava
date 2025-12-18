package repositorio;

import modelos.Producto;
import utils.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiciosCrudRepositorio implements CrudRepositorio<Producto> {

    private final Connection connection = Conexion.getConnection();

    @Override
    public List<Producto> listar() {
        List<Producto> productos = new ArrayList<>();
        String query = "SELECT * FROM productos";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("precio"),
                        null
                );
                productos.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productos;
    }

    @Override
    public Producto guardar(Producto producto) {
        String query;

        if (producto.getId() != null && producto.getId() > 0) {
            query = "UPDATE productos SET nombre = ?, precio = ?, id_categoria = ? WHERE id = ?";
        } else {
            query = "INSERT INTO productos(nombre, precio, id_categoria) VALUES (?, ?, ?)";
        }

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, producto.getNombre());
            preparedStatement.setInt(2, producto.getPrecio());
            preparedStatement.setInt(3, producto.getCategoria().getId());

            if (producto.getId() != null && producto.getId() > 0) {
                preparedStatement.setInt(4, producto.getId());
            }

            preparedStatement.executeUpdate();

            if (producto.getId() == null || producto.getId() == 0) {
                try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                    if (rs.next()) {
                        producto.setId(rs.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return producto;
    }

    @Override
    public Producto buscarPorId(Integer id) {
        Producto p = null;
        String query = "SELECT * FROM productos WHERE id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                p = new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("precio"),
                        null
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return p;
    }

    @Override
    public void eliminar(Integer id) {
        String query = "DELETE FROM productos WHERE id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
