package net.gefco.controlclientes.persistencia;

import java.util.List;

import net.gefco.controlclientes.modelo.Comercial;

public interface ComercialDao {

	public void 			guardar(Comercial comercial);
	
	public void 			actualizar(Comercial comercial);
	
	public void 			eliminar(Comercial comercial);

	public Comercial 		buscarId(Integer id);
	
	public List<Comercial> 	listado();

	public Long 			totalRegistros (String hql);
	
	public List<Object[]>	listadoClaseCustom(String hql);
	
	public List<Object[]>	listadoClaseCustomOrdenado(String hql, String campoOrden);
	
	public List<Object[]>	listadoClaseCustomPaginado(String hql, Integer primero, Integer maximo, String campoOrden);


}
