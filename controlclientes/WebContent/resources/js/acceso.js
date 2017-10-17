$(document).ready(function() {
	
	//Foco en login
	$("#login").focus();
	
	//Asociar el evento enter a la caja password, para no tener que hacer click en el boton
	$("#password").keyup(function(event){
	    if(event.keyCode == 13){
	        $("#btnAcceso").click();
	    }
	});
		
	//LOGIN
	$('#btnAcceso').click(function(event){
				
		event.preventDefault();
		
		var valorLogin  	= $('#login').val();
		var valorPass   	= $('#password').val();
		
		var $this = $(this);
		$this.button('loading');

        $.ajax({
        	url		 : 'login',        		
    		type	 : "post",
    		async	 : true,        		
    		data     : 'login='+valorLogin+'&password='+valorPass,
    		dataType : "json",
    		success  : function( data, textStatus, jqXHR) {
    			
    					if(data.mensaje!=null){					        				
    						alert(data.mensaje); //Error
    						$this.button('reset');
    						$("#login").focus();
    					}else{        						
    						$(location).attr('href','terceroLista');    						
    					}
    					
    		}
        });	
        event.stopImmediatePropagation();
		
	});
	
});
