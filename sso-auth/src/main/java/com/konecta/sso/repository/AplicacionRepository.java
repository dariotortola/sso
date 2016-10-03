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

    /**
     * @param codigo
     *            código que tienen que tener las aplicaciones contadas
     * @param id
     *            id de la aplicación que tenemos que ignorar
     * @return cantidad de aplicaciones con el código indicado pero no la id
     */
    long countByCodigoAndIdNot(String codigo, Long id);

    /**
     * @param codigo
     *            código de las aplicaciones buscadas
     * @return cantidad de aplicaciones con este código
     */
    long countByCodigo(String codigo);
}
