package com.example.PaseoAPP.servicios;

import com.example.PaseoAPP.modelos.Reserva;
import com.example.PaseoAPP.repositorios.IRepositorioReserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReservaServicio {

    //Inyectando una dependencia al repositorioReserva
    @Autowired
    private IRepositorioReserva repositorioReserva;

    public Reserva guardarReservaEnBD(Reserva datos){

        //condiciones logicas para validar datos a guardar

        //1. validar que la fecha no este vacia
        if(datos.getFecha().isEmpty()||datos.getFecha().isBlank()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La fecha de la reserva no puede enviarse vacia"
            );
        }

        if(datos.getTiempo().isEmpty()||datos.getTiempo().isBlank()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El tiempo de la reserva no puede enviarse vacio"
            );
        }

        //Si paso la zona de validaciones procedo a preparar la
        // receta(Ejecuto la query que se necesite)
        return this.repositorioReserva.save(datos);

    }
    public Reserva modificarReservaEnBD(Reserva datos, UUID id){
        //validar que datos me envian y si estos cumplen las reglas del negocio
        //Modificar los datos en BD con ayuda del repositorio

        //1.Buscar si la reserva existe en BD
        Optional<Reserva>reserva_que_estoy_buscando=this.repositorioReserva.findById(id);
        if(reserva_que_estoy_buscando.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "La reserva que quieres editar, no existe en BD"
            );
        }
        Reserva reserva_que_encontre=reserva_que_estoy_buscando.get();

        //2.Validar la informacion nueva que me manda el cliente
        if(datos.getFecha().isEmpty() || datos.getFecha().isBlank()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Revisa la fecha ingresada"
            );

        }

        //3.Ejecutar el nuevo guardado y retronar
        reserva_que_encontre.setFecha(datos.getFecha());
        reserva_que_encontre.setTiempo(datos.getTiempo());
        return this.repositorioReserva.save(reserva_que_encontre);


    }
    public boolean eliminarReservaEnBD(UUID id){
        //Buscar y validar si el ID que me envian existe
        //Elimino el registro en BD
        Optional<Reserva>reserva_que_estoy_buscando=this.repositorioReserva.findById(id);
        if(reserva_que_estoy_buscando.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "La reserva que quieres eliminar, no existe en BD"
            );
        }
        Reserva reserva_que_encontre=reserva_que_estoy_buscando.get();
        this.repositorioReserva.deleteById(id);
        return true;
    }
    public List<Reserva> buscarReservasEnBD(){
        //**** Dependiendo del parametro de busqueda debo implementar validaciones
        //devuelvo las reservas que encuentre en BD
        List<Reserva>reservasEncontradas=this.repositorioReserva.findAll();
        return reservasEncontradas;
    }


}
