package net.gefco.controlclientes.persistencia;

import java.util.List;

import net.gefco.controlclientes.modelo.TerceroMarketLine;

public interface TerceroMarketLineDao {

	public void 					guardar(TerceroMarketLine terceroMarketLine);
	
	public void 					actualizar(TerceroMarketLine terceroMarketLine);
	
	public void 					eliminar(TerceroMarketLine terceroMarketLine);

	public TerceroMarketLine 		buscarId(Integer id);
	
	public List<TerceroMarketLine> 	listado();

	public List<TerceroMarketLine> 	listadoOrdenado(String campoOrden);
	
	public List<TerceroMarketLine> 	listadoPaginado(Integer primero, Integer maximo, String campoOrden);

}
