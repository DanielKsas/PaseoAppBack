package com.example.PaseoAPP.validadores;

import com.example.PaseoAPP.modelos.Reserva;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ValidadorReservaImpl implements IValidadorReserva {

    // --- PRIMER MÉTODO (Validar la creación) ---
    @Override
    public void validarReserva(Reserva datos) {
        if(datos.getFecha() == null || datos.getFecha().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de la reserva no puede enviarse vacia");
        }
        if(datos.getTiempo() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El tiempo de la reserva no puede enviarse vacio");
        }
    }

    // --- SEGUNDO MÉTODO (Validar la modificación) ---
    @Override
    public void validarModificacionReserva(Reserva datos) {
        if(datos.getFecha() == null || datos.getFecha().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Revisa la fecha ingresada");
        }
        if(datos.getTiempo() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Revisa el tiempo ingresado");
        }
    }
}