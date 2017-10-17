package net.gefco.controlclientes.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AgenciaRegional implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue	
	private Integer 	id 		= null;
	
	private String agre_codigo	= null;
	
	private String agre_nombre	= null;
			
	public AgenciaRegional() {
		super();
	}
	
	public AgenciaRegional(Integer id, String agre_codigo, String agre_nombre) {
		super();
		this.id = id;
		this.agre_codigo = agre_codigo;
		this.agre_nombre = agre_nombre;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAgre_codigo() {
		return agre_codigo;
	}

	public void setAgre_codigo(String agre_codigo) {
		this.agre_codigo = agre_codigo;
	}

	public String getAgre_nombre() {
		return agre_nombre;
	}

	public void setAgre_nombre(String agre_nombre) {
		this.agre_nombre = agre_nombre;
	}

	public void copiarValores(AgenciaRegional dAux,boolean copiarId){
		this.vaciar();
		if (dAux==null){return;}
		if(copiarId){id=dAux.id;}
		agre_codigo=dAux.agre_codigo;
		agre_nombre = dAux.agre_nombre;		
	}
	
	public void vaciar() {
		id = 0;
		agre_codigo = "";
		agre_nombre = "";
	}
}