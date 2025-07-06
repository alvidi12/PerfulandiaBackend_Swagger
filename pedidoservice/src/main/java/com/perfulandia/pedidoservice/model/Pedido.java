package com.perfulandia.pedidoservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Pedido de compra de Perfulandia.")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del pedido", example = "1")
    private Long id;

    @Column(name = "carrito_id")
    @Schema(description = "ID del carrito asociado al pedido", example = "1")
    private Long carritoId;

    @Column(name = "usuario_id")
    @Schema(description = "ID del usuario que realiza el pedido", example = "1")
    private Long usuarioId;

    @Schema(description = "Estado del pedido", example = "PENDIENTE")
    private String estado;

    @Schema(description = "Total del pedido", example = "125.50")
    private BigDecimal total;

    @Schema(description = "Dirección de envío del pedido", example = "Av. Principal 123, Santiago")
    private String direccionEnvio;

    @Schema(description = "Método de pago", example = "TARJETA_CREDITO")
    private String metodoPago;

    @Schema(description = "Fecha de creación del pedido")
    private LocalDateTime fechaCreacion;

    @Schema(description = "Fecha de última modificación del pedido")
    private LocalDateTime fechaModificacion;

    @Schema(description = "Fecha estimada de entrega")
    private LocalDateTime fechaEntregaEstimada;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "carrito_id", insertable = false, updatable = false)
    @Schema(description = "Carrito asociado al pedido")
    private Carrito carrito;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaModificacion = LocalDateTime.now();
        if (estado == null) {
            estado = "PENDIENTE";
        }
        // Fecha estimada de entrega: 7 días después de la creación
        fechaEntregaEstimada = LocalDateTime.now().plusDays(7);
    }

    @PreUpdate
    protected void onUpdate() {
        fechaModificacion = LocalDateTime.now();
    }
}