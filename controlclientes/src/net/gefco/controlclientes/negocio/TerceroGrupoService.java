package net.gefco.controlclientes.negocio;

import javax.annotation.PostConstruct;

import net.gefco.controlclientes.modelo.TerceroGrupo;
import net.gefco.controlclientes.persistencia.TerceroGrupoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TerceroGrupoService  extends AbstractService<TerceroGrupo, TerceroGrupoDao>{

	@Autowired
	private TerceroGrupoDao terceroGrupoDao;

	@PostConstruct
	public void iniciarService() {
		super.dao = terceroGrupoDao;
	}
	
}