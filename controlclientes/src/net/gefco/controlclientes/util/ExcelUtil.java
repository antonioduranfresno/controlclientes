package net.gefco.controlclientes.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.OldExcelFormatException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//*****************************************************
//VERSION ExcelUtil: 2017.10.27
//*****************************************************

public class ExcelUtil {
	private static ArrayList<Integer> formatosFechas = new ArrayList<Integer>(Arrays.asList(14, 16, 164, 165, 166
										, 167, 168, 169, 170, 171, 172, 173, 174
										, 175, 177, 178, 180, 181, 182));
	
	private static CellStyle crearEstiloCeldaCabecera(Workbook libro) {
	    CellStyle cellStyleCabecera = libro.createCellStyle();
	    
	    cellStyleCabecera.setAlignment(CellStyle.ALIGN_CENTER);
	    cellStyleCabecera.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	    cellStyleCabecera.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
	    cellStyleCabecera.setBottomBorderColor((short) 8);
	    cellStyleCabecera.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
	    cellStyleCabecera.setLeftBorderColor((short) 8);
	    cellStyleCabecera.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
	    cellStyleCabecera.setRightBorderColor((short) 8);
	    cellStyleCabecera.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
	    cellStyleCabecera.setTopBorderColor((short) 8);
	    cellStyleCabecera.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
	    cellStyleCabecera.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    
	    return cellStyleCabecera;
	    
	}
	
	private static CellStyle crearEstiloCeldaDatos(Workbook libro) {
	    CellStyle cellStyleDatos = libro.createCellStyle();
	    
	    cellStyleDatos.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	    cellStyleDatos.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    cellStyleDatos.setBottomBorderColor((short) 8);
	    cellStyleDatos.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    cellStyleDatos.setLeftBorderColor((short) 8);
	    cellStyleDatos.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    cellStyleDatos.setRightBorderColor((short) 8);
	    cellStyleDatos.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    cellStyleDatos.setTopBorderColor((short) 8);
	    
	    return cellStyleDatos;
	}
	
	private static CellStyle crearEstiloCeldaDatosFecha(Workbook libro) {
	    
		CellStyle cellStyleDatos 		= crearEstiloCeldaDatos(libro);
	    CellStyle cellStyleDatosFecha 	= libro.createCellStyle();
	    
	    cellStyleDatosFecha.cloneStyleFrom(cellStyleDatos);
	    cellStyleDatosFecha.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    
	    return cellStyleDatosFecha;
	}
	
	private static Map<String, CellStyle> crearMapaEstilosCeldas(Workbook		libro) {

		Map <String,CellStyle> mapaEstilosCeldas 	= new HashMap<String, CellStyle>();
		
		CreationHelper 			createHelper 		= libro.getCreationHelper();
		
		//EstiloDatos (estilo base)
		CellStyle cellStyleDatos = libro.createCellStyle();	    
	    cellStyleDatos.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	    cellStyleDatos.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    cellStyleDatos.setBottomBorderColor((short) 8);
	    cellStyleDatos.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    cellStyleDatos.setLeftBorderColor((short) 8);
	    cellStyleDatos.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    cellStyleDatos.setRightBorderColor((short) 8);
	    cellStyleDatos.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    cellStyleDatos.setTopBorderColor((short) 8);
	    mapaEstilosCeldas.put("cellStyleDatos", cellStyleDatos);
	    
	    //Estilo Cabecera
	    CellStyle cellStyleCabecera = libro.createCellStyle();
	    cellStyleCabecera.setAlignment(CellStyle.ALIGN_CENTER);
	    cellStyleCabecera.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	    cellStyleCabecera.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
	    cellStyleCabecera.setBottomBorderColor((short) 8);
	    cellStyleCabecera.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
	    cellStyleCabecera.setLeftBorderColor((short) 8);
	    cellStyleCabecera.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
	    cellStyleCabecera.setRightBorderColor((short) 8);
	    cellStyleCabecera.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
	    cellStyleCabecera.setTopBorderColor((short) 8);
	    cellStyleCabecera.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
	    cellStyleCabecera.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    mapaEstilosCeldas.put("cellStyleCabecera", cellStyleCabecera);
	    
		CellStyle estiloFormatoFechaHora = libro.createCellStyle();
		estiloFormatoFechaHora.cloneStyleFrom(cellStyleDatos);
		estiloFormatoFechaHora.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		estiloFormatoFechaHora.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy hh:mm"));
		mapaEstilosCeldas.put("estiloFormatoFechaHora", estiloFormatoFechaHora);
		
		CellStyle estiloFormatoFecha = libro.createCellStyle();
		estiloFormatoFecha.cloneStyleFrom(cellStyleDatos);
		estiloFormatoFecha.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		estiloFormatoFecha.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
		mapaEstilosCeldas.put("estiloFormatoFecha", estiloFormatoFecha);
		
		return mapaEstilosCeldas;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void generarExcel(ArrayList<Object> cabeceraFichero, 
									int filaCabecera, 
									ArrayList<Object> datosFichero, 
									List tiposDatosFichero, 
									String nombreFichero,   
									String ruta, 
									String nombrePestana) throws Exception  {
		
		//Si no existe la carpeta para ficheros temporales, la creamos
//		File f = new File(ruta);
//		if (! f.isDirectory()) {f.mkdirs();}
		
		Workbook 				libro		= null;
		NPOIFSFileSystem 		fs			= null;
		File 					f 			= new File(ruta + nombreFichero);
		
		//Construir el fichero excel en el servidor. si existe, lo abrimos para modificarlo. Si no, lo creamos.
		if(f.exists()){
			try {
				
				fs 		= new NPOIFSFileSystem(f);
				libro 	= WorkbookFactory.create(new FileInputStream(ruta + nombreFichero));
				
			} catch (Exception e) {
				
				if (fs != null) { fs.close();}
				throw e;
					
			}
			
		} else {
			libro = new HSSFWorkbook();
		}
		
		CreationHelper 		createHelper 		= libro.getCreationHelper();		
		FileOutputStream 	archivoServidor 	= new FileOutputStream(ruta+nombreFichero);
		HSSFSheet 			hoja 				= null;
		
		for (Integer i = 0; i < libro.getNumberOfSheets(); i++) {
           if (libro.getSheetName(i).equals(nombrePestana)) {
        	   hoja = (HSSFSheet) libro.getSheetAt(i);
        	   break;
           }
		}
		
		if (hoja == null){ hoja=(HSSFSheet) libro.createSheet(nombrePestana); }
		
		CellStyle 		cellStyleCabecera 			= crearEstiloCeldaCabecera(libro);   
		CellStyle 		cellStyleDatos 				= crearEstiloCeldaDatos(libro);   
		CellStyle 		cellStyleDatosFecha 		= crearEstiloCeldaDatosFecha(libro);
	        	    	    		    
	    int  			i 		= 0;	    
	    HSSFRow 		fila 	= hoja.createRow((short) filaCabecera -1 );
	    HSSFCell 		cell;
	    
	    for (i = 0; i < cabeceraFichero.size(); i++) {
	    	cell = fila.createCell(i);
	    	cell.setCellValue( (cabeceraFichero.get(i) == null ? "" : cabeceraFichero.get(i).toString()) );
	    	cell.setCellStyle(cellStyleCabecera);	    	
		}
	   
	    //Datos
	    Object valor;
	    for (Integer x = 0; x < datosFichero.size(); x++) {
	    	i 		= 0;
	    	fila 	= hoja.createRow(x + filaCabecera);
	    	
	    	for (Integer j=0; j<cabeceraFichero.size(); j++){
	    		cell = fila.createCell(i);
	    		cell.setCellStyle(cellStyleDatos);
	    		valor	= null;
	    		
    			if (ArrayList.class.isInstance(datosFichero.get(x))) {
    				valor = ((ArrayList<Object>) datosFichero.get(x)).get(j);
    				
    			} else if (Object[].class.isInstance(datosFichero.get(x))) {
    				valor = ((Object[]) datosFichero.get(x))[j];
    				
				} else {
    				valor = datosFichero.get(x);
    				
    			}
    			
				if(valor ==null){
					cell.setCellValue("");
					
				}else if (tiposDatosFichero.get(j).equals("FECHA_CON_HORA")){
					
					if (valor.toString().equals("")){
						cell.setCellValue(valor.toString());
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellType(HSSFCellStyle.ALIGN_LEFT);
						
					} else {
						cell.setCellType((short)0);
						cell.setCellValue((Date)valor);
						cellStyleDatosFecha.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy hh:mm"));
						cell.setCellStyle(cellStyleDatosFecha);
						
					}
					
				}else if (tiposDatosFichero.get(j).equals("FECHA_SIN_HORA") || tiposDatosFichero.get(j).equals("DATE")){
					cell.setCellType((short)0);
					if (valor.toString().equals("")){
						cell.setCellValue(valor.toString());
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellType(HSSFCellStyle.ALIGN_LEFT);
					} else {
						cell.setCellValue(FechaUtil.convertToDate(valor.toString()));
						cellStyleDatosFecha.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
						cell.setCellStyle(cellStyleDatosFecha);
					}
					
					
				}else if (tiposDatosFichero.get(j).equals("TEXTO") || tiposDatosFichero.get(j).equals("STRING")){
					cell.setCellValue(valor.toString());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellType(HSSFCellStyle.ALIGN_LEFT);
					
				}else if (tiposDatosFichero.get(j).equals("DOUBLE")){
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellType(HSSFCellStyle.ALIGN_RIGHT);
					cell.setCellValue(Double.parseDouble((valor.toString().equals("") ? "0.0" : valor.toString())));
					
				}else if (tiposDatosFichero.get(j).equals("PORCENTAJE")){
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellType(HSSFCellStyle.ALIGN_RIGHT);
					cell.setCellValue(valor.toString().equals("") ? "0.0 %" : valor.toString() + " %");
				
				}else if (tiposDatosFichero.get(j).equals("INT") || tiposDatosFichero.get(j).equals("INTEGER") || tiposDatosFichero.get(j).equals("LONG")){
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellType(HSSFCellStyle.ALIGN_RIGHT);
					cell.setCellValue(Integer.parseInt((valor.toString().equals("") ? "0" : valor.toString())));
				}
	    		i++;
	    	}			
	    }
	    
	    for (Integer j = 0; j < i; j++) {
	    	hoja.autoSizeColumn(j);
		}
	    
	    libro.write(archivoServidor); 
	    archivoServidor.flush();
	    archivoServidor.close();
	    if (fs != null) { fs.close();}
	}
	
	private static void rellenarCelda(Workbook libro, Cell cell, Object valor, String tipoClase, Map<String, CellStyle> mapaEstilosCeldas) {
		
		
		if (valor == null){ 
			cell.setCellValue("");
			return;
		}
		
		if (tipoClase.equals(Date.class.getCanonicalName()) ) {
			cell.setCellType((short)0);
			if (valor.toString().equals("")){
				cell.setCellValue(valor.toString());
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellType(HSSFCellStyle.ALIGN_LEFT);
			} else {
				cell.setCellValue((Date) valor);
				
				Calendar fecha = Calendar.getInstance();
				fecha.setTime((Date) valor);
				
				if (fecha.get(Calendar.HOUR) == 0 && fecha.get(Calendar.MINUTE) == 0  && fecha.get(Calendar.SECOND) == 0) {
					cell.setCellStyle(mapaEstilosCeldas.get("estiloFormatoFecha"));
				} else {
					cell.setCellStyle(mapaEstilosCeldas.get("estiloFormatoFechaHora"));
				}
				
			}
			
			return;
		}
		
		if (tipoClase.equals(String.class.getCanonicalName()) ) {
		
			cell.setCellValue(valor.toString());
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellType(HSSFCellStyle.ALIGN_LEFT);
			
			return;
		}
		
		if (tipoClase.equals(Double.class.getCanonicalName()) ) {
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellType(HSSFCellStyle.ALIGN_RIGHT);
			cell.setCellValue(Double.parseDouble((valor.toString().equals("") ? "0.0" : valor.toString())));
			
			return;
		}
		
		if (tipoClase.equals("PORCENTAJE")) {
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellType(HSSFCellStyle.ALIGN_RIGHT);
			cell.setCellValue(valor.toString().equals("") ? "0.0 %" : valor.toString() + " %");
			
			return;
		}
			
		if (tipoClase.equals(Integer.class.getCanonicalName()) ) {
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellType(HSSFCellStyle.ALIGN_RIGHT);
			cell.setCellValue(Integer.parseInt((valor.toString().equals("") ? "0" : valor.toString())));
		}
		
	}

	public static Workbook generarXLS(LinkedHashMap<String, DataTableColumn> columnas, List<Object> datos, String columnasAMostrar, String nombrePestana) throws InvocationTargetException {
		
		int 					filaCabecera		= 1;
		HSSFWorkbook			libro				= new HSSFWorkbook();
//		CreationHelper 		createHelper 		= libro.getCreationHelper();	
//		CellStyle 			cellStyleCabecera 	= crearEstiloCeldaCabecera(libro);   
//		CellStyle 			cellStyleDatos 		= crearEstiloCeldaDatos(libro);   
		Map <String,CellStyle> 	mapaEstilosCeldas 	= new HashMap<String, CellStyle>();
		HSSFSheet 				hoja 				= null;
		HSSFRow 				fila;
		HSSFCell 				cell;
		
		for (Integer i = 0; i < libro.getNumberOfSheets(); i++) {
			if (libro.getSheetName(i).equals(nombrePestana)) {
				hoja = (HSSFSheet) libro.getSheetAt(i);
				break;
			}
		}
		
		if (hoja == null){ hoja=(HSSFSheet) libro.createSheet(nombrePestana); }
	    
		//Definir estilos para las celdas				
		mapaEstilosCeldas = crearMapaEstilosCeldas (libro);
		
	    //Generar cabecera
		int  			contadorCeldas	= 0;	    
		fila 	= hoja.createRow((short) filaCabecera -1 );

		for (String nombreColumna : columnasAMostrar.split("; ")) {

			DataTableColumn columna = (DataTableColumn) columnas.get(nombreColumna);
			
			String encabezado = columna.getLabel(); 
			
			cell = fila.createCell(contadorCeldas);
			cell.setCellValue( (encabezado == null ? "" : encabezado) );
			cell.setCellStyle(mapaEstilosCeldas.get("cellStyleCabecera"));
			contadorCeldas ++;
			
		}
	    
	   
	    //Datos
	    Object 		valor 				= null;
	    Integer		contadorFilas 		= null;
	    Integer 	contadorColumnas	= null;
	    
	    for (Object registro : datos) {	

	    	contadorFilas = (contadorFilas== null ? 0 : contadorFilas + 1);
	    	contadorColumnas = 0;

	    	fila 	= hoja.createRow(contadorFilas + filaCabecera);
	    	
	    	for (String nombreColumna : columnasAMostrar.split("; ")) {
	    		DataTableColumn columna = (DataTableColumn) columnas.get(nombreColumna);
	    		cell = fila.createCell(contadorColumnas);
	    		cell.setCellStyle(mapaEstilosCeldas.get("cellStyleDatos"));
	    		
				String propiedad = "get" + columna.getBeanCustomPropiedad().substring(0,1).toUpperCase() + columna.getBeanCustomPropiedad().substring(1);
				valor = ReflexionUtil.valorPropiedad(registro, propiedad);
				contadorColumnas ++;
				
				rellenarCelda(libro, cell, valor, columna.getBeanCustomClase().getCanonicalName(), mapaEstilosCeldas);
								
			 }
	    	
	    }
	    
	    //Autosize columnas
	    for (Integer col = 0; col < datos.size(); col++) {
	    	hoja.autoSizeColumn(col);
		}
	    
	    return libro;
	    
	}
	
	

	public static XSSFWorkbook generarXLSX(LinkedHashMap<String, DataTableColumn> columnas, List<Object> datos, String columnasAMostrar, String nombrePestana) throws InvocationTargetException {
	
		int 					filaCabecera		= 1;
		XSSFWorkbook			libro				= new XSSFWorkbook();
//		CreationHelper 		createHelper 		= libro.getCreationHelper();	
//		XSSFCellStyle 		cellStyleCabecera 	= (XSSFCellStyle) crearEstiloCeldaCabecera(libro);   
//		XSSFCellStyle 		cellStyleDatos 		= (XSSFCellStyle) crearEstiloCeldaDatos(libro);
		Map <String,CellStyle> 	mapaEstilosCeldas 	= new HashMap<String, CellStyle>();
		XSSFSheet 				hoja 				= null;
		XSSFRow 				fila;
		XSSFCell 				cell;
		
		for (Integer i = 0; i < libro.getNumberOfSheets(); i++) {
			if (libro.getSheetName(i).equals(nombrePestana)) {
				hoja = (XSSFSheet) libro.getSheetAt(i);
				break;
			}
		}
		
		if (hoja == null){ hoja=(XSSFSheet) libro.createSheet(nombrePestana); }
		
		//Definir estilos para las celdas				
		mapaEstilosCeldas = crearMapaEstilosCeldas (libro);
		
		//Generar cabecera
		int  			contadorCeldas	= 0;	    
		fila 	= hoja.createRow((short) filaCabecera -1 );
		
		for (String nombreColumna : columnasAMostrar.split("; ")) {
		
			DataTableColumn columna = (DataTableColumn) columnas.get(nombreColumna);
			
			String encabezado = columna.getLabel(); 
			
			cell = fila.createCell(contadorCeldas);
			cell.setCellValue( (encabezado == null ? "" : encabezado) );
				cell.setCellStyle(mapaEstilosCeldas.get("cellStyleCabecera"));
				contadorCeldas ++;
				
			}
		    
		   
		//Datos
		Object 		valor 				= null;
		Integer		contadorFilas 		= null;
		Integer 	contadorColumnas	= null;
		
		for (Object registro : datos) {	
		
			contadorFilas = (contadorFilas== null ? 0 : contadorFilas + 1);
			contadorColumnas = 0;
		
			fila 	= hoja.createRow(contadorFilas + filaCabecera);
			
			for (String nombreColumna : columnasAMostrar.split("; ")) {
				DataTableColumn columna = (DataTableColumn) columnas.get(nombreColumna);
				cell = fila.createCell(contadorColumnas);
				cell.setCellStyle(mapaEstilosCeldas.get("cellStyleDatos"));
				
				String propiedad = "get" + columna.getBeanCustomPropiedad().substring(0,1).toUpperCase() + columna.getBeanCustomPropiedad().substring(1);
				valor = ReflexionUtil.valorPropiedad(registro, propiedad);
				contadorColumnas ++;
				rellenarCelda(libro, cell, valor, columna.getBeanCustomClase().getCanonicalName(), mapaEstilosCeldas);
				
			 }
			
		}
		
		//Autosize columnas
	    for (Integer col = 0; col < datos.size(); col++) {
	    	hoja.autoSizeColumn(col);
		}
	    
	    return libro;
		
	}
	
		
	public static ArrayList<String> pestanasArchivoExcel(String archivo) {
		ArrayList<String> 		resultado			= new ArrayList<String>();
		try	{
			
			FileInputStream 	fileInputStream 	= new FileInputStream(archivo);
			POIFSFileSystem 	fsFileSystem 		= new POIFSFileSystem(fileInputStream);
			HSSFWorkbook 		workBook 			= new HSSFWorkbook(fsFileSystem);
			
			for (int i = 0; i < workBook.getNumberOfSheets(); i++) {
			
				resultado.add(workBook.getSheetAt(i).getSheetName());
				
			}
			
			fileInputStream.close();
			return resultado;
			
		} catch (Exception e){
			
			e.printStackTrace();
			return null;
			
		}
	}
		
	public static int indicePestanaArchivoExcel(String archivo, String nombrePestanaABuscar) {
		try	{
			FileInputStream 	fileInputStream 	= new FileInputStream(archivo);
			POIFSFileSystem 	fsFileSystem 		= new POIFSFileSystem(fileInputStream);
			HSSFWorkbook 		workBook 			= new HSSFWorkbook(fsFileSystem);
			
			for (int i = 0; i < workBook.getNumberOfSheets(); i++) {
				if (workBook.getSheetAt(i).getSheetName().trim().toUpperCase().equals(nombrePestanaABuscar.trim().toUpperCase())) {
					return i;
				}
			}
			
			fileInputStream.close();
			return -1;
			
		} catch (Exception e){
			
			e.printStackTrace();
			return -1;
			
		}
	}
	
	@SuppressWarnings("resource")
	public static String nombrePestanaArchivoExcel(String archivo, int indicePestanaABuscar) throws Exception {
		FileInputStream 			fileInputStream 	= new FileInputStream(archivo);
		String 						fileExt 			= extensionFichero(archivo);
		
		if (fileExt.equalsIgnoreCase("xls")) {
			try	{
				POIFSFileSystem 	fsFileSystem 		= new POIFSFileSystem(fileInputStream);
				HSSFWorkbook 		workBook 			= new HSSFWorkbook(fsFileSystem);
				String 				nombrePestana 		= "";
				
				nombrePestana = workBook.getSheetAt(indicePestanaABuscar).getSheetName();
				fileInputStream.close();
				return nombrePestana;
				
			} catch (OldExcelFormatException e) {
				
				//Se ha detectado que es un excel versión 95
				fileInputStream  = new FileInputStream(archivo);
				
				jxl.Workbook 		workbook 		= jxl.Workbook.getWorkbook(fileInputStream);
				jxl.Sheet 			hssfSheet 		= workbook.getSheet(indicePestanaABuscar);
				
				return hssfSheet.getName();
				
			} catch (Exception e){
				
				e.printStackTrace();
				return "";
			}
		}
		if (fileExt.equalsIgnoreCase("xlsx")) {
			try {
				XSSFWorkbook 		workBook 		= new XSSFWorkbook(fileInputStream);
				String 				nombrePestana 	= "";
				
				nombrePestana = workBook.getSheetAt(indicePestanaABuscar).getSheetName();
				fileInputStream.close();
				return nombrePestana;
				
			} catch (Exception e){
				
				e.printStackTrace();
				return "";
			}
		}
		return "";
		
	}
		
		
	public static int numColumna(ArrayList<Object> cabeceraFicheroImportado, String encabezadoColumna) {
		int resultado = -1;
		for (int columna = 0; columna < cabeceraFicheroImportado.size(); columna++) {
			if (cabeceraFicheroImportado.get(columna).toString().toUpperCase().equals(encabezadoColumna.toUpperCase())) {
				
				resultado = columna;
				break;
				
			}
		}
		
		return resultado;
	}
	
	@SuppressWarnings({ "rawtypes", "unused", "resource" })
	public static ArrayList<Object> leerCabeceraExcel(String archivo, String nombrePestana, int filaCabecera) throws Exception {
		ArrayList<Object> 		cabeceras 			= new ArrayList<Object>();
		FileInputStream 		fileInputStream 	= new FileInputStream(archivo);
		String 					fileExt 			= extensionFichero(archivo);
		
		if (fileExt.equalsIgnoreCase("xls")) {
			
			try {
				POIFSFileSystem fsFileSystem 		= new POIFSFileSystem(fileInputStream);
				HSSFWorkbook 	workBook 			= new HSSFWorkbook(fsFileSystem);
				HSSFSheet 		hssfSheet 			= workBook.getSheet(nombrePestana);
				Iterator 		rowIterator 		= hssfSheet.rowIterator();
				HSSFRow 		hssfRow 			= (HSSFRow)  hssfSheet.getRow(filaCabecera-1); //rowIterator.next();
				Iterator 		iterator 			= hssfRow.cellIterator();
				List 			cellTempList 		= new ArrayList();
				int 			i 					= 0;
				
				while (iterator.hasNext()){
					HSSFCell hssfCell = (HSSFCell) iterator.next();
					cabeceras.add(hssfCell);					
					i++;
				}
	
				fileInputStream.close();
				return cabeceras;
				
			} catch (OldExcelFormatException e) {
				
				//Se ha detectado que es un excel versión 95
				fileInputStream 	= new FileInputStream(archivo);
				jxl.Workbook 		workbook 			= jxl.Workbook.getWorkbook(fileInputStream);
				jxl.Sheet 			hssfSheet 			= workbook.getSheet(nombrePestana);
				jxl.Cell[] 			row 				= hssfSheet.getRow(filaCabecera-1);
				@SuppressWarnings("unchecked")
				ArrayList<Object> 	fila				= new ArrayList();
				
				for (jxl.Cell cell : row) {
					fila.add(cell.getContents());                              
				}

				workbook.close();
				fileInputStream.close();
				return fila;
				
		
			} catch (Exception e){
				e.printStackTrace();
				return null;
			}
		}
		
		if (fileExt.equalsIgnoreCase("xlsx")) {
			try {
				XSSFWorkbook 			workBook 		= new XSSFWorkbook(fileInputStream);
				XSSFSheet 				xssfSheet 		= workBook.getSheet(nombrePestana);
	//			Iterate the rows and cells of the spreadsheet to get all the datas.			
				Iterator 				rowIterator 	= xssfSheet.rowIterator();
				XSSFRow 				xssfRow 		= (XSSFRow)  xssfSheet.getRow(filaCabecera-1); //rowIterator.next();
				Iterator 				iterator 		= xssfRow.cellIterator();
				List 					cellTempList 	= new ArrayList();
				int 					i 				= 0;
				
				while (iterator.hasNext()){
					XSSFCell xssfCell = (XSSFCell) iterator.next();
					cabeceras.add(xssfCell);					
					i++;
				}
				
				fileInputStream.close();
				return cabeceras;
				
			} catch (Exception e){
				
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	
			
	@SuppressWarnings({ "rawtypes", "unchecked", "resource" })
	public static ArrayList<ArrayList<Object>> leerDatosExcel(String archivo, String nombrePestana, 
					int primeraFilaDatos, int ultimaFilaDatos) throws Exception {
		
		ArrayList<ArrayList<Object>> 	filas 			= new ArrayList<ArrayList<Object>>();
		FileInputStream 				fileInputStream = new FileInputStream(archivo);
		String 							fileExt 		= extensionFichero(archivo);
		
		if (fileExt.equalsIgnoreCase("xls")) {
			POIFSFileSystem 			fsFileSystem 	= new POIFSFileSystem(fileInputStream);
			
			try {
				HSSFWorkbook 			workBook 		= new HSSFWorkbook(fsFileSystem);
				HSSFSheet 				hssfSheet 		= workBook.getSheet(nombrePestana);
				Iterator 				rowIterator 	= hssfSheet.rowIterator();
				
				//Leer la cabecera para saber el tamaño de los arrays de objetos a crear
				if (hssfSheet.getRow(primeraFilaDatos-1) == null) {
					//La pestaña a leer no tiene datos
					return null;
				}
				
				HSSFRow 				row 			= hssfSheet.getRow(primeraFilaDatos-1);
				Iterator 				iterator 		= row.cellIterator();
				List 					cellTempList 	= new ArrayList();
				
				while (iterator.hasNext()){
					HSSFCell 			hssfCell 		= (HSSFCell) iterator.next();
					cellTempList.add(hssfCell);	
				}
				
				ArrayList<Object> 		fila			= new ArrayList();
				
				//Leer las filas de datos
				int 					contFilas 		= 0;
				
				while (rowIterator.hasNext() && (contFilas <= ultimaFilaDatos || ultimaFilaDatos==0)){					
					row = (HSSFRow) rowIterator.next();
					
					if ( contFilas > primeraFilaDatos-2 ){
						iterator = row.cellIterator();
						for (int i = 0; i < row.getLastCellNum(); i++) {					
							HSSFCell hssfCell = (HSSFCell) row.getCell(i);
							//pasamos todos los campos que no son fecha a texto
							//Si no lo hacemos, a los campos que sólo tienen dígitos, les pone un .0 detrás
							if(hssfCell != null) {
								
								if (! formatosFechas.contains(new Integer(hssfCell.getCellStyle().getDataFormat()))){
									hssfCell.setCellType(1);
								}
							}
							fila.add(hssfCell);					
						}
						filas.add(fila);
						fila = new ArrayList();
					}
					
					contFilas++;
				}
				
				fileInputStream.close();
				return filas;
				
			} catch (OldExcelFormatException e) {
				
				//Se ha detectado que es un excel versión 95
				fileInputStream 			= new FileInputStream(archivo);
				jxl.Workbook 				workbook 			= jxl.Workbook.getWorkbook(fileInputStream);
				jxl.Sheet 					hssfSheet 			= workbook.getSheet(nombrePestana);
				
				for (int filaActual = primeraFilaDatos-1; filaActual < hssfSheet.getRows(); filaActual++) {
					jxl.Cell[] 				row 	= hssfSheet.getRow(filaActual);
					ArrayList<Object> 		fila 	= new ArrayList();
					
					for (jxl.Cell cell : row) {
						fila.add(cell.getContents());                              
					}
					filas.add(fila);
				}
				
				workbook.close();
				fileInputStream.close();
				return filas;
				
			} catch (Exception e){
				
				e.printStackTrace();
				return null;
			}
		}
		if (fileExt.equalsIgnoreCase("xlsx")) {
			try {
				XSSFWorkbook 			workBook 			= new XSSFWorkbook(fileInputStream);
				XSSFSheet 				xssfSheet 			= workBook.getSheet(nombrePestana);
				Iterator 				rowIterator 		= xssfSheet.rowIterator();
				
//				Leer la cabecera para saber el tamaño de los arrays de objetos a crear
				XSSFRow 				row 				= xssfSheet.getRow(primeraFilaDatos-1);
				Iterator 				iterator 			= row.cellIterator();
				List 					cellTempList 		= new ArrayList();
				
				while (iterator.hasNext()){
					XSSFCell xssfCell = (XSSFCell) iterator.next();
					cellTempList.add(xssfCell);	
				}
				
				ArrayList<Object> 		fila				= new ArrayList();
				
//				Leer las filas de datos
				int 					contFilas 			= 0;
				while (rowIterator.hasNext() && (contFilas <= ultimaFilaDatos || ultimaFilaDatos==0)){					
					row = (XSSFRow) rowIterator.next();
					if( contFilas > primeraFilaDatos-2 ){
						iterator = row.cellIterator();
						for (int i = 0; i < row.getLastCellNum(); i++) {						
							XSSFCell xssfCell = (XSSFCell) row.getCell(i);
							//pasamos todos los campos que no son fecha a texto
							//Si no lo hacemos, a los campos que sólo tienen dígitos, les pone un .0 detrás
							if(xssfCell != null) {
								if (! formatosFechas.contains(new Integer(xssfCell.getCellStyle().getDataFormat()))){
									xssfCell.setCellType(1);
								}
							}
							fila.add(xssfCell);
						}
						filas.add(fila);
						fila=new ArrayList();
					}
					contFilas++;
				}
				fileInputStream.close();
				return filas;
				
			} catch (Exception e){
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unused")
	private static String extensionFichero(String fname2) { 
		String 		fileName 	= fname2; 
		String 		fname		= ""; 
		String 		ext			= ""; 
		int 		mid			= fileName.lastIndexOf(".");
		
		fname	= fileName.substring(0,mid); 
		ext		= fileName.substring(mid+1,fileName.length()); 
		return ext; 
	}
	
	public static String valorColumnaString(ArrayList<Object> fila, int numColumna) {
		if ( fila.get(numColumna).getClass() == HSSFCell.class ){
			return ((HSSFCell) fila.get(numColumna)).getStringCellValue();
		}
	
		if ( fila.get(numColumna).getClass() == XSSFCell.class ){
			return ((XSSFCell) fila.get(numColumna)).getStringCellValue();
		}
		return null;
	}
	
	public static Integer valorColumnaInteger(ArrayList<Object> fila, int numColumna) {
		if ( fila.get(numColumna).getClass() == HSSFCell.class ){
			return Integer.parseInt(((HSSFCell) fila.get(numColumna)).getStringCellValue());
		}
	
		if ( fila.get(numColumna).getClass() == XSSFCell.class ){
			return Integer.parseInt(((XSSFCell) fila.get(numColumna)).getStringCellValue());
		}
		return null;
	}
	
	public static Date valorColumnaDate(ArrayList<Object> fila, int numColumna) {
		if ( fila.get(numColumna).getClass() == HSSFCell.class ){
			return (Date) ((HSSFCell) fila.get(numColumna)).getDateCellValue();
		}
	
		if ( fila.get(numColumna).getClass() == XSSFCell.class ){
			return (Date) ((XSSFCell) fila.get(numColumna)).getDateCellValue();
		}
		return null;
	}
	
	public static Double valorColumnaDouble(ArrayList<Object> fila, int numColumna) {
		if ( fila.get(numColumna).getClass() == HSSFCell.class ){
			return Double.parseDouble(((HSSFCell) fila.get(numColumna)).getStringCellValue());
		}
	
		if ( fila.get(numColumna).getClass() == XSSFCell.class ){
			return Double.parseDouble(((XSSFCell) fila.get(numColumna)).getStringCellValue());
		}
		return null;
	}

	

	
}
