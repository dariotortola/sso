package com.konecta.acceso.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.konecta.acceso.model.Aplicacion;

public interface AplicacionRepository extends JpaRepository<Aplicacion, Long> {
    List<Aplicacion> findByCodigo(String codigo);
}
