package com.example.PaseoAPP.modelos;

import jakarta.persistence.*;

import java.util.UUID;

import com.example.PaseoAPP.enums.FranjaHoraria;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String fecha;

    @Enumerated(EnumType.STRING)
    private FranjaHoraria tiempo;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)

    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_espacio", nullable = false)
    private Espacio espacio;

    public Reserva() {
    }

    public Reserva(UUID id, String fecha, FranjaHoraria tiempo, Usuario usuario, Espacio espacio) {
        this.id = id;
        this.fecha = fecha;
        this.tiempo = tiempo;
        this.usuario = usuario;
        this.espacio = espacio;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public FranjaHoraria getTiempo() {
        return tiempo;
    }

    public void setTiempo(FranjaHoraria tiempo) {
        this.tiempo = tiempo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Espacio getEspacio() {
        return espacio;
    }

    public void setEspacio(Espacio espacio) {
        this.espacio = espacio;
    }

}
