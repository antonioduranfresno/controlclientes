package net.gefco.controlclientes.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//*****************************************************
//VERSION FechaUtil: 2017.10.27
//*****************************************************

public class FechaUtil {

	//Con esto nos aseguramos de usar siempre el mismo formato para las fechas
    public static final SimpleDateFormat SDF_Barras         	= new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat SDF_Web            	= new SimpleDateFormat("d-MMM-yyyy,HH:mm:ss aaa");
    public static final SimpleDateFormat SDF_BarrasConHora  	= new SimpleDateFormat("dd/MM/yyyy HH:mm");
    public static final SimpleDateFormat SDF_Guiones        	= new SimpleDateFormat("dd-MM-yyyy");
    public static final SimpleDateFormat SDF_GuionesConHora 	= new SimpleDateFormat("dd-MM-yyyy HH-mm");
    public static final SimpleDateFormat SDF_CalendarioRF   	= new SimpleDateFormat("dd-MMM-yyyy");
    public static final SimpleDateFormat SDF_MySQL          	= new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat SDF_MySQL_TimeStamp	= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat SDF_NombreMes      	= new SimpleDateFormat("MMM");
    
    @SuppressWarnings("serial")
	private static List<SimpleDateFormat>        
		dateFormats = new ArrayList<SimpleDateFormat>() {{             
			add(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.forLanguageTag("es_ES")));
			add(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.UK));
			add(new SimpleDateFormat("dd/MM/yyyy",Locale.forLanguageTag("es_ES")));
			add(new SimpleDateFormat("M/dd/yyyy hh:mm:ss a"));             
			add(new SimpleDateFormat("M/dd/yyyy",Locale.UK));             
			add(new SimpleDateFormat("dd.M.yyyy hh:mm:ss a")); 
			add(new SimpleDateFormat("dd.M.yyyy"));
			add(new SimpleDateFormat("dd.MMM.yyyy"));             
			add(new SimpleDateFormat("dd-MMM-yyyy")); 
			add(new SimpleDateFormat("dd-MM-yyyy"));		
			add(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSSSS"));
			add(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S")); 
			add(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));			
			add(new SimpleDateFormat("yyyy-MM-dd"));
			add(new SimpleDateFormat("yyyyMMdd"));
		}};       
	
	
		public static Date convertToDate(String input) {         
			
			Date 	date 	= null;         
			
			if (null == input || input.equals("")) {             
				return null;         
			}   
			
			for (SimpleDateFormat format : dateFormats) {             

				try { 
					
					format.setLenient(false);                 
					date = format.parse(input);
					
				} catch (ParseException e) {                 
					//Shhh.. try other formats             
				}  
				
				if (date != null) {
									
					Calendar calendario = Calendar.getInstance();				
					calendario.setTime(date);
					
					//Añadir condicion año<>1900 porque los ficheros de IVOIRE que nos manda peugeot sale una fecha como 01-01-1900
					if (format.toPattern().endsWith("yyyy") 
							&& calendario.get(Calendar.YEAR) < 2000 
							&& calendario.get(Calendar.YEAR) != 1900) {
						
						calendario.add(Calendar.YEAR,2000);
						date=calendario.getTime();
						
					}
					break;             
				}         
			}
			
			if (date == null) {
				try {
					
					return DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.LONG).parse(input);
					
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			return date;     
		}
	
}
