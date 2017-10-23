package net.gefco.controlclientes.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Actividad implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue	
	private Integer id 				= null;
	
	private String acti_codigo		= null;
	
	private String acti_nombre		= null;
	
	@ManyToOne
	@JoinColumn(name = "grupo")
	private ActividadGrupo grupo 	= null;
	
	public Actividad() {
		super();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getActi_codigo() {
		return acti_codigo;
	}
	public void setActi_codigo(String acti_codigo) {
		this.acti_codigo = acti_codigo;
	}

	public String getActi_nombre() {
		return acti_nombre;
	}
	public void setActi_nombre(String acti_nombre) {
		this.acti_nombre = acti_nombre;
	}

	public ActividadGrupo getGrupo() {
		return grupo;
	}

	public void setGrupo(ActividadGrupo grupo) {
		this.grupo = grupo;
	}

	public Actividad(Integer id, String acti_codigo, String acti_nombre,
			ActividadGrupo grupo) {
		super();
		this.id = id;
		this.acti_codigo = acti_codigo;
		this.acti_nombre = acti_nombre;
		this.grupo = grupo;
	}

	public void copiarValores(Actividad dAux, boolean copiarId) {
		this.vaciar();
		if (dAux==null){return;}
		if(copiarId){id=dAux.id;}
		acti_codigo = dAux.acti_codigo;
		grupo.copiarValores(dAux.grupo, true);;	
	}
	
	public void vaciar() {
		id = 0;
		acti_codigo = "";
		acti_nombre = "";
		grupo = new ActividadGrupo();
	}

	@Override
	public String toString() {
		return "Actividad [id=" + id + ", acti_codigo=" + acti_codigo
				+ ", acti_nombre=" + acti_nombre + ", grupo=" + grupo + "]";
	}
	
	public String toStringCodigoNombre() {
		return acti_codigo + " - " + acti_nombre;
	}
}