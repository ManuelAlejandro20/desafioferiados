function feriados() {
	var anio = document.getElementById('anioSelect').value;
	if(anio != ""){
		$.ajax({
			url: "/feriados",
			data: "anio=" + anio,
			type: "GET",
			success: function (data) {
			  $("#verFeriadosButton").removeAttr("disabled");
			  document.getElementById('modalTitle').innerHTML = "Dias feriados de " + anio;
			  document.getElementById('modalText').innerHTML = data;
			}
		});		
	}else{
		$("#verFeriadosButton").attr("disabled", "disabled");
	}	
}