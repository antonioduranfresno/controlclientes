package net.gefco.controlclientes.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Rol implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id					= null;
	
	private String rol_descripcion		= null;
		
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getRol_descripcion() {
		return rol_descripcion;
	}
	public void setRol_descripcion(String rol_descripcion) {
		this.rol_descripcion = rol_descripcion;
	}

	public Rol() {
		super();	
	}
	
	public Rol(Integer id, String rol_descripcion) {
		super();
		this.id = id;
		this.rol_descripcion = rol_descripcion;
	}

	public void copiarValores(Rol dAux,boolean copiarId){
		this.vaciar();
		if (dAux==null){return;}
		//Para esta instancia el codigo hace las veces de ID
		if(copiarId){id=dAux.id;}		
		rol_descripcion = dAux.rol_descripcion;		
	}
	
	public void vaciar() {
		id = 0;
		rol_descripcion = "";
	}
}