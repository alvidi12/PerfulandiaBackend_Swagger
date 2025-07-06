package com.perfulandia.usuarioservice.service;

import com.perfulandia.usuarioservice.model.Usuario;
import com.perfulandia.usuarioservice.repository.UsuarioRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.mockito.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock //Creando una instancia para reemplazar el repositorio por datos simulados.
    private UsuarioRepository repo;

    @InjectMocks //Creando una instancia para inyectar los Mocks
    private UsuarioService service;

    @Test
    @DisplayName("Testing 1 para lista de usuarios")
    void listar() {
        // Arrange
        MockitoAnnotations.openMocks(this);

        //Simular al repo y a simular un usuario
        when(repo.findAll()).thenReturn(List.of(new Usuario(1L, "Andy", "andy.villarroel@gmail.com","ADMIN")));

        //Llamamos al metodo del servicio que sera probado
        List<Usuario> resultado = service.listar();

        //Verificar que los datos simulados tengan coincidencia
        assertEquals(1, resultado.size());
        assertEquals("Andy", resultado.get(0).getNombre());
        verify(repo, times(1)).findAll();
    }

    @Test
    @DisplayName("Testing 2 para guardar usuario")
    void testGuardar() {
        // Arrange
        MockitoAnnotations.openMocks(this);
        Usuario usuario = new Usuario(1L, "Camila Gonzalez", "camila.gonzales@gmail.com", "USUARIO");

        when(repo.save(any(Usuario.class))).thenReturn(usuario);

        // Act
        Usuario resultado = service.guardar(usuario);

        // Assert
        assertNotNull(resultado);
        assertEquals("Camila Gonzalez", resultado.getNombre());
        assertEquals("camila.gonzales@gmail.com", resultado.getCorreo());
        assertEquals("USUARIO", resultado.getRol());
        verify(repo, times(1)).save(usuario);
    }

    @Test
    @DisplayName("Testing 3 Eliminar usuario por ID")
    void testEliminar() {
        // Arrange
        MockitoAnnotations.openMocks(this);
        doNothing().when(repo).deleteById(1L);

        // Act
        service.eliminar(1L);

        // Assert
        verify(repo, times(1)).deleteById(1L);
    }
}