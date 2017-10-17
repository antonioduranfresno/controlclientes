package net.gefco.controlclientes.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class TerceroTipo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id 				= null;

	private String teti_codigo 		= null;

	private String teti_nombre 		= null;

	@ManyToOne
	@JoinColumn(name = "suiviFr")
	private SuiviFr suiviFr 		= null;

	public TerceroTipo() {
		super();
	}

	public TerceroTipo(Integer id, String teti_codigo, String teti_nombre,
			SuiviFr suiviFr) {
		super();
		this.id = id;
		this.teti_codigo = teti_codigo;
		this.teti_nombre = teti_nombre;
		this.suiviFr = suiviFr;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTeti_codigo() {
		return teti_codigo;
	}

	public void setTeti_codigo(String teti_codigo) {
		this.teti_codigo = teti_codigo;
	}

	public String getTeti_nombre() {
		return teti_nombre;
	}

	public void setTeti_nombre(String teti_nombre) {
		this.teti_nombre = teti_nombre;
	}

	public SuiviFr getSuiviFr() {
		return suiviFr;
	}

	public void setSuiviFr(SuiviFr suiviFr) {
		this.suiviFr = suiviFr;
	}

	public void copiarValores(TerceroTipo dAux, boolean copiarId) {
		this.vaciar();
		if (dAux == null) {
			return;
		}
		if (copiarId) {
			id = dAux.id;
		}
		teti_codigo = dAux.teti_codigo;
		teti_nombre = dAux.teti_nombre;
		suiviFr.copiarValores(dAux.suiviFr, true);
	}

	public void vaciar() {
		id = 0;
		teti_codigo = "";
		teti_nombre = "";
		suiviFr = new SuiviFr();
	}

	@Override
	public String toString() {
		return "TerceroTipo [id=" + id + ", teti_codigo=" + teti_codigo
				+ ", teti_nombre=" + teti_nombre + ", suiviFr=" + suiviFr + "]";
	}
	
}
