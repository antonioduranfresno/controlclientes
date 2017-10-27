package net.gefco.controlclientes.util;

import java.util.LinkedHashMap;

import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.core.NamingPolicy;
import org.springframework.cglib.core.Predicate;

public class CfgUtil {

	public static final String URL_BBDD = "jdbc:mysql://localhost:3306/control_clientes";
	public static final String USR_BBDD = "root";
	public static final String PW_BBDD = "root";
	
	public static final String RUTA_FICHEROS   	 = "D:\\control_clientes\\ficheros\\";
	
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