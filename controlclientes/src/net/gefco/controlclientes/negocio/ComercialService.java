package net.gefco.controlclientes.negocio;

import javax.annotation.PostConstruct;

import net.gefco.controlclientes.modelo.Comercial;
import net.gefco.controlclientes.persistencia.ComercialDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComercialService extends AbstractService<Comercial, ComercialDao>{

	@Autowired
	private ComercialDao comercialDao;
	
	@PostConstruct
	public void iniciarService() {
		super.dao = comercialDao;		
	}

}