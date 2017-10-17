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
    <link rel="stylesheet" href="res/css/sb-admin-2.min.css"/>      
    <link rel="stylesheet" href="res/css/metisMenu.min.css"/>
    <link rel="stylesheet" href="res/css/estilos.css"/>

  </head>

<body>

    <div id="wrapper">

		<jsp:include page="menu.jsp"/>

        <div id="page-wrapper">
        
            <div class="row">
                <div class="col-sm-12">
                    <h2 class="page-header">Tercero</h2>
                </div>                                
           </div> 
           
           <sf:form method="post" modelAttribute="tercero" >
					
				<sf:input type="hidden" path="id" value="${tercero.id}" />

				<div class="row">
					<div class="col-sm-4">
						<label>Código</label>
						<sf:errors path="terc_codigo" class="label label-danger"></sf:errors>						
		        		<sf:input class="form-control" path="terc_codigo" value="${tercero.terc_codigo}" maxlength="6" />
					</div>
					<div class="col-sm-8">
						<label>Razón Social</label>
						<sf:errors path="terc_razonSocial" class="label label-danger"></sf:errors>						
		        		<sf:input class="form-control" path="terc_razonSocial" value="${tercero.terc_razonSocial}" maxlength="255" />
					</div>				
				</div>
							
				<br>			
							
				<div class="row">
					<div class="col-sm-4">
						<label>Grupo</label>
						<sf:errors path="terceroGrupo.id" class="label label-danger"></sf:errors>
						<sf:select class="form-control" id="selTerceroGrupo" path="terceroGrupo.id" value="${tercero.terceroGrupo.id}">
							<option value="0">Selección</option>
							<c:forEach items="${listaTercerosGrupo}" var="c" varStatus="index">
			        			<option value="${c.id}" ${c.id == tercero.terceroGrupo.id ? 'selected' : '' }>${c.tegr_nombre}</option>
			        		</c:forEach>
						</sf:select>
					</div>				

					<div class="col-sm-4">
						<label>Market Line</label>
						<sf:errors path="terceroMarketLine.id" class="label label-danger"></sf:errors>
						<sf:select class="form-control" id="selTerceroMarketLine" path="terceroMarketLine.id" value="${tercero.terceroMarketLine.id}">
							<option value="0">Selección</option>
							<c:forEach items="${listaTercerosMarketLine}" var="c" varStatus="index">
			        			<option value="${c.id}" ${c.id == tercero.terceroMarketLine.id ? 'selected' : '' }>${c.teml_nombre}</option>
			        		</c:forEach>
						</sf:select>
					</div>				
				
					<div class="col-sm-4">
						<label>Tipo</label>
						<sf:errors path="terceroTipo.id" class="label label-danger"></sf:errors>
						<sf:select class="form-control" id="selTerceroTipo" path="terceroTipo.id" value="${tercero.terceroTipo.id}">
							<option value="0">Selección</option>
							<c:forEach items="${listaTercerosTipo}" var="c" varStatus="index">
			        			<option value="${c.id}" ${c.id == tercero.terceroTipo.id ? 'selected' : '' }>${c.teti_nombre}</option>
			        		</c:forEach>
						</sf:select>
					</div>				
				</div>				
				
				<br>
			
			   	<div class="row">
					<div class="col-sm-2">
		        		<label>Es MAF</label>		        		
		        		<sf:select class="form-control" path="terc_Maf" value="${tercero.terc_Maf}">
		        			<sf:option value="false">No</sf:option>		        		
		        			<sf:option value="true">Sí</sf:option>
		        		</sf:select>
		        	</div>		  
					<div class="col-sm-2">
		        		<label>No Válido</label>		        		
		        		<sf:select class="form-control" path="terc_noValido" value="${tercero.terc_noValido}">
		        			<sf:option value="false">No</sf:option>		        		
		        			<sf:option value="true">Sí</sf:option>
		        		</sf:select>
		        	</div>			        	      	
				</div>
				
				<br>
				
				<div class="footer">      
				
					<div class="col-sm-10 derecha">			      		
			    		<input type="button" class="btn btn-danger" value="Eliminar" onclick='eliminar($("#id").val(),"tercero");'>
			      	</div>      	
				 	<div class="col-sm-2 derecha">			      		
			      		<input type="submit" class="btn btn-primary" name="action" value="Aceptar" >
			      	</div>      	
			      	
			    </div>				
					
			</sf:form>
			            
        </div>
    </div>

    <script type="text/javascript" src='<c:url value="/res/js/jquery-1.10.2.js" />' ></script>
	<script type="text/javascript" src='<c:url value="/res/js/bootstrap.min.js" />' ></script>    
	<script type="text/javascript" src='<c:url value="/res/js/sb-admin-2.js" />'></script>
    <script type="text/javascript" src='<c:url value="/res/js/metisMenu.min.js" />'></script>    
    <script type="text/javascript" src='<c:url value="/res/js/loading.js" />'></script>
    <script type="text/javascript" src='<c:url value="/res/js/maestro.js" />'></script>
     
</body>

</html>