package com.project.tigunibh2015_2.rastreador;

/**
 * Created by Jonatas on 23/04/2016.
 */
public class Dependente {
    private String nome;
    private String telefone;
    private boolean autorizado;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public boolean isAutorizado() {
        return autorizado;
    }

    public void setAutorizado(boolean autorizado) {
        this.autorizado = autorizado;
    }
}
