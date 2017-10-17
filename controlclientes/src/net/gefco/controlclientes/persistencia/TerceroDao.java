package net.gefco.controlclientes.persistencia;

import java.util.List;

import net.gefco.controlclientes.modelo.Tercero;

public interface TerceroDao {

	public void guardar(Tercero tercero);
	
	public void actualizar(Tercero tercero);
	
	public void eliminar(Tercero tercero);

	public Tercero buscarTercero(Integer id);
	
	public List<Tercero> listarTerceros();

	public List<Tercero> listarTercerosOrdenados(String campoOrden);
	
	public List<Tercero> listarTercerosPaginados(Integer primero, Integer maximo, String campoOrden);

}
