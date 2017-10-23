package net.gefco.controlclientes.util;

import java.io.Serializable;

public class DataTableColumn implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String label		= null;
	private String campoHQL		= null;
	private String hrefOrden	= null;
	private String classOrden	= null;
	
	private String 		beanCustomPropiedad			= null;
	private Class<?> 	beanCustomClase				= null;
	private String 		beanCustomProyeccion		= null;
	
	public DataTableColumn() {
		super();
	}

	public DataTableColumn(String label, String campoHQL, String hrefOrden,
			String classOrden, String beanCustomPropiedad,
			Class<?> beanCustomClase, String beanCustomProyeccion) {
		super();
		this.label = label;
		this.campoHQL = campoHQL;
		this.hrefOrden = hrefOrden;
		this.classOrden = classOrden;
		this.beanCustomPropiedad = beanCustomPropiedad;
		this.beanCustomClase = beanCustomClase;
		this.beanCustomProyeccion = beanCustomProyeccion;
	}
	
	public DataTableColumn(String label, Class<?> beanCustomClase, String beanCustomProyeccion) {
		super();
		this.label 					= label;		
		this.beanCustomClase 		= beanCustomClase;
		this.beanCustomProyeccion 	= beanCustomProyeccion;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getCampoHQL() {
		return campoHQL;
	}

	public void setCampoHQL(String campoHQL) {
		this.campoHQL = campoHQL;
	}

	public String getHrefOrden() {
		return hrefOrden;
	}

	public void setHrefOrden(String hrefOrden) {
		this.hrefOrden = hrefOrden;
	}

	public String getClassOrden() {
		return classOrden;
	}

	public void setClassOrden(String classOrden) {
		this.classOrden = classOrden;
	}

	public String getBeanCustomPropiedad() {
		return beanCustomPropiedad;
	}

	public void setBeanCustomPropiedad(String beanCustomPropiedad) {
		this.beanCustomPropiedad = beanCustomPropiedad;
	}
	
	public Class<?> getBeanCustomClase() {
		return beanCustomClase;
	}

	public void setBeanCustomClase(Class<?> beanCustomClase) {
		this.beanCustomClase = beanCustomClase;
	}

	public String getBeanCustomProyeccion() {
		return beanCustomProyeccion;
	}

	public void setBeanCustomProyeccion(String beanCustomProyeccion) {
		this.beanCustomProyeccion = beanCustomProyeccion;
	}

	
}