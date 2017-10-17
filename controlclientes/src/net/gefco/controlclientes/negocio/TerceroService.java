package net.gefco.controlclientes.negocio;

import java.util.List;

import net.gefco.controlclientes.modelo.Tercero;
import net.gefco.controlclientes.persistencia.TerceroDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TerceroService {

	@Autowired
	private TerceroDao terceroDao;

	public void guardar(Tercero tercero) {		
		terceroDao.guardar(tercero);
	}
	
	public void actualizar(Tercero tercero) {		
		terceroDao.actualizar(tercero);
	}	
	
	public void eliminar(Tercero tercero) {		
		terceroDao.eliminar(tercero);
	}
	
	public Tercero buscarTercero(Integer id){
		return terceroDao.buscarTercero(id);
	}
	
	public List<Tercero> listarTerceros(){
		return terceroDao.listarTerceros();
	}
	
	public List<Tercero> listarTercerosOrdenados(String campoOrden){
		return terceroDao.listarTercerosOrdenados(campoOrden);
	}
	
	public List<Tercero> listarTercerosPaginados(Integer primero, Integer maximo, String campoOrden){
		return terceroDao.listarTercerosPaginados(primero, maximo, campoOrden);
	}
		
}