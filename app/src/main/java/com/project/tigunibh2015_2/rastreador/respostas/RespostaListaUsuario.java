package com.project.tigunibh2015_2.rastreador.respostas;

import com.project.tigunibh2015_2.rastreador.Modelos.Usuario;

import java.util.List;

public class RespostaListaUsuario {
	Resultado resultado;
	List<Usuario> listaUsuarios;
	
	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}
	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
	public Resultado getResultado() {
		return resultado;
	}
	public void setResultado(Resultado resultado) {
		this.resultado = resultado;
	}
}
