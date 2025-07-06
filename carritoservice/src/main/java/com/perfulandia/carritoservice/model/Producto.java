package com.perfulandia.carritoservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Producto dentro del carrito de compras.")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del producto en el carrito", example = "1")
    private Long id;

    @Column(name = "producto_id")
    @Schema(description = "ID del producto original", example = "1")
    private Long productoId;

    @Schema(description = "Nombre del producto", example = "Perfume Tio Nacho")
    private String nombre;

    @Schema(description = "Descripción del producto", example = "Un perfume de miel con ligeras notas de jazmín.")
    private String descripcion;

    @Schema(description = "Precio del producto", example = "49000")
    private Double precio;

    @Schema(description = "Cantidad del producto en el carrito", example = "2")
    private Integer cantidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrito_id")
    @Schema(description = "Carrito al que pertenece este producto")
    private Carrito carrito;
}