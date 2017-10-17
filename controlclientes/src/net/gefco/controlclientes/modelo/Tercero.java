package net.gefco.controlclientes.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Tercero implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id 							= null;

	@NotEmpty
	private String terc_codigo 					= null;

	@NotEmpty
	private String terc_razonSocial 			= null;

	@ManyToOne
	@JoinColumn(name = "terceroTipo")
	private TerceroTipo terceroTipo 			= null;

	@ManyToOne
	@JoinColumn(name = "terceroGrupo")
	private TerceroGrupo terceroGrupo 			= null;

	@ManyToOne
	@JoinColumn(name = "terceroMarketLine")
	private TerceroMarketLine terceroMarketLine = null;

	private Boolean terc_Maf				    = null;

	private Boolean terc_noValido 				= null;

	public Tercero() {
		super();
	}

	public Tercero(Integer id, String terc_codigo, String terc_razonSocial,
			TerceroTipo terceroTipo, TerceroGrupo terceroGrupo,
			TerceroMarketLine terceroMarketLine, boolean terc_Maf,
			boolean terc_noValido) {
		super();
		this.id = id;
		this.terc_codigo = terc_codigo;
		this.terc_razonSocial = terc_razonSocial;
		this.terceroTipo = terceroTipo;
		this.terceroGrupo = terceroGrupo;
		this.terceroMarketLine = terceroMarketLine;
		this.terc_Maf = terc_Maf;
		this.terc_noValido = terc_noValido;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTerc_codigo() {
		return terc_codigo;
	}

	public void setTerc_codigo(String terc_codigo) {
		this.terc_codigo = terc_codigo;
	}

	public String getTerc_razonSocial() {
		return terc_razonSocial;
	}

	public void setTerc_razonSocial(String terc_razonSocial) {
		this.terc_razonSocial = terc_razonSocial;
	}

	public TerceroTipo getTerceroTipo() {
		return terceroTipo;
	}

	public void setTerceroTipo(TerceroTipo terceroTipo) {
		this.terceroTipo = terceroTipo;
	}

	public TerceroGrupo getTerceroGrupo() {
		return terceroGrupo;
	}

	public void setTerceroGrupo(TerceroGrupo terceroGrupo) {
		this.terceroGrupo = terceroGrupo;
	}

	public TerceroMarketLine getTerceroMarketLine() {
		return terceroMarketLine;
	}

	public void setTerceroMarketLine(TerceroMarketLine terceroMarketLine) {
		this.terceroMarketLine = terceroMarketLine;
	}

	public Boolean getTerc_Maf() {
		return terc_Maf;
	}

	public void setTerc_Maf(Boolean terc_Maf) {
		this.terc_Maf = terc_Maf;
	}

	public Boolean getTerc_noValido() {
		return terc_noValido;
	}

	public void setTerc_noValido(Boolean terc_noValido) {
		this.terc_noValido = terc_noValido;
	}

	public void copiarValores(Tercero dAux, boolean copiarId) {
		this.vaciar();
		if (dAux == null) {
			return;
		}
		if (copiarId) {
			id = dAux.id;
		}
		terc_codigo = dAux.terc_codigo;
		terc_razonSocial = dAux.terc_razonSocial;
		terceroTipo.copiarValores(dAux.terceroTipo, true);
		terceroGrupo.copiarValores(dAux.terceroGrupo, true);
		terceroMarketLine.copiarValores(dAux.terceroMarketLine, true);
		terc_Maf = dAux.terc_Maf;
		terc_noValido = dAux.terc_noValido;
	}

	public void vaciar() {
		id = 0;
		terc_codigo = "";
		terc_razonSocial = "";
		terceroTipo = new TerceroTipo();
		terceroGrupo = new TerceroGrupo();
		terceroMarketLine = new TerceroMarketLine();
		terc_Maf = false;
		terc_noValido = false;
	}

	@Override
	public String toString() {
		return "Tercero [id=" + id + ", terc_codigo=" + terc_codigo
				+ ", terc_razonSocial=" + terc_razonSocial + ", terceroTipo="
				+ terceroTipo + ", terceroGrupo=" + terceroGrupo
				+ ", terceroMarketLine=" + terceroMarketLine + ", terc_Maf="
				+ terc_Maf + ", terc_noValido=" + terc_noValido + "]";
	}

	public String toStringCodigoTercero() {
		return terc_codigo + " - " + terc_razonSocial;
	}
	
}
