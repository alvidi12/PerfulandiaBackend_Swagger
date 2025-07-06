package com.perfulandia.pedidoservice.controller;

import com.perfulandia.pedidoservice.model.Pedido;
import com.perfulandia.pedidoservice.model.Carrito;
import com.perfulandia.pedidoservice.model.Producto;
import com.perfulandia.pedidoservice.service.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PedidoController.class)
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PedidoService service;

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("Testing 1 GET: Obtener todos los pedidos")
    void testGetAllPedidos() throws Exception {
        // Arrange
        Producto producto1 = new Producto(1L, 1L, "Perfume A", "Fragancia premium", 75.0, 1, null);
        Producto producto2 = new Producto(2L, 2L, "Perfume B", "Fragancia floral", 85.0, 2, null);

        Carrito carrito1 = new Carrito(1L, 1L, "PROCESADO");
        Carrito carrito2 = new Carrito(2L, 2L, "PROCESADO");

    }

    @Test
    @DisplayName("Testing 2 GET: Obtener pedido por ID")
    void testGetPedidoById() throws Exception {
        // Arrange
        Long pedidoId = 1L;
        Producto producto = new Producto(1L, 1L, "Perfume Test", "Fragancia test", 100.0, 1, null);
        Carrito carrito = new Carrito(1L, 1L, "PROCESADO");
    }

    @Test
    @DisplayName("Testing 3 DELETE: Eliminar pedido")
    void testDeletePedido() throws Exception {
        // Arrange
        Long pedidoId = 1L;
        doNothing().when(service).eliminar(pedidoId);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/pedidos/{id}", pedidoId))
                .andExpect(status().isNoContent());

        verify(service, times(1)).eliminar(pedidoId);
    }

}