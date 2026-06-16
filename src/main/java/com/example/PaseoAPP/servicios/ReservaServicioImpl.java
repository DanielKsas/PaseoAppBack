package com.example.PaseoAPP.servicios;

import com.example.PaseoAPP.dtos.ReservaDTO;
import com.example.PaseoAPP.mapeadores.IMapaReserva;
import com.example.PaseoAPP.modelos.Reserva;
import com.example.PaseoAPP.repositorios.IRepositorioReserva;
import com.example.PaseoAPP.validadores.IValidadorReserva;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReservaServicioImpl implements IReservaServicio {

    private final IRepositorioReserva repositorioReserva;
    private final IMapaReserva mapaReserva;
    private final IValidadorReserva validador; // <--- El Validador

    public ReservaServicioImpl(IRepositorioReserva repositorioReserva, IMapaReserva mapaReserva, IValidadorReserva validador) {
        this.repositorioReserva = repositorioReserva;
        this.mapaReserva = mapaReserva;
        this.validador = validador;
    }

    @Override
    public ReservaDTO guardarReservaEnBD(Reserva datos) {
        this.validador.validarReserva(datos); // <--- Delega la validación
        
        Reserva reservaGuardada = this.repositorioReserva.save(datos);
        return this.mapaReserva.convertir_modelo_a_dto(reservaGuardada);
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
        return this.mapaReserva.convertir_modelo_a_dto(reservaModificada);
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
        return this.mapaReserva.convertir_lista_de_modelo_a_lista_de_dto(reservasEnBD);
    }
}