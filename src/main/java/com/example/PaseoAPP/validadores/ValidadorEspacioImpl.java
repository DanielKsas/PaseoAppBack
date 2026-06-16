package com.example.PaseoAPP.validadores;

import com.example.PaseoAPP.modelos.Espacio;
import com.example.PaseoAPP.repositorios.IRepositorioEspacio;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ValidadorEspacioImpl implements IValidadorEspacio {

    private final IRepositorioEspacio repositorioEspacio;

   

    public ValidadorEspacioImpl(IRepositorioEspacio repositorioEspacio) {
        this.repositorioEspacio = repositorioEspacio;
    }
@Override
public void validarEspacio(Espacio datos) {
        if(datos.getNombre() == null || datos.getNombre().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre del espacio no puede enviarse vacio");
        }
        if(repositorioEspacio.findByNombre(datos.getNombre()).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un espacio registrado con ese nombre");
        }
        if(datos.getDescripcion() == null || datos.getDescripcion().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La descripcion del espacio no puede enviarse vacia");
        }
        if(datos.getAforo() == null || datos.getAforo() <= 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El aforo debe ser mayor a 0");
        }
    }
}