package net.gefco.controlclientes.persistencia;

import java.util.List;

import net.gefco.controlclientes.modelo.Tercero;

public interface TerceroDao {

	public void 			guardar(Tercero tercero);
	
	public void 			actualizar(Tercero tercero);
	
	public void 			eliminar(Tercero tercero);

	public Tercero 			buscarId(Integer id);
	
	public List<Tercero> 	listado();

	public List<Tercero> 	listadoOrdenado(String campoOrden);
	
	public List<Tercero> 	listadoPaginado(Integer primero, Integer maximo, String campoOrden);

}
