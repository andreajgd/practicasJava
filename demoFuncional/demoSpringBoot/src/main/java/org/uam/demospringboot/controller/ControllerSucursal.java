package org.uam.demospringboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.uam.demospringboot.model.Sucursal;
import org.uam.demospringboot.service.IServiceSucursal;

import java.util.List;

@RestController
@RequestMapping("sucursal ")
public class ControllerSucursal {

    private final IServiceSucursal serviceSucursal;
    public ControllerSucursal(IServiceSucursal serviceSucursal) {
        this.serviceSucursal = serviceSucursal;
    }
    @GetMapping("/all")
    public ResponseEntity<List<Sucursal>> getAll() {
        return ResponseEntity.ok(serviceSucursal.listAllSucursal());
    }

    //via post: se envia info a traves del get

    @PostMapping("/save")
    public ResponseEntity<Sucursal> save(@RequestBody Sucursal sucursal) {
        return ResponseEntity.ok(serviceSucursal.save(sucursal));
    }

    @PutMapping("/update")
    public ResponseEntity<Sucursal> update(@RequestBody Sucursal sucursal) {
        Sucursal s = this.serviceSucursal.findSucursalById(sucursal.get);
        return  ResponseEntity.ok(serviceSucursal.save(s));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Sucursal> delete(@PathVariable String id) {
        this.serviceSucursal.deleteSucursalById(id);
        return ResponseEntity.noContent().build();
    }
}
