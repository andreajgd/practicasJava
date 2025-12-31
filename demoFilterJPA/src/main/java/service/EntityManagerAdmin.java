package service;

import config.FilterEntityRestrictiva;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerAdmin {

    private static final String UNIDAD_PERSISTENCE = "DEMOJPA";

    private static EntityManager instance;

    public static EntityManager getInstance() {
        if ((instance == null) || (!instance.isOpen())) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory(UNIDAD_PERSISTENCE);
            if (FilterEntityRestrictiva.getSizeFilterTable() == 0) {
                FilterEntityRestrictiva.registerEntity(emf);
            }
            return emf.createEntityManager();
        }
        return instance;
    }

}