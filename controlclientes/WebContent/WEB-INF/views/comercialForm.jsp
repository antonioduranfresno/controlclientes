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
                    <h2 class="page-header">Comercial</h2>
                </div>                                
           </div> 
           
           <sf:form method="post" modelAttribute="comercial" >
					
				<sf:input type="hidden" path="id" value="${comercial.id}" />

				<div class="row">
					<div class="col-sm-3">
						<label>Código</label>
						<sf:errors path="come_codigo" class="label label-danger"></sf:errors>						
		        		<sf:input class="form-control" path="come_codigo" value="${comercial.come_codigo}" maxlength="6" />
					</div>
					<div class="col-sm-9">
						<label>Nombre</label>
						<sf:errors path="come_nombre" class="label label-danger"></sf:errors>						
		        		<sf:input class="form-control" path="come_nombre" value="${comercial.come_nombre}" maxlength="255" />
					</div>				
				</div>
							
				<br>			
							
				<div class="row">
					<div class="col-sm-3">
						<label>Agencia</label>
						<sf:errors path="agencia.id" class="label label-danger"></sf:errors>
						<sf:select class="form-control" id="selAgencia" path="agencia.id" value="${comercial.agencia.id}">
							<option value="0">Selección</option>
							<c:forEach items="${listaAgencias}" var="c" varStatus="index">
			        			<option value="${c.id}" ${c.id == comercial.agencia.id ? 'selected' : '' }>${c.toStringCodigoNombre()}</option>
			        		</c:forEach>
						</sf:select>
					</div>				

					<div class="col-sm-5">
						<label>Tipo</label>
						<sf:errors path="comercialTipo.id" class="label label-danger"></sf:errors>
						<sf:select class="form-control" id="selComercialTipo" path="comercialTipo.id" value="${comercial.comercialTipo.id}">
							<option value="0">Selección</option>
							<c:forEach items="${listaComercialesTipo}" var="c" varStatus="index">
			        			<option value="${c.id}" ${c.id == comercial.comercialTipo.id ? 'selected' : '' }>${c.toStringCodigoNombre()}</option>
			        		</c:forEach>
						</sf:select>
					</div>				
				
					<div class="col-sm-4">
						<label>Suivi ES</label>
						<sf:errors path="suiviEs.id" class="label label-danger"></sf:errors>
						<sf:select class="form-control" id="selSuiviEs" path="suiviEs.id" value="${comercial.suiviEs.id}">
							<option value="0">Selección</option>
							<c:forEach items="${listaSuiviEs}" var="c" varStatus="index">
			        			<option value="${c.id}" ${c.id == comercial.suiviEs.id ? 'selected' : '' }>${c.sues_nombre}</option>
			        		</c:forEach>
						</sf:select>
					</div>				
				</div>				
				
				<br>
				
				<div class="footer">      
				
					<div class="col-sm-10 derecha">			      		
			    		<input type="button" class="btn btn-danger" value="Eliminar" onclick='eliminar($("#id").val(),"comercial");'>
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