package net.gefco.controlclientes.persistencia;

import java.util.List;

import net.gefco.controlclientes.modelo.Actividad;

public interface ActividadDao {

	public void 			guardar(Actividad actividad);
	
	public void 			actualizar(Actividad actividad);
	
	public void 			eliminar(Actividad actividad);

	public Actividad 		buscarId(Integer id);
	
	public List<Actividad> 	listado();

	public List<Actividad> 	listadoOrdenado(String campoOrden);
	
	public List<Actividad> 	listadoPaginado(Integer primero, Integer maximo, String campoOrden);
	
	public Actividad 		buscarCodigo(String codigo);

}
