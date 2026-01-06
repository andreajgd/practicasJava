import model.Autor;
import model.Libro;
import model.Usuario;
import model.Ventas;
import service.IDAO;
import service.ImplDAO;
import service.serviceLibro;

public class Test {
    public static void deleteAutor() {
        ImplDAO dao = new ImplDAO();
        dao.delete("a3a7c00e-17b3-4403-a5e5-bf48edc2646d",Autor.class);
    }

    public static void crearAutor(){
        ImplDAO implDAO = new ImplDAO();
        Autor autor = new Autor();
        autor.setNombre("Norman");
        autor.setApellido("Cash");
        implDAO.insert(autor);
    }

    public static void handleLibro(){
        serviceLibro serviceLibro = new serviceLibro();
        /*Libro libro = new Libro();
        libro.setName("Azul");
        libro.setIsbn("234234dr23");*/
        Libro libro = serviceLibro.findById("40c8a155-bdfa-475d-86fe-c0f3ef2e7102",Libro.class);
        serviceLibro.delete(libro.getId());
        //serviceLibro.update(libro);
        serviceLibro.getAll("Libro.all",Libro.class)
                .forEach(u-> System.out.println(u));
    }

    public static void crearUsuario(){
        ImplDAO implDAO = new ImplDAO();
        Usuario usuario = new Usuario();
        usuario.setNombre("Norman");
        usuario.setApellido("Cash");
        implDAO.insert(usuario);
    }

    public static void vender(Usuario usuario, String producto, double cantidad){
        ImplDAO implDAO = new ImplDAO();
        Ventas ventas = new Ventas();
        ventas.setUsuario(usuario);
        ventas.setProducto(producto);
        ventas.setVenta(venta);
    }

    public static void main(String[] args) {
        crearUsuario();

        //simular login
        ImplDAO implDAO = new ImplDAO();
        Usuario usuario = implDAO.findById("Ventas.all", Ventas.class).forEach;
    }
}