package net.gefco.controlclientes.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class TerceroComercial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue         
    private Integer id						= null;
    
    private Date teco_desde     			= null;
    
    private Date teco_hasta     			= null;
    
    @ManyToOne
    @JoinColumn(name = "tercero")
    private Tercero tercero      			= null;
    
    @ManyToOne
    @JoinColumn(name = "comercial")
    private Comercial comercial  			= null;
    
    @ManyToOne
    @JoinColumn(name = "comercialGestion")
    private Comercial comercialGestion      = null;
    
    @ManyToOne
    @JoinColumn(name = "uo")
    private UO uo							= null;
    
    @ManyToOne
    @JoinColumn(name = "agencia")
    private Agencia agencia                 = null;
    
    public TerceroComercial() {
    	super();
    }
    
    public TerceroComercial(Integer id, Date teco_desde, Date teco_hasta,
		Tercero tercero, Comercial comercial, Comercial comercialGestion,
        UO uo, Agencia agencia) {
           super();
           this.id = id;
           this.teco_desde = teco_desde;
           this.teco_hasta = teco_hasta;
           this.tercero = tercero;
           this.comercial = comercial;
           this.comercialGestion = comercialGestion;
           this.uo = uo;
           this.agencia = agencia;
    }

    public Integer getId() {
    	return id;
    }

    public void setId(Integer id) {
    	this.id = id;
    }

    public Date getTeco_desde() {
    	return teco_desde;
    }

    public void setTeco_desde(Date teco_desde) {
        this.teco_desde = teco_desde;
    }

    public Date getTeco_hasta() {
        return teco_hasta;
    }

    public void setTeco_hasta(Date teco_hasta) {
        this.teco_hasta = teco_hasta;
    }

    public Tercero getTercero() {
        return tercero;
    }

    public void setTercero(Tercero tercero) {
        this.tercero = tercero;
    }

    public Comercial getComercial() {
        return comercial;
    }

    public void setComercial(Comercial comercial) {
        this.comercial = comercial;
    }

    public Comercial getComercialGestion() {
        return comercialGestion;
    }

    public void setComercialGestion(Comercial comercialGestion) {
        this.comercialGestion = comercialGestion;
    }

    public UO getUo() {
        return uo;
    }

    public void setUo(UO uo) {
        this.uo = uo;
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public void setAgencia(Agencia agencia) {
        this.agencia = agencia;
    }


    public void copiarValores(TerceroComercial dAux,boolean copiarId){
       this.vaciar();
       if (dAux==null){return;}
       if(copiarId){id=dAux.id;}
       teco_desde = dAux.teco_desde;
       teco_hasta = dAux.teco_hasta;
       tercero.copiarValores(dAux.tercero,true);
       comercial.copiarValores(dAux.comercial,true);
       comercialGestion.copiarValores(dAux.comercialGestion,true);
       uo.copiarValores(dAux.uo,true);
       agencia.copiarValores(dAux.agencia,true);
    }
    
    public void vaciar() {
		id = 0;
		teco_desde = null;
		teco_hasta = null;
		tercero = new Tercero();
		comercial = new Comercial();
		comercialGestion = new Comercial();
		uo = new UO();
		agencia = new Agencia();
    }
}
