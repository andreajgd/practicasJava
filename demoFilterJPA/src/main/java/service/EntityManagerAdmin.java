package service;

import config.FilterEntityRestrictiva;
import config.InicializadorSistema;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerAdmin {

    private static final String UNIDAD_PERSISTENCE = "DEMOJPA";
    private static EntityManagerFactory emf;

    public static EntityManager getInstance() {
        if (emf == null || !emf.isOpen()) {
            emf = Persistence.createEntityManagerFactory(UNIDAD_PERSISTENCE);

            //Inicializar sistema de filtrado si no está inicializado
            if (!InicializadorSistema.estaInicializado()) {
                InicializadorSistema.inicializar();
            }
        }
        return emf.createEntityManager();
    }

    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}