package com.perfulandia.productservice.service;

import com.perfulandia.productservice.model.Producto;
import com.perfulandia.productservice.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repo;

    public List<Producto> listar() {
        return repo.findAll();
    }

    public Producto guardar(Producto producto) {
        return repo.save(producto);
    }

    public Producto buscar(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Producto actualizar(Long id, Producto productoActualizado) {
        Optional<Producto> productoExistente = repo.findById(id);
        if (productoExistente.isPresent()) {
            Producto producto = productoExistente.get();
            producto.setNombre(productoActualizado.getNombre());
            producto.setDescripcion(productoActualizado.getDescripcion());
            producto.setPrecio(productoActualizado.getPrecio());
            producto.setStock(productoActualizado.getStock());

            // Actualizar el usuario creador si se proporciona
            if (productoActualizado.getUsuarioCreador() != null) {
                producto.setUsuarioCreador(productoActualizado.getUsuarioCreador());
            }

            return repo.save(producto);
        }
        return null;
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}