package com.perfulandia.pedidoservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Producto en el carrito del pedido.")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del producto", example = "1")
    private Long id;

    @Column(name = "producto_id")
    @Schema(description = "ID del producto en el catálogo", example = "1")
    private Long productoId;

    @Schema(description = "Nombre del producto", example = "Perfume Elegante")
    private String nombre;

    @Schema(description = "Descripción del producto", example = "Fragancia floral con notas cítricas")
    private String descripcion;

    @Schema(description = "Precio del producto", example = "89.99")
    private Double precio;

    @Schema(description = "Cantidad del producto", example = "2")
    private Integer cantidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrito_id")
    @Schema(description = "Carrito al que pertenece el producto")
    private Carrito carrito;
}