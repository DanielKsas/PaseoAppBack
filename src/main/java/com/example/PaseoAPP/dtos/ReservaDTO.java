package com.example.PaseoAPP.dtos;

import com.example.PaseoAPP.enums.FranjaHoraria;
import java.util.UUID;

public record ReservaDTO( 
       UUID id,
       String fecha,
       FranjaHoraria tiempo,
       String correoUsuario,
       String nombreEspacio
) {}
