package net.gefco.controlclientes.negocio;


import net.gefco.controlclientes.modelo.Usuario;
import net.gefco.controlclientes.persistencia.UsuarioDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioDao usuarioDao;

	public void guardar(Usuario usuario) {		
		usuarioDao.guardar(usuario);
	}

	public void eliminar(Usuario usuario) {		
		usuarioDao.eliminar(usuario);
	}

	public Usuario buscarMatricula(String login){
		return usuarioDao.buscarMatricula(login);
	}
}