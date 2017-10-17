package net.gefco.controlclientes.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id 			= null;

	@Column
	private String uO_codigo 	= null;

	@Column
	private String uO_nombre 	= null;

	public UO() {
		super();
	}

	public UO(Integer id, String uO_codigo, String uO_nombre) {
		super();
		this.id = id;
		this.uO_codigo = uO_codigo;
		this.uO_nombre = uO_nombre;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getuO_codigo() {
		return uO_codigo;
	}

	public void setuO_codigo(String uO_codigo) {
		this.uO_codigo = uO_codigo;
	}

	public String getuO_nombre() {
		return uO_nombre;
	}

	public void setuO_nombre(String uO_nombre) {
		this.uO_nombre = uO_nombre;
	}

	public void copiarValores(UO aux, boolean copiarId) {
		this.vaciar();
		if (aux == null) {
			return;
		}
		if (copiarId) {
			id = aux.id;
		}
		uO_codigo = aux.uO_codigo;
		uO_nombre = aux.uO_nombre;
	}

	public void vaciar() {
		id = 0;
		uO_codigo = ""; // null;
		uO_nombre = ""; // null;
	}
}
