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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Main controller
 */
@RestController
public class MainController {
        
    @GetMapping("/feriados")
    public ArrayList<String> feriados(@RequestParam String anio) {
    	return getFeriados(Integer.parseInt(anio));
    }        
    
    private ArrayList<String> getFeriados(int anio) {    	
    	LocalDate fechaInicio = LocalDate.of(anio, 1, 1);
    	LocalDate fechaFinal = LocalDate.of(anio, 12, 31);
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
    
//    private void diaSemana() {
//    	Calendar c = Calendar.getInstance(); 
//    	try {
//    		c.setTime(new SimpleDateFormat("dd/MM/yyyy").parse("20/08/2022"));    	
//    	}catch(ParseException e) {
//    		c.setTime(new Date());
//    	}    	
//    	int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
//    	System.out.println(dayOfWeek);
//    }
//    
}