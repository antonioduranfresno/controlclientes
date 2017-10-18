package net.gefco.controlclientes.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractDataTable<T, Service> {
	
//	CONSTRUCTOR
	public AbstractDataTable() {
		super();
		
	}
	
	protected List<T> lista = new ArrayList<T>();
	//Paginación
	protected int paginaActual			= 1;
	protected int numeroPaginas			= 0;
	protected int registrosPorPagina	= 50;
	protected int totalRegistros;
    
	protected String textoBusqueda		= "";
	protected String orden				= null;
	protected Service service			= null;
	protected String paginaLista		= null;
    
	protected Map<String, DataTableColumn> encabezados			= new HashMap<String, DataTableColumn>();
		
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
	
	@SuppressWarnings("unchecked")
	protected void filtrarLista () throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		lista = new ArrayList<T>();
		
		if(textoBusqueda.equals("")){
			List<T> listaCompleta = (List<T>) ejecutarMetodo (service, "listadoOrdenado",  new Object[] {orden});
			
			totalRegistros = listaCompleta.size();
			
			if(listaCompleta.size() % registrosPorPagina == 0){
				numeroPaginas = listaCompleta.size() / registrosPorPagina;
			}else{
				numeroPaginas = listaCompleta.size() / registrosPorPagina + 1;
			}
			
			lista =  (List<T>) ejecutarMetodo (service, "listadoPaginado", new Object[] {(paginaActual - 1) * registrosPorPagina, registrosPorPagina, orden});
			
		}else{
			
			for(T t : (List<T>) ejecutarMetodo(service, "listadoOrdenado",  new Object[] {orden})){
				if(t.toString().toUpperCase().contains(textoBusqueda.toUpperCase())){					
					lista.add(t);
				}
			}
			
			totalRegistros = lista.size();
			paginaActual =  1;
			numeroPaginas = 1;
		}
		
	}
    
	protected void irAPrimeraPagina(){		
	
		paginaActual = 1;
		
	}
	
	
	protected void irAUltimaPagina(){		
	
		paginaActual = numeroPaginas;
		
	}
	
	protected void irAPaginaAnterior(){		
	
		paginaActual = paginaActual-1;
		
        if (paginaActual < 1) {
        	paginaActual = 1;
        }

	}
	
	protected void irAPaginaSiguiente(){		
	
		paginaActual = paginaActual+1;
		
        if (paginaActual > numeroPaginas) {
        	paginaActual = numeroPaginas;
        }
  
	}
	
	protected void ordenarLista(String campoNuevoOrden){
		String ordenConPuntos = campoNuevoOrden.replace("-",".");
				
		if(orden.contains(ordenConPuntos)){
			if(orden.contains(" DESC")){
				orden = orden.replace(" DESC", " ASC");
			}else{
				orden = orden.replace(" ASC", " DESC");	
			}			
		}else{
			orden = ordenConPuntos + (ordenConPuntos.endsWith(" ASC") ? "" : " ASC"); 
		}
		
		for (Map.Entry<String, DataTableColumn> entry : encabezados.entrySet()) {
//		    String key = entry.getKey();
		    DataTableColumn value = entry.getValue();
		    
		    if ( value.getHrefOrden().contains(campoNuevoOrden) ) {
		    	if (value.getHrefOrden().contains(" DESC")) {
		    		value.setClassOrden( "glyphicon glyphicon-sort-by-order-alt");
		    		value.setHrefOrden( paginaLista + "&orden=" + value.getCampoHQL() + " ASC");
		    	} else {
		    		value.setClassOrden( "glyphicon glyphicon-sort-by-order");
		    		value.setHrefOrden( paginaLista + "&orden=" + value.getCampoHQL() + " DESC");
		    	}
		    } else {
		    	value.setClassOrden( "");
		    	value.setHrefOrden( paginaLista + "&orden=" + value.getCampoHQL() + " ASC");
		    }
		}
		
	}
	
	protected void buscar(String textoBuscar){
		
		paginaActual  = 1;
		
		textoBusqueda = textoBuscar;
		
	}

}
