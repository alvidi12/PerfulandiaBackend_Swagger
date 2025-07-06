package com.perfulandia.pedidoservice.service;

import com.perfulandia.pedidoservice.model.Pedido;
import com.perfulandia.pedidoservice.model.Carrito;
import com.perfulandia.pedidoservice.model.Producto;
import com.perfulandia.pedidoservice.repository.PedidoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class PedidoServiceTest {

    @Mock
    private PedidoRepository repo;

    @InjectMocks
    private PedidoService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testing 1 para lista de pedidos")
    void testListarPedidos() {
        // Arrange
        Producto producto1 = new Producto(1L, 1L, "Perfume A", "Fragancia premium", 75.0, 1, null);
        Producto producto2 = new Producto(2L, 2L, "Perfume B", "Fragancia floral", 85.0, 2, null);

        Carrito carrito1 = new Carrito(1L, 1L, "PROCESADO");
        Carrito carrito2 = new Carrito(2L, 2L, "PROCESADO");

    }

    @Test
    @DisplayName("Testing 2 para buscar pedido por ID")
    void testBuscarPedidoPorIdEncontrado() {
        // Arrange
        Long pedidoId = 1L;
        Producto producto = new Producto(1L, 1L, "Perfume Test", "Fragancia test", 100.0, 1, null);
        Carrito carrito = new Carrito(1L, 1L, "PROCESADO");

    }

    @Test
    @DisplayName("Testing 3 para eliminar pedido")
    void testEliminarPedido() {
        // Arrange
        Long pedidoId = 1L;
        doNothing().when(repo).deleteById(pedidoId);

        // Act
        service.eliminar(pedidoId);

        verify(repo, times(1)).deleteById(pedidoId);
    }

    @Test
    @DisplayName("Testing 4 para crear pedido")
    void testCrearPedido() {
        // Arrange
        Producto producto = new Producto(1L, 1L, "Perfume Nuevo", "Fragancia nueva", 80.0, 1, null);
        Carrito carrito = new Carrito(1L, 1L, "PROCESADO");
    }
}