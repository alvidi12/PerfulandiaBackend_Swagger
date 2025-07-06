package com.perfulandia.carritoservice.service;

import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.model.Producto;
import com.perfulandia.carritoservice.repository.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository repo;

    public List<Carrito> listar() {
        return repo.findAll();
    }

    public Carrito buscar(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Carrito actualizar(Long id, Carrito carritoActualizado) {
        Optional<Carrito> carritoExistente = repo.findById(id);
        if (carritoExistente.isPresent()) {
            Carrito carrito = carritoExistente.get();
            carrito.setEstado(carritoActualizado.getEstado());

            // Actualizar productos si se proporcionan
            if (carritoActualizado.getProductos() != null) {
                // Limpiar productos existentes
                if (carrito.getProductos() != null) {
                    carrito.getProductos().clear();
                }

                // Agregar nuevos productos
                carritoActualizado.getProductos().forEach(producto -> {
                    producto.setCarrito(carrito);
                });
                carrito.setProductos(carritoActualizado.getProductos());
            }

            return repo.save(carrito);
        }
        return null;
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    public Carrito crearCarritoParaUsuario(Long usuarioId) {
        Carrito carrito = Carrito.builder()
                .usuarioId(usuarioId)
                .estado("ACTIVO")
                .build();
        return repo.save(carrito);
    }

    public Carrito agregarProducto(Long carritoId, Producto producto) {
        Optional<Carrito> carritoOpt = repo.findById(carritoId);
        if (carritoOpt.isPresent()) {
            Carrito carrito = carritoOpt.get();
            producto.setCarrito(carrito);
            if (carrito.getProductos() == null) {
                carrito.setProductos(List.of(producto));
            } else {
                carrito.getProductos().add(producto);
            }
            return repo.save(carrito);
        }
        return null;
    }
}