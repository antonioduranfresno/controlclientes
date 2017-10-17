package net.gefco.controlclientes.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SuiviFr implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id			= null;
	
	private String sufr_nombre	= null;
	
	public SuiviFr() {
		super();
	}

	public SuiviFr(Integer id, String sufr_nombre) {
		super();
		this.id = id;
		this.sufr_nombre = sufr_nombre;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSufr_nombre() {
		return sufr_nombre;
	}

	public void setSufr_nombre(String sufr_nombre) {
		this.sufr_nombre = sufr_nombre;
	}

	public void copiarValores(SuiviFr dAux,boolean copiarId){
		this.vaciar();
		if (dAux==null){return;}
		if(copiarId){id=dAux.id;}
		sufr_nombre = dAux.sufr_nombre;
	}
	
	public void vaciar() {
		id = 0;
		sufr_nombre = "";
	}
}