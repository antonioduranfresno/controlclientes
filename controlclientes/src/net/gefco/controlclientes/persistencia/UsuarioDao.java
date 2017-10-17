package net.gefco.controlclientes.persistencia;

import net.gefco.controlclientes.modelo.Usuario;

public interface UsuarioDao {

	public void guardar(Usuario usuario);
	
	public void actualizar(Usuario usuario);
	
	public void eliminar(Usuario usuario);
	
	public Usuario buscarMatricula(String matricula);

}
