package org.fabio.serviflashproject.Modelos;

/**
 * Created by root on 14/06/16.
 */
public class Pedido {

    public String descripcion;
    public String estado;
    public Double origenlat;
    public Double origenlong;
    public Double destinolat;
    public Double destinolong;
    public int idcliente;
    public int idempleado;
    public String direccionorigen;
    public String direcciondestino;
    public String barrio;

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getDireccionorigen() {
        return direccionorigen;
    }

    public void setDireccionorigen(String direccionorigen) {
        this.direccionorigen = direccionorigen;
    }

    public String getDirecciondestino() {
        return direcciondestino;
    }

    public void setDirecciondestino(String direcciondestino) {
        this.direcciondestino = direcciondestino;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getOrigenlat() {
        return origenlat;
    }

    public void setOrigenlat(Double origenlat) {
        this.origenlat = origenlat;
    }

    public Double getOrigenlong() {
        return origenlong;
    }

    public void setOrigenlong(Double origenlong) {
        this.origenlong = origenlong;
    }

    public Double getDestinolat() {
        return destinolat;
    }

    public void setDestinolat(Double destinolat) {
        this.destinolat = destinolat;
    }

    public Double getDestinolong() {
        return destinolong;
    }

    public void setDestinolong(Double destinolong) {
        this.destinolong = destinolong;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public int getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(int idempleado) {
        this.idempleado = idempleado;
    }
}
