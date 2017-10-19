package net.gefco.controlclientes.negocio;

import javax.annotation.PostConstruct;

import net.gefco.controlclientes.modelo.TerceroMarketLine;
import net.gefco.controlclientes.persistencia.TerceroMarketLineDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TerceroMarketLineService extends AbstractService<TerceroMarketLine, TerceroMarketLineDao>{

	@Autowired
	private TerceroMarketLineDao terceroMarketLineDao;

	@PostConstruct
	public void iniciarService() {
		super.dao = terceroMarketLineDao;
	}
	
}