package com.example.PaseoAPP.controladores;

import com.example.PaseoAPP.dtos.UsuarioDTO;
import com.example.PaseoAPP.modelos.Usuario;
import com.example.PaseoAPP.servicios.IUsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/paseoapi/v1/usuarios")
public class UsuarioControlador {

    @Autowired
    IUsuarioServicio servicio;

    @PostMapping
    public ResponseEntity<UsuarioDTO> controlarGuardado(@RequestBody Usuario datos){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.servicio.guardarUsuarioEnBD(datos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> controlarModificado(@RequestBody Usuario datos, @PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(this.servicio.modificarUsuarioEnBD(datos,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> controlarBorrado(@PathVariable UUID id){
        this.servicio.eliminarUsuarioEnBD(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> controlarListado(){
        return ResponseEntity.status(HttpStatus.OK).body(this.servicio.buscarUsuariosEnBD());
    }
}