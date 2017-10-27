package net.gefco.controlclientes.persistencia;

import java.util.List;

import net.gefco.controlclientes.modelo.Tercero;

public interface TerceroDao {

	public void 			guardar(Tercero tercero);
	
	public void 			actualizar(Tercero tercero);
	
	public void 			eliminar(Tercero tercero);

	public Tercero 			buscarId(Integer id);
	
	public List<Tercero> 	listado();
	
	public Long 			totalRegistros (String hql);
	
	public List<Object[]>	listadoClaseCustom(String hql);
	
	public List<Object[]>	listadoClaseCustomOrdenado(String hql, String campoOrden);
	
	public List<Object[]>	listadoClaseCustomPaginado(String hql, Integer primero, Integer maximo, String campoOrden);

	public Tercero 			buscarCodigo(String codigo);

}