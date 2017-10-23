package net.gefco.controlclientes.negocio;

import javax.annotation.PostConstruct;

import net.gefco.controlclientes.modelo.Agencia;
import net.gefco.controlclientes.persistencia.AgenciaDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgenciaService extends AbstractService<Agencia, AgenciaDao>{

	@Autowired
	private AgenciaDao agenciaDao;
	
	@PostConstruct
	public void iniciarService() {
		super.dao = agenciaDao;		
	}

}