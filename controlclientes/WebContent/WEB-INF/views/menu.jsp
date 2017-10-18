<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<meta http-equiv="refresh" content="${pageContext.session.maxInactiveInterval};url='${pageContext.servletContext.contextPath}/salir'">

        <nav class="navbar navbar-default navbar-static-top" style="margin-bottom: 0">
            <div class="navbar-header">                
                <a class="navbar-brand" href="terceroLista">
                	<img src="res/img/bg/gefco.png">                	
                </a>
            </div>

            <ul class="nav navbar-top-links navbar-right" >            		
				<li><a href="salir" ><i class="fa fa-sign-out fa-lg"></i> SALIR</a></li>				
            </ul>
            

            <div class="navbar-default sidebar">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
                        <li class="sidebar-search">
                            <div class="input-group custom-search-form">
                                <input type="text" class="form-control" placeholder="Buscar">
                                <span class="input-group-btn">
                                <button class="btn btn-default" type="button">
                                    <i class="fa fa-search"></i>
                                </button>
                            </span>
                            </div>
                        </li>                    
                        
						<li>
                            <a href="rutaLista" ><i class="fa fa-money fa-fw"></i> FACTURACIÓN</a>
                        </li>
                        <li>
                            <a href="rutaLista" ><i class="fa fa-folder-open-o fa-fw"></i> FICHEROS</a>
                        </li>   
                        <li>
                            <a href="rutaLista" ><i class="fa fa-area-chart fa-fw"></i> INFORMES</a>
                        </li>      
                        
						<li>
                            <a href="#"><b>MAESTROS</b><span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level collapse in">                               
                                <li>
                                    <a href="terceroLista"><i class="fa fa-users fa-fw"></i> Terceros</a>
                                </li>
                                <li>
                                    <a href="terceroGrupoLista"><i class="fa fa-users fa-fw"></i> Grupos terceros</a>
                                </li>
                            </ul>                          
                        </li>    
                                   	                        
                    </ul>
                </div>
            </div>
        </nav>
        
