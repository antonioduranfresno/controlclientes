<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<html lang="es">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">    
    <link rel="icon" href="res/img/ico/favicon.ico" type="image/x-icon" />

    <title>CONTROL CLIENTES - Gefco España S.A.</title>

    <link rel="stylesheet" href="res/css/bootstrap.min.css"/>    
    <link rel="stylesheet" href="res/css/font-awesome.min.css"/>        
    <link rel="stylesheet" href="res/css/estilos.css"/>

  </head>

  <body>

    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" >
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>          
        </div>
        
      </div>
    </nav>

    <div class="jumbotron">

      	<div class="container">

	        <div class="imagenAdios">
	                
	                <h2>La sesión ha terminado.</h2>
	                
	        </div>
	    
    	</div>
    	
    </div>
    
    <div class="container">

      <hr>

      <footer>
        <p>&copy; 2017 Gefco España S.A. - Departamento Informática
						
	  		<a href='${pageContext.request.contextPath}' class="pull-right">VOLVER A INICIO DE SESIÓN</a>        	
	        
	    </p>    
      </footer>
    </div> 
	    

  </body>
</html>