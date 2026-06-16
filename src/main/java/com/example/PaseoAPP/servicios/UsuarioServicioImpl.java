package com.example.PaseoAPP.servicios;

import com.example.PaseoAPP.dtos.UsuarioDTO;
import com.example.PaseoAPP.mapeadores.IMapaUsuario;
import com.example.PaseoAPP.modelos.Usuario;
import com.example.PaseoAPP.repositorios.IRepositorioUsuario;
import com.example.PaseoAPP.validadores.IValidadorUsuario;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioServicioImpl implements IUsuarioServicio {

    // 1. Declaramos las variables como private final
    private final IRepositorioUsuario repositorioUsuario;
    private final IMapaUsuario mapaUsuario;
    private final IValidadorUsuario validador;

    // 2. Creamos el constructor para la inyección de dependencias
    public UsuarioServicioImpl(IRepositorioUsuario repositorioUsuario, IMapaUsuario mapaUsuario, IValidadorUsuario validador) {
        this.repositorioUsuario = repositorioUsuario;
        this.mapaUsuario = mapaUsuario;
        this.validador = validador;
    }

    @Override
    public UsuarioDTO guardarUsuarioEnBD(Usuario datos) {
       // 1. El Inspector (Validador) hace TODO el trabajo de los if.
        // Si algo está mal, él detiene el programa aquí mismo lanzando el error.
        this.validador.validarUsuario(datos);

        // 2. Si pasamos de la línea anterior, el Chef (Servicio) cocina tranquilo.
        Usuario usuarioGuardado = this.repositorioUsuario.save(datos);
        
        // 3. Empacamos (Mapeador) y entregamos.
        return this.mapaUsuario.convertir_modelo_a_dto(usuarioGuardado);
    }

    @Override
    public UsuarioDTO modificarUsuarioEnBD(Usuario datos, UUID id) {
        Optional<Usuario> usuario_que_estoy_buscando = this.repositorioUsuario.findById(id);
        if(usuario_que_estoy_buscando.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario que quieres editar, no existe en BD");
        }
        
        Usuario usuario_que_encontre = usuario_que_estoy_buscando.get();
        if(datos.getNombres() == null || datos.getNombres().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Revisa el nombre ingresado");
        }

        usuario_que_encontre.setNombres(datos.getNombres());
        Usuario usuarioModificado = this.repositorioUsuario.save(usuario_que_encontre);
        
        return this.mapaUsuario.convertir_modelo_a_dto(usuarioModificado);
    }

    @Override
    public void eliminarUsuarioEnBD(UUID id) {
        Optional<Usuario> usuario_que_estoy_buscando = this.repositorioUsuario.findById(id);
        if(usuario_que_estoy_buscando.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario que quieres eliminar, no existe en BD");
        }
        this.repositorioUsuario.deleteById(id);
    }

    @Override
    public List<UsuarioDTO> buscarUsuariosEnBD() {
        List<Usuario> usuariosEnBD = this.repositorioUsuario.findAll();
        return this.mapaUsuario.convertir_lista_de_modelo_a_lista_de_dto(usuariosEnBD);
    }
}