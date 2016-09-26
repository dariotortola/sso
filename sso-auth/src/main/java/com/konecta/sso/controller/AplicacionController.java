package com.konecta.sso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.konecta.sso.model.Aplicacion;
import com.konecta.sso.repository.AplicacionRepository;

@RestController
@RequestMapping("aplicacion")
public class AplicacionController {

    // @Autowired
    // private PerfilRepository perfilRepo;
    // @Autowired
    // private PermisoRepository permisoRepo;
    @Autowired
    private AplicacionRepository repository;

    // /**
    // * @param nueva
    // * aplicación con código no existente
    // * @return aplicación modificada como resultado del guardado
    // */
    // @RequestMapping(method = RequestMethod.POST)
    // public Aplicacion create(@RequestBody Aplicacion nueva) {
    // Assert.hasText(nueva.getCodigo());
    //
    // nueva.setId(null);
    // return repository.save(nueva);
    // }
    //
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
    //
    // /**
    // * @param id
    // * @return aplicación con esta id
    // */
    // @RequestMapping(value = "{id}", method = RequestMethod.GET)
    // public Aplicacion get(@PathVariable Long id) {
    // return repository.findOne(id);
    // }

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
    //
    // /**
    // * Elimina la aplicación
    // *
    // * @param id
    // */
    // @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    // public void remove(@PathVariable Long id) {
    // repository.delete(id);
    // }
    //
    // /**
    // * Modifica la aplicación con la id indicada de modo que sus campos sean
    // los
    // * que tiene modificada (salvo, obviamente, id y version porque los
    // gestiona
    // * la bbdd)
    // *
    // * Sólo cambia la password si no es igual
    // *
    // * @param id
    // * @param modificada
    // */
    // @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    // @Transactional
    // public void updateAplicacion(@PathVariable Long id, @RequestBody
    // Aplicacion modificada) {
    // // actualiza todo excepto version, id y password
    // Aplicacion app = repository.findOne(id);
    // app.setAccessTokenValidity(modificada.getAccessTokenValidity());
    // app.setDescripcion(modificada.getDescripcion());
    // app.setRefreshTokenValidity(modificada.getRefreshTokenValidity());
    // app.setWebServerRedirectUri(modificada.getWebServerRedirectUri());
    // // antes de cambiar el código, se asegura de que no perdamos unicidad
    // List<Aplicacion> apps = repository.findByCodigo(modificada.getCodigo());
    // if (apps.isEmpty() || (apps.size() == 1 &&
    // apps.get(0).getId().equals(id))) {
    // app.setCodigo(modificada.getCodigo());
    // }
    // if (StringUtils.equals(app.getPassword(), modificada.getPassword())) {
    // if (StringUtils.isBlank(modificada.getPassword())) {
    // app.setPassword(null);
    // } else {
    // // la password ha cambiado
    // // TODO codificar la de modificada para actualizar app
    // }
    // }
    // repository.save(app);
    // }
}
