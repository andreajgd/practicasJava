package org.uam.demospringboot.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.uam.demospringboot.model.Sucursal;
import org.uam.demospringboot.repository.RepositorySucursal;

import java.util.List;

@Service
public class ServiceSucursal implements IServiceSucursal {

    private final RepositorySucursal repositorySucursal;

    public ServiceSucursal(RepositorySucursal repositorySucursal) {
        this.repositorySucursal = repositorySucursal;
    }

    @Override
    public List<Sucursal> listAllSucursal(){return repositorySucursal.findAll();}

    @Override
    public Sucursal findSucursalById(String id) {

        return repositorySucursal.findById(id).orElseThrow(() -> new RuntimeException("Sucursal no encontrado"));
    }

    @Override
    public Sucursal save(Sucursal sucursal) {
        return repositorySucursal.save(sucursal);
    }

    @Override
    public void deleteSucursalById(String id) {
        repositorySucursal.deleteById(id);
    }
}
