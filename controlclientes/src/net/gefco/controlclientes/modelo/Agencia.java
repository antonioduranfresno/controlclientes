package net.gefco.controlclientes.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.ListUtils;
import org.hibernate.collection.internal.PersistentBag;

@Entity
public class Agencia implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue	
	private Integer id						= null;
	
	private String agen_codigo				= null;
	
	private String agen_nombre  			= null;	
	
	@ManyToOne
	@JoinColumn(name = "agenciaregional")
	private AgenciaRegional agenciaRegional = null;
	
	@ManyToOne
	@JoinColumn(name = "uo")
	private UO uo 							= null;
	
	@OneToMany (mappedBy="agencia", fetch=FetchType.LAZY)
	private List<Comercial> comerciales=new ArrayList<Comercial>();
	
	@OneToMany (mappedBy="agencia", fetch=FetchType.LAZY)
	private List<Usuario> usuarios=new ArrayList<Usuario>();
	
	public Agencia() {
		super();
	}

	public Agencia(Integer id, String agen_codigo, String agen_nombre,
			AgenciaRegional agenciaRegional, UO uo,
			List<Comercial> comerciales, List<Usuario> usuarios) {
		super();
		this.id = id;
		this.agen_codigo = agen_codigo;
		this.agen_nombre = agen_nombre;
		this.agenciaRegional = agenciaRegional;
		this.uo = uo;
		this.comerciales = comerciales;
		this.usuarios = usuarios;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAgen_codigo() {
		return agen_codigo;
	}

	public void setAgen_codigo(String agen_codigo) {
		this.agen_codigo = agen_codigo;
	}

	public String getAgen_nombre() {
		return agen_nombre;
	}

	public void setAgen_nombre(String agen_nombre) {
		this.agen_nombre = agen_nombre;
	}

	public AgenciaRegional getAgenciaRegional() {
		return agenciaRegional;
	}

	public void setAgenciaRegional(AgenciaRegional agenciaRegional) {
		this.agenciaRegional = agenciaRegional;
	}

	public UO getUo() {
		return uo;
	}

	public void setUo(UO uo) {
		this.uo = uo;
	}

	public List<Comercial> getComerciales() {
		return comerciales;
	}

	public void setComerciales(List<Comercial> comerciales) {
		this.comerciales = comerciales;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@SuppressWarnings("unchecked")
	public void copiarValores(Agencia dAux,boolean copiarId){
		this.vaciar();
		if (dAux==null){return;}
		if(copiarId){id=dAux.id;}
		agen_codigo = dAux.agen_codigo;
		agen_nombre = dAux.agen_nombre;
		agenciaRegional.copiarValores(dAux.agenciaRegional,true);
		uo.copiarValores(dAux.uo,true);
		
//		Copiar la lista de usuarios
		usuarios.clear();
		//Como es ..toMany -> Fetch = Lazy, comprobamos si no está inicializado. 
		//Si es así, forzamos la inicialización.
		if (dAux.usuarios.getClass().getName().equals("org.hibernate.collection.PersistentBag")){
			if(((PersistentBag) dAux.usuarios).getSession()==null ){
				dAux.usuarios = ListUtils.lazyList(new ArrayList<Usuario>(), FactoryUtils.instantiateFactory(Usuario.class));
			}
		}
		usuarios.addAll(dAux.usuarios);    

//		Copiar la lista de comerciales
		comerciales.clear();
		//Como es ..toMany -> Fetch = Lazy, comprobamos si no está inicializado. 
		//Si es así, forzamos la inicialización.
		if (dAux.comerciales.getClass().getName().equals("org.hibernate.collection.PersistentBag")){
			if(((PersistentBag) dAux.comerciales).getSession()==null ){
				dAux.comerciales = ListUtils.lazyList(new ArrayList<Comercial>(), FactoryUtils.instantiateFactory(Comercial.class));
			}
		}
		comerciales.addAll(dAux.comerciales);    
	}
	
	public void vaciar() {
		id = 0;
		agen_codigo = "";
		agen_nombre = "";
		agenciaRegional = new AgenciaRegional();
		uo = new UO();
		usuarios = new ArrayList<Usuario>();
		comerciales = new ArrayList<Comercial>();
	}

	@Override
	public String toString() {
		return "Agencia [id=" + id + ", agen_codigo=" + agen_codigo
				+ ", agen_nombre=" + agen_nombre + ", agenciaRegional="
				+ agenciaRegional + ", uo=" + uo + "]";
	}

	public String toStringCodigoNombre() {
		return agen_codigo + " - " + agen_nombre;
	}
	
}
