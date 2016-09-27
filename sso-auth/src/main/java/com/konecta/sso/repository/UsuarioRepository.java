package com.konecta.sso.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.konecta.sso.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByUsernameOrMeta4OrEmail(String username, String meta4, String email);

    default List<Usuario> findByUsernameOrMeta4OrEmail(String query) {
        return findByUsernameOrMeta4OrEmail(query, query, query);
    }

    List<Usuario> findByUsername(String username);

    /**
     * @param username
     * @return cantidad de usuarios con este username, case-insensitive
     */
    long countByUsernameIgnoreCase(String username);

    /**
     * @param meta4
     * @return cantidad de usuarios con este meta4, case-insensitive
     */
    long countByMeta4IgnoreCase(String meta4);

    /**
     * @param email
     * @return cantidad de usuarios con este email, case-insensitive
     */
    long countByEmailIgnoreCase(String email);

    /**
     * @param username
     * @return cantidad de usuarios con este username, case-insensitive, sin
     *         contar uno con la id indicada
     */
    long countByUsernameIgnoreCaseAndIdNot(String username, Long id);

    /**
     * @param meta4
     * @return cantidad de usuarios con este meta4, case-insensitive, sin contar
     *         uno con la id indicada
     */
    long countByMeta4IgnoreCaseAndIdNot(String meta4, Long id);

    /**
     * @param email
     * @return cantidad de usuarios con este email, case-insensitive, sin contar
     *         uno con la id indicada
     */
    long countByEmailIgnoreCaseAndIdNot(String email, Long id);
}
