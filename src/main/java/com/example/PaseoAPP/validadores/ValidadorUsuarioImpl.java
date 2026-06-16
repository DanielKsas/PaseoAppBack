package com.example.PaseoAPP.validadores;

import com.example.PaseoAPP.modelos.Usuario;
import com.example.PaseoAPP.repositorios.IRepositorioUsuario;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ValidadorUsuarioImpl implements IValidadorUsuario {

    private final IRepositorioUsuario repositorioUsuario;

    public ValidadorUsuarioImpl(IRepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override // <--- Faltaba esto para confirmar que viene de la interfaz
    public void validarUsuario(Usuario datos) {
        if(datos.getCorreo() == null || datos.getCorreo().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El correo no puede enviarse vacio");
        }
        if(repositorioUsuario.findByCorreo(datos.getCorreo()).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un usuario con este correo");
        }
        if(datos.getNombres()==null || datos.getNombres().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre es obligatorio");
        }
        if(datos.getContraseña()==null || datos.getContraseña().length()<6){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contraseña inválida");
        }
    }
}
