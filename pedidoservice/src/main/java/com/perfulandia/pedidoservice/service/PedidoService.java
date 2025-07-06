package com.perfulandia.pedidoservice.service;

import com.perfulandia.pedidoservice.model.Pedido;
import com.perfulandia.pedidoservice.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    public List<Pedido> listar() {
        return repo.findAll();
    }

    public Pedido buscar(Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<Pedido> buscarPorUsuario(Long usuarioId) {
        return repo.findByUsuarioIdOrderByFechaCreacionDesc(usuarioId);
    }

    public Pedido actualizar(Long id, Pedido pedidoActualizado) {
        Optional<Pedido> pedidoExistente = repo.findById(id);
        if (pedidoExistente.isPresent()) {
            Pedido pedido = pedidoExistente.get();

            // Actualizar solo los campos permitidos
            if (pedidoActualizado.getEstado() != null) {
                pedido.setEstado(pedidoActualizado.getEstado());
            }

            if (pedidoActualizado.getDireccionEnvio() != null) {
                pedido.setDireccionEnvio(pedidoActualizado.getDireccionEnvio());
            }

            if (pedidoActualizado.getMetodoPago() != null) {
                pedido.setMetodoPago(pedidoActualizado.getMetodoPago());
            }

            if (pedidoActualizado.getTotal() != null) {
                pedido.setTotal(pedidoActualizado.getTotal());
            }

            if (pedidoActualizado.getFechaEntregaEstimada() != null) {
                pedido.setFechaEntregaEstimada(pedidoActualizado.getFechaEntregaEstimada());
            }

            return repo.save(pedido);
        }
        return null;
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    public Pedido cambiarEstado(Long id, String nuevoEstado) {
        Optional<Pedido> pedidoOpt = repo.findById(id);
        if (pedidoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();
            pedido.setEstado(nuevoEstado);

            // Si el estado es "ENTREGADO", actualizar fecha de entrega
            if ("ENTREGADO".equals(nuevoEstado)) {
                pedido.setFechaEntregaEstimada(java.time.LocalDateTime.now());
            }

            return repo.save(pedido);
        }
        return null;
    }
}