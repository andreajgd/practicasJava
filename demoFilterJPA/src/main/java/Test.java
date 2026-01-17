import config.Global;
import config.SecurityContext;
import model.Usuario;
import model.Ventas;
import model.Autor;
import service.ImplDAO;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        ImplDAO dao = new ImplDAO();

        Usuario usuario1 = crearUsuario("Karen", "Bonilla", dao);
        Usuario usuario2 = crearUsuario("Wilfredo", "Gómez", dao);

        //datos para ambos
        crearDatosParaUsuario(usuario1, dao);
        crearDatosParaUsuario(usuario2, dao);


        System.out.println("\n1.Juan hace Login");
        Global.IDUSUARIO = usuario1.getId();

        System.out.println("Consultando Ventas:");
        List<Ventas> ventasJuan = dao.getAll("Ventas.all", Ventas.class);
        System.out.println("Resultados: " + ventasJuan.size());
        ventasJuan.forEach(v -> System.out.println(" - " + v.getProducto()));

        System.out.println("\nUsuario:");
        List<Usuario> usuariosJuan = dao.getAll("Usuario.all", Usuario.class);
        System.out.println("Resultados: " + usuariosJuan.size());

        System.out.println("\n2. María hace Login");
        config.SecurityContext.setCurrentUser(usuario2.getId());

        System.out.println("Consultando Ventas: ");
        List<Ventas> ventasMaria = dao.getAll("Ventas.all", Ventas.class);
        System.out.println("Resultados: " + ventasMaria.size());
        ventasMaria.forEach(v -> System.out.println(" - " + v.getProducto()));

        System.out.println("\nUsuario:");
        List<Usuario> usuariosMaria = dao.getAll("Usuario.all", Usuario.class);
        System.out.println("Resultados: " + usuariosMaria.size());
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