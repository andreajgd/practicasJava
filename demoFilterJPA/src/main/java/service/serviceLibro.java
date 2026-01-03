package service;

import model.Libro;

public class serviceLibro extends ImplDAO{

    public void delete(String id) {
        Libro libro = super.findById(id,Libro.class);
        libro.setRestrictiva(true);
        super.update(libro);
    }
}