package net.gefco.controlclientes.controladores;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutController {
	
	  @RequestMapping(value="/salir")
	  public String invalidate(HttpSession session, Model model) {
	    
		  session.invalidate();
	    		  
		  model.asMap().clear();	  
	    
		  return "salir";
	  }
  
}