package com.perfulandia.carritoservice.repository;

import com.perfulandia.carritoservice.model.Carrito;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {

    Optional<Carrito> findByUsuarioIdAndEstado(Long usuarioId, String estado);

    List<Carrito> findByUsuarioId(Long usuarioId);

    @Query("SELECT c FROM Carrito c WHERE c.usuarioId = :usuarioId AND c.estado = 'Comprando'")
    Optional<Carrito> findCarritoActivoByUsuarioId(@Param("usuarioId") Long usuarioId);
}