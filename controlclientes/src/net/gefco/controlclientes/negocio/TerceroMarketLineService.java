package net.gefco.controlclientes.negocio;

import java.util.List;

import net.gefco.controlclientes.modelo.TerceroMarketLine;
import net.gefco.controlclientes.persistencia.TerceroMarketLineDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TerceroMarketLineService {

	@Autowired
	private TerceroMarketLineDao terceroMarketLineDao;

	public void guardar(TerceroMarketLine terceroMarketLine) {		
		terceroMarketLineDao.guardar(terceroMarketLine);
	}
	
	public void actualizar(TerceroMarketLine terceroMarketLine) {		
		terceroMarketLineDao.actualizar(terceroMarketLine);
	}	
	
	public void eliminar(TerceroMarketLine terceroMarketLine) {		
		terceroMarketLineDao.eliminar(terceroMarketLine);
	}
	
	public TerceroMarketLine buscarTerceroMarketLine(Integer id){
		return terceroMarketLineDao.buscarTerceroMarketLine(id);
	}
	
	public List<TerceroMarketLine> listarTercerosMarketLine(){
		return terceroMarketLineDao.listarTercerosMarketLine();
	}
	
}