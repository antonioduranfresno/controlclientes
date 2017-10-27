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
                    <h2 class="page-header derecha">Grupos de Tercero <small>(<fmt:formatNumber type = "number" pattern="#,##0" value = "${numeroRegistros}" />) </small>
                    
                    <a href="tercerosGrupoExcel" class="btn btn-success"><span class="glyphicon glyphicon-file"></span> Excel</a>
                    <a href="terceroGrupoForm" class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span> Nuevo</a>
                    </h2>
                </div>                                
           </div>
           
           <sf:form method="post" action="buscarTercerosGrupo">
           
           <div class="row" style="margin-bottom: 10px;">
				<div class="col-sm-8">
				
		           <a href="terceroGrupoListaMoverAPaginaPrimera" class="btn btn-default"><i class="glyphicon glyphicon-step-backward"></i></a>           
		           <a href="terceroGrupoListaMoverAPaginaAnterior" class="btn btn-default"><i class="glyphicon glyphicon-chevron-left"></i></a>
		           Página ${paginaActual} de ${numeroPaginas}
		           <a href="terceroGrupoListaMoverAPaginaSiguiente" class="btn btn-default"><i class="glyphicon glyphicon-chevron-right"></i></a>
		           <a href="terceroGrupoListaMoverAPaginaUltima" class="btn btn-default"><i class="glyphicon glyphicon-step-forward"></i></a>					
				
				</div>
				<div class="col-sm-1" >
					<label style="float: right; margin: 6px -16px 0px 0px;">Buscar:</label>
				</div>
				<div class="col-sm-3">	
					<div class="input-group">
			           <input type="text" class="form-control" name="textoBuscar" value="${textoBuscar}"/>
			           <span class="input-group-btn">			             
			           	<button type="submit" class="btn btn-default" >
			           	<i class="fa fa-search"></i>
			           	</button>
			           </span>	
					</div>				 
				</div>
					
		   </div>
		   
		   </sf:form>
		   
		   <table id='tablaGrupoTerceros' class='table table-hover table-striped table-condensed table-bordered'>
						
				<thead>
					<tr class="info">						
						<th width="50%;">
							<a href="${columnas['nombre'].hrefOrden}">${columnas['nombre'].label} 
								<span class="${columnas['nombre'].classOrden}"></span>								
							</a>
						</th>
						
						<th width="6%;"></th>
						<th width="6%;"></th>
					</tr>
				</thead>
			
				<c:choose>
				    <c:when test="${param.success eq true}">
				        <div class="alert alert-success">Cambios realizados correctamente.</div>
				    </c:when>
				</c:choose>	
							
				<c:forEach items="${listaTercerosGrupo}" var="c" varStatus="index">
			
					<tr>
						<td>${c.nombre}</td>
						<td style="text-align: center;"><a href="terceroGrupoForm?idTerceroGrupo=${c.id}" class="btn btn-default"><span class="glyphicon glyphicon-pencil"></span></a></td>
						<td style="text-align: center;"><a href="#" onclick="eliminar(${c.id},'terceroGrupo');" class="btn btn-default"><span class="glyphicon glyphicon-trash"></span></a></td>
					</tr>
			
				</c:forEach>
			
		    </table>	     
		    		            
        </div>
    </div>

    <script type="text/javascript" src='<c:url value="/res/js/jquery-1.10.2.js" />' ></script>
	<script type="text/javascript" src='<c:url value="/res/js/bootstrap.min.js" />' ></script>    
	<script type="text/javascript" src='<c:url value="/res/js/sb-admin-2.js" />'></script>
    <script type="text/javascript" src='<c:url value="/res/js/metisMenu.min.js" />'></script>
    <script type="text/javascript" src='<c:url value="/res/js/jquery.dataTables.js" />'></script>
	<script type="text/javascript" src='<c:url value="/res/js/dataTables.bootstrap.js" />'></script>
    <script type="text/javascript" src='<c:url value="/res/js/loading.js" />'></script>
    <script type="text/javascript" src='<c:url value="/res/js/maestro.js" />'></script>
    
</body>

</html>