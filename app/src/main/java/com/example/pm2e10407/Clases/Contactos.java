package com.example.pm2e10407.Clases;
//REALIZADO POR:
//Mirian Fatima Ordoñez Amador
//Katherin Nicole Amador Maradiaga

import java.sql.Blob;

public class Contactos {
    private int codigo;
    private String codigoPais;
    private String nombre;
    private int numero;
    private String nota;
    private Blob imagen;

    public Blob getImage() {
        return imagen;
    }

    public Contactos() {
    }

    public Contactos(int codigo, String codigoPais, String nombre, int numero, String nota, Blob imagen) {
        this.codigo = codigo;
        this.codigoPais = codigoPais;
        this.nombre = nombre;
        this.numero = numero;
        this.nota = nota;
        this.imagen = imagen;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(String codigoPais) {
        this.codigoPais = codigoPais;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public void setImage(Blob image) {
        this.imagen = image;
    }

}
