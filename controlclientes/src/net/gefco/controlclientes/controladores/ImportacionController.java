package net.gefco.controlclientes.controladores;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.gefco.controlclientes.modelo.Actividad;
import net.gefco.controlclientes.modelo.Agencia;
import net.gefco.controlclientes.modelo.Facturacion;
import net.gefco.controlclientes.modelo.Tercero;
import net.gefco.controlclientes.negocio.ActividadService;
import net.gefco.controlclientes.negocio.AgenciaService;
import net.gefco.controlclientes.negocio.FacturacionService;
import net.gefco.controlclientes.negocio.TerceroService;
import net.gefco.controlclientes.util.CfgUtil;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@Scope("session")
@SessionAttributes("usuarioSesion")
public class ImportacionController {
	
	@Autowired
	private AgenciaService agenciaService;
	
	@Autowired
	private TerceroService terceroService;
	
	@Autowired
	private ActividadService actividadService;
	
	@Autowired
	private FacturacionService facturacionService;

	private static final int BYTES_DOWNLOAD = 1024;
	
	@Autowired
    private SessionFactory sessionFactory;

	@RequestMapping("/importacion")
	public String importacion(Model model){
		
		return "importacion";
	}
	
	@RequestMapping("/infoFicheros")
	@ResponseBody
	public String cargaAreaImportacion(){		
		
		return infoFicheros();
	}
	
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)	
	public String uploadFileHandler(HttpServletRequest request) {

		String name   		= null;
		
		try {
			
			@SuppressWarnings("unchecked")
			List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
			
			for(FileItem item : multiparts){
				if(!item.isFormField()){
					
					name = new File(item.getName()).getName();
					
					if(name.toLowerCase().contains("xlsx")){
						item.write( new File((CfgUtil.RUTA_FICHEROS + name)));	
					}
										
				}else{
					continue;
				}				    
			}
			
		} catch (FileUploadException e) {		
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/importacion";
		
	}
	
	@RequestMapping(value = "/descargarFichero/{nombre_fichero:.+}", method = RequestMethod.GET)
	public void descargaFichero(@PathVariable("nombre_fichero") String nombreFichero, HttpServletResponse response){
		
		 response.setHeader("Cache-Control", "max-age=0");
		 response.setHeader("Content-disposition", "attachment; filename="+nombreFichero);
		 
	     try {
	    	 
			File fichero = new File(CfgUtil.RUTA_FICHEROS+nombreFichero);
			
			InputStream is=new FileInputStream(fichero);
			 
			int read=0;
			byte[] bytes = new byte[BYTES_DOWNLOAD];
			OutputStream os = response.getOutputStream();
			while((read = is.read(bytes))!= -1){
			  os.write(bytes, 0, read);
			}
			os.flush();
			is.close();
			 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/eliminarFichero/{nombre_fichero:.+}", method = RequestMethod.GET)
	public String eliminarFichero(@PathVariable("nombre_fichero") String nombreFichero){

		String 	carpertaDocumento 	= CfgUtil.RUTA_FICHEROS;

			//Eliminar contenido de la carpeta
			File folder = new File(carpertaDocumento);
			
			File[] listOfFiles = folder.listFiles();
			
			for (File file : listOfFiles) {
			    if (file.isFile()) {
			    	if(file.getName().equals(nombreFichero)){			
			    		file.delete();	
			    	}
			    	
			    }
			}
			
		return "redirect:/importacion";
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/importarFichero", method = RequestMethod.GET)
	@ResponseBody
	public String importarFichero(@RequestParam("nombre_fichero") String fichero, @RequestParam("nombre_hoja") String hoja) throws InvocationTargetException{
		
		StringBuilder	cadenaErrores   = new StringBuilder();
		
		String 			rutaFichero 	= CfgUtil.RUTA_FICHEROS+fichero;
		
		List<Facturacion> listaFacturacion = new ArrayList<Facturacion>();
		
		try {

			InputStream ExcelFileToRead = new FileInputStream(rutaFichero);
			
			XSSFWorkbook 	wb   	= new XSSFWorkbook(ExcelFileToRead);
			
			XSSFSheet 		sheet   = wb.getSheet(hoja);
			
			XSSFRow   		row; 
			XSSFCell  		cell;

			Iterator rows = sheet.rowIterator();
			
			//Campos
			Integer	 	periodo					= null;
			String  	codigoAgencia			= null;
			String		codigoTercero			= null;
			String		codigoActividad			= null;
			String 		tipoTerceroPagador		= null;
			Double		fact_ventaAgencia		= null;
			Double		fact_compraAgencia		= null;
			Double 		fact_ventaAgenciaSAP	= null;
			
			Agencia		agencia 				= null;
			Tercero		tercero					= null;
			Actividad	actividad				= null;
			
			int i=0; //Numero fila
			
			while (rows.hasNext()){
				
				row = (XSSFRow) rows.next();
				
				Iterator cells = row.cellIterator();
				
				int j=0; //Numero de columna
					
				if(i>0){ //La primera fila contiene títulos
					
					while (cells.hasNext()){
							
						cell= (XSSFCell) cells.next();
						
						cell = row.getCell(j, Row.RETURN_BLANK_AS_NULL);
						
						if(row.getCell(0)!=null && row.getCell(0).toString().length()>0){
						
							if(j==1){								
								if(cell!=null && cell.getCellType()==0){
									periodo		 			= new Integer((int) cell.getNumericCellValue() + hoja);
								}									
							}
							if(j==3){	
								if(cell!=null){
									cell.setCellType(Cell.CELL_TYPE_STRING); 
									codigoAgencia 			= cell.getStringCellValue();	
								}									
							}
							if(j==4){
								if(cell!=null){
									cell.setCellType(Cell.CELL_TYPE_STRING); 
									codigoTercero 			= cell.getStringCellValue();	
								}																	
							}
							if(j==6){
								if(cell!=null){																													
									tipoTerceroPagador		= cell.getStringCellValue();												
								}
							}
							if(j==7){
								if(cell!=null){
									cell.setCellType(Cell.CELL_TYPE_STRING); 
									codigoActividad 		= cell.getStringCellValue();
								}
							}
							if(j==9){
								if(cell!=null && cell.getCellType()==0){										
									fact_ventaAgencia 		= cell.getNumericCellValue();
								}
							}
							if(j==15){						
								if(cell!=null && cell.getCellType()==0){
									fact_compraAgencia		= cell.getNumericCellValue();
								}
							}
							if(j==35){		
								if(cell!=null && cell.getCellType()==0){
									fact_ventaAgenciaSAP	= cell.getNumericCellValue();
								}
							}
														
							j++;
							
						}else{
							break;
						}						
					}
					
					//Verificación existencia de campos relacionados
					
					if(agenciaService.buscarCodigo(codigoAgencia)!=null){
						
						agencia =  agenciaService.buscarCodigo(codigoAgencia);
						
						if(terceroService.buscarCodigo(codigoTercero)!=null){
							
							tercero =  terceroService.buscarCodigo(codigoTercero);
							
							if(actividadService.buscarCodigo(codigoActividad)!=null){
								
								actividad =  actividadService.buscarCodigo(codigoActividad);
								
								if(facturacionService.buscarFacturacion(periodo, tercero, agencia, actividad)==null){
								
									if(tipoTerceroPagador.equals("HGROUPE")){
										
										Facturacion facturacion = new Facturacion(0, periodo, tercero, agencia, actividad, fact_ventaAgencia, fact_compraAgencia, fact_ventaAgenciaSAP, null, null, null, null, null, null);
										listaFacturacion.add(facturacion);	
									}
									
								}else{
									cadenaErrores.append("Línea "+i+". Ya existe el dato en la base de datos.\n");
								}
								
							}else{									
								cadenaErrores.append("Línea "+i+". Código de actividad "+codigoActividad+" desconocido.\n");									
							}
							
						}else{
							cadenaErrores.append("Línea "+i+". Código de tercero "+codigoTercero+" desconocido.\n");								
						}						
					}else{
						cadenaErrores.append("Línea "+i+". Código de agencia "+codigoAgencia+" desconocido.\n");							
					}

				}			
				
				i++;				
				
			}
						
		} catch (FileNotFoundException e) {
			e.printStackTrace();			
		} catch (IOException e) {			
			e.printStackTrace();
		} 

		if(cadenaErrores.length()==0){
			
			for(Facturacion f : listaFacturacion){
				facturacionService.guardar(f);
			}
			
			return "ok";	
		}else{
			return cadenaErrores.toString();
		}			
	}
	
	private String infoFicheros(){
		
		StringBuilder cadenaValores = new StringBuilder();
		
		try {
			cadenaValores.append("<table class='table table-striped table-hover table-bordered'><thead><tr class='info'>"
					+ "<th>Fichero</th><th>Hoja</th><th></th><th></th><th></th></tr></thead><tbody>");
			
			String directorio = (CfgUtil.RUTA_FICHEROS);
			
			File folder = new File(directorio);
			
			File[] listOfFiles = folder.listFiles();

			for (File file : listOfFiles) {
				
			    if (file.isFile()) {
			    		
		    		InputStream ExcelFileToRead = new FileInputStream(CfgUtil.RUTA_FICHEROS+file.getName());
		    		
		    		XSSFWorkbook 	wb   	= new XSSFWorkbook(ExcelFileToRead);
		    		
		    		cadenaValores.append("<tr><td style='padding-left: 7px;'><input value='"+file.getName()+"' class='transparente'/></td>");
		    		
		    		cadenaValores.append("<td style='padding-left: 7px;'><select class='form-control' id='selHoja_"+file.getName()+"'>");
		    		
		    		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
		    			cadenaValores.append("<option>"+wb.getSheetName(i)+"</option>");
		    		}
		    		
		    		cadenaValores.append("</select></td>");
		    		
		    		cadenaValores.append("<td style='width: 5%;'><a href='eliminarFichero/"+file.getName()+"' class='btn btn-default' ><span class='glyphicon glyphicon-trash'></span> </a></td>");
		    		cadenaValores.append("<td style='width: 5%;'><a href='descargarFichero/"+file.getName()+"' class='btn btn-default' ><span class='glyphicon glyphicon-download-alt'></span> </a></td>");    			    
		    		cadenaValores.append("<td style='width: 12%;'><a href='#' onclick='procesarFichero(this.name);' name='"+file.getName()+"' class='btn btn-success'> IMPORTAR</a></td>");
		    		
			    }
			}
			
			cadenaValores.append("</tbody></table>");
			
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		return cadenaValores.toString();
		
	}
	
}
