function feriados() {
	const anio = $("#anioSelect").val();
	$('#datepicker-div').datepicker('destroy');
	$("#listaFeriados").empty();
	if(anio == ""){
		$("#panelContainer").hide();
		return;
	}		
	var ul = $('#listaFeriados')[0];	
	$("#panelContainer").show();
	$("#buttonPanel").text('Días feriados y hábiles de ' + anio);	
	$.ajax({
		url: "/feriados",
		data: "anio=" + anio,
		type: "GET",
		success: function (data) {
			const feriados = data.feriados;
			const feriadosEnSemana = data.feriadosEnSemana;
			const diasLaborales = data.diasLaborales;
			for(var i = 0; i < feriados.length; i++){
  				addRows(ul, feriados[i]);
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
						if(date.getDay() == 6 || date.getDay() == 0){
							return false;
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

function addRows(ul, feriado){
	var li = document.createElement("li");
	li.appendChild(document.createTextNode(feriado));
    li.setAttribute("class", "list-group-item"); 
    ul.appendChild(li);		
}
