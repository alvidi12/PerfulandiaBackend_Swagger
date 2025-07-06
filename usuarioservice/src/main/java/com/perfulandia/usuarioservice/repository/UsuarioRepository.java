package com.perfulandia.usuarioservice.repository;

import com.perfulandia.usuarioservice.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//findAll()
//findById(id)
//save()
//delete(id)
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
