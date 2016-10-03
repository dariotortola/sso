package com.konecta.sso.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.konecta.sso.config.AuthServerConfig;
import com.konecta.sso.model.Aplicacion;
import com.konecta.sso.repository.AplicacionRepository;

@RestController
@RequestMapping("aplicacion")
public class AplicacionController {
    @Autowired
    @Qualifier(AuthServerConfig.SECURITY_ENCODER_BEAN_NAME)
    private PasswordEncoder encoder;

    // @Autowired
    // private PerfilRepository perfilRepo;
    // @Autowired
    // private PermisoRepository permisoRepo;
    @Autowired
    private AplicacionRepository repository;

    /**
     * @param nueva
     *            aplicación con código no existente
     * @return aplicación modificada como resultado del guardado
     */
    @PostMapping
    public Aplicacion create(@RequestBody Aplicacion nueva) {
        Assert.hasText(nueva.getCodigo());
        nueva.setId(null);
        return repository.save(nueva);
    }

    // @RequestMapping(value = "{appId}/perfil", method = RequestMethod.POST)
    // @Transactional
    // public Perfil createPerfil(@PathVariable Long appId, @RequestBody Perfil
    // perfil) {
    // /*
    // * TODO restricción de unicidad de código de perfil para una aplicación
    // */
    // perfil.setAplicacion(repository.findOne(appId));
    // perfil.setId(null);
    // return perfilRepo.save(perfil);
    // }
    //
    // @RequestMapping(value = "{id}/permiso/{codigo}", method =
    // RequestMethod.POST)
    // @Transactional
    // public CreateFindResult<Permiso> createPermiso(@PathVariable Long id,
    // @PathVariable String codigo) {
    // /*
    // * TODO esta restricción no debería estar aquí sino en la definición del
    // * modelo
    // */
    // List<Permiso> existen = permisoRepo.findByAplicacionIdAndCodigo(id,
    // codigo);
    // CreateFindResult<Permiso> cfr = new CreateFindResult<>();
    // cfr.setCreada(existen.isEmpty());
    // if (cfr.isCreada()) {
    // Permiso creado = new Permiso();
    // creado.setCodigo(codigo);
    // creado.setAplicacion(repository.findOne(id));
    // creado = permisoRepo.save(creado);
    // cfr.setObject(creado);
    // } else {
    // cfr.setObject(existen.get(0));
    // }
    // return cfr;
    // }

    /**
     * @param id
     *            id de la aplicación que queremos
     * @return aplicación con esta id
     */
    @GetMapping("{id}")
    public Aplicacion get(@PathVariable Long id) {
        return repository.findOne(id);
    }

    /**
     * @return todas las aplicaciones. No deberían ser suficientes como para
     *         merecer un paginado
     */
    @GetMapping
    public List<Aplicacion> list(@RequestParam(required = false) String query) {
        Sort sort = new Sort("codigo");
        if (query == null) {
            return repository.findAll(sort);
        } else {
            return repository.findByCodigoOrDescripcion(query, sort);
        }
    }

    // @RequestMapping(value = "{id}/perfil", method = RequestMethod.GET)
    // public List<Perfil> listPerfiles(@PathVariable Long id) {
    // return perfilRepo.findByAplicacionId(id);
    // }
    //
    // @RequestMapping(value = "{id}/permiso", method = RequestMethod.GET)
    // public List<Permiso> listPermisos(@PathVariable Long id) {
    // return permisoRepo.findByAplicacionId(id);
    // }

    /**
     * Elimina la aplicación
     *
     * @param id
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void remove(@PathVariable Long id) {
        repository.delete(id);
    }

    /**
     * 
     * @param id
     *            id de la aplicación
     * @param codigo
     *            código que queremos saber si sería único, si la aplicación se
     *            llamara así
     */
    @GetMapping({ "unique/{id}/codigo" })
    public long codigoUnico(@PathVariable Long id, @RequestParam String codigo) {
        return repository.countByCodigoAndIdNot(codigo, id);
    }

    /**
     * 
     * @param codigo
     *            código que queremos saber si sería único, si la aplicación se
     *            llamara así
     */
    @GetMapping({ "unique/codigo" })
    public long codigoUnico(@RequestParam String codigo) {
        return repository.countByCodigo(codigo);
    }

    /**
     * Modifica la aplicación con la id indicada de modo que sus campos sean los
     * que tiene modificada (salvo, obviamente, id y version porque los gestiona
     * la bbdd)
     *
     * Sólo cambia la password si no es igual
     * 
     * Asume que está comprobado que codigo respeta la unicidad
     *
     * @param id
     * @param modificada
     */
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @Transactional
    public void updateAplicacion(@PathVariable Long id, @RequestBody Aplicacion modificada) {

        // actualiza todo excepto version, id y password
        Aplicacion app = repository.findOne(id);
        app.setCodigo(modificada.getCodigo());
        app.setAccessTokenValidity(modificada.getAccessTokenValidity());
        app.setDescripcion(modificada.getDescripcion());
        app.setRefreshTokenValidity(modificada.getRefreshTokenValidity());
        app.setWebServerRedirectUri(modificada.getWebServerRedirectUri());

        if (StringUtils.isNotBlank(modificada.getPassword())
                && StringUtils.equals(app.getPassword(), modificada.getPassword())) {
            // la password ha cambiado
            encoder.encode(modificada.getPassword());
        }
        repository.save(app);
    }
}
