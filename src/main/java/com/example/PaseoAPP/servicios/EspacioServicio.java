package com.example.PaseoAPP.servicios;

import com.example.PaseoAPP.modelos.Espacio;
import com.example.PaseoAPP.repositorios.IRepositorioEspacio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EspacioServicio {

    //Inyectando una dependencia al repositorioEspacio
    @Autowired
    private IRepositorioEspacio repositorioEspacio;

    public Espacio guardarEspacioEnBD(Espacio datos){

        //condiciones logicas para validar datos a guardar

        //1. validar que el nombre del espacio no se haya guardado previamente
        if(repositorioEspacio.findByNombre(datos.getNombre()).isPresent()){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe un espacio registrado con ese nombre"
            );
        }

        if(datos.getNombre().isEmpty()||datos.getNombre().isBlank()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El nombre del espacio no puede enviarse vacio"
            );
        }

        if(datos.getDescripcion().isEmpty()||datos.getDescripcion().isBlank()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La descripcion del espacio no puede enviarse vacia"
            );
        }

        if(datos.getAforo()<=0){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El aforo debe ser mayor a 0"
            );
        }

        //Si paso la zona de validaciones procedo a preparar la
        // receta(Ejecuto la query que se necesite)
        return this.repositorioEspacio.save(datos);

    }
    public Espacio modificarEspacioEnBD(Espacio datos, UUID id){
        //validar que datos me envian y si estos cumplen las reglas del negocio
        //Modificar los datos en BD con ayuda del repositorio

        //1.Buscar si el espacio existe en BD
        Optional<Espacio>espacio_que_estoy_buscando=this.repositorioEspacio.findById(id);
        if(espacio_que_estoy_buscando.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "El espacio que quieres editar, no existe en BD"
            );
        }
        Espacio espacio_que_encontre=espacio_que_estoy_buscando.get();

        //2.Validar la informacion nueva que me manda el cliente
        if(datos.getNombre().isEmpty() || datos.getNombre().isBlank()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Revisa el nombre ingresado"
            );

        }

        //3.Ejecutar el nuevo guardado y retronar
        espacio_que_encontre.setNombre(datos.getNombre());
        espacio_que_encontre.setDescripcion(datos.getDescripcion());
        espacio_que_encontre.setFoto(datos.getFoto());
        espacio_que_encontre.setAforo(datos.getAforo());
        return this.repositorioEspacio.save(espacio_que_encontre);


    }
    public boolean eliminarEspacioEnBD(UUID id){
        //Buscar y validar si el ID que me envian existe
        //Elimino el registro en BD
        Optional<Espacio>espacio_que_estoy_buscando=this.repositorioEspacio.findById(id);
        if(espacio_que_estoy_buscando.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "El espacio que quieres eliminar, no existe en BD"
            );
        }
        Espacio espacio_que_encontre=espacio_que_estoy_buscando.get();
        this.repositorioEspacio.deleteById(id);
        return true;
    }
    public List<Espacio> buscarEspaciosEnBD(){
        //**** Dependiendo del parametro de busqueda debo implementar validaciones
        //devuelvo los espacios que encuentre en BD
        List<Espacio>espaciosEncontrados=this.repositorioEspacio.findAll();
        return espaciosEncontrados;
    }


}
