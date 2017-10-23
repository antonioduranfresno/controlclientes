package net.gefco.controlclientes.negocio;

import javax.annotation.PostConstruct;

import net.gefco.controlclientes.modelo.ComercialTipo;
import net.gefco.controlclientes.persistencia.ComercialTipoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComercialTipoService extends AbstractService<ComercialTipo, ComercialTipoDao>{

	@Autowired
	private ComercialTipoDao comercialTipoDao;

	@PostConstruct
	public void iniciarService() {
		super.dao = comercialTipoDao;
	}
	
}