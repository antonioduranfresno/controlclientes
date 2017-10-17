package net.gefco.controlclientes.negocio;

import java.util.List;

import net.gefco.controlclientes.modelo.TerceroGrupo;
import net.gefco.controlclientes.persistencia.TerceroGrupoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TerceroGrupoService {

	@Autowired
	private TerceroGrupoDao terceroGrupoDao;

	public void guardar(TerceroGrupo terceroGrupo) {		
		terceroGrupoDao.guardar(terceroGrupo);
	}
	
	public void actualizar(TerceroGrupo terceroGrupo) {		
		terceroGrupoDao.actualizar(terceroGrupo);
	}	
	
	public void eliminar(TerceroGrupo terceroGrupo) {		
		terceroGrupoDao.eliminar(terceroGrupo);
	}
	
	public TerceroGrupo buscarTerceroGrupo(Integer id){
		return terceroGrupoDao.buscarTerceroGrupo(id);
	}
	
	public List<TerceroGrupo> listarTercerosGrupo(){
		return terceroGrupoDao.listarTercerosGrupo();
	}
	
}