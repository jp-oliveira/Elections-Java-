package com.pac.elections.Modelo;

import java.io.Serializable;

public class eleitor implements Serializable {
    String nome;
    int titulo;
    boolean votou;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTitulo() {
        return titulo;
    }

    public void setTitulo(int titulo) {
        this.titulo = titulo;
    }

    public boolean isVotou() {
        return votou;
    }

    public void setVotou(boolean votou) {
        this.votou = votou;
    }
}
