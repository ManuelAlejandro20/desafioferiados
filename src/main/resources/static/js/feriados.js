//Función que se activa cada vez que se escoge un año. Si se escoge un año permitido se muestra el panel de lo contrario se oculta
//Se hace un llamado al servicio rest para obtener los datos del año y configurar el calendario junto con los textos correspondientes
function feriados() {
	const anio = $("#anioSelect").val();
	$('#datepicker-div').datepicker('destroy');
	$("#listaFeriados").empty();
	if(anio == ""){
		$("#panelContainer").hide();
		return;
	}		
	var ul = $('#listaFeriados');	
	$("#panelContainer").show();
	$("#buttonPanel").text('Días feriados y hábiles de ' + anio);	
	$.ajax({
		url: "/feriados",
		data: "anio=" + anio,
		type: "GET",
		success: function (data) {
			const feriados = data.feriados;
			const dias = data.dias;
			const feriadosEnSemana = data.feriadosEnSemana;
			const diasLaborales = data.diasLaborales;
			for(var i = 0; i < feriados.length; i++){
  				ul.append("<li class='list-group-item'>" + feriados[i] + " (" + dias[i] + ")" + "</li>");
  			}
			$('#datepicker-div').datepicker({      
				format: "dd/mm/yyyy",
				startDate: "01/01/" + anio,
				endDate: "31/12/" + anio,	
				maxViewMode: 0,
				language: "es",
				defaultViewDate: { year: parseInt(anio), month: 00, day: 01 },
			    beforeShowDay: function(date){		
			  		for(var i = 0; i < feriados.length; i++){			  			
			  			const dateSplit = feriados[i].split("/");
			  			const dia = parseInt(dateSplit[0]);
			  			const mes = parseInt(dateSplit[1]) - 1;
			  			const anioSplit = parseInt(dateSplit[2]);			  			  	
			          	if (date.getDate() == dia && date.getMonth() == mes && date.getFullYear() == anioSplit){
							return { classes: 'today' };					
						}  						    						
			  		}			
		        }
			});		
			$("#feriadosText").text('Hay ' + feriados.length + ' feriados en ' + anio);
			$("#feriadosSemanaText").text('Hay ' + feriadosEnSemana + ' feriados en días de semana');	
			$("#habilesText").text('Hay ' + diasLaborales + ' días laborales');	  			  			  			  				  		 
		}
	});	
		
}