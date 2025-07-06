package com.perfulandia.carritoservice.service;

import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.model.Producto;
import com.perfulandia.carritoservice.repository.CarritoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class CarritoServiceTest {

    @Mock
    private CarritoRepository repo;

    @InjectMocks
    private CarritoService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testing 1 para lista de carritos")
    void testListarCarritos() {
        // Arrange
        Producto producto1 = new Producto(1L, 1L, "Perfume A", "Descripción A", 50.0, 2, null);
        Producto producto2 = new Producto(2L, 2L, "Perfume B", "Descripción B", 75.0, 1, null);

        List<Carrito> carritos = Arrays.asList(
                new Carrito(1L, 1L, "ACTIVO", LocalDateTime.now(), LocalDateTime.now(), List.of(producto1)),
                new Carrito(2L, 2L, "ACTIVO", LocalDateTime.now(), LocalDateTime.now(), List.of(producto2))
        );
        when(repo.findAll()).thenReturn(carritos);

        // Act
        List<Carrito> resultado = service.listar();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(1L, resultado.get(0).getUsuarioId());
        assertEquals(2L, resultado.get(1).getUsuarioId());
        verify(repo, times(1)).findAll();
    }

    @Test
    @DisplayName("Testing 2 para buscar carrito por ID encontrado")
    void testBuscarCarritoPorIdEncontrado() {
        // Arrange
        Long carritoId = 1L;
        Producto producto = new Producto(1L, 1L, "Perfume Test", "Descripción test", 100.0, 1, null);
        Carrito carrito = new Carrito(carritoId, 1L, "ACTIVO", LocalDateTime.now(), LocalDateTime.now(), List.of(producto));
        when(repo.findById(carritoId)).thenReturn(Optional.of(carrito));

        // Act
        Carrito resultado = service.buscar(carritoId);

        // Assert
        assertNotNull(resultado);
        assertEquals(carritoId, resultado.getId());
        assertEquals(1L, resultado.getUsuarioId());
        assertEquals("ACTIVO", resultado.getEstado());
        assertEquals(1, resultado.getProductos().size());
        verify(repo, times(1)).findById(carritoId);
    }

    @Test
    @DisplayName("Testing 3 para eliminar carrito")
    void testEliminarCarrito() {
        // Arrange
        Long carritoId = 1L;
        doNothing().when(repo).deleteById(carritoId);

        // Act
        service.eliminar(carritoId);

        // Assert
        verify(repo, times(1)).deleteById(carritoId);
    }

}