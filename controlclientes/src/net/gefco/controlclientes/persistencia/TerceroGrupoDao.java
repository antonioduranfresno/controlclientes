package net.gefco.controlclientes.persistencia;

import java.util.List;

import net.gefco.controlclientes.modelo.TerceroGrupo;

public interface TerceroGrupoDao {

	public void guardar(TerceroGrupo terceroGrupo);
	
	public void actualizar(TerceroGrupo terceroGrupo);
	
	public void eliminar(TerceroGrupo terceroGrupo);

	public TerceroGrupo buscarTerceroGrupo(Integer id);
	
	public List<TerceroGrupo> listarTercerosGrupo();

}
