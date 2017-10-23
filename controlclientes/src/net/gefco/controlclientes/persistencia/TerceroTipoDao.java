package net.gefco.controlclientes.persistencia;

import java.util.List;

import net.gefco.controlclientes.modelo.TerceroTipo;

public interface TerceroTipoDao {

	public void guardar(TerceroTipo terceroTipo);
	
	public void actualizar(TerceroTipo terceroTipo);
	
	public void eliminar(TerceroTipo terceroTipo);

	public TerceroTipo buscarId(Integer id);
	
	public List<TerceroTipo> listado();

	public List<TerceroTipo> listadoOrdenado(String campoOrden);
	
	public List<TerceroTipo> listadoPaginado(Integer primero, Integer maximo, String campoOrden);

}
