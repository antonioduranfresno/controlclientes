package net.gefco.controlclientes.negocio;

import java.util.List;

import net.gefco.controlclientes.modelo.TerceroTipo;
import net.gefco.controlclientes.persistencia.TerceroTipoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TerceroTipoService {

	@Autowired
	private TerceroTipoDao terceroTipoDao;

	public void guardar(TerceroTipo terceroTipo) {		
		terceroTipoDao.guardar(terceroTipo);
	}
	
	public void actualizar(TerceroTipo terceroTipo) {		
		terceroTipoDao.actualizar(terceroTipo);
	}	
	
	public void eliminar(TerceroTipo terceroTipo) {		
		terceroTipoDao.eliminar(terceroTipo);
	}
	
	public TerceroTipo buscarTerceroTipo(Integer id){
		return terceroTipoDao.buscarTerceroTipo(id);
	}
	
	public List<TerceroTipo> listarTercerosTipo(){
		return terceroTipoDao.listarTercerosTipo();
	}
	
}