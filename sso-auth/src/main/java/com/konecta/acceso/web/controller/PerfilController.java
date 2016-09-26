package com.konecta.acceso.web.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.konecta.acceso.model.Perfil;
import com.konecta.acceso.repository.PerfilRepository;
import com.konecta.acceso.repository.PermisoRepository;
import com.konecta.sso.model.Permiso;

@RestController
@RequestMapping("perfil")
public class PerfilController {

    @Autowired
    private PerfilRepository repository;
    @Autowired
    private PermisoRepository permisoRepo;

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void remove(@PathVariable Long id) {
        repository.delete(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @Transactional
    public void update(@PathVariable Long id, @RequestBody Perfil modificado) {
        Perfil perfil = repository.findOne(id);
        perfil.setDescripcion(modificado.getDescripcion());
        // aseguramos que no exista repetido
        if (repository.findByAplicacionIdAndCodigo(perfil.getAplicacion().getId(), modificado.getCodigo()).isEmpty()) {
            perfil.setCodigo(modificado.getCodigo());
        }
        repository.save(perfil);
    }

    @RequestMapping(value = "{id}/permiso", method = RequestMethod.GET)
    public List<Permiso> getIncluded(@PathVariable Long id) {
        return repository.findOne(id).getPermisos();
    }

    @RequestMapping(value = "{idPerfil}/permiso/{idPermiso}", method = RequestMethod.POST)
    @Transactional
    public void addPermiso(@PathVariable Long idPerfil, @PathVariable Long idPermiso) {
        Perfil perfil = repository.findOne(idPerfil);
        Permiso permiso = permisoRepo.findOne(idPermiso);
        if (!perfil.getPermisos().contains(permiso)) {
            perfil.getPermisos().add(permiso);
            repository.save(perfil);
        }
    }

    @RequestMapping(value = "{idPerfil}/permiso/{idPermiso}", method = RequestMethod.DELETE)
    @Transactional
    public void removePermiso(@PathVariable Long idPerfil, @PathVariable Long idPermiso) {
        Perfil perfil = repository.findOne(idPerfil);
        Permiso permiso = permisoRepo.findOne(idPermiso);
        if (perfil.getPermisos().remove(permiso)) {
            repository.save(perfil);
        }
    }
}
