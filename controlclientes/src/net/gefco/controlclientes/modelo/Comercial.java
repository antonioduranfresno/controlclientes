package net.gefco.controlclientes.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Comercial implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer id 					= null;
	
	private String come_codigo			= null;
	
	private String come_nombre			= null;
	
	@ManyToOne
	@JoinColumn(name = "agencia")
	private Agencia agencia  			= null;	

	@ManyToOne
	@JoinColumn(name = "comercialTipo")
	private ComercialTipo comercialTipo = null;
	
	@ManyToOne
	@JoinColumn(name = "suiviEs")
	private SuiviEs suiviEs 			= null;
	
	public Comercial() {
		super();
	}		
	
	public Comercial(int id, String come_codigo, String come_nombre,
			Agencia agencia, ComercialTipo comercialTipo, SuiviEs suiviEs) {
		super();
		this.id = id;
		this.come_codigo = come_codigo;
		this.come_nombre = come_nombre;
		this.agencia = agencia;
		this.comercialTipo = comercialTipo;
		this.suiviEs = suiviEs;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getCome_codigo() {
		return come_codigo;
	}
	public void setCome_codigo(String come_codigo) {
		this.come_codigo = come_codigo;
	}

	public String getCome_nombre() {
		return come_nombre;
	}
	public void setCome_nombre(String come_nombre) {
		this.come_nombre = come_nombre;
	}

	public Agencia getAgencia() {
		return agencia;
	}
	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public ComercialTipo getComercialTipo() {
		return comercialTipo;
	}
	public void setComercialTipo(ComercialTipo comercialTipo) {
		this.comercialTipo = comercialTipo;
	}

	public SuiviEs getSuiviEs() {
		return suiviEs;
	}
	public void setSuiviEs(SuiviEs suiviEs) {
		this.suiviEs = suiviEs;
	}

	

	public void copiarValores(Comercial aux,boolean copiarId){
		this.vaciar();
		if (aux==null){return;}
		if(copiarId){id=aux.id;}
		come_codigo = aux.come_codigo;
		come_nombre    	= aux.come_nombre;
		agencia.copiarValores(aux.agencia,true);
		comercialTipo.copiarValores(aux.comercialTipo, true);
		suiviEs.copiarValores(aux.suiviEs,true);
	}
	
	public void vaciar() {
		id 					= 0;
		come_codigo			= "";
		come_nombre    		= "";
		agencia				= new Agencia();
		comercialTipo		= new ComercialTipo();
		suiviEs				= new SuiviEs();		
	}
}
