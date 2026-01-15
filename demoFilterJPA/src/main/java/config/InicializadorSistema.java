package config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class InicializadorSistema {

    private static boolean inicializado = false; //bandera para inicializacion

    //sincronizado para evitar inicializacion multiple
    public static synchronized void inicializar() {
        if (!inicializado) {
            EntityManagerFactory emf = null;
            try {
                emf = Persistence.createEntityManagerFactory("DEMOJPA");
                //registra entidades con @RestricitvaUsuario
                FilterEntityRestrictiva.registerEntity(emf);
                System.out.println("Sistema de filtrado inicializado. Tablas filtradas: " +
                        FilterEntityRestrictiva.getSizeFilterTable());
            } finally {
                if (emf != null && emf.isOpen()) {
                    emf.close();
                }
            }
            inicializado = true;
        }
    }

    public static boolean estaInicializado() {
        return inicializado;
    }
}