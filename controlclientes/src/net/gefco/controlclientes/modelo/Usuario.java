package net.gefco.controlclientes.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="usuario")
public class Usuario implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer id 				= null;
	
	private String usua_idioma		= null;
	
	private String usua_login		= null;
	
	private String usua_mail		= null;
	
	private String usua_nombre		= null;
	
	private String usua_pw			= null;
	
	@ManyToOne
	@JoinColumn(name = "agencia")
	private Agencia agencia 		= null;	

	@ManyToOne
	@JoinColumn(name = "rol")
	private Rol rol 				= null;
	
	@ManyToOne
	@JoinColumn(name = "comercial")
	private Comercial comercial 	= null;
	
	public Usuario() {
		super();
	}		
	
	public Usuario(Integer id, String usua_idioma, String usua_login,
			String usua_mail, String usua_nombre, String usua_pw,
			Agencia agencia, Rol rol, Comercial comercial) {
		super();
		this.id = id;
		this.usua_idioma = usua_idioma;
		this.usua_login = usua_login;
		this.usua_mail = usua_mail;
		this.usua_nombre = usua_nombre;
		this.usua_pw = usua_pw;
		this.agencia = agencia;
		this.rol = rol;
		this.comercial = comercial;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsua_idioma() {
		return usua_idioma;
	}

	public void setUsua_idioma(String usua_idioma) {
		this.usua_idioma = usua_idioma;
	}

	public String getUsua_login() {
		return usua_login;
	}

	public void setUsua_login(String usua_login) {
		this.usua_login = usua_login;
	}

	public String getUsua_mail() {
		return usua_mail;
	}

	public void setUsua_mail(String usua_mail) {
		this.usua_mail = usua_mail;
	}

	public String getUsua_nombre() {
		return usua_nombre;
	}

	public void setUsua_nombre(String usua_nombre) {
		this.usua_nombre = usua_nombre;
	}

	public String getUsua_pw() {
		return usua_pw;
	}

	public void setUsua_pw(String usua_pw) {
		this.usua_pw = usua_pw;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Comercial getComercial() {
		return comercial;
	}

	public void setComercial(Comercial comercial) {
		this.comercial = comercial;
	}

	/*
	public void copiarValores(Usuario aux,boolean copiarId){
		this.vaciar();
		if (aux==null){return;}
		if(copiarId){id=aux.id;}
		usua_nombre    	= aux.usua_nombre;
		usua_login     	= aux.usua_login;
		usua_pw       	= aux.usua_pw;
		usua_mail		= aux.usua_mail;
		usua_idioma    	= aux.usua_idioma;
	}
	
	public void vaciar() {
		id 					= 0;
		usua_nombre    		= "";
		usua_login     		= "";
		usua_pw       		= "";
		usua_idioma    		= "";	
		usua_mail			= "";
	}
	*/

}
