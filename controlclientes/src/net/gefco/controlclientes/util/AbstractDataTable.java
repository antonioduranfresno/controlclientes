package net.gefco.controlclientes.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractDataTable<T, Service> {
	
	static final String CLASS_ORDEN_DESCENDENTE 	= "glyphicon glyphicon-sort-by-order-alt";
	static final String CLASS_ORDEN_ASCENDENTE 		= "glyphicon glyphicon-sort-by-order";
	static final String CLASS_ORDEN_SIN_ORDEN 		= "";
	
	protected List< Object > dt_lista 		= new ArrayList< Object >();
	protected Class<?> beanClassCustom 		= null;
	
	//Paginación
	protected int dt_paginaActual			= 1;
	protected int dt_numeroPaginas			= 0;
	protected int dt_registrosPorPagina		= 50;
	protected Long dt_totalRegistros;
    
	protected String dt_textoBusqueda		= "";
	protected String dt_orden				= null;
	protected String dt_HQLfrom				= null;
	protected Service dt_service			= null;
	protected String dt_paginaLista			= null;
    
	protected LinkedHashMap<String, DataTableColumn> dt_columnas   = new LinkedHashMap<String, DataTableColumn>();
	
	private   LinkedHashMap<String, Class<?>> beanCustomProperties = new LinkedHashMap<String, Class<?>>();
	
	private String hql = null;
	
	public AbstractDataTable() {
		super();		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object ejecutarMetodo (Object instanciaClase, String metodo, Object [] parametros) throws 	InvocationTargetException {

		Class tipoClase 	= instanciaClase.getClass();
		
		Method 		m 		= null;
		Object 		r		= null;
		
		try{
			
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
	
	@SuppressWarnings("rawtypes")
	protected void iniciarControllerAbstract(){
		
		hql = ""; 
		
		for (Map.Entry entry : dt_columnas.entrySet()) {

			DataTableColumn columna = (DataTableColumn) entry.getValue();
			
			columna.setBeanCustomPropiedad(entry.getKey().toString()); 
			
			beanCustomProperties.put(columna.getBeanCustomPropiedad(), columna.getBeanCustomClase());
			
			hql = (hql.equals("") ? "select " : hql + ", ") + columna.getBeanCustomProyeccion() ; 
			
			if (columna.getHrefOrden() == null || columna.getHrefOrden().equals("")) {
				columna.setHrefOrden(dt_paginaLista + "&orden=" + entry.getKey());
			}
			
		}
		
	    beanClassCustom = CfgUtil.createBeanClass("net.gefco.controlclientes." + dt_paginaLista, beanCustomProperties);
	    
	    hql = hql + " from " + dt_HQLfrom;
	    
	}
	
//	@SuppressWarnings("unchecked")
//	protected void filtrarLista () throws InvocationTargetException {	
//		dt_lista = new ArrayList<Class<?>>();
//		
//		if(dt_textoBusqueda.equals("")){
//			List<T> listaCompleta = (List<T>) ejecutarMetodo (dt_service, "listadoOrdenado",  new Object[] {dt_orden});
//			
//			dt_totalRegistros = listaCompleta.size();
//			
//			if(listaCompleta.size() % dt_registrosPorPagina == 0){
//				dt_numeroPaginas = listaCompleta.size() / dt_registrosPorPagina;
//			}else{
//				dt_numeroPaginas = listaCompleta.size() / dt_registrosPorPagina + 1;
//			}
//			
//			dt_lista =  (List<Class<?>>) ejecutarMetodo (dt_service, "listadoPaginado", new Object[] {(dt_paginaActual - 1) * dt_registrosPorPagina, dt_registrosPorPagina, dt_orden});
//			
//		}else{
//			
//			for(T t : (List<T>) ejecutarMetodo(dt_service, "listadoOrdenado",  new Object[] {dt_orden})){
//				if(t.toString().toUpperCase().contains(dt_textoBusqueda.toUpperCase())){					
//					dt_lista.add((Class<?>) t);
//				}
//			}
//			
//			dt_totalRegistros = dt_lista.size();
//			dt_paginaActual =  1;
//			dt_numeroPaginas = 1;
//		}
//		
//	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void filtrarLista () throws InvocationTargetException {	
		
		String ordenHQL = "";
		
		if (dt_orden.contains(" ASC")) {
			
			ordenHQL = dt_columnas.get( dt_orden.replace(" ASC", "").replace(" DESC", "")).getBeanCustomProyeccion() + " ASC";
		
		} else if (dt_orden.contains(" DESC")) {
		
			ordenHQL = dt_columnas.get( dt_orden.replace(" DESC", "")).getBeanCustomProyeccion() + " DESC";
		
		} else if (! dt_orden.equals("")) {
		
			ordenHQL = dt_columnas.get( dt_orden).getBeanCustomProyeccion();
		}
		
		String where = "";
		
		if(dt_textoBusqueda.equals("")){
			where = " where 1=1";
			
		} else {
			for (Map.Entry entry : dt_columnas.entrySet()) {

				DataTableColumn columna = (DataTableColumn) entry.getValue();
				
				if (columna.getBeanCustomProyeccion().endsWith(".id")) {
					continue;
				}
				
				where = (where.equals("") ? "" : where + ", ") + columna.getBeanCustomProyeccion();

			}
			where = " where CONCAT(" + where + ") like '%" + dt_textoBusqueda + "%'";
		} 
			
		//Incluir where en hql
		if ( hql.indexOf(" where ") > -1) {
			hql = hql.substring(0, hql.indexOf(" where ")) + where;
		} else {
			hql = hql + where;
		}
		
		List<Object> resultado 	= new ArrayList<Object>();
		Long totalRegistros		= (Long) ejecutarMetodo (dt_service, "totalRegistros",  new Object[] {hql});
		List<Object[]> lista 	= (List<Object[]>) ejecutarMetodo (dt_service, "listadoClaseCustomOrdenado",  new Object[] {hql, ordenHQL});
		
		dt_totalRegistros = totalRegistros;
		if(lista.size() % dt_registrosPorPagina == 0){
			dt_numeroPaginas = (int) (totalRegistros / dt_registrosPorPagina);
		}else{
			dt_numeroPaginas = (int) (totalRegistros / dt_registrosPorPagina + 1);
		}
		lista =  (List<Object[]>) ejecutarMetodo (dt_service, "listadoClaseCustomPaginado", new Object[] {hql, (dt_paginaActual - 1) * dt_registrosPorPagina, dt_registrosPorPagina, ordenHQL});
	
		try {
//				Method metodos[] = beanClassCustom.getDeclaredMethods();

			 Object obj;
			 obj = beanClassCustom.newInstance();
			
			 for (Object[] registro : lista) {
				 int contadorColumnas = 0;
				 obj = beanClassCustom.newInstance();
				 for (Map.Entry entry : beanCustomProperties.entrySet()) {
					String metodo = "set" + entry.getKey().toString().substring(0,1).toUpperCase() + entry.getKey().toString().substring(1);
					beanClassCustom.getMethod(metodo, (Class<?>) entry.getValue()).invoke(obj, registro [contadorColumnas] );
					contadorColumnas ++;
				}
				
				resultado.add( obj);				 
			 }
			
		} catch (IllegalAccessException | IllegalArgumentException
				| NoSuchMethodException | SecurityException | InstantiationException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		dt_lista = resultado;
	}
    
	protected void irAPrimeraPagina(){		
	
		dt_paginaActual = 1;
		
	}
	
	
	protected void irAUltimaPagina(){		
	
		dt_paginaActual = dt_numeroPaginas;
		
	}
	
	protected void irAPaginaAnterior(){		
	
		dt_paginaActual = dt_paginaActual-1;
		
        if (dt_paginaActual < 1) {
        	dt_paginaActual = 1;
        }

	}
	
	protected void irAPaginaSiguiente(){		
	
		dt_paginaActual = dt_paginaActual+1;
		
        if (dt_paginaActual > dt_numeroPaginas) {
        	dt_paginaActual = dt_numeroPaginas;
        }
  
	}
	
	protected void buscar(String textoBuscar){
		
		dt_paginaActual  = 1;
		
		dt_textoBusqueda = textoBuscar;
		
	}

	protected void ordenarLista(String nuevoOrden){
		
		for (Map.Entry<String, DataTableColumn> entry : dt_columnas.entrySet()) {
			
			entry.getValue().setClassOrden( CLASS_ORDEN_SIN_ORDEN);
			entry.getValue().setHrefOrden( dt_paginaLista + "&orden=" + entry.getKey() + " ASC");
			
		}
		
		String keyColumna = nuevoOrden.replace(" ASC", "").replace(" DESC", "");
		
		if (dt_orden.equals("")) {
			
			dt_columnas.get(keyColumna).setClassOrden( CLASS_ORDEN_ASCENDENTE );
			dt_columnas.get(keyColumna).setHrefOrden( dt_paginaLista + "&orden=" + keyColumna + " DESC");
			
		} else {
			
			if(nuevoOrden.contains(" ASC")){
				
				dt_columnas.get(keyColumna).setClassOrden( CLASS_ORDEN_ASCENDENTE );
				dt_columnas.get(keyColumna).setHrefOrden( dt_paginaLista + "&orden=" + keyColumna + " DESC");
				
			}else{
				
				dt_columnas.get(keyColumna).setClassOrden( CLASS_ORDEN_DESCENDENTE );
				dt_columnas.get(keyColumna).setHrefOrden( dt_paginaLista + "&orden=" + keyColumna + " ASC");
			}
		}
		dt_orden = nuevoOrden + (nuevoOrden.endsWith(" ASC") || nuevoOrden.endsWith(" DESC") ? "" : " ASC");
		
	}
	
}