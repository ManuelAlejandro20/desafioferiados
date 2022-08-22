function feriados() {
	var anio = document.getElementById('anioSelect').value;
	$('#datepicker-div').datepicker('destroy');
	$("#listaFeriados").empty();
	if(anio != ""){
		$.ajax({
			url: "/feriados",
			data: "anio=" + anio,
			type: "GET",
			success: function (data) {
			  $("#verFeriadosButton").removeAttr("disabled");
			  document.getElementById('modalTitle').innerHTML = "Dias feriados de " + anio;
			  var ul = document.getElementById("listaFeriados");
			  for(var i = 0; i < data.length; i++){
			  		addRows(ul, data[i]);
			  }			  
			  
			  $('#datepicker-div').datepicker({      
			  	format: "dd/mm/yyyy",
			    startDate: "01/01/" + anio,
			    endDate: "31/12/" + anio,	
				maxViewMode: 0,
			    language: "es",
			    defaultViewDate: { year: parseInt(anio), month: 00, day: 01 },
			    beforeShowDay: function(date){		
			  		for(var i = 0; i < data.length; i++){			  			
			  			var dateSplit = data[i].split("/");
			  			var dia = parseInt(dateSplit[0]);
			  			var mes = parseInt(dateSplit[1]) - 1;
			  			var anioSplit = parseInt(dateSplit[2]);			  			
			          	if (date.getDate() == dia && date.getMonth() == mes && date.getFullYear() == anioSplit)
							return {
								classes: 'today'
							};	          	
			  		}			
		        }			    
			  });			  
			  
			}
		});		
	}else{
		$("#verFeriadosButton").attr("disabled", "disabled");
	}	
}

function addRows(ul, feriado){
	var li = document.createElement("li");
	li.appendChild(document.createTextNode(feriado));
    li.setAttribute("class", "list-group-item"); 
    ul.appendChild(li);		
}
