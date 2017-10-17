package net.gefco.controlclientes.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TerceroMarketLine implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id 			= null;

	private String teml_nombre 	= null;

	public TerceroMarketLine() {
		super();
	}

	public TerceroMarketLine(Integer id, String teml_nombre) {
		super();
		this.id = id;
		this.teml_nombre = teml_nombre;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTeml_nombre() {
		return teml_nombre;
	}

	public void setTeml_nombre(String teml_nombre) {
		this.teml_nombre = teml_nombre;
	}

	public void copiarValores(TerceroMarketLine dAux, boolean copiarId) {
		this.vaciar();
		if (dAux == null) {
			return;
		}
		if (copiarId) {
			id = dAux.id;
		}
		teml_nombre = dAux.teml_nombre;
	}

	public void vaciar() {
		id = 0;
		teml_nombre = "";
	}

	@Override
	public String toString() {
		return "TerceroMarketLine [id=" + id + ", teml_nombre=" + teml_nombre
				+ "]";
	}
	
}
