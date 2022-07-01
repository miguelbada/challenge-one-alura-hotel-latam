package jdbc.controller;

import jdbc.dao.UsuarioDAO;
import jdbc.modelo.Usuario;

public class UsuarioController {
	private UsuarioDAO usuarioDAO;
	
	public UsuarioController() {
		this.usuarioDAO = new UsuarioDAO();
	}
	
	public Boolean verificarUsuario(Usuario usuario) {
		Usuario usuarioDao = this.usuarioDAO.recuperarUsuario(usuario);
		
		return usuarioDao != null;
	}
}
