package com.konecta.sso.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.konecta.sso.model.Aplicacion;

public interface AplicacionRepository extends JpaRepository<Aplicacion, Long> {
    List<Aplicacion> findByCodigo(String codigo);

    List<Aplicacion> findByCodigoStartingWithIgnoreCaseOrDescripcionContainingIgnoreCase(String codigo,
            String descripcion, Sort sort);

    /**
     * @param query
     * @param sort
     * @return lista de aplicaciones ordenadas por sort, tales que el código
     *         empieza por query o la descripción contiene query, ambas ignore
     *         case
     */
    default List<Aplicacion> findByCodigoOrDescripcion(String query, Sort sort) {
        return findByCodigoStartingWithIgnoreCaseOrDescripcionContainingIgnoreCase(query, query, sort);
    }
}
