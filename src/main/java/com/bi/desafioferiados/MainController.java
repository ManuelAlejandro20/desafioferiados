/*
 * Copyright (c) 2021 CMCORP
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * An intermediate form of license used by the X Consortium for X11 used the following wording:[16]
 *
 */
package com.bi.desafioferiados;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
 * Main controller
 */
@RestController
public class MainController {
        
    @GetMapping("/feriados")
    public Anio feriados(@RequestParam String anio) {
    	Anio year = new Anio();
    	LocalDate fechaInicio = LocalDate.of(Integer.parseInt(anio), 1, 1);
    	LocalDate fechaFinal = LocalDate.of(Integer.parseInt(anio), 12, 31);    	
    	ArrayList<String> feriados = getFeriados(fechaInicio, fechaFinal); 
//    	List<String> a = Arrays.asList(
//    	                                            "09/08/2022", 
//    	                                            "07/05/2022", 
//    	                                            "30/03/2022", 
//    	                                            "02/03/2022", 
//    	                                            "10/02/2022"
//    	                                          ); 
//    	ArrayList<String> feriados = new ArrayList<String>(a);
    	int feriadosDiaSemana = feriadosDiaSemana(feriados);
    	int diasLaboralesSinFinde = diasLaboralesSinFinde(fechaInicio, fechaFinal);
    	year.setFeriados(feriados);
    	year.setFeriadosEnSemana(feriadosDiaSemana);
    	year.setDiasLaborales(diasLaboralesSinFinde - feriadosDiaSemana);
    	return year;
    }        
    
    private ArrayList<String> getFeriados(LocalDate fechaInicio, LocalDate fechaFinal) {    	
    	int rand = numRandom();    	
    	ArrayList<String> listaFeriados = new ArrayList<String>();
    	String feriado = "";
    	for(int i = 0; i < rand; i++) {
    		while(true) {
    			feriado = randomDate(fechaInicio, fechaFinal);
    			if(!listaFeriados.contains(feriado)) {
    				listaFeriados.add(feriado);
    				break;
    			}
    		}    		
    	}
    	return listaFeriados;
    }
    
    private String randomDate(LocalDate fechaInicio, LocalDate fechaFinal) {    	
    	long dias = fechaInicio.until(fechaFinal, ChronoUnit.DAYS);
    	long diasRandom = ThreadLocalRandom.current().nextLong(dias + 1);
    	LocalDate randomDate = fechaInicio.plusDays(diasRandom);
    	return randomDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
    
    private int numRandom() {
    	Random rand = new Random();
    	int min = 5;
    	int max = 16;
    	return rand.nextInt((max - min) + 1) + min;    	    	
    }
    
    private int feriadosDiaSemana(ArrayList<String> feriados) {
    	int feriadosDiaSemana = 0;
    	int numeroDia;
    	String[] fecha;
    	LocalDate fechaLocalDate;
    	for(String s : feriados) {
    		fecha = s.split("/");
    		fechaLocalDate = LocalDate.of(Integer.parseInt(fecha[2]), Integer.parseInt(fecha[1]), Integer.parseInt(fecha[0]));
    		numeroDia = fechaLocalDate.getDayOfWeek().getValue();
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
    
    private int diasLaboralesSinFinde (LocalDate fechaInicio, LocalDate fechaFinal){
        Predicate<LocalDate> esFinde = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
        List<LocalDate> diasLaborales = fechaInicio.datesUntil(fechaFinal.plusDays(1))
                .filter(esFinde.negate())
                .collect(Collectors.toList());
        return diasLaborales.size();    	               
    }
    
}