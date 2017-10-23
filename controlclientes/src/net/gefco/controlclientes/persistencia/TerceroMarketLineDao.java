package net.gefco.controlclientes.persistencia;

import java.util.List;

import net.gefco.controlclientes.modelo.TerceroMarketLine;

public interface TerceroMarketLineDao {

	public void 					guardar(TerceroMarketLine terceroMarketLine);
	
	public void 					actualizar(TerceroMarketLine terceroMarketLine);
	
	public void 					eliminar(TerceroMarketLine terceroMarketLine);

	public TerceroMarketLine 		buscarId(Integer id);
	
	public List<TerceroMarketLine> 	listado();

	public Long 			totalRegistros (String hql);
	
	public List<Object[]>	listadoClaseCustom(String hql);
	
	public List<Object[]>	listadoClaseCustomOrdenado(String hql, String campoOrden);
	
	public List<Object[]>	listadoClaseCustomPaginado(String hql, Integer primero, Integer maximo, String campoOrden);


}
