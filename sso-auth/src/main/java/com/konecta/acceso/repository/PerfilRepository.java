package com.konecta.acceso.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.konecta.acceso.model.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    List<Perfil> findByAplicacionId(Long id);

    List<Perfil> findByAplicacionIdAndCodigo(Long id, String codigo);
}
