package net.gefco.controlclientes.negocio;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public abstract class AbstractService <T, Dao>{

		
	protected Dao dao				= null;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object ejecutarMetodo (Object instanciaClase, String metodo, Object [] parametros) throws 	InvocationTargetException {

		Class tipoClase 	= instanciaClase.getClass();
		Method 		m 		= null;
		Object 		r		= null;
		
		try {
			if (parametros.length == 0) {
				m = tipoClase.getMethod(metodo);
				r = m.invoke(instanciaClase);
							
			} else {
	
				Class [] clasesParametros 	= new Class [parametros.length];
				
				for (int i = 0; i < parametros.length; i++) {
					
					clasesParametros [i] =parametros[i].getClass();
				}
				
				m = tipoClase.getMethod(metodo, clasesParametros);
				r = m.invoke(instanciaClase, parametros);
				
			}
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException |IllegalArgumentException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return r;
	}
	
	public void guardar(T t) throws InvocationTargetException {	
		ejecutarMetodo (dao, "guardar", new Object[] {t});			
	}
	
	public void actualizar(T t) throws InvocationTargetException {	
		
		ejecutarMetodo (dao, "actualizar", new Object[] {t});
			
	}	
	
	public void eliminar(T t) throws InvocationTargetException {
		ejecutarMetodo (dao, "eliminar", new Object[] {t});
			
	}
	
	@SuppressWarnings("unchecked")
	public T buscarId(Integer id) throws InvocationTargetException {	
		
		return (T) ejecutarMetodo (dao, "buscarId", new Object[] {id});
			
	}
	
	@SuppressWarnings("unchecked")
	public List<T> listado() throws InvocationTargetException {
		return (List<T>) ejecutarMetodo (dao, "listado", new Object[] {});
			
	}
	
	
	public Long totalRegistros(String hql) throws InvocationTargetException {
		return (Long) ejecutarMetodo (dao, "totalRegistros", new Object[] {hql});
			
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> listadoClaseCustom(String hql) throws InvocationTargetException {
		return (List<Object[]>) ejecutarMetodo (dao, "listadoClaseCustom", new Object[] {hql});
			
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> listadoClaseCustomOrdenado(String hql, String campoOrden) throws InvocationTargetException {
		return (List<Object[]>) ejecutarMetodo (dao, "listadoClaseCustomOrdenado", new Object[] {hql, campoOrden});
			
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> listadoClaseCustomPaginado(String hql, Integer primero, Integer maximo, String campoOrden) throws InvocationTargetException {
		return (List<Object[]>) ejecutarMetodo (dao, "listadoClaseCustomPaginado", new Object[] {hql, primero, maximo, campoOrden});
			
	}
	
	@SuppressWarnings("unchecked")
	public List<T> listadoOrdenado(String campoOrden) throws InvocationTargetException {	
		return (List<T>) ejecutarMetodo (dao, "listadoOrdenado", new Object[] {campoOrden});
			
	}
	
	@SuppressWarnings("unchecked")
	public List<T> listadoPaginado (Integer primero, Integer maximo, String campoOrden) throws InvocationTargetException {	
		return (List<T>) ejecutarMetodo (dao, "listadoPaginado", new Object[] {primero, maximo, campoOrden});
			
	}
	
}