package net.gefco.controlclientes.controladores;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import net.gefco.controlclientes.modelo.Tercero;
import net.gefco.controlclientes.negocio.TerceroGrupoService;
import net.gefco.controlclientes.negocio.TerceroMarketLineService;
import net.gefco.controlclientes.negocio.TerceroService;
import net.gefco.controlclientes.negocio.TerceroTipoService;

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

@Controller
@Scope("session")
@SessionAttributes("usuarioSesion")
public class TerceroController {
	
	@Autowired
	private TerceroService 				terceroService;
	
	@Autowired
	private TerceroGrupoService 		terceroGrupoService;
	
	@Autowired
	private TerceroMarketLineService 	terceroMarketLineService;
	
	@Autowired
	private TerceroTipoService 			terceroTipoService;
	
	//Paginación
    private int paginaActual			= 1;
    private int numeroPaginas			= 0;
    private int registrosPorPagina		= 50;
    
    private String textoBusqueda		= "";
    private String orden				= "terc_codigo ASC";
    		
	@RequestMapping(value = "/terceroLista", method = RequestMethod.GET)
	public String lista(Model model, @ModelAttribute("tercero") Tercero tercero){		
		
		List<Tercero> listaResultados	= new ArrayList<Tercero>();

		if(textoBusqueda.equals("")){
			
			listaResultados = terceroService.listarTercerosOrdenados(orden);
			
			if(listaResultados.size()%registrosPorPagina==0){
				numeroPaginas = listaResultados.size()/registrosPorPagina;
			}else{
				numeroPaginas = listaResultados.size()/registrosPorPagina + 1;
			}
			
			model.addAttribute("paginaActual", paginaActual);
			model.addAttribute("numeroPaginas", numeroPaginas);			
			model.addAttribute("listaTerceros", terceroService.listarTercerosPaginados((paginaActual - 1) * registrosPorPagina, registrosPorPagina, orden));
			
		}else{
			
			for(Tercero terc : terceroService.listarTercerosOrdenados(orden)){
				if(terc.toString().toUpperCase().contains(textoBusqueda.toUpperCase())){					
					listaResultados.add(terc);
				}
			}
			
			model.addAttribute("textoBuscar", textoBusqueda);
			model.addAttribute("paginaActual", 1);
			model.addAttribute("numeroPaginas", 1);			
			model.addAttribute("listaTerceros", listaResultados);
		}
		
		System.out.println(orden);
		
		model.addAttribute("orden", orden);
		
		return "terceroLista";
	}
	
	@RequestMapping(value = "/terceroListaPrimero", method = RequestMethod.GET)
	public String listaPrimero(){		
	
		paginaActual = 1;
		
		return "redirect:/terceroLista";
	}
	
	@RequestMapping(value = "/terceroListaUltimo", method = RequestMethod.GET)
	public String listaUltimo(){		
	
		paginaActual = numeroPaginas;
		
		return "redirect:/terceroLista";
	}
	
	@RequestMapping(value = "/terceroListaAnterior", method = RequestMethod.GET)
	public String listaAnterior(){		
	
		paginaActual = paginaActual-1;
		
        if (paginaActual < 1) {
        	paginaActual = 1;
        }

		return "redirect:/terceroLista";
	}
	
	@RequestMapping(value = "/terceroListaSiguiente", method = RequestMethod.GET)
	public String listaSiguiente(){		
	
		paginaActual = paginaActual+1;
		
        if (paginaActual > numeroPaginas) {
        	paginaActual = numeroPaginas;
        }
  
		return "redirect:/terceroLista";
	}
	
	@RequestMapping(value = "/terceroLista&orden={campoOrden}", method = RequestMethod.GET)
	private String ordenar(@PathVariable("campoOrden") String campoOrden){
		
		campoOrden = campoOrden.replace("-",".");
		
		if(orden.contains(campoOrden)){
			if(orden.contains("ASC")){
				orden = orden.replace("ASC", "DESC");	
			}else{
				orden = orden.replace("DESC", "ASC");
			}			
		}else{
			orden = campoOrden + " ASC"; //Ponemos guión y replace porque . no funciona en href	
		}
		
		return "redirect:/terceroLista";
	}
	
	@RequestMapping(value = "/buscarTerceros", method = RequestMethod.POST)	
	public String buscar(Model model, @ModelAttribute("textoBuscar") String textoBuscar){
		
		paginaActual  = 1;
		
		textoBusqueda = textoBuscar;
		
		return "redirect:/terceroLista";
	}
	
	@RequestMapping(value = "/terceroForm", method = RequestMethod.GET)
	public String mostrarFormulario(Model model, @RequestParam(value="idTercero",required=false) Integer idTercero){
				
		Tercero tercero = terceroService.buscarTercero(idTercero);
		
		if(idTercero!=null){
			model.addAttribute("tercero", tercero);	
		}else{
			model.addAttribute("tercero", new Tercero());
		}
		
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
		
		Tercero tercero = terceroService.buscarTercero(idTercero);
		
		try{
			terceroService.eliminar(tercero);	
			return "ok";			
		}catch(Exception e){			
			return "error";			
		}		
	}
	
	
}