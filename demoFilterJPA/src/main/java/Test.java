import config.SecurityContext;
import model.Usuario;
import model.Ventas;
import model.Autor;
import service.ImplDAO;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        ImplDAO dao = new ImplDAO();

        //lista de usuarios
        Usuario[] usuarios = {
                crearUsuario("UsuarioA", "ApellidoA", dao),
                crearUsuario("UsuarioB", "ApellidoB", dao),
                crearUsuario("UsuarioC", "ApellidoC", dao)
        };

        //datos cada usuario
        for (Usuario usuario : usuarios) {
            crearDatosParaUsuario(usuario, dao);
        }

        //filtro dinámico con cada usuario
        for (int i = 0; i < usuarios.length; i++) {
            Usuario usuario = usuarios[i];
            System.out.println("\n=== Usuario " + (i+1) + " hace Login (" +
                    usuario.getNombre() + ") ===");

            SecurityContext.setCurrentUser(usuario.getId());

            System.out.println("Consultando Ventas:");
            List<Ventas> ventas = dao.getAll("Ventas.all", Ventas.class);
            System.out.println("Resultados: " + ventas.size());

            System.out.println("Consultando Usuarios:");
            List<Usuario> listaUsuarios = dao.getAll("Usuario.all", Usuario.class);
            System.out.println("Resultados: " + listaUsuarios.size());
        }
    }

    public static Usuario crearUsuario(String nombre, String apellido, ImplDAO dao) {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        dao.insert(usuario);
        System.out.println("Usuario creado: " + nombre + " " + apellido +
                " (ID: " + usuario.getId() + ")");
        return usuario;
    }

    public static void crearDatosParaUsuario(Usuario usuario, ImplDAO dao) {
        //Crear ventas
        for (int i = 1; i <= 2; i++) {
            Ventas venta = new Ventas();
            venta.setUsuario(usuario);
            venta.setProducto("Producto " + i + " de " + usuario.getNombre());
            venta.setTotalVenta(100 * i);
            dao.insert(venta);
        }

        //Crear autor
        Autor autor = new Autor();
        autor.setNombre("Autor " + usuario.getNombre());
        autor.setApellido(usuario.getApellido());
        dao.insert(autor);
    }
}