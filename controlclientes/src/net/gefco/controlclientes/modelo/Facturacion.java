package net.gefco.controlclientes.modelo;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class Facturacion implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id						= null;
	
	private Integer	fact_periodo 			= null;
	
	@ManyToOne
	@JoinColumn(name = "tercero")
	private Tercero tercero 				= null;
	
	@ManyToOne
	@JoinColumn(name = "agencia")
	private Agencia agencia 				= null;
	
	@ManyToOne
	@JoinColumn(name = "actividad")
	private Actividad actividad 			= null;
	
	private Double fact_ventaAgencia 		= null;
	private Double fact_compraAgencia 		= null;
	private Double fact_ventaAgenciaSAP 	= null;
	
	private Double fact_ajusteVentaAgencia 	= null;
	private Double fact_ajusteCompraAgencia = null;
	private String fact_ajusteMotivo 		= null;
	
	private Double fact_ventaGlobal 		= null;
	private Double fact_compraGlobal 		= null;
	private Double fact_ventaGlobalSAP 		= null;
	
	
	public Facturacion() {
		super();
	}

	public Facturacion(Integer id, int fact_periodo, Tercero tercero,
			Agencia agencia, Actividad actividad, Double fact_ventaAgencia,
			Double fact_compraAgencia, Double fact_ventaAgenciaSAP,
			Double fact_ajusteVentaAgencia, Double fact_ajusteCompraAgencia,
			String fact_ajusteMotivo, Double fact_ventaGlobal,
			Double fact_compraGlobal, Double fact_ventaGlobalSAP) {
		super();
		this.id = id;
		this.fact_periodo = fact_periodo;
		this.tercero = tercero;
		this.agencia = agencia;
		this.actividad = actividad;
		this.fact_ventaAgencia = fact_ventaAgencia;
		this.fact_compraAgencia = fact_compraAgencia;
		this.fact_ventaAgenciaSAP = fact_ventaAgenciaSAP;
		this.fact_ajusteVentaAgencia = fact_ajusteVentaAgencia;
		this.fact_ajusteCompraAgencia = fact_ajusteCompraAgencia;
		this.fact_ajusteMotivo = fact_ajusteMotivo;
		this.fact_ventaGlobal = fact_ventaGlobal;
		this.fact_compraGlobal = fact_compraGlobal;
		this.fact_ventaGlobalSAP = fact_ventaGlobalSAP;
	}

	public Double getFact_ventaAgenciaSAP() {
		return fact_ventaAgenciaSAP;
	}

	public void setFact_ventaAgenciaSAP(Double fact_ventaAgenciaSAP) {
		this.fact_ventaAgenciaSAP = fact_ventaAgenciaSAP;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getFact_periodo() {
		return fact_periodo;
	}

	public void setFact_periodo(int fact_periodo) {
		this.fact_periodo = fact_periodo;
	}

	public Tercero getTercero() {
		return tercero;
	}

	public void setTercero(Tercero tercero) {
		this.tercero = tercero;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public Actividad getActividad() {
		return actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}

	public Double getFact_ventaAgencia() {
		return fact_ventaAgencia;
	}

	public void setFact_ventaAgencia(Double fact_ventaAgencia) {
		this.fact_ventaAgencia = fact_ventaAgencia;
	}

	public Double getFact_compraAgencia() {
		return fact_compraAgencia;
	}

	public void setFact_compraAgencia(Double fact_compraAgencia) {
		this.fact_compraAgencia = fact_compraAgencia;
	}

	public Double getFact_ajusteVentaAgencia() {
		return fact_ajusteVentaAgencia;
	}

	public void setFact_ajusteVentaAgencia(Double fact_ajusteVentaAgencia) {
		this.fact_ajusteVentaAgencia = fact_ajusteVentaAgencia;
	}

	public Double getFact_ajusteCompraAgencia() {
		return fact_ajusteCompraAgencia;
	}

	public void setFact_ajusteCompraAgencia(Double fact_ajusteCompraAgencia) {
		this.fact_ajusteCompraAgencia = fact_ajusteCompraAgencia;
	}

	public String getFact_ajusteMotivo() {
		return fact_ajusteMotivo;
	}

	public void setFact_ajusteMotivo(String fact_ajusteMotivo) {
		this.fact_ajusteMotivo = fact_ajusteMotivo;
	}

	public Double getFact_ventaGlobal() {
		return fact_ventaGlobal;
	}

	public void setFact_ventaGlobal(Double fact_ventaGlobal) {
		this.fact_ventaGlobal = fact_ventaGlobal;
	}

	public Double getFact_compraGlobal() {
		return fact_compraGlobal;
	}

	public void setFact_compraGlobal(Double fact_compraGlobal) {
		this.fact_compraGlobal = fact_compraGlobal;
	}
	
	public Double getFact_ventaGlobalSAP() {
		return fact_ventaGlobalSAP;
	}

	public void setFact_ventaGlobalSAP(Double fact_ventaGlobalSAP) {
		this.fact_ventaGlobalSAP = fact_ventaGlobalSAP;
	}

	public Double getMargenAgencia(){
		Double venta = (getFact_ajusteVentaAgencia() > 0.0 ? getFact_ajusteVentaAgencia() : getFact_ventaAgencia());
		Double compra = (getFact_ajusteCompraAgencia() > 0.0 ? getFact_ajusteCompraAgencia() : getFact_compraAgencia());
		if (compra == 0.0){
			return 0.0;
		} else {
			return (venta - (-1 * compra)) / venta;
		}
	}
	
	public Double getMargenAgenciaSinAjustes(){
		Double venta = getFact_ventaAgencia();
		Double compra = getFact_compraAgencia();
		if (compra == 0.0){
			return 0.0;
		} else {
			return (venta - (-1 * compra)) / venta;
		}
	}
	
	public Double getMargenAgenciaSoloAjustes(){
		Double venta = getFact_ajusteVentaAgencia();
		Double compra = getFact_ajusteCompraAgencia();
		if (compra == 0.0){
			return 0.0;
		} else {
			return (venta - (-1 * compra)) / venta;
		}
	}
	
	public Double getMargenGlobal(){
		Double venta = getFact_ventaGlobal();
		Double compra = getFact_compraGlobal();
		if (compra == 0.0){
			return 0.0;
		} else {
			return (venta - (-1 * compra)) / venta;
		}
	}
	
	public void copiarValores(Facturacion dAux, boolean copiarId) {
		this.vaciar();
		if (dAux==null){return;}
		if(copiarId){id=dAux.id;}		
		fact_periodo = dAux.fact_periodo;
		tercero.copiarValores(dAux.tercero,true);
		agencia.copiarValores(dAux.agencia,true);
		actividad.copiarValores(dAux.actividad,true);
		fact_ventaAgencia = dAux.fact_ventaAgencia;
		fact_compraAgencia = dAux.fact_compraAgencia;
		fact_ventaAgenciaSAP = dAux.fact_ventaAgenciaSAP;
		fact_ajusteVentaAgencia = dAux.fact_ajusteVentaAgencia;
		fact_ajusteCompraAgencia = dAux.fact_ajusteCompraAgencia;
		fact_ajusteMotivo = dAux.fact_ajusteMotivo;
		fact_ventaGlobal = dAux.fact_ventaGlobal;
		fact_compraGlobal = dAux.fact_compraGlobal;
		fact_ventaGlobalSAP = dAux.fact_ventaGlobalSAP;
	}		
	
	public void vaciar() {
		id = 0;
		fact_periodo = 0;
		tercero = new Tercero();
		agencia = new Agencia();
		actividad = new Actividad();
		fact_ventaAgencia = 0.0;
		fact_compraAgencia = 0.0;
		fact_ventaAgenciaSAP = 0.0;
		fact_ajusteVentaAgencia = 0.0;
		fact_ajusteCompraAgencia = 0.0;
		fact_ajusteMotivo = "";
		fact_ventaGlobal = 0.0;
		fact_compraGlobal = 0.0;
		fact_ventaGlobalSAP = 0.0;
	}	
}
