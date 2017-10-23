package net.gefco.controlclientes.persistencia;

import java.util.List;

import net.gefco.controlclientes.modelo.ComercialTipo;

public interface ComercialTipoDao {

	public void guardar(ComercialTipo comercialTipo);
	
	public void actualizar(ComercialTipo comercialTipo);
	
	public void eliminar(ComercialTipo comercialTipo);

	public ComercialTipo buscarId(Integer id);
	
	public List<ComercialTipo> listado();

	public List<ComercialTipo> listadoOrdenado(String campoOrden);
	
	public List<ComercialTipo> listadoPaginado(Integer primero, Integer maximo, String campoOrden);

}
