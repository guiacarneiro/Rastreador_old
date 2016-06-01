package com.project.tigunibh2015_2.rastreador.parametros;


import com.project.tigunibh2015_2.rastreador.Modelos.Usuario;

public class ParametroRastreamento {
	private Usuario usuarioPai;
	private Usuario usuarioFilho;
	
	public Usuario getUsuarioPai() {
		return usuarioPai;
	}
	public void setUsuarioPai(Usuario usuarioPai) {
		this.usuarioPai = usuarioPai;
	}
	public Usuario getUsuarioFilho() {
		return usuarioFilho;
	}
	public void setUsuarioFilho(Usuario usuarioFilho) {
		this.usuarioFilho = usuarioFilho;
	}
}
