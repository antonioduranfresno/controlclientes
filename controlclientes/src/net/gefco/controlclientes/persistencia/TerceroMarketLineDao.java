package net.gefco.controlclientes.persistencia;

import java.util.List;

import net.gefco.controlclientes.modelo.TerceroMarketLine;

public interface TerceroMarketLineDao {

	public void guardar(TerceroMarketLine terceroMarketLine);
	
	public void actualizar(TerceroMarketLine terceroMarketLine);
	
	public void eliminar(TerceroMarketLine terceroMarketLine);

	public List<TerceroMarketLine> listarTercerosMarketLine();
	
	public TerceroMarketLine buscarTerceroMarketLine(Integer id);

}
