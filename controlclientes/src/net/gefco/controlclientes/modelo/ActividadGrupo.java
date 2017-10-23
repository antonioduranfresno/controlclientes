package net.gefco.controlclientes.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ActividadGrupo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue	
	private Integer id 			= null;
	
	private String acgr_nombre	= null;
	
	public ActividadGrupo() {
		super();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getAcgr_nombre() {
		return acgr_nombre;
	}
	public void setAcgr_nombre(String acgr_nombre) {
		this.acgr_nombre = acgr_nombre;
	}

	public ActividadGrupo(Integer id, String acgr_nombre) {
		super();
		this.id = id;
		this.acgr_nombre = acgr_nombre;
	}

	public void copiarValores(ActividadGrupo dAux, boolean copiarId) {
		this.vaciar();
		if (dAux==null){return;}
		if(copiarId){id=dAux.id;}
		acgr_nombre = dAux.acgr_nombre;	
	}
		
	public void vaciar() {
		id = 0;
		acgr_nombre = "";
	}

	@Override
	public String toString() {
		return "ActividadGrupo [id=" + id + ", acgr_nombre=" + acgr_nombre
				+ "]";
	}
		
}