package net.gefco.controlclientes.controladores;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.gefco.controlclientes.modelo.TerceroTipo;
import net.gefco.controlclientes.negocio.TerceroTipoService;
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
public class TerceroTipoController extends AbstractDataTable<TerceroTipo, TerceroTipoService> {
	
	@Autowired
	private TerceroTipoService 		terceroTipoService;
	
	@PostConstruct
	public void iniciarControler() {
		

		dt_service 			= terceroTipoService;
		dt_paginaLista 		= "terceroTipoLista";
		dt_orden			= "";
		dt_HQLfrom			= "TerceroTipo tt";
				
		dt_columnas = new LinkedHashMap<String, DataTableColumn>();
		dt_columnas.put("id",  					new DataTableColumn("Id", 			Integer.class, 	"tt.id"));	
		dt_columnas.put("nombre", 				new DataTableColumn("Nombre", 		String.class,	"tt.teti_nombre"));
		
		//No olvidar llamar a este método después de configurar las columnas.
		iniciarControllerAbstract();
		
	}
	
	
	@RequestMapping(value = "/terceroTipoLista", method = RequestMethod.GET)
	public String lista(Model model, @ModelAttribute("terceroTipo") TerceroTipo terceroTipo) 
			throws 	NoSuchMethodException, SecurityException, IllegalAccessException, 
					IllegalArgumentException, InvocationTargetException{
		
		filtrarLista(); //Prepara la lista y la guarda en la variable dt_lista
		
		model.addAttribute("textoBuscar", 				dt_textoBusqueda);
		model.addAttribute("paginaActual", 				dt_paginaActual);
		model.addAttribute("numeroPaginas", 			dt_numeroPaginas);
		model.addAttribute("listaTercerosTipo",			dt_lista);
		model.addAttribute("columnas", 					dt_columnas);
		model.addAttribute("orden", 					dt_orden);
		model.addAttribute("numeroRegistros", 			dt_totalRegistros);
		
		return "terceroTipoLista";
	}
	
	@RequestMapping(value = "/terceroTipoListaMoverAPagina{param1}", method = RequestMethod.GET)
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
	
	@RequestMapping(value = "/terceroTipoLista&orden={campoOrden}", method = RequestMethod.GET)
	private String ordenar(@PathVariable("campoOrden") String campoOrden){
		super.ordenarLista(campoOrden);
		
		return "redirect:/" + dt_paginaLista;
	}
	
	@RequestMapping(value = "/buscarTercerosTipo", method = RequestMethod.POST)	
	public String buscar(Model model, @ModelAttribute("textoBuscar") String textoBuscar){
		
		super.buscar(textoBuscar);
		
		return "redirect:/" + dt_paginaLista;
	}
	
	@RequestMapping(value = "/terceroTipoForm", method = RequestMethod.GET)
	public String mostrarFormulario(Model model, @RequestParam(value="idTerceroTipo",required=false) Integer idTerceroTipo) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
				
		TerceroTipo terceroTipo = new TerceroTipo();
		
		if(idTerceroTipo != null){

			terceroTipo = terceroTipoService.buscarId(idTerceroTipo);
			
		}
		
		model.addAttribute("terceroTipo", terceroTipo);	
				
		return "terceroTipoForm";
	}
	
	//Tanto para crear uno nuevo como para editar uno existente
	@RequestMapping(value = "/terceroTipoForm", method = RequestMethod.POST, params="action=Aceptar")
	public String aceptar(Model model,@ModelAttribute("terceroTipo") @Valid TerceroTipo terceroTipo, BindingResult result){
		
		if(result.hasErrors()){			
			return "terceroTipoForm";			
		}

		try{
				
			//Creación
			if(terceroTipo.getId()==null || terceroTipo.getId()==0){
			
				terceroTipo.setId(0);	
				terceroTipoService.guardar(terceroTipo);				
				terceroTipo = new TerceroTipo();
                
			//Actualización	
			}else{				
				terceroTipoService.actualizar(terceroTipo);				
			}
            
			return "redirect:/terceroTipoLista?success=true";
							                
         } catch (Exception e) {
        	 
                FieldError error;
                                
                if (e.getClass().equals(DataIntegrityViolationException.class)){
                	error = new FieldError("terceroTipo", "teti_nombre", "El nombre del grupo ya existente.");
                
                }else{
                	error = new FieldError("terceroTipo", "teti_nombre", "error no controlado: " + e.getMessage());
                }
                
                result.addError(error);     
                
                return "terceroForm";                
         } 
		
			
	}
	
	@RequestMapping(value = "/terceroTipoLista&id={idTerceroTipo}/eliminar", method = RequestMethod.POST)
	@ResponseBody
	public String eliminarDeLista(@PathVariable("idTerceroTipo") Integer idTerceroTipo) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		TerceroTipo terceroTipo = terceroTipoService.buscarId(idTerceroTipo);
		
		try{
			terceroTipoService.eliminar(terceroTipo);	
			return "ok";			
		}catch(Exception e){			
			return "error";			
		}		
	}
	
	
//	@RequestMapping(value = "/tercerosTipoExcel", method = RequestMethod.GET)
//    public ModelAndView descargarExcel() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//		List <TerceroTipo> listaExcel = new ArrayList<TerceroTipo>();
//		
//		//Aplicar filtro
//		for(TerceroTipo tg : terceroTipoService.listadoOrdenado(dt_orden)){
//			if(tg.toString().toUpperCase().contains(dt_textoBusqueda.toUpperCase())){					
//				listaExcel.add(tg);
//			}
//		}
//		
//		return new ModelAndView("excelViewTercerosTipo", "tercerosTipoExcel", listaExcel);
//    }
	
	@RequestMapping(value = "/tercerosTipoExcel", method = RequestMethod.GET)
	@ResponseBody
	public void downloadExcel(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IOException, InvalidFormatException, IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException {
		
        String columnasAMostrar = "nombre";
        super.descargarExcel(request, response, "tercerosTipo.xls", "Tipos", columnasAMostrar);
        
	}
	
}