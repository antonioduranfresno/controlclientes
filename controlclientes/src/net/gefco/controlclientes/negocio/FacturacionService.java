package net.gefco.controlclientes.negocio;

import javax.annotation.PostConstruct;

import net.gefco.controlclientes.modelo.Actividad;
import net.gefco.controlclientes.modelo.Agencia;
import net.gefco.controlclientes.modelo.Facturacion;
import net.gefco.controlclientes.modelo.Tercero;
import net.gefco.controlclientes.persistencia.FacturacionDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacturacionService extends AbstractService<Facturacion, FacturacionDao>{

	@Autowired
	private FacturacionDao facturacionDao;
	
	@PostConstruct
	public void iniciarService() {
		super.dao = facturacionDao;
		
	}

	public Facturacion buscarFacturacion(Integer periodo, Tercero tercero, Agencia agencia, Actividad actividad){
		return facturacionDao.buscarFacturacion(periodo, tercero, agencia, actividad);
	}
	
}