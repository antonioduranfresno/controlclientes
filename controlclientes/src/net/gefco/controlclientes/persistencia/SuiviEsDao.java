package net.gefco.controlclientes.persistencia;

import java.util.List;

import net.gefco.controlclientes.modelo.SuiviEs;

public interface SuiviEsDao {

	public void 			guardar(SuiviEs suiviEs);
	
	public void 			actualizar(SuiviEs suiviEs);
	
	public void 			eliminar(SuiviEs suiviEs);

	public SuiviEs 			buscarId(Integer id);
	
	public List<SuiviEs> 	listado();

	public List<SuiviEs> 	listadoOrdenado(String campoOrden);
	
	public List<SuiviEs> 	listadoPaginado(Integer primero, Integer maximo, String campoOrden);

}
