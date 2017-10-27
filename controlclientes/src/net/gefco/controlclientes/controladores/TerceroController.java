package net.gefco.controlclientes.controladores;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import net.gefco.controlclientes.modelo.Tercero;
import net.gefco.controlclientes.negocio.TerceroGrupoService;
import net.gefco.controlclientes.negocio.TerceroMarketLineService;
import net.gefco.controlclientes.negocio.TerceroService;
import net.gefco.controlclientes.negocio.TerceroTipoService;
import net.gefco.controlclientes.util.AbstractDataTable;
import net.gefco.controlclientes.util.DataTableColumn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("session")
@SessionAttributes("usuarioSesion")
public class TerceroController extends AbstractDataTable<Tercero, TerceroService> {
	
	@Autowired
	private TerceroService 				terceroService;
	
	@Autowired
	private TerceroGrupoService 		terceroGrupoService;
	
	@Autowired
	private TerceroMarketLineService 	terceroMarketLineService;
	
	@Autowired
	private TerceroTipoService 			terceroTipoService;

	@PostConstruct
	public void iniciarControler() {
		
		dt_service 			= terceroService;
		dt_paginaLista 		= "terceroLista";
		dt_orden			= "";
		dt_HQLfrom			= "Tercero t";
		
		dt_columnas = new LinkedHashMap<String, DataTableColumn>();
		
		dt_columnas.put("id",  			new DataTableColumn("Id", 			Integer.class, "t.id"));		
		dt_columnas.put("codigo", 		new DataTableColumn("Código", 		String.class,  "t.terc_codigo"));		
		dt_columnas.put("razonSocial", 	new DataTableColumn("Razón Social",	String.class,  "t.terc_razonSocial"));		
		dt_columnas.put("grupo", 		new DataTableColumn("Grupo", 		String.class,  "t.terceroGrupo.tegr_nombre"));		
		dt_columnas.put("tipo", 		new DataTableColumn("Tipo", 		String.class,  "t.terceroTipo.teti_nombre"));		
		dt_columnas.put("marketLine", 	new DataTableColumn("Market Line",	String.class,  "t.terceroMarketLine.teml_nombre"));		
		dt_columnas.put("maf", 			new DataTableColumn("MAF", 			String.class,  "(CASE WHEN (t.terc_Maf = true) THEN 'SI' ELSE 'NO' END)" ));		
		dt_columnas.put("noValido", 	new DataTableColumn("No vál.", 		String.class,  "(CASE WHEN (t.terc_noValido = true) THEN 'SI' ELSE 'NO' END)"));
		
		//No olvidar llamar a este método después de configurar las columnas.
		iniciarControllerAbstract();
		
	}
	
	@RequestMapping(value = "/terceroLista", method = RequestMethod.GET)
	public String lista(Model model, @ModelAttribute("tercero") Tercero tercero) 
			throws 	NoSuchMethodException, SecurityException, IllegalAccessException, 
					IllegalArgumentException, InvocationTargetException{
		
		filtrarLista(); //Prepara la lista y la guarda en la variable dt_lista
		
		model.addAttribute("textoBuscar", 		dt_textoBusqueda);
		model.addAttribute("paginaActual", 		dt_paginaActual);
		model.addAttribute("numeroPaginas", 	dt_numeroPaginas);
		model.addAttribute("listaTerceros", 	dt_lista);
		model.addAttribute("columnas", 			dt_columnas);
		model.addAttribute("orden", 			dt_orden);
		model.addAttribute("numeroRegistros", 	dt_totalRegistros);		
		
		return "terceroLista";
	}
	
	@RequestMapping(value = "/terceroListaMoverAPagina{param1}", method = RequestMethod.GET)
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
	
	@RequestMapping(value = "/terceroLista&orden={campoOrden}", method = RequestMethod.GET)
	private String ordenar(@PathVariable("campoOrden") String campoOrden){
		super.ordenarLista(campoOrden);
		
		return "redirect:/" + dt_paginaLista;
	}
	
	@RequestMapping(value = "/buscarTerceros", method = RequestMethod.POST)	
	public String buscar(Model model, @ModelAttribute("textoBuscar") String textoBuscar){
		
		super.buscar(textoBuscar);
		
		return "redirect:/" + dt_paginaLista;
	}
	
	@RequestMapping(value = "/terceroForm", method = RequestMethod.GET)
	public String mostrarFormulario(Model model, @RequestParam(value="idTercero",required=false) Integer idTercero) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
				
		Tercero tercero = new Tercero();
		
		if(idTercero!=null){

			tercero = terceroService.buscarId(idTercero);
			
		}
		
		model.addAttribute("tercero", tercero);	
		model.addAttribute("listaTercerosGrupo", terceroGrupoService.listado());
		model.addAttribute("listaTercerosMarketLine", terceroMarketLineService.listado());
		model.addAttribute("listaTercerosTipo", terceroTipoService.listado());
		
		return "terceroForm";
	}
	
	//Tanto para crear uno nuevo como para editar uno existente
	@RequestMapping(value = "/terceroForm", method = RequestMethod.POST, params="action=Aceptar")
	public String aceptar(Model model,@ModelAttribute("tercero") @Valid Tercero tercero, BindingResult result) throws InvocationTargetException {
		
		if(tercero.getTerceroGrupo().getId()==0){			
			FieldError error = new FieldError("tercero", "terceroGrupo.id", "Por favor, seleccione una opción");			
			result.addError(error);			
		}
		
		if(tercero.getTerceroMarketLine().getId()==0){			
			FieldError error = new FieldError("tercero", "terceroMarketLine.id", "Por favor, seleccione una opción");			
			result.addError(error);			
		}
		
		if(tercero.getTerceroTipo().getId()==0){			
			FieldError error = new FieldError("tercero", "terceroTipo.id", "Por favor, seleccione una opción");			
			result.addError(error);			
		}
		
		if(result.hasErrors()){			
			model.addAttribute("listaTercerosGrupo", terceroGrupoService.listado());			
			model.addAttribute("listaTercerosMarketLine", terceroMarketLineService.listado());
			model.addAttribute("listaTercerosTipo", terceroTipoService.listado());
			return "terceroForm";			
		}

		try{
				
			//Creación
			if(tercero.getId()==null || tercero.getId()==0){
			
				tercero.setId(0);	
				terceroService.guardar(tercero);				
				tercero = new Tercero();
                
			//Actualización	
			}else{				
				terceroService.actualizar(tercero);				
			}
            
			return "redirect:/terceroLista?success=true";
							                
         } catch (InvocationTargetException e) {
        	 
                FieldError error;
                if(e.getCause().toString().contains("key 2") || e.getCause().toString().contains("terc_codigo")){
                	error = new FieldError("tercero", "terc_codigo", "Código de tercero ya existente.");
                }else if(e.getCause().toString().contains("key 3") || e.getCause().toString().contains("terc_razonSocial")){
                	error = new FieldError("tercero", "terc_razonSocial", "Razón Social ya existente.");
                }else{
                	error = new FieldError("tercero", "terc_codigo", "error no controlado: " + e.getCause().getMessage());
                }
                
                result.addError(error);     
                
                model.addAttribute("listaTercerosGrupo", terceroGrupoService.listado());
    			model.addAttribute("listaTercerosMarketLine", terceroMarketLineService.listado());
    			model.addAttribute("listaTercerosTipo", terceroTipoService.listado());    
    			
                return "terceroForm";                
         } 
		
			
	}
	
	@RequestMapping(value = "/terceroLista&id={idTercero}/eliminar", method = RequestMethod.POST)
	@ResponseBody
	public String eliminarDeLista(@PathVariable("idTercero") Integer idTercero){
		
		try{
			Tercero tercero = terceroService.buscarId(idTercero);
			terceroService.eliminar(tercero);	
			return "ok";
			
		}catch(Exception e){			
			return "error";	
			
		}		
	}
	
	
	@RequestMapping(value = "/tercerosExcel", method = RequestMethod.GET)
    public ModelAndView descargarExcel() {
		List <Tercero> listaExcel = new ArrayList<Tercero>();
		
		//Aplicar filtro
		try {
			for(Tercero terc : terceroService.listadoOrdenado(dt_orden)){
				if(terc.toString().toUpperCase().contains(dt_textoBusqueda.toUpperCase())){					
					listaExcel.add(terc);
				}
			}
			
		} catch ( SecurityException | IllegalArgumentException	| InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
		
		return new ModelAndView("excelViewTerceros", "tercerosExcel", listaExcel);
    }
	
}