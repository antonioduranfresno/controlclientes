package net.gefco.controlclientes.negocio;

import javax.annotation.PostConstruct;

import net.gefco.controlclientes.modelo.SuiviEs;
import net.gefco.controlclientes.persistencia.SuiviEsDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SuiviEsService extends AbstractService<SuiviEs, SuiviEsDao>{

	@Autowired
	private SuiviEsDao suiviEsDao;
	
	@PostConstruct
	public void iniciarService() {
		super.dao = suiviEsDao;		
	}

}