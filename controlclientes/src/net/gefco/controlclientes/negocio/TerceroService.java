package net.gefco.controlclientes.negocio;

import javax.annotation.PostConstruct;

import net.gefco.controlclientes.modelo.Tercero;
import net.gefco.controlclientes.persistencia.TerceroDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TerceroService extends AbstractService<Tercero, TerceroDao>{

	@Autowired
	private TerceroDao terceroDao;
	
	@PostConstruct
	public void iniciarService() {
		super.dao = terceroDao;
		
	}

}