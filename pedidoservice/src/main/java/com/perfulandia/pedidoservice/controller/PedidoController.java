package com.perfulandia.pedidoservice.controller;

import com.perfulandia.pedidoservice.model.Pedido;
import com.perfulandia.pedidoservice.service.PedidoService;

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
@RequestMapping("/api/v1/pedidos")
@Tag(name = "Pedidos", description = "API para gestión de pedidos de compra")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping
    @Operation(summary = "Obtener todos los pedidos", description = "Retorna una lista de todos los pedidos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pedidos obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    public ResponseEntity<List<Pedido>> listar() {
        List<Pedido> pedidos = service.listar();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pedido por ID", description = "Busca un pedido por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    public ResponseEntity<Pedido> buscar(
            @Parameter(description = "ID del pedido a buscar") @PathVariable Long id) {
        Pedido pedido = service.buscar(id);
        if (pedido != null) {
            return ResponseEntity.ok(pedido);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un pedido existente", description = "Actualiza los datos de un pedido por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos no válidos"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    public ResponseEntity<Pedido> actualizar(
            @Parameter(description = "ID del pedido a actualizar") @PathVariable Long id,
            @RequestBody Pedido pedidoActualizado) {
        Pedido pedido = service.actualizar(id, pedidoActualizado);
        if (pedido != null) {
            return ResponseEntity.ok(pedido);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un pedido", description = "Elimina un pedido de la base de datos por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del pedido a eliminar") @PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}