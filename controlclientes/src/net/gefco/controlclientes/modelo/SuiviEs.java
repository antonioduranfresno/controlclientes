package net.gefco.controlclientes.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SuiviEs implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id 			= null;
	
	private String sues_nombre	= null;	
	
	public SuiviEs() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSues_nombre() {
		return sues_nombre;
	}

	public void setSues_nombre(String sues_nombre) {
		this.sues_nombre = sues_nombre;
	}

	public SuiviEs(Integer id, String sues_nombre) {
		super();
		this.id = id;
		this.sues_nombre = sues_nombre;
	}

	public void copiarValores(SuiviEs dAux,boolean copiarId){
		this.vaciar();
		if (dAux==null){return;}
		if(copiarId){id=dAux.id;}
		sues_nombre = dAux.sues_nombre;
	}
	
	public void vaciar() {
		id = 0;
		sues_nombre = "";
	}

	@Override
	public String toString() {
		return "SuiviEs [id=" + id + ", sues_nombre=" + sues_nombre + "]";
	}
	
}