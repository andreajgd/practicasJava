package service;

import java.util.List;

public interface IDAO {

    <T> List<T> getAll(String nameQuery, Class clazz);

    <T> void insert(T entity);

    <T> void update(T entity);
    <T> void delete(String id, Class<T> clazz);
    <T> T findById(String id, Class<T> clazz);
}