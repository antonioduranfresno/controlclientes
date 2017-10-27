package net.gefco.controlclientes.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;

import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.core.NamingPolicy;
import org.springframework.cglib.core.Predicate;

//*****************************************************
//VERSION ReflexionUtil: 2017.10.27
//*****************************************************

public class ReflexionUtil {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object valorPropiedad (Object instanciaClase,String propiedad) throws InvocationTargetException{
		propiedad= propiedad.replace("()", "");

		if (propiedad.startsWith("CONST(") && propiedad.endsWith(")")) {
			return (String) ("" + propiedad.substring(0, propiedad.length()-1).replace("CONST(", "").replace("'","")) ;
		}
		
		if (propiedad.startsWith("CONCAT(")) {
			propiedad = propiedad.substring(0, propiedad.length()-1).replace("CONCAT(", "").replace("'","");
			String[] aParametros = propiedad.split(",");
			
			if (aParametros.length==1) {
			
				return valorPropiedad(instanciaClase, aParametros[0]);
				
			} else {
				
				String res= "";
				for (int i = 0; i < aParametros.length; i++) {
					res = res + valorPropiedad(instanciaClase, aParametros[i]);
				}
				return res;
				
			}
		}
		
		String[] 	aMetodos 		= propiedad.split("\\.");
		Class 		tipoClase 		= instanciaClase.getClass();
		try{
			if (aMetodos.length==1) {
				Method m = tipoClase.getMethod(propiedad);
				
				if (m.getReturnType().getName().equalsIgnoreCase("boolean")){
					if (((Boolean) m.invoke(instanciaClase)) == true) {
						
						return "SI";
						
					} else {
						
						return "NO";
						
					}
				}
				
				Object r = m.invoke(instanciaClase);
				return r;
				
			} else {
				Method m = tipoClase.getMethod(aMetodos[0]);
				Object r = m.invoke(instanciaClase);
				
				if (r==null){return null;}
				int inicio = propiedad.indexOf(".");
				
				return valorPropiedad(r,propiedad.substring(inicio + 1));
				
			}
			
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException |IllegalArgumentException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object ejecutarMetodo (Object instanciaClase, String metodo, Object [] parametros) throws 	InvocationTargetException {

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
	
	public static Class<?> createBeanClass(
			
	    final String className, /* fully qualified class name */
	    final LinkedHashMap<String, Class<?>> properties/* bean properties, name -> type */){
	
	    final BeanGenerator beanGenerator = new BeanGenerator();
	
	    /* use our own hard coded class name instead of a real naming policy */		    
	    beanGenerator.setNamingPolicy(new NamingPolicy(){
	        @Override public String getClassName(final String prefix,
	            final String source, final Object key, final Predicate names){
	            return className;
	        }});
	    
	    BeanGenerator.addProperties(beanGenerator, properties);
	    
	    return (Class<?>) beanGenerator.createClass();
	}
	
}
