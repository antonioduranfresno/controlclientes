package net.gefco.controlclientes.negocio;

import javax.annotation.PostConstruct;

import net.gefco.controlclientes.modelo.Actividad;
import net.gefco.controlclientes.persistencia.ActividadDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActividadService extends AbstractService<Actividad, ActividadDao>{

	@Autowired
	private ActividadDao actividadDao;
	
	@PostConstruct
	public void iniciarService() {
		super.dao = actividadDao;		
	}

}