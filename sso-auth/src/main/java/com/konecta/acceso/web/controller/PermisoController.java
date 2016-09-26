package com.konecta.acceso.web.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.konecta.acceso.repository.PermisoRepository;
import com.konecta.sso.model.Permiso;

@RestController
@RequestMapping("permiso")
public class PermisoController {

    @Autowired
    private PermisoRepository repository;

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void remove(@PathVariable Long id) {
        repository.delete(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @Transactional
    public void update(@PathVariable Long id, @RequestBody Permiso modificado) {
        Permiso permiso = repository.findOne(id);
        permiso.setDescripcion(modificado.getDescripcion());
        // aseguramos que no exista repetido
        if (repository.findByAplicacionIdAndCodigo(permiso.getAplicacion().getId(), modificado.getCodigo()).isEmpty()){            
            permiso.setCodigo(modificado.getCodigo());
        }
        repository.save(permiso);
    }
}
