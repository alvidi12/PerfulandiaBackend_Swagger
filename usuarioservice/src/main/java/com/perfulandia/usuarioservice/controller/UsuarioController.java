package com.perfulandia.usuarioservice.controller;

import com.perfulandia.usuarioservice.model.Usuario;
import com.perfulandia.usuarioservice.service.UsuarioService;

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
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "API para gestión de usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    @Operation(summary = "Obtener todos los usuarios", description = "Retorna una lista de todos los usuarios registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    public ResponseEntity<List<Usuario>> listar() {
        List<Usuario> usuarios = service.listar();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo usuario", description = "Crea y guarda un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos no validos"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    public ResponseEntity<Usuario> guardar(@RequestBody Usuario usuario) {
        Usuario usuarioGuardado = service.guardar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioGuardado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID", description = "Busca a un usuario por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    public ResponseEntity<Usuario> buscar(
            @Parameter(description = "ID del usuario a buscar") @PathVariable Long id) {
        Usuario usuario = service.buscar(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del usuario a eliminar") @PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}