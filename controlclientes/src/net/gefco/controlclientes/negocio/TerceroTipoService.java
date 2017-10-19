package net.gefco.controlclientes.negocio;

import javax.annotation.PostConstruct;

import net.gefco.controlclientes.modelo.TerceroTipo;
import net.gefco.controlclientes.persistencia.TerceroTipoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TerceroTipoService extends AbstractService<TerceroTipo, TerceroTipoDao>{

	@Autowired
	private TerceroTipoDao terceroTipoDao;

	@PostConstruct
	public void iniciarService() {
		super.dao = terceroTipoDao;
	}
	
}