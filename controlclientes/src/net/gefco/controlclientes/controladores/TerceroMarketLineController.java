package net.gefco.controlclientes.controladores;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.gefco.controlclientes.modelo.TerceroMarketLine;
import net.gefco.controlclientes.negocio.TerceroMarketLineService;
import net.gefco.controlclientes.util.AbstractDataTable;
import net.gefco.controlclientes.util.DataTableColumn;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@Scope("session")
@SessionAttributes("usuarioSesion")
public class TerceroMarketLineController extends AbstractDataTable<TerceroMarketLine, TerceroMarketLineService> {
	
	@Autowired
	private TerceroMarketLineService 		terceroMarketLineService;
	

	@PostConstruct
	public void iniciarControler() {
		
		dt_service 			= terceroMarketLineService;
		dt_paginaLista 		= "terceroMarketLineLista";
		dt_orden			= "";
		dt_HQLfrom			= "TerceroMarketLine tml";
				
		dt_columnas = new LinkedHashMap<String, DataTableColumn>();
		dt_columnas.put("id",  					new DataTableColumn("Id", 			Integer.class, 	"tml.id"));	
		dt_columnas.put("nombre", 				new DataTableColumn("Nombre", 		String.class,	"tml.teml_nombre"));
		
		//No olvidar llamar a este método después de configurar las columnas.
		iniciarControllerAbstract();
		
	}
	
	
	@RequestMapping(value = "/terceroMarketLineLista", method = RequestMethod.GET)
	public String lista(Model model, @ModelAttribute("terceroMarketLine") TerceroMarketLine terceroMarketLine) 
			throws 	NoSuchMethodException, SecurityException, IllegalAccessException, 
					IllegalArgumentException, InvocationTargetException{
						
filtrarLista();
		
		model.addAttribute("textoBuscar", 				dt_textoBusqueda);
		model.addAttribute("paginaActual", 				dt_paginaActual);
		model.addAttribute("numeroPaginas", 			dt_numeroPaginas);
		model.addAttribute("listaTercerosMarketLine",	dt_lista);
		model.addAttribute("columnas", 					dt_columnas);
		model.addAttribute("orden", 					dt_orden);
		model.addAttribute("numeroRegistros", 			dt_totalRegistros);
		
		return "terceroMarketLineLista";
	}
	
	@RequestMapping(value = "/terceroMarketLineListaMoverAPagina{param1}", method = RequestMethod.GET)
	public String moverAPagina(@PathVariable(value = "param1") String param1){
		switch (param1) {
		case "Primera":
			irAPrimeraPagina();
			break;
		
		case "Anterior":
			super.irAPaginaAnterior();
			break;

		case "Ultima":		
			super.irAUltimaPagina();
			break;
		
		case "Siguiente":
			super.irAPaginaSiguiente();
			break;
		}	
		
		return "redirect:/" + dt_paginaLista;
	}
	
	@RequestMapping(value = "/terceroMarketLineLista&orden={campoOrden}", method = RequestMethod.GET)
	private String ordenar(@PathVariable("campoOrden") String campoOrden){
		super.ordenarLista(campoOrden);
		
		return "redirect:/" + dt_paginaLista;
	}
	
	@RequestMapping(value = "/buscarTercerosMarketLine", method = RequestMethod.POST)	
	public String buscar(Model model, @ModelAttribute("textoBuscar") String textoBuscar){
		
		super.buscar(textoBuscar);
		
		return "redirect:/" + dt_paginaLista;
	}
	
	@RequestMapping(value = "/terceroMarketLineForm", method = RequestMethod.GET)
	public String mostrarFormulario(Model model, @RequestParam(value="idTerceroMarketLine",required=false) Integer idTerceroMarketLine) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
				
		TerceroMarketLine terceroMarketLine = new TerceroMarketLine();
		
		if(idTerceroMarketLine != null){

			terceroMarketLine = terceroMarketLineService.buscarId(idTerceroMarketLine);
			
		}
		
		model.addAttribute("terceroMarketLine", terceroMarketLine);	
				
		return "terceroMarketLineForm";
	}
	
	//Tanto para crear uno nuevo como para editar uno existente
	@RequestMapping(value = "/terceroMarketLineForm", method = RequestMethod.POST, params="action=Aceptar")
	public String aceptar(Model model,@ModelAttribute("terceroMarketLine") @Valid TerceroMarketLine terceroMarketLine, BindingResult result){
		
		if(result.hasErrors()){			
			return "terceroMarketLineForm";			
		}

		try{
				
			//Creación
			if(terceroMarketLine.getId()==null || terceroMarketLine.getId()==0){
			
				terceroMarketLine.setId(0);	
				terceroMarketLineService.guardar(terceroMarketLine);				
				terceroMarketLine = new TerceroMarketLine();
                
			//Actualización	
			}else{				
				terceroMarketLineService.actualizar(terceroMarketLine);				
			}
            
			return "redirect:/terceroMarketLineLista?success=true";
							                
         } catch (Exception e) {
        	 
                FieldError error;
                                
                if (e.getClass().equals(DataIntegrityViolationException.class)){
                	error = new FieldError("terceroMarketLine", "teml_nombre", "El nombre del grupo ya existente.");
                
                }else{
                	error = new FieldError("terceroMarketLine", "teml_nombre", "error no controlado: " + e.getMessage());
                }
                
                result.addError(error);     
                
                return "terceroForm";                
         } 
		
			
	}
	
	@RequestMapping(value = "/terceroMarketLineLista&id={idTerceroMarketLine}/eliminar", method = RequestMethod.POST)
	@ResponseBody
	public String eliminarDeLista(@PathVariable("idTerceroMarketLine") Integer idTerceroMarketLine) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		TerceroMarketLine terceroMarketLine = terceroMarketLineService.buscarId(idTerceroMarketLine);
		
		try{
			terceroMarketLineService.eliminar(terceroMarketLine);	
			return "ok";			
		}catch(Exception e){			
			return "error";			
		}		
	}
	
	
//	@RequestMapping(value = "/tercerosMarketLineExcel", method = RequestMethod.GET)
//    public ModelAndView descargarExcel() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//		List <TerceroMarketLine> listaExcel = new ArrayList<TerceroMarketLine>();
//		
//		//Aplicar filtro
//		for(TerceroMarketLine tg : terceroMarketLineService.listadoOrdenado(dt_orden)){
//			if(tg.toString().toUpperCase().contains(dt_textoBusqueda.toUpperCase())){					
//				listaExcel.add(tg);
//			}
//		}
//		
//		return new ModelAndView("excelViewTercerosMarketLine", "tercerosMarketLineExcel", listaExcel);
//    }
	
	@RequestMapping(value = "/tercerosMarketLineExcel", method = RequestMethod.GET)
	@ResponseBody
	public void downloadExcel(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IOException, InvalidFormatException, IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException {
		
        String columnasAMostrar = "nombre";
        super.descargarExcel(request, response, "tercerosMarketLine.xls", "Market Line", columnasAMostrar);
        
	}
	
}