package net.gefco.controlclientes.negocio;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public abstract class AbstractService <T, Dao>{

	protected Dao dao			= null;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object ejecutarMetodo (Object instanciaClase, String metodo, Object [] parametros) 
			throws 	NoSuchMethodException, SecurityException, IllegalAccessException, 
					IllegalArgumentException, InvocationTargetException{

		Class tipoClase 	= instanciaClase.getClass();
		Method 		m 		= null;
		Object 		r		= null;
		
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
		
		return r;
	}
	
	
	public void guardar(T t) {	
		try {
			ejecutarMetodo (dao, "guardar", new Object[] {t});
		
		}catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			
			e.printStackTrace();
		
		}	
	}
	
	public void actualizar(T t) {	
		try {
			ejecutarMetodo (dao, "actualizar", new Object[] {t});	
		
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			
			e.printStackTrace();
		}	
	}	
	
	public void eliminar(T t) {	
		try {
			ejecutarMetodo (dao, "eliminar", new Object[] {t});
		
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	@SuppressWarnings("unchecked")
	public T buscarId(Integer id) {	
		
		try {
			
			return (T) ejecutarMetodo (dao, "buscarId", new Object[] {id});
		
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			
			e.printStackTrace();
			return null;
		}	
	}
	
	@SuppressWarnings("unchecked")
	public List<T> listado() {
		try {
			return (List<T>) ejecutarMetodo (dao, "listado", new Object[] {});
		
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			
			e.printStackTrace();
			return null;
		}	
	}
	
	@SuppressWarnings("unchecked")
	public List<T> listadoOrdenado(String campoOrden) {	
		try {
			return (List<T>) ejecutarMetodo (dao, "listadoOrdenado", new Object[] {campoOrden});
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			
			e.printStackTrace();
			return null;
		}	
	}
	
	@SuppressWarnings("unchecked")
	public List<T> listadoPaginado(Integer primero, Integer maximo, String campoOrden) {	
		try {
			return (List<T>) ejecutarMetodo (dao, "listadoPaginado", new Object[] {primero, maximo, campoOrden});
		
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			
			e.printStackTrace();
			return null;
		}	
	}
}
