package net.gefco.controlclientes.persistencia;

import java.util.List;

import net.gefco.controlclientes.modelo.TerceroGrupo;

public interface TerceroGrupoDao {
	
	public void guardar(TerceroGrupo terceroGrupo);
	
	public void actualizar(TerceroGrupo terceroGrupo);
	
	public void eliminar(TerceroGrupo terceroGrupo);

	public TerceroGrupo buscarId(Integer id);
	
	public List<TerceroGrupo> listado();

	public Long 			totalRegistros (String hql);
	
	public List<Object[]>	listadoClaseCustom(String hql);
	
	public List<Object[]>	listadoClaseCustomOrdenado(String hql, String campoOrden);
	
	public List<Object[]>	listadoClaseCustomPaginado(String hql, Integer primero, Integer maximo, String campoOrden);

	
}
