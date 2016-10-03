package com.konecta.sso.controller;

import java.security.Principal;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.konecta.sso.controller.model.NewUsuario;
import com.konecta.sso.controller.model.PasswordChange;
import com.konecta.sso.controller.model.SimpleResponse;
import com.konecta.sso.model.Usuario;
import com.konecta.sso.repository.UsuarioRepository;
import com.konecta.sso.service.UserClientAuthoritiesService;
import com.konecta.sso.service.UsuarioService;

/**
 * Gestiones sobre el usuario logado
 * 
 * @author dtortola
 *
 */
@RestController
public class LoggedInController {
    private static final Log LOG = LogFactory.getLog(LoggedInController.class);
    @Autowired
    private JdbcUserDetailsManager userManager;

    @Autowired
    private UserClientAuthoritiesService service;

    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private UsuarioService userService;

    /**
     * @param logged
     * @return información del usuario logado actualmente
     */
    @GetMapping({ "me", "usuario/me/auth" })
    public Authentication getMe(Authentication logged) {
        if (logged instanceof OAuth2Authentication) {
            // entrada por OAuth, filtramos los permisos por aplicación
            List<String> authorities = service.getAuthorities((OAuth2Authentication) logged);
            return new KonectaAuthentication(logged, authorities);
        } else {
            return logged;
        }
    }

    @GetMapping("usuario/me")
    @PreAuthorize("isAuthenticated()")
    public NewUsuario getPersonalInformation(Principal logged) {
        // asumimos que existe, en otro caso no podríamos estar logados
        Usuario usuario = repository.findByUsername(logged.getName()).get(0);
        NewUsuario dto = new NewUsuario();
        dto.setEmail(usuario.getEmail());
        dto.setMeta4(usuario.getMeta4());
        dto.setNombre(usuario.getNombre());
        dto.setUsername(usuario.getUsername());
        return dto;
    }

    @PostMapping("usuario/me")
    @PreAuthorize("isAuthenticated()")
    @Transactional
    public void changeMe(@RequestBody NewUsuario cambiado, Principal logged) {
        Usuario usuario = repository.findByUsername(logged.getName()).get(0);
        usuario.setEmail(cambiado.getEmail());
        usuario.setNombre(cambiado.getNombre());
        usuario.setUsername(cambiado.getUsername());
        usuario.setMeta4(cambiado.getMeta4());
        repository.save(usuario);
    }

    /**
     * @param change
     * @return null si todo va bien, un mensaje de error si la password original
     *         no es correcta)
     */
    @PostMapping("usuario/me/password")
    @PreAuthorize("isAuthenticated()")
    @Transactional
    public SimpleResponse changePassword(@RequestBody PasswordChange change, Principal principal) {
        try {
            userManager.changePassword(change.getCurrent(), change.getNueva());
            /*
             * ahora que sabemos que cambia la password, cambiamos las demás
             * codificaciones
             */
            userService.changePassword(principal.getName(), change.getNueva());
            return SimpleResponse.success();
        } catch (BadCredentialsException e) {
            LOG.info("Password incorrecta cambiando de password", e);
            return SimpleResponse.error("La password es incorrecta");
        }
    }

    @GetMapping("usuario/me/count/username")
    public long countUsername(@RequestParam(required = false) String username, Principal logged) {
        Long id = repository.findByUsername(logged.getName()).get(0).getId();
        return repository.countByUsernameIgnoreCaseAndIdNot(username, id);
    }

    @GetMapping("usuario/me/count/meta4")
    public long countMeta4(@RequestParam(required = false) String meta4, Principal logged) {
        Long id = repository.findByUsername(logged.getName()).get(0).getId();
        return repository.countByMeta4IgnoreCaseAndIdNot(meta4, id);
    }

    @GetMapping("usuario/me/count/email")
    public long countEmail(@RequestParam(required = false) String email, Principal logged) {
        Long id = repository.findByUsername(logged.getName()).get(0).getId();
        return repository.countByEmailIgnoreCaseAndIdNot(email, id);
    }
}
