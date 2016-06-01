package com.project.tigunibh2015_2.rastreador.Modelos;

/**
 * Created by Jonatas on 15/05/2016.
 */
public class Resultado {
    private int status;
    private String descricao;

    public Resultado() {

    }
    public Resultado( int status, String descricao) {
        this.status = status;
        this.descricao = descricao;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
