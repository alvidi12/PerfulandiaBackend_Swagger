package com.perfulandia.productservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Producto disponible en Perfulandia.")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del producto", example = "1")
    private Long id;

    @Schema(description = "Nombre del producto", example = "Perfume Tio Nacho")
    private String nombre;

    @Schema(description = "Descripción del producto", example = "Un perfume de miel con ligeras notas de jazmín.")
    private String descripcion;

    @Schema(description = "Precio del producto", example = "49000")
    private Double precio;

    @Schema(description = "Stock disponible del producto", example = "20")
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido del usuario por id")
    @Schema(description = "Usuario que ingreso el producto")
    private Usuario usuarioCreador;
}