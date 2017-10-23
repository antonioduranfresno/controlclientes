package net.gefco.controlclientes.persistencia;

import java.util.List;

import net.gefco.controlclientes.modelo.Agencia;

public interface AgenciaDao {

	public void 			guardar(Agencia agencia);
	
	public void 			actualizar(Agencia agencia);
	
	public void 			eliminar(Agencia agencia);

	public Agencia 			buscarId(Integer id);
	
	public List<Agencia> 	listado();

	public List<Agencia> 	listadoOrdenado(String campoOrden);
	
	public List<Agencia> 	listadoPaginado(Integer primero, Integer maximo, String campoOrden);

}
