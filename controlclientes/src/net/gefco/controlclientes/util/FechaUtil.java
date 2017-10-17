package net.gefco.controlclientes.util;

import java.text.SimpleDateFormat;

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
	
}
