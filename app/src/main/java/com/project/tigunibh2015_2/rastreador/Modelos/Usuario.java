package com.project.tigunibh2015_2.rastreador.Modelos;

/**
 * Created by Jonatas on 15/05/2016.
 */
public class Usuario {

    private int codUsuario;
    private String nomeUsuario;
    private String loginUsuario;
    private String senhaUsuario;
    private boolean rastreavel;
    private String telefone;

    public Usuario() {

    }

    public int getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(int codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getLoginUsuario() {
        return loginUsuario;
    }

    public void setLoginUsuario(String loginUsuario) {
        this.loginUsuario = loginUsuario;
    }

    public String getSenhaUsuario() {
        return senhaUsuario;
    }

    public void setSenhaUsuario(String senhaUsuario) {
        this.senhaUsuario = senhaUsuario;
    }

    public boolean isRastreavel() {
        return rastreavel;
    }

    public void setRastreavel(boolean rastreavel) {
        this.rastreavel = rastreavel;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String toString() {
        return "" + codUsuario + " - " + nomeUsuario;
    }
}
