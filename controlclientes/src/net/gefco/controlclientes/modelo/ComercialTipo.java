package net.gefco.controlclientes.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ComercialTipo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer id 				= null;

	private String coti_codigo 		= null;
	
	private String coti_nombre		= null;
	
	public ComercialTipo() {
		super();
	}
	
	public ComercialTipo(Integer id, String coti_codigo, String coti_nombre) {
		super();
		this.id = id;
		this.coti_codigo = coti_codigo;
		this.coti_nombre = coti_nombre;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getCoti_codigo() {
		return coti_codigo;
	}

	public void setCoti_codigo(String coti_codigo) {
		this.coti_codigo = coti_codigo;
	}

	public String getCoti_nombre() {
		return coti_nombre;
	}

	public void setCoti_nombre(String coti_nombre) {
		this.coti_nombre = coti_nombre;
	}

	public void copiarValores(ComercialTipo dAux,boolean copiarId){
		this.vaciar();
		if (dAux==null){return;}
		if(copiarId){id=dAux.id;}
		coti_codigo = dAux.coti_codigo;
		coti_nombre = dAux.coti_nombre;
	}
	
	public void vaciar() {
		id = 0;
		coti_codigo = "";
		coti_nombre = "";
	}
	
	@Override
	public String toString() {
		return "ComercialTipo [id=" + id + ", coti_codigo=" + coti_codigo
				+ ", coti_nombre=" + coti_nombre + "]";
	}

	public String toStringCodigoNombre() {
		return coti_codigo + " - " + coti_nombre;
	}
	
}