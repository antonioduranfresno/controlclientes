package net.gefco.controlclientes.persistencia;

import java.util.List;

import net.gefco.controlclientes.modelo.Actividad;
import net.gefco.controlclientes.modelo.Agencia;
import net.gefco.controlclientes.modelo.Facturacion;
import net.gefco.controlclientes.modelo.Tercero;

public interface FacturacionDao {

	public void 				guardar(Facturacion facturacion);
	
	public void 				actualizar(Facturacion facturacion);
	
	public void 				eliminar(Facturacion facturacion);

	public Facturacion 			buscarId(Integer id);
	
	public List<Facturacion> 	listado();

	public Long 				totalRegistros (String hql);
	
	public List<Object[]>		listadoClaseCustom(String hql);
	
	public List<Object[]>		listadoClaseCustomOrdenado(String hql, String campoOrden);
	
	public List<Object[]>		listadoClaseCustomPaginado(String hql, Integer primero, Integer maximo, String campoOrden);

	public Facturacion 			buscarFacturacion(Integer periodo, Tercero tercero, Agencia agencia, Actividad actividad);

}