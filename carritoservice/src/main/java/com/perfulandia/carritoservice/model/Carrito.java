package com.perfulandia.carritoservice.model;

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
@Schema(description = "Carrito de compras de Perfulandia.")
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

    @Schema(description = "Fecha de creación del carrito")
    private LocalDateTime fechaCreacion;

    @Schema(description = "Fecha de última modificación del carrito")
    private LocalDateTime fechaModificacion;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Schema(description = "Lista de productos en el carrito")
    private List<Producto> productos;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaModificacion = LocalDateTime.now();
        if (estado == null) {
            estado = "ACTIVO";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        fechaModificacion = LocalDateTime.now();
    }
}