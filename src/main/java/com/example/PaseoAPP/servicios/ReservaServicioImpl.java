package com.example.PaseoAPP.servicios;

import com.example.PaseoAPP.dtos.ReservaDTO;
import com.example.PaseoAPP.mapeadores.IMapaReserva;
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
public class ReservaServicioImpl implements IReservaServicio {

    @Autowired
    private IRepositorioReserva repositorioReserva;

    @Override
    public ReservaDTO guardarReservaEnBD(Reserva datos) {
        if(datos.getFecha() == null || datos.getFecha().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de la reserva no puede enviarse vacia");
        }
        if(datos.getTiempo() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El tiempo de la reserva no puede enviarse vacio");
        }

        Reserva reservaGuardada = this.repositorioReserva.save(datos);
        return IMapaReserva.INSTANCIA.convertir_modelo_a_dto(reservaGuardada);
    }

    @Override
    public ReservaDTO modificarReservaEnBD(Reserva datos, UUID id) {
        Optional<Reserva> reserva_que_estoy_buscando = this.repositorioReserva.findById(id);
        if(reserva_que_estoy_buscando.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La reserva que quieres editar, no existe en BD");
        }
        
        Reserva reserva_que_encontre = reserva_que_estoy_buscando.get();
        if(datos.getFecha() == null || datos.getFecha().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Revisa la fecha ingresada");
        }
        if(datos.getTiempo() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Revisa el tiempo ingresado");
        }

        reserva_que_encontre.setFecha(datos.getFecha());
        reserva_que_encontre.setTiempo(datos.getTiempo());
        
        Reserva reservaModificada = this.repositorioReserva.save(reserva_que_encontre);
        return IMapaReserva.INSTANCIA.convertir_modelo_a_dto(reservaModificada);
    }

    @Override
    public void eliminarReservaEnBD(UUID id) {
        Optional<Reserva> reserva_que_estoy_buscando = this.repositorioReserva.findById(id);
        if(reserva_que_estoy_buscando.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La reserva que quieres eliminar, no existe en BD");
        }
        this.repositorioReserva.deleteById(id);
    }

    @Override
    public List<ReservaDTO> buscarReservasEnBD() {
        List<Reserva> reservasEnBD = this.repositorioReserva.findAll();
        return IMapaReserva.INSTANCIA.convertir_lista_de_modelo_a_lista_de_dto(reservasEnBD);
    }
}