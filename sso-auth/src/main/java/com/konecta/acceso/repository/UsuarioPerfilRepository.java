package com.konecta.acceso.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.konecta.acceso.model.UsuarioPerfil;
import com.konecta.acceso.model.UsuarioPerfilId;

public interface UsuarioPerfilRepository extends JpaRepository<UsuarioPerfil, UsuarioPerfilId> {
    
}
