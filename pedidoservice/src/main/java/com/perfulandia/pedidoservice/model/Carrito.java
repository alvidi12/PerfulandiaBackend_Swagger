package com.perfulandia.pedidoservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Carrito de compras asociado al pedido.")
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del carrito", example = "1")
    private Long id;

    @Column(name = "usuario_id")
    @Schema(description = "ID del usuario propietario del carrito", example = "1")
    private Long usuarioId;

    @Schema(description = "Estado del carrito", example = "ACTIVO")
    private String estado;

}