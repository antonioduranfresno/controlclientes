package net.gefco.controlclientes.controladores;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
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
	
	DecimalFormat 						format		= new DecimalFormat("#,###,###,##0.##");

	@PostConstruct
	public void iniciarControler() {
		
		service 		= terceroService;
		paginaLista 	= "terceroLista";
		orden			= "";
		
		encabezados = new HashMap<String, DataTableColumn>();
		encabezados.put("tercero", 		new DataTableColumn("Tercero", 		"terc_codigo", 					paginaLista + "&orden=terc_codigo",						""));
		encabezados.put("grupo", 		new DataTableColumn("Grupo", 		"terceroGrupo-tegr_nombre", 	paginaLista + "&orden=terceroGrupo-tegr_nombre",		""));
		encabezados.put("tipo", 		new DataTableColumn("Tipo", 		"terceroTipo-teti_nombre", 		paginaLista + "&orden=terceroTipo-teti_nombre",			""));
		encabezados.put("marketLine", 	new DataTableColumn("Market Line",	"terceroMarketLine-teml_nombre",paginaLista + "&orden=terceroMarketLine-teml_nombre",	""));
		encabezados.put("maf", 			new DataTableColumn("MAF", 			"terc_Maf", 					paginaLista + "&orden=terc_Maf",						""));
		encabezados.put("noValido", 	new DataTableColumn("No vál.", 		"terc_noValido", 				paginaLista + "&orden=terc_noValido",					""));
		
	}
	
	
	@RequestMapping(value = "/terceroLista", method = RequestMethod.GET)
	public String lista(Model model, @ModelAttribute("tercero") Tercero tercero) 
			throws 	NoSuchMethodException, SecurityException, IllegalAccessException, 
					IllegalArgumentException, InvocationTargetException{
						
		filtrarLista();
		model.addAttribute("textoBuscar", textoBusqueda);
		model.addAttribute("paginaActual", paginaActual);
		model.addAttribute("numeroPaginas", numeroPaginas);			
		model.addAttribute("listaTerceros", lista);
		model.addAttribute("orden", orden);
		model.addAttribute("numeroRegistros", format.format(totalRegistros));
		
		model.addAttribute("encabezados", encabezados);
		
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
		
		return "redirect:/" + paginaLista;
	}
	
	@RequestMapping(value = "/terceroLista&orden={campoOrden}", method = RequestMethod.GET)
	private String ordenar(@PathVariable("campoOrden") String campoOrden){
		super.ordenarLista(campoOrden);
		
		return "redirect:/" + paginaLista;
	}
	
	@RequestMapping(value = "/buscarTerceros", method = RequestMethod.POST)	
	public String buscar(Model model, @ModelAttribute("textoBuscar") String textoBuscar){
		
		super.buscar(textoBuscar);
		
		return "redirect:/" + paginaLista;
	}
	
	@RequestMapping(value = "/terceroForm", method = RequestMethod.GET)
	public String mostrarFormulario(Model model, @RequestParam(value="idTercero",required=false) Integer idTercero){
				
		Tercero tercero = new Tercero();
		
		if(idTercero!=null){

			tercero = terceroService.buscarId(idTercero);
			
		}
		
		model.addAttribute("tercero", tercero);	
		model.addAttribute("listaTercerosGrupo", terceroGrupoService.listarTercerosGrupo());
		model.addAttribute("listaTercerosMarketLine", terceroMarketLineService.listarTercerosMarketLine());
		model.addAttribute("listaTercerosTipo", terceroTipoService.listarTercerosTipo());
		
		return "terceroForm";
	}
	
	//Tanto para crear uno nuevo como para editar uno existente
	@RequestMapping(value = "/terceroForm", method = RequestMethod.POST, params="action=Aceptar")
	public String aceptar(Model model,@ModelAttribute("tercero") @Valid Tercero tercero, BindingResult result){
		
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
			model.addAttribute("listaTercerosGrupo", terceroGrupoService.listarTercerosGrupo());			
			model.addAttribute("listaTercerosMarketLine", terceroMarketLineService.listarTercerosMarketLine());
			model.addAttribute("listaTercerosTipo", terceroTipoService.listarTercerosTipo());
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
							                
         } catch (Exception e) {
        	 
                FieldError error;
                                
                if(e.toString().contains("key 2")){
                	error = new FieldError("tercero", "terc_codigo", "Código de tercero ya existente.");
                }else if(e.toString().contains("key 3")){
                	error = new FieldError("tercero", "terc_razonSocial", "Razón Social ya existente.");
                }else{
                	error = new FieldError("tercero", "terc_codigo", "error no controlado: " + e.getMessage());
                }
                
                result.addError(error);     
                
    			model.addAttribute("listaTercerosGrupo", terceroGrupoService.listarTercerosGrupo());			
    			model.addAttribute("listaTercerosMarketLine", terceroMarketLineService.listarTercerosMarketLine());
    			model.addAttribute("listaTercerosTipo", terceroTipoService.listarTercerosTipo());    
    			
                return "terceroForm";                
         } 
		
			
	}
	
	@RequestMapping(value = "/terceroLista&id={idTercero}/eliminar", method = RequestMethod.POST)
	@ResponseBody
	public String eliminarDeLista(@PathVariable("idTercero") Integer idTercero){
		
		Tercero tercero = terceroService.buscarId(idTercero);
		
		try{
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
		for(Tercero terc : terceroService.listadoOrdenado(orden)){
			if(terc.toString().toUpperCase().contains(textoBusqueda.toUpperCase())){					
				listaExcel.add(terc);
			}
		}
		
		return new ModelAndView("excelViewTerceros", "tercerosExcel", listaExcel);
    }
	
}