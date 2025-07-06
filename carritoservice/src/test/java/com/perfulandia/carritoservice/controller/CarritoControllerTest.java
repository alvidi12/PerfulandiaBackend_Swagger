package com.perfulandia.carritoservice.controller;

import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.model.Producto;
import com.perfulandia.carritoservice.service.CarritoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarritoController.class)
public class CarritoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarritoService service;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Testing 1 GET: Obtener todos los carritos")
    void testGetAllCarritos() throws Exception {
        // Arrange
        Producto producto1 = new Producto(1L, 1L, "Perfume A", "Fragancia A", 50.0, 2, null);
        Producto producto2 = new Producto(2L, 2L, "Perfume B", "Fragancia B", 75.0, 1, null);

        List<Carrito> carritos = Arrays.asList(
                new Carrito(1L, 1L, "ACTIVO", LocalDateTime.now(), LocalDateTime.now(), List.of(producto1)),
                new Carrito(2L, 2L, "ACTIVO", LocalDateTime.now(), LocalDateTime.now(), List.of(producto2))
        );
        when(service.listar()).thenReturn(carritos);

        // Act & Assert
        mockMvc.perform(get("/api/v1/carritos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].usuarioId").value(1L))
                .andExpect(jsonPath("$[1].usuarioId").value(2L));

        verify(service, times(1)).listar();
    }

    @Test
    @DisplayName("Testing 2 GET: Obtener carrito por ID")
    void testGetCarritoById() throws Exception {
        // Arrange
        Long carritoId = 1L;
        Producto producto = new Producto(1L, 1L, "Perfume Test", "Fragancia test", 100.0, 1, null);
        Carrito carrito = new Carrito(carritoId, 1L, "ACTIVO", LocalDateTime.now(), LocalDateTime.now(), List.of(producto));
        when(service.buscar(carritoId)).thenReturn(carrito);

        // Act & Assert
        mockMvc.perform(get("/api/v1/carritos/{id}", carritoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(carritoId))
                .andExpect(jsonPath("$.usuarioId").value(1L))
                .andExpect(jsonPath("$.estado").value("ACTIVO"))
                .andExpect(jsonPath("$.productos").isArray())
                .andExpect(jsonPath("$.productos.length()").value(1));

        verify(service, times(1)).buscar(carritoId);
    }


    @Test
    @DisplayName("Testing 3 PUT: Actualizar carrito existente")
    void testPutCarrito() throws Exception {
        // Arrange
        Long carritoId = 1L;
        Producto producto = new Producto(1L, 1L, "Perfume Actualizado", "Fragancia actualizada", 65.0, 2, null);
        Carrito carritoRequest = new Carrito(null, 1L, "PENDIENTE", null, null, List.of(producto));
        Carrito carritoResponse = new Carrito(carritoId, 1L, "PENDIENTE", LocalDateTime.now(), LocalDateTime.now(), List.of(producto));
        when(service.actualizar(eq(carritoId), any(Carrito.class))).thenReturn(carritoResponse);

        // Act & Assert
        mockMvc.perform(put("/api/v1/carritos/{id}", carritoId)
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(carritoRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(carritoId))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"))
                .andExpect(jsonPath("$.usuarioId").value(1L));

        verify(service, times(1)).actualizar(eq(carritoId), any(Carrito.class));
    }

}