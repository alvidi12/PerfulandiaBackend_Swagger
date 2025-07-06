package com.perfulandia.productservice.controller;

import com.perfulandia.productservice.model.Producto;
import com.perfulandia.productservice.model.Usuario;
import com.perfulandia.productservice.service.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductoService service;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Testing 1 GET: Obtener todos los productos")
    void testGetAllProductos() throws Exception {
        // Arrange
        Usuario usuario1 = new Usuario(1L, "Andy Villarroel", "andy.villarroel@gmail.com", "ADMIN");
        List<Producto> productos = Arrays.asList(
                new Producto(1L, "Perfume SpecialOne", "Fragancia fuerte", 50000.0, 50, usuario1),
                new Producto(2L, "Perfume Azul", "Fragancia marina", 7000.0, 30, usuario1)
        );
        when(service.listar()).thenReturn(productos);

        // Act & Assert
        mockMvc.perform(get("/api/v1/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nombre").value("Perfume SpecialOne"))
                .andExpect(jsonPath("$[1].nombre").value("Perfume Azul"));

        verify(service, times(1)).listar();
    }

    @Test
    @DisplayName("Testing 2 POST: Crear nuevo producto")
    void testPostProducto() throws Exception {
        // Arrange
        Usuario usuarioCreador = new Usuario(1L, "Andy Villarroel", "andy.villarroel@gmail.com", "ADMIN");
        Producto newProducto = new Producto(null, "Perfume de Noche", "Fragancia nocturna", 5000.0, 25, usuarioCreador);
        Producto savedProducto = new Producto(3L, "Perfume del sur", "Fragancia intensa", 35000.0, 25, usuarioCreador);
        when(service.guardar(any(Producto.class))).thenReturn(savedProducto);

        // Act & Assert
        mockMvc.perform(post("/api/v1/productos")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(newProducto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.nombre").value("Perfume del sur"))
                .andExpect(jsonPath("$.usuarioCreador.nombre").value("Andy Villarroel"));

        verify(service, times(1)).guardar(any(Producto.class));
    }

    @Test
    @DisplayName("Testing 3 PUT: Actualizar producto")
    void testPutProducto() throws Exception {
        // Arrange
        Long productoId = 1L;
        Usuario usuarioOriginal = new Usuario(1L, "Andy Villarroel", "andy.villarroel@gmail.com", "ADMIN");
        Producto updatedProductoRequest = new Producto(null, "Perfume Verano", "Fragancia fresca actualizada", 65.0, 60, usuarioOriginal);
        Producto updatedProductoResponse = new Producto(productoId, "Perfume Verano", "Fragancia fresca actualizada", 65.0, 60, usuarioOriginal);
        when(service.actualizar(eq(productoId), any(Producto.class))).thenReturn(updatedProductoResponse);

        // Act & Assert
        mockMvc.perform(put("/api/v1/productos/{id}", productoId)
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(updatedProductoRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productoId))
                .andExpect(jsonPath("$.nombre").value("Perfume Verano"))
                .andExpect(jsonPath("$.precio").value(65.0));

        verify(service, times(1)).actualizar(eq(productoId), any(Producto.class));
    }

    @Test
    @DisplayName("Testing 4 DELETE: Eliminar producto")
    void testDeleteProducto() throws Exception {
        // Arrange
        Long productoId = 1L;
        doNothing().when(service).eliminar(productoId);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/productos/{id}", productoId))
                .andExpect(status().isNoContent());

        verify(service, times(1)).eliminar(productoId);
    }
}