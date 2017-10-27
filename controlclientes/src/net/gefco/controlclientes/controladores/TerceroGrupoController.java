package net.gefco.controlclientes.controladores;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.gefco.controlclientes.modelo.TerceroGrupo;
import net.gefco.controlclientes.negocio.TerceroGrupoService;
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
public class TerceroGrupoController extends AbstractDataTable<TerceroGrupo, TerceroGrupoService> {
	
	@Autowired
	private TerceroGrupoService 		terceroGrupoService;
	
	@PostConstruct
	public void iniciarControler() {
		
		dt_service 			= terceroGrupoService;
		dt_paginaLista 		= "terceroGrupoLista";
		dt_orden			= "";
		dt_HQLfrom			= "TerceroGrupo tg";
				
		dt_columnas = new LinkedHashMap<String, DataTableColumn>();
		dt_columnas.put("id",  					new DataTableColumn("Id", 			Integer.class, 	"tg.id"));	
		dt_columnas.put("nombre", 				new DataTableColumn("Nombre", 		String.class,	"tg.tegr_nombre"));
		
		//No olvidar llamar a este método después de configurar las columnas.
		iniciarControllerAbstract();
		
	}
	
	
	@RequestMapping(value = "/terceroGrupoLista", method = RequestMethod.GET)
	public String lista(Model model, @ModelAttribute("terceroGrupo") TerceroGrupo terceroGrupo) 
			throws 	NoSuchMethodException, SecurityException, IllegalAccessException, 
					IllegalArgumentException, InvocationTargetException{
						
		filtrarLista();
		
		model.addAttribute("textoBuscar", 			dt_textoBusqueda);
		model.addAttribute("paginaActual", 			dt_paginaActual);
		model.addAttribute("numeroPaginas", 		dt_numeroPaginas);
		model.addAttribute("listaTercerosGrupo",	dt_lista);
		model.addAttribute("columnas", 				dt_columnas);
		model.addAttribute("orden", 				dt_orden);
		model.addAttribute("numeroRegistros", 		dt_totalRegistros);		
		
		return "terceroGrupoLista";
	}
	
	@RequestMapping(value = "/terceroGrupoListaMoverAPagina{param1}", method = RequestMethod.GET)
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
	
	@RequestMapping(value = "/terceroGrupoLista&orden={campoOrden}", method = RequestMethod.GET)
	private String ordenar(@PathVariable("campoOrden") String campoOrden){
		super.ordenarLista(campoOrden);
		
		return "redirect:/" + dt_paginaLista;
	}
	
	@RequestMapping(value = "/buscarTercerosGrupo", method = RequestMethod.POST)	
	public String buscar(Model model, @ModelAttribute("textoBuscar") String textoBuscar){
		
		super.buscar(textoBuscar);
		
		return "redirect:/" + dt_paginaLista;
	}
	
	@RequestMapping(value = "/terceroGrupoForm", method = RequestMethod.GET)
	public String mostrarFormulario(Model model, @RequestParam(value="idTerceroGrupo",required=false) Integer idTerceroGrupo) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
				
		TerceroGrupo terceroGrupo = new TerceroGrupo();
		
		if(idTerceroGrupo != null){

			terceroGrupo = terceroGrupoService.buscarId(idTerceroGrupo);
			
		}
		
		model.addAttribute("terceroGrupo", terceroGrupo);	
				
		return "terceroGrupoForm";
	}
	
	//Tanto para crear uno nuevo como para editar uno existente
	@RequestMapping(value = "/terceroGrupoForm", method = RequestMethod.POST, params="action=Aceptar")
	public String aceptar(Model model,@ModelAttribute("terceroGrupo") @Valid TerceroGrupo terceroGrupo, BindingResult result){
		
		if(result.hasErrors()){			
			return "terceroGrupoForm";			
		}

		try{
				
			//Creación
			if(terceroGrupo.getId()==null || terceroGrupo.getId()==0){
			
				terceroGrupo.setId(0);	
				terceroGrupoService.guardar(terceroGrupo);				
				terceroGrupo = new TerceroGrupo();
                
			//Actualización	
			}else{				
				terceroGrupoService.actualizar(terceroGrupo);				
			}
            
			return "redirect:/terceroGrupoLista?success=true";
							                
         } catch (Exception e) {
        	 
                FieldError error;
                                
                if (e.getClass().equals(DataIntegrityViolationException.class)){
                	error = new FieldError("terceroGrupo", "tegr_nombre", "El nombre del grupo ya existente.");
                
                }else{
                	error = new FieldError("terceroGrupo", "tegr_nombre", "error no controlado: " + e.getMessage());
                }
                
                result.addError(error);     
                
                return "terceroForm";                
         } 
		
			
	}
	
	@RequestMapping(value = "/terceroGrupoLista&id={idTerceroGrupo}/eliminar", method = RequestMethod.POST)
	@ResponseBody
	public String eliminarDeLista(@PathVariable("idTerceroGrupo") Integer idTerceroGrupo) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		TerceroGrupo terceroGrupo = terceroGrupoService.buscarId(idTerceroGrupo);
		
		try{
			terceroGrupoService.eliminar(terceroGrupo);	
			return "ok";			
		}catch(Exception e){			
			return "error";			
		}		
	}
	
	
//	@RequestMapping(value = "/tercerosGrupoExcel", method = RequestMethod.GET)
//    public ModelAndView descargarExcel() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//		List <TerceroGrupo> listaExcel = new ArrayList<TerceroGrupo>();
//		
//		//Aplicar filtro
//		for(TerceroGrupo tg : terceroGrupoService.listadoOrdenado(dt_orden)){
//			if(tg.toString().toUpperCase().contains(dt_textoBusqueda.toUpperCase())){					
//				listaExcel.add(tg);
//			}
//		}
//		
//		return new ModelAndView("excelViewTercerosGrupo", "tercerosGrupoExcel", listaExcel);
//    }
	
	@RequestMapping(value = "/tercerosGrupoExcel", method = RequestMethod.GET)
	@ResponseBody
	public void downloadExcel(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IOException, InvalidFormatException, IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException {
		
        String columnasAMostrar = "nombre";
        super.descargarExcel(request, response, "tercerosGrupo.xls", "Grupos", columnasAMostrar);
        
	}
	
}