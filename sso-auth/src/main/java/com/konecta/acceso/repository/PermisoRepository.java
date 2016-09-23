package com.konecta.acceso.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.konecta.acceso.model.Permiso;

public interface PermisoRepository extends JpaRepository<Permiso, Long> {
    List<Permiso> findByAplicacionId(Long id);

    List<Permiso> findByAplicacionIdAndCodigo(Long id, String codigo);
}
