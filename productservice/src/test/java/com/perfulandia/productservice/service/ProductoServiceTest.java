package com.perfulandia.productservice.service;

import com.perfulandia.productservice.model.Producto;
import com.perfulandia.productservice.model.Usuario;
import com.perfulandia.productservice.repository.ProductoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class ProductoServiceTest {

    @Mock
    private ProductoRepository repo;

    @InjectMocks
    private ProductoService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testing 1 GET: para lista de productos")
    void testListarProductos() {
        // Arrange
        Usuario usuario1 = new Usuario(1L, "Andy Villarroel", "andy.villarroel@gmail.com", "ADMIN");
        List<Producto> productos = Arrays.asList(
                new Producto(1L, "Perfume A", "Descripción A", 50.0, 100, usuario1),
                new Producto(2L, "Perfume B", "Descripción B", 75.0, 50, usuario1)
        );
        when(repo.findAll()).thenReturn(productos);

        // Act
        List<Producto> resultado = service.listar();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Perfume A", resultado.get(0).getNombre());
        assertEquals("Perfume B", resultado.get(1).getNombre());
        verify(repo, times(1)).findAll();
    }


    @Test
    @DisplayName("Testing 2 POST: Para guardar producto")
    void testGuardarProducto() {
        // Arrange
        Usuario usuarioCreador = new Usuario(1L, "Andy Villarroel", "andy.villarroel@gmail.com", "ADMIN");
        Producto nuevoProducto = new Producto(null, "Perfume Nuevo", "Descripción nuevo", 120.0, 30, usuarioCreador);
        Producto productoGuardado = new Producto(3L, "Perfume Nuevo", "Descripción nuevo", 120.0, 30, usuarioCreador);
        when(repo.save(any(Producto.class))).thenReturn(productoGuardado);

        // Act
        Producto resultado = service.guardar(nuevoProducto);

        // Assert
        assertNotNull(resultado);
        assertEquals(3L, resultado.getId());
        assertEquals("Perfume Nuevo", resultado.getNombre());
        assertEquals("Andy Villarroel", resultado.getUsuarioCreador().getNombre());
        verify(repo, times(1)).save(any(Producto.class));
    }

    @Test
    @DisplayName("Testing 3 PUT: para actualizar producto")
    void testActualizarProductoExistente() {
        // Arrange
        Long productoId = 1L;
        Usuario usuarioOriginal = new Usuario(1L, "Andy Villarroel", "andy.villarroel@gmail.com", "ADMIN");
        Producto productoExistente = new Producto(productoId, "Perfume Original", "Desc Original", 50.0, 100, usuarioOriginal);
        Producto productoActualizado = new Producto(null, "Perfume Modificado", "Desc Modificado", 60.0, 110, usuarioOriginal);

        when(repo.findById(productoId)).thenReturn(Optional.of(productoExistente));
        when(repo.save(any(Producto.class))).thenReturn(productoExistente);

        // Act
        Producto resultado = service.actualizar(productoId, productoActualizado);

        // Assert
        assertNotNull(resultado);
        assertEquals(productoId, resultado.getId());
        assertEquals("Perfume Modificado", resultado.getNombre());
        assertEquals("Desc Modificado", resultado.getDescripcion());
        assertEquals(60.0, resultado.getPrecio());
        assertEquals(110, resultado.getStock());
        verify(repo, times(1)).findById(productoId);
        verify(repo, times(1)).save(productoExistente);
    }

    @Test
    @DisplayName("Testing 4 DELETE: para eliminar producto")
    void testEliminarProducto() {
        // Arrange
        Long productoId = 1L;
        doNothing().when(repo).deleteById(productoId);

        // Act
        service.eliminar(productoId);

        // Assert
        verify(repo, times(1)).deleteById(productoId);
    }
}