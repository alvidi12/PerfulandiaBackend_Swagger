package com.perfulandia.productservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Ahora eres un usuario de Perfulandia.")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del usuario", example = "1")
    private Long id;

    @Schema(description = "Nombre del usuario", example = "Andy Villarroel")
    private String nombre;

    @Schema(description = "Correo electrónico del usuario", example = "andy.villarroel@gmail.com")
    private String correo;

    @Schema(description = "Rol del usuario en el sistema", example = "ADMIN", allowableValues = {"ADMIN", "EMPLEADO", "USUARIO"})
    private String rol;
}