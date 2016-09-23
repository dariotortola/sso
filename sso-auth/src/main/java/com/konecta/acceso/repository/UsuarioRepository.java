package com.konecta.acceso.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.konecta.acceso.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByUsernameOrMeta4OrEmail(String username, String meta4, String email);

    default List<Usuario> findByUsernameOrMeta4OrEmail(String query) {
        return findByUsernameOrMeta4OrEmail(query, query, query);
    }

    List<Usuario> findByUsername(String username);
}
