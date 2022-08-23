package com.bi.desafioferiados;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bi.desafioferiados.model.Anio;

/**
 * Controlador principal
 */
@RestController
public class MainController {
        
	/**
	 * Servicio Rest que retorna el objeto año con todos sus atributos
	 * @param anio 
	 * @return objeto año
	 */
    @GetMapping("/feriados")
    public Anio feriados(@RequestParam String anio) {
    	Anio year = new Anio();
    	LocalDate fechaInicio = LocalDate.of(Integer.parseInt(anio), 1, 1);
    	LocalDate fechaFinal = LocalDate.of(Integer.parseInt(anio), 12, 31);    	
    	ArrayList<LocalDate> feriados = getFeriados(fechaInicio, fechaFinal);    
    	int feriadosDiaSemana = feriadosDiaSemana(feriados);
    	int diasLaboralesSinFinde = diasLaboralesSinFinde(fechaInicio, fechaFinal);
    	year.setFeriados(feriadosString(feriados));
    	year.setDias(getDias(feriados));
    	year.setFeriadosEnSemana(feriadosDiaSemana);
    	year.setDiasLaborales(diasLaboralesSinFinde - feriadosDiaSemana);
    	return year;
    }        
    
    /**
     * Función que agrega una cantidad aleatoria de fechas a un array (estas serán los feriados). 
     * No se admiten repeticiones al final se ordenan las fechas de menor a mayor
     * @param fechaInicio. Fecha inicial del rango
     * @param fechaFinal. Fecha final del rango
     * @return un array de LocalDate que contiene todos los feriados
     */
    private ArrayList<LocalDate> getFeriados(LocalDate fechaInicio, LocalDate fechaFinal) {    	
    	int rand = numRandom();    	
    	ArrayList<LocalDate> listaFeriados = new ArrayList<LocalDate>();
    	LocalDate feriado;
    	    	    	
    	for(int i = 0; i < rand; i++) {
    		while(true) {
    			feriado = randomDate(fechaInicio, fechaFinal);
    			if(!listaFeriados.stream().anyMatch(feriado::equals)) {
    				listaFeriados.add(feriado);
    				break;
    			}
    		}    		
    	}
    	Collections.sort(listaFeriados);
    	return listaFeriados;
    }
    
    /**
     * Función que devuelve una fecha aleatoria dada una fecha inicial
     * y una fecha final. La fecha final se convierte a long y despues se le suma
     * 1 para que el rango no excluya esta fecha
     * @param fechaInicio. Fecha inicial del rango
     * @param fechaFinal. Fecha final del rango
     * @return Una fecha aleatoria
     */
    private LocalDate randomDate(LocalDate fechaInicio, LocalDate fechaFinal) {    	
    	long dias = fechaInicio.until(fechaFinal, ChronoUnit.DAYS);
    	long diasRandom = ThreadLocalRandom.current().nextLong(dias + 1);
    	return fechaInicio.plusDays(diasRandom);
    }
    
    /**
     * Función que genera un número aleatorio entre 5 y 16, incluyendo 16.
     * @return un número aleatorio
     */
    private int numRandom() {
    	Random rand = new Random();
    	int min = 5;
    	int max = 16;
    	return rand.nextInt((max - min) + 1) + min;    	    	
    }
    
    /**
     * Convierte un array de Localdate en un array de String. 
     * @param feriados. Array que contiene los feriados en formato Localdate
     * @return un array que contiene los feriados en formato String
     */
    private ArrayList<String> feriadosString(ArrayList<LocalDate> feriados){
    	ArrayList<String> feriadosString = new ArrayList<String>();
    	for(LocalDate l : feriados) {
    		feriadosString.add(l.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    	}
    	return feriadosString;
    }
     
    /**
     * Calcula cuantos feriados del array son día de semana
     * @param feriados. Array de feriados.
     * @return la cantidad de feriados que caen en día de semana
     */
    private int feriadosDiaSemana(ArrayList<LocalDate> feriados) {
    	int feriadosDiaSemana = 0;
    	int numeroDia;
    	for(LocalDate l : feriados) {
    		numeroDia = l.getDayOfWeek().getValue();
    		switch(numeroDia) {
    			case 1:
    			case 2:
    			case 3:
    			case 4:
    			case 5:
    				feriadosDiaSemana++;
    				break;
    			default:
    				break;
    		}    			
    	}
    	return feriadosDiaSemana;
    }
    
    /**
     * Genera un array con los nombres de los dia de semana del array 
     * de feriados
     * @param feriados. Array de feriados en formato LocalDate
     * @return un array que contiene el nombre de los dias de semana
     */
    private ArrayList<String> getDias(ArrayList<LocalDate> feriados) {
    	ArrayList<String> dias = new ArrayList<String>();
    	int numeroDia;
    	for(LocalDate l : feriados) {
    		numeroDia = l.getDayOfWeek().getValue();
    		switch(numeroDia) {
    			case 1:
    				dias.add("Lunes");
    				break;
    			case 2:
    				dias.add("Martes");
    				break;
    			case 3:
    				dias.add("Miércoles");
    				break;
    			case 4:
    				dias.add("Jueves");
    				break;
    			case 5:
    				dias.add("Viernes");
    				break;
    			case 6:
    				dias.add("Sábado");
    				break;
    			case 7:
    				dias.add("Domingo");
    				break;
    			default:
    				break;
    		}    			
    	}
    	return dias;
    }    
    
    /**
     * Función que calcula la cantidad de dias en un año quitando los sabados y domingo
     * @param fechaInicio. Fecha inicial del rango
     * @param fechaFinal. Fecha final del rango
     * @return la cantidad de días en un año sin contar todos los sabados y domingo
     */
    private int diasLaboralesSinFinde (LocalDate fechaInicio, LocalDate fechaFinal){
        Predicate<LocalDate> esFinde = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
        List<LocalDate> diasLaborales = fechaInicio.datesUntil(fechaFinal.plusDays(1))
                .filter(esFinde.negate())
                .collect(Collectors.toList());
        return diasLaborales.size();    	               
    }
    
}