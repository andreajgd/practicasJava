package org.uam;

import org.w3c.dom.ls.LSOutput;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class InterfaceFunctionalDemo {
    record Usuario(String nombre, int edad, boolean estado) {}

    public static void main(String[] args) {
        Supplier<List<Usuario>> proveedorUsuario = ()->List.of(
                new Usuario("David", 21, true),
                new Usuario("Andrea", 20, true));

        //Predicate<Usuario> estado = u -> u.estado;
        Predicate<Usuario> estado = Usuario::estado;
        Predicate<Usuario> esMayorEdad = u-> u.edad() > 19;
        Predicate<Usuario> usuarioValido = esMayorEdad.and(estado);

        Function<Usuario,String> formatearNombre = u-> u.nombre.toUpperCase();

        Consumer<String> imprimir = System.out::println;

        proveedorUsuario.get().stream().filter(usuarioValido)
                .map(formatearNombre).forEach(imprimir);

    }
}
