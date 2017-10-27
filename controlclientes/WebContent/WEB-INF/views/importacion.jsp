<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix ="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="es">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <link rel="icon" href="res/img/ico/favicon.ico" type="image/x-icon" />
    
    <title>CONTROL CLIENTES - Gefco España S.A.</title>

    <link rel="stylesheet" href="res/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="res/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="res/css/sb-admin-2.min.css"/>      
    <link rel="stylesheet" href="res/css/metisMenu.min.css"/>
    <link rel="stylesheet" href="res/css/estilos.css"/>
    <link rel="stylesheet" href="res/css/dataTables.bootstrap.min.css"/>

  </head>

<body>

    <div id="wrapper">

		<jsp:include page="menu.jsp"/>

        <div id="page-wrapper">
        
            <div class="row">
                <div class="col-sm-12">
                    <h2 class="page-header derecha">Importación Ficheros</h2>
                </div>                                
           </div>
           
           <sf:form action="uploadFile" method="post" enctype="multipart/form-data">
		     
			      <div class="row text-right">
					
						<div class="col-sm-12">			        	
				        <input type="file" id="upload" name="upload" />				
							<input type="submit" class="btn btn-primary" value="SUBIR FICHERO">						
						</div>	
				        	
				  </div>
				  
				  <br/>
			    
		  		  <div id="contenidoCarpeta">
					
				  </div>
				  
				  			             
		   </sf:form> 	
		   	            
        </div>
    </div>

	<div id="modalFicheroImportacion" class="modal fade">
	  <div class="modal-dialog" id="divContenidoModal">
	
	  </div>
	</div>	

    <script type="text/javascript" src='<c:url value="/res/js/jquery-1.10.2.js" />' ></script>
	<script type="text/javascript" src='<c:url value="/res/js/bootstrap.min.js" />' ></script>    
	<script type="text/javascript" src='<c:url value="/res/js/sb-admin-2.js" />'></script>
    <script type="text/javascript" src='<c:url value="/res/js/metisMenu.min.js" />'></script>    
    <script type="text/javascript" src='<c:url value="/res/js/loading.js" />'></script>
    
    <script type="text/javascript">
    
	$(document).ready(function() {
		cargaAreaImportacion();		
	});
	
	function cargaAreaImportacion(){
		
		waitingDialog.show('Un momento, por favor...');
		
		$.ajax({
			type	 : "post",
			url      : "infoFicheros",
			data	 : {}, 				 				 				
			success  : function( resultado ) {				
							$("#contenidoCarpeta").html(resultado);
							waitingDialog.hide();
					   }								
		});
	
	}
	
	function procesarFichero(nombreFichero){
		
		var selector				= document.getElementById("selHoja_"+nombreFichero);
		var nombreHoja				= selector.options[selector.selectedIndex].value;
		
		if(isNaN(nombreHoja)==false){

			if(confirm("¿Desea importar la pestaña "+nombreHoja+ " del fichero "+nombreFichero+"?")){
				
				waitingDialog.show('Un momento, por favor...');
				
				$.ajax({
					
					type	 : "get",
					url      : "importarFichero",
					data     : {
									nombre_fichero	: nombreFichero,
									nombre_hoja		: nombreHoja
							   },	
				    cache    : false,
				}).done(function (data) {
					if(data=="ok"){									
						$(location).attr('href','facturacionLista');						
					}else{
						waitingDialog.hide();		
						alert(data);
					}
					
				}).fail(function (jqXHR, textStatus) {
				    console.log("Error: "+textStatus);
				});	
				
			}
			
		}else{
		
			alert("El nombre de la hoja debe ser únicamente el mes numérico.");
			
		}
		
	}
    
    </script>
    
        
</body>

</html>