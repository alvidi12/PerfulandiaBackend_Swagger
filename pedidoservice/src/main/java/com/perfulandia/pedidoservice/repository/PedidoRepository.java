package com.perfulandia.pedidoservice.repository;

import com.perfulandia.pedidoservice.model.Pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByUsuarioId(Long usuarioId);

    List<Pedido> findByEstado(String estado);

    Optional<Pedido> findByCarritoId(Long carritoId);

    @Query("SELECT p FROM Pedido p WHERE p.usuarioId = :usuarioId AND p.estado = :estado")
    List<Pedido> findByUsuarioIdAndEstado(@Param("usuarioId") Long usuarioId, @Param("estado") String estado);

    @Query("SELECT p FROM Pedido p WHERE p.usuarioId = :usuarioId ORDER BY p.fechaCreacion DESC")
    List<Pedido> findByUsuarioIdOrderByFechaCreacionDesc(@Param("usuarioId") Long usuarioId);
}