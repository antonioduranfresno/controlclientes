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
    <link rel="stylesheet" href="res/css/dataTables.bootstrap.min.css"/>

  </head>

<body>

    <div id="wrapper">

		<jsp:include page="menu.jsp"/>

        <div id="page-wrapper">
        
            <div class="row">
                <div class="col-sm-12">
                    <h2 class="page-header derecha">Terceros <small>(${numeroRegistros})</small>
                    
                    <a href="tercerosExcel" class="btn btn-success"><span class="glyphicon glyphicon-file"></span> Excel</a>
                    <a href="terceroForm" class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span> Nuevo</a>
                    </h2>
                </div>                                
           </div>
           
           <sf:form method="post" action="buscarTerceros">
           
           <div class="row" style="margin-bottom: 10px;">
				<div class="col-sm-8">
				
		           <a href="terceroListaMoverAPaginaPrimera" class="btn btn-default"><i class="glyphicon glyphicon-step-backward"></i></a>           
		           <a href="terceroListaMoverAPaginaAnterior" class="btn btn-default"><i class="glyphicon glyphicon-chevron-left"></i></a>
		           Página ${paginaActual} de ${numeroPaginas}
		           <a href="terceroListaMoverAPaginaSiguiente" class="btn btn-default"><i class="glyphicon glyphicon-chevron-right"></i></a>
		           <a href="terceroListaMoverAPaginaUltima" class="btn btn-default"><i class="glyphicon glyphicon-step-forward"></i></a>					
				
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
		   
		   <table id='tablaTerceros' class='table table-hover table-striped table-condensed table-bordered'>
						
				<thead>
					<tr class="info">						
						<th width="33%;">
							<a href="${encabezados['tercero'].hrefOrden}">${encabezados['tercero'].label} 
								<span class="${encabezados['tercero'].classOrden}"></span>								
							</a>
						</th>
						<th width="14%;">
							<a href="${encabezados['grupo'].hrefOrden}">${encabezados['grupo'].label} 
								<span class="${encabezados['grupo'].classOrden}"></span>								
							</a>
						
						</th>
						<th width="14%;">
							<a href="${encabezados['tipo'].hrefOrden}">${encabezados['tipo'].label} 
								<span class="${encabezados['tipo'].classOrden}"></span>								
							</a>
							
						</th>
						<th width="12%;">
							<a href="${encabezados['marketLine'].hrefOrden}">${encabezados['marketLine'].label} 
								<span class="${encabezados['marketLine'].classOrden}"></span>								
							</a>
							
						</th>
						<th width="7%;">
							<a href="${encabezados['maf'].hrefOrden}">${encabezados['maf'].label} 
								<span class="${encabezados['maf'].classOrden}"></span>								
							</a>
														
						</th>
						<th width="8%;">
							<a href="${encabezados['noValido'].hrefOrden}">${encabezados['noValido'].label} 
								<span class="${encabezados['noValido'].classOrden}"></span>								
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
							
				<c:forEach items="${listaTerceros}" var="c" varStatus="index">
			
					<tr>
						<td>${c.toStringCodigoTercero()}</td>
						<td>${c.terceroGrupo.tegr_nombre}</td>
						<td>${c.terceroTipo.teti_nombre}</td>
						<td>${c.terceroMarketLine.teml_nombre}</td>
						<td>${c.terc_Maf == true ? 'Sí' : 'No'}</td>
						<td>${c.terc_noValido == true ? 'Sí' : 'No'}</td>
						<td style="text-align: center;"><a href="terceroForm?idTercero=${c.id}" class="btn btn-default"><span class="glyphicon glyphicon-pencil"></span></a></td>
						<td style="text-align: center;"><a href="#" onclick="eliminar(${c.id},'tercero');" class="btn btn-default"><span class="glyphicon glyphicon-trash"></span></a></td>
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