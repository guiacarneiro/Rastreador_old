package com.project.tigunibh2015_2.rastreador.Modelos;

/**
 * Created by Jonatas on 17/05/2016.
 */
public class UsuarioCadastro {
    private String nomeUsuario;
    private String loginUsuario;
    private String senhaUsuario;
    private String telefone;

    public UsuarioCadastro() {

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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

}
