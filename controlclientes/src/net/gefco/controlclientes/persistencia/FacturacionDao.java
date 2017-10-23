package net.gefco.controlclientes.persistencia;

import java.util.List;

import net.gefco.controlclientes.modelo.Facturacion;

public interface FacturacionDao {

	public void 				guardar(Facturacion facturacion);
	
	public void 				actualizar(Facturacion facturacion);
	
	public void 				eliminar(Facturacion facturacion);

	public Facturacion 			buscarId(Integer id);
	
	public List<Facturacion> 	listado();

	public List<Facturacion> 	listadoOrdenado(String campoOrden);
	
	public List<Facturacion> 	listadoPaginado(Integer primero, Integer maximo, String campoOrden);

}
