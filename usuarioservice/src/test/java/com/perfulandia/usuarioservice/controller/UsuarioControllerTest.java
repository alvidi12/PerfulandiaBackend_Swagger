package com.perfulandia.usuarioservice.controller;

import com.perfulandia.usuarioservice.model.Usuario;
import com.perfulandia.usuarioservice.service.UsuarioService;

//1 Importaciones de JUnit 5
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//2 Anotación para probar solo el controlador (no el contexto completo de Spring Boot)
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

//3 Anotación para simular un bean dentro del ApplicationContext de Spring
import org.springframework.test.context.bean.override.mockito.MockitoBean; // Usando MockitoBean según tu ejemplo

//4 Inyección automática del cliente de pruebas web
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

//5 Métodos estáticos de Mockito
import static org.mockito.Mockito.*;

//6 Métodos para construir peticiones HTTP simuladas y verificar respuestas
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//7 Librería para convertir objetos a JSON (necesaria en peticiones POST)
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional; // Necesario para simular findById que retorna Optional

//Anotación: indicar que en esta clase se realizaran testing del controlador
@WebMvcTest(UsuarioController.class) // Especifica el controlador a probar

public class UsuarioControllerTest {
    //Inyectando Mock para utilizar dentro de esta clase
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean // Simula el UsuarioService en el contexto de Spring
    private UsuarioService service;

    //Convertir de texto a Json y viceversa
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Testing 1 GET")
    void testGetAllUsuarios() throws Exception {
        // Simulación que el servicio me entregue una lista de usuarios
        when(service.listar()).thenReturn(List.of(
                new Usuario(1L, "Andy Villarroel", "andy.villarroel@gmail.com", "ADMIN"),
                new Usuario(2L, "Camila Gonzalez", "camila.gonzalez@gmail.com", "USUARIO")
        ));

        // Simular una petición GET al EndPoint y verificar la respuesta
        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk()) // Espera un estado HTTP 200 OK
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("Andy Villarroel"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].nombre").value("Camila Gonzalez"));

        // Verificacion del metodo listar() por una vez
        verify(service, times(1)).listar();
    }

    @Test
    @DisplayName("Testing 2 Buscar usuario por ID")
    void testGetUsuarioByIdEncontrado() throws Exception {
        Long userId = 1L;
        Usuario usuario = new Usuario(userId, "Andy Villarroel", "andy.villarroel@gmail.com", "ADMIN");

        // Simula que el servicio entrega el usuario cuando se busca por ID
        when(service.buscar(userId)).thenReturn(usuario);

        // Simular una petición GET por ID y verificar la respuesta
        mockMvc.perform(get("/api/v1/usuarios/{id}", userId))
                .andExpect(status().isOk()) // Espera un estado HTTP 200 OK
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.nombre").value("Andy Villarroel"));

        // Verifica el metodo buscar() con el ID correcto
        verify(service, times(1)).buscar(userId);
    }

    @Test
    @DisplayName("Testing 3 POST")
    void testPostUsuario() throws Exception {
        // Objeto Usuario sin ID, que se enviaría para crear
        Usuario newUsuario = new Usuario(null, "Nuevo Usuario", "nuevo.usuario@example.com", "USUARIO");
        // Objeto Usuario con ID asignado, que el servicio devolvería después de guardar
        Usuario savedUsuario = new Usuario(3L, "Nuevo Usuario", "nuevo.usuario@example.com", "USUARIO");

        // Simular el guardado de un usuario y su respuesta
        when(service.guardar(any(Usuario.class))).thenReturn(savedUsuario);

        // Ejecutar una petición POST y hacer la conversión de datos
        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType("application/json") // Indicando que es en formato Json
                        .content(mapper.writeValueAsString(newUsuario))) // Convirtiendo el objeto a JSON

                .andExpect(status().isCreated()) // Indicamos la respuesta esperada 201 Created
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.nombre").value("Nuevo Usuario"));

        // Verificar el metodo guardar()
        verify(service, times(1)).guardar(any(Usuario.class));
    }

    @Test
    @DisplayName("Testing 4 DELETE")
    void testDeleteUsuario() throws Exception {
        Long userId = 1L;

        // Simula una petición delete sin lanzar una excepción (para métodos void)
        doNothing().when(service).eliminar(userId);

        // Ejecutamos la petición DELETE
        mockMvc.perform(delete("/api/v1/usuarios/{id}", userId))
                .andExpect(status().isNoContent()); // Espera un estado HTTP 204 No Content

        // Verificar el metodo eliminar()
        verify(service, times(1)).eliminar(userId);
    }
}