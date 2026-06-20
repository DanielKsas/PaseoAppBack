package com.example.PaseoAPP.validadores;

import com.example.PaseoAPP.modelos.Reserva;

public interface IValidadorReserva {
    // Promesa 1: Validar cuando se crea
    void validarReserva(Reserva datos);
    
    // Promesa 2: Validar cuando se modifica (¡Esta era la que faltaba!)
    void validarModificacionReserva(Reserva datos);
}