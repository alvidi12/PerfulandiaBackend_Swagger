package com.perfulandia.carritoservice.controller;

import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.service.CarritoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carritos")
@Tag(name = "Carritos", description = "API para gestión de carritos de compras")
public class CarritoController {

    @Autowired
    private CarritoService service;

    @GetMapping
    @Operation(summary = "Obtener todos los carritos", description = "Retorna una lista de todos los carritos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de carritos obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    public ResponseEntity<List<Carrito>> listar() {
        List<Carrito> carritos = service.listar();
        return ResponseEntity.ok(carritos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener carrito por ID", description = "Busca un carrito por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrito encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Carrito no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    public ResponseEntity<Carrito> buscar(
            @Parameter(description = "ID del carrito a buscar") @PathVariable Long id) {
        Carrito carrito = service.buscar(id);
        if (carrito != null) {
            return ResponseEntity.ok(carrito);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un carrito existente", description = "Actualiza los datos de un carrito por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrito actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Carrito no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos no válidos"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    public ResponseEntity<Carrito> actualizar(
            @Parameter(description = "ID del carrito a actualizar") @PathVariable Long id,
            @RequestBody Carrito carritoActualizado) {
        Carrito carrito = service.actualizar(id, carritoActualizado);
        if (carrito != null) {
            return ResponseEntity.ok(carrito);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un carrito", description = "Elimina un carrito de la base de datos por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Carrito eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Carrito no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del carrito a eliminar") @PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}