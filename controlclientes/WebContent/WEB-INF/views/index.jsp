<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
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
          <a class="navbar-brand" href="#">CONTROL CLIENTES <small>v17.10.10</small></a>
          
        </div>
        
        	<div id="navbar" class="navbar-collapse collapse">

				<form class="navbar-form navbar-right">

					<div class="form-group">
						<input type="text" id="login" name="login" placeholder="Matrícula Gefco" class="form-control">
					</div>
					<div class="form-group">
						<input type="password" id="password" name="password" placeholder="Contraseña" class="form-control">
					</div>

					<button type="button" id="btnAcceso" class="btn btn-success" 
						data-loading-text="<i class='fa fa-circle-o-notch fa-spin'></i> Espere...">Acceder</button>
						
				</form>

			</div>
			
      </div>
    </nav>

    <div class="jumbotron">

      	<div class="container">

	        <div class="imagenFondo">
	                
	        </div>
	    
    	</div>
    	
    </div>
    
    <div class="container">

      <hr>

      <footer>
        <p>&copy; 2017 Gefco España S.A. - Departamento Informática</p>    
      </footer>
    </div> 

	<script type="text/javascript" src='<c:url value="/res/js/jquery-1.10.2.js" />' ></script>
	<script type="text/javascript" src='<c:url value="/res/js/bootstrap.min.js" />' ></script>    
	<script type="text/javascript" src='<c:url value="/res/js/sb-admin-2.js" />'></script>
    <script type="text/javascript" src='<c:url value="/res/js/metisMenu.min.js" />'></script>
	<script type="text/javascript" src='<c:url value="/res/js/acceso.js" />' charset="UTF-8"></script>
    
  </body>
</html>