package net.gefco.controlclientes.controladores;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import net.gefco.controlclientes.modelo.TerceroGrupo;
import net.gefco.controlclientes.negocio.TerceroGrupoService;
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
public class TerceroGrupoController extends AbstractDataTable<TerceroGrupo, TerceroGrupoService> {
	
	@Autowired
	private TerceroGrupoService 		terceroGrupoService;
	
	
	DecimalFormat 						format		= new DecimalFormat("#,###,###,##0.##");

	@PostConstruct
	public void iniciarControler() {
		
		service 		= terceroGrupoService;
		paginaLista 	= "terceroGrupoLista";
		orden			= "";
		
		encabezados = new HashMap<String, DataTableColumn>();
		encabezados.put("nombre", 		new DataTableColumn("Nombre", 		"tegr_nombre", 					paginaLista + "&orden=tegr_nombre",						""));
		
	}
	
	
	@RequestMapping(value = "/terceroGrupoLista", method = RequestMethod.GET)
	public String lista(Model model, @ModelAttribute("terceroGrupo") TerceroGrupo terceroGrupo) 
			throws 	NoSuchMethodException, SecurityException, IllegalAccessException, 
					IllegalArgumentException, InvocationTargetException{
						
		filtrarLista();
		model.addAttribute("textoBuscar", textoBusqueda);
		model.addAttribute("paginaActual", paginaActual);
		model.addAttribute("numeroPaginas", numeroPaginas);			
		model.addAttribute("lista", lista);
		model.addAttribute("orden", orden);
		model.addAttribute("numeroRegistros", format.format(totalRegistros));
		
		model.addAttribute("encabezados", encabezados);
		
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
		
		return "redirect:/" + paginaLista;
	}
	
	@RequestMapping(value = "/terceroGrupoLista&orden={campoOrden}", method = RequestMethod.GET)
	private String ordenar(@PathVariable("campoOrden") String campoOrden){
		super.ordenarLista(campoOrden);
		
		return "redirect:/" + paginaLista;
	}
	
	@RequestMapping(value = "/buscarTercerosGrupo", method = RequestMethod.POST)	
	public String buscar(Model model, @ModelAttribute("textoBuscar") String textoBuscar){
		
		super.buscar(textoBuscar);
		
		return "redirect:/" + paginaLista;
	}
	
	@RequestMapping(value = "/terceroGrupoForm", method = RequestMethod.GET)
	public String mostrarFormulario(Model model, @RequestParam(value="idTerceroGrupo",required=false) Integer idTerceroGrupo){
				
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
                                
                if(e.toString().contains("key 2")){
                	error = new FieldError("terceroGrupo", "tegr_nombre", "El nombre del grupo ya existente.");
                
                }else{
                	error = new FieldError("terceroGrupo", "tegr_nombre", "error no controlado: " + e.getMessage());
                }
                
                result.addError(error);     
                
                return "terceroForm";                
         } 
		
			
	}
	
	@RequestMapping(value = "/terceroGrupoLista&id={idTercero}/eliminar", method = RequestMethod.POST)
	@ResponseBody
	public String eliminarDeLista(@PathVariable("idTerceroGrupo") Integer idTerceroGrupo){
		
		TerceroGrupo terceroGrupo = terceroGrupoService.buscarId(idTerceroGrupo);
		
		try{
			terceroGrupoService.eliminar(terceroGrupo);	
			return "ok";			
		}catch(Exception e){			
			return "error";			
		}		
	}
	
	
	@RequestMapping(value = "/tercerosGrupoExcel", method = RequestMethod.GET)
    public ModelAndView descargarExcel() {
		List <TerceroGrupo> listaExcel = new ArrayList<TerceroGrupo>();
		
		//Aplicar filtro
		for(TerceroGrupo tg : terceroGrupoService.listadoOrdenado(orden)){
			if(tg.toString().toUpperCase().contains(textoBusqueda.toUpperCase())){					
				listaExcel.add(tg);
			}
		}
		
		return new ModelAndView("excelViewTerceros", "tercerosExcel", listaExcel);
    }
	
}