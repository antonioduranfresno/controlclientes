package net.gefco.controlclientes.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TerceroGrupo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id 			= null;

	private String tegr_nombre 	= null;

	public TerceroGrupo() {
		super();
	}

	public TerceroGrupo(Integer id, String tegr_nombre) {
		super();
		this.id = id;
		this.tegr_nombre = tegr_nombre;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTegr_nombre() {
		return tegr_nombre;
	}

	public void setTegr_nombre(String tegr_nombre) {
		this.tegr_nombre = tegr_nombre;
	}

	public void copiarValores(TerceroGrupo dAux, boolean copiarId) {
		this.vaciar();
		if (dAux == null) {
			return;
		}
		if (copiarId) {
			id = dAux.id;
		}
		tegr_nombre = dAux.tegr_nombre;
	}

	public void vaciar() {
		id = 0;
		tegr_nombre = "";
	}

	@Override
	public String toString() {
		return "TerceroGrupo [id=" + id + ", tegr_nombre=" + tegr_nombre + "]";
	}
	
}
