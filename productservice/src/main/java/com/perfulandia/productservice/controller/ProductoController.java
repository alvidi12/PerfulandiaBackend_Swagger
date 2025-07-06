package com.perfulandia.productservice.controller;

import com.perfulandia.productservice.model.Producto;
import com.perfulandia.productservice.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
@Tag(name = "Productos", description = "API para gestion de productos")
public class ProductoController {

    @Autowired
    private ProductoService service;

    @GetMapping
    @Operation(summary = "Obtener todos los productos", description = "Retorna una lista de todos los productos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    public ResponseEntity<List<Producto>> listar() {
        List<Producto> productos = service.listar();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Busca un producto por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    public ResponseEntity<Producto> buscar(
            @Parameter(description = "ID del producto a buscar") @PathVariable Long id) {
        Producto producto = service.buscar(id);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo producto", description = "Crea y guarda un nuevo producto en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos no válidos"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    public ResponseEntity<Producto> guardar(@RequestBody Producto producto) {
        Producto productoGuardado = service.guardar(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoGuardado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto existente", description = "Actualiza los datos de un producto por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos no válidos"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    public ResponseEntity<Producto> actualizar(
            @Parameter(description = "ID del producto a actualizar") @PathVariable Long id,
            @RequestBody Producto productoActualizado) {
        Producto producto = service.actualizar(id, productoActualizado);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto", description = "Elimina un producto de la base de datos por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del producto a eliminar") @PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}