package service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;


import java.util.List;

public class ImplDAO implements IDAO {

    @Override
    public <T> List<T> getAll(String nameQuery, Class clazz) {
        EntityManager em = EntityManagerAdmin.getInstance();
        try {
            TypedQuery<T> query = em.createNamedQuery(nameQuery, clazz);
            return query.getResultList();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            em.close();
        }
        return null;
    }

    @Override
    public <T> void insert(T entity) {
        EntityManager em = EntityManagerAdmin.getInstance();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.flush();
            em.getTransaction().commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            em.close();
        }
    }

    @Override
    public <T> void update(T entity) {
        EntityManager em = EntityManagerAdmin.getInstance();
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.flush();
            em.getTransaction().commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            em.close();
        }
    }

    @Override
    public <T> void delete(String id, Class<T> clazz) {
        EntityManager em = EntityManagerAdmin.getInstance();
        try {
            em.getTransaction().begin();
            em.remove(em.find(clazz, id));
            em.flush();
            em.getTransaction().commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            em.close();
        }
    }

    @Override
    public <T> T findById(String id, Class<T> clazz) {
        EntityManager em = EntityManagerAdmin.getInstance();
        try {
            T entity = em.find(clazz, id);
            return entity;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            em.close();
        }
        return null;
    }


}