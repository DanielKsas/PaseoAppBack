package com.example.PaseoAPP.servicios;

import com.example.PaseoAPP.dtos.EspacioDTO;
import com.example.PaseoAPP.mapeadores.IMapaEspacio;
import com.example.PaseoAPP.modelos.Espacio;
import com.example.PaseoAPP.repositorios.IRepositorioEspacio;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EspacioServicioImpl implements IEspacioServicio {

    // 1. Variables final
    private final IRepositorioEspacio repositorioEspacio;
    private final IMapaEspacio mapaEspacio;

    // 2. Inyección por constructor
    public EspacioServicioImpl(IRepositorioEspacio repositorioEspacio, IMapaEspacio mapaEspacio) {
        this.repositorioEspacio = repositorioEspacio;
        this.mapaEspacio = mapaEspacio;
    }

    @Override
    public EspacioDTO guardarEspacioEnBD(Espacio datos) {
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

        Espacio espacioGuardado = this.repositorioEspacio.save(datos);
        // 3. Uso del mapeador inyectado
        return this.mapaEspacio.convertir_modelo_a_dto(espacioGuardado);
    }

    @Override
    public EspacioDTO modificarEspacioEnBD(Espacio datos, UUID id) {
        Optional<Espacio> espacio_que_estoy_buscando = this.repositorioEspacio.findById(id);
        if(espacio_que_estoy_buscando.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El espacio que quieres editar, no existe en BD");
        }
        
        Espacio espacio_que_encontre = espacio_que_estoy_buscando.get();
        if(datos.getNombre() == null || datos.getNombre().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Revisa el nombre ingresado");
        }
        if(datos.getDescripcion() == null || datos.getDescripcion().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Revisa la descripcion ingresada");
        }
        if(datos.getAforo() == null || datos.getAforo() <= 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El aforo debe ser mayor a 0");
        }

        espacio_que_encontre.setNombre(datos.getNombre());
        espacio_que_encontre.setDescripcion(datos.getDescripcion());
        espacio_que_encontre.setFoto(datos.getFoto());
        espacio_que_encontre.setAforo(datos.getAforo());
        
        Espacio espacioModificado = this.repositorioEspacio.save(espacio_que_encontre);
        return this.mapaEspacio.convertir_modelo_a_dto(espacioModificado);
    }

    @Override
    public void eliminarEspacioEnBD(UUID id) {
        Optional<Espacio> espacio_que_estoy_buscando = this.repositorioEspacio.findById(id);
        if(espacio_que_estoy_buscando.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El espacio que quieres eliminar, no existe en BD");
        }
        this.repositorioEspacio.deleteById(id);
    }

    @Override
    public List<EspacioDTO> buscarEspaciosEnBD() {
        List<Espacio> espaciosEnBD = this.repositorioEspacio.findAll();
        return this.mapaEspacio.convertir_lista_de_modelo_a_lista_de_dto(espaciosEnBD);
    }
}