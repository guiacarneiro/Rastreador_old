package com.project.tigunibh2015_2.rastreador.Modelos;
import com.project.tigunibh2015_2.rastreador.Modelos.Resultado;
import com.project.tigunibh2015_2.rastreador.Modelos.Usuario;


/**
 * Created by Jonatas on 15/05/2016.
 */
public class RespostaUsuario {
    Resultado resultado;
    Usuario usuario;

    public Resultado getResultado() {
        return resultado;
    }
    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
