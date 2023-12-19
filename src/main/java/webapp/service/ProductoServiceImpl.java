package webapp.service;

import webapp.models.Categoria;
import webapp.models.Producto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductoServiceImpl implements ProductoService{
    @Override
    public List<Producto> listar() {
        return Arrays.asList(new Producto(1L, "Notebook", "Computación", 175000),
                new Producto(2L, "Mesa escritorio", "Oficina", 10000),
                new Producto(3L, "Teclado mecánico", "Computación", 40000));
    }

    @Override
    public Optional<Producto> buscarProducto(String nombre) {
        return this.listar().stream().filter(producto -> {
            if(nombre == null || nombre.isBlank())
                return false;
            return producto.getNombre().contains(nombre);
        }).findFirst();
    }

    @Override
    public Optional<Producto> porId(Long id) {
        return listar().stream().filter(p -> p.getId().equals(id)).findAny();
    }

    @Override
    public void guardar(Producto producto) {

    }

    @Override
    public void eliminar(Long id) {

    }

    @Override
    public List<Categoria> listarCategorias() {
        return null;
    }

    @Override
    public Optional<Categoria> porIdCategoria(Long id) {
        return Optional.empty();
    }
}
