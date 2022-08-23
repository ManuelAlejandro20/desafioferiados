package com.bi.desafioferiados.model;

import java.util.ArrayList;

/**
 * Clase año 
 * @author aleja
 *
 */
public class Anio {
	
	/**
	 * Feriados en formato String
	 */
	private ArrayList<String> feriados;
	/**
	 * Nombres de los dias de los feriados
	 */
	private ArrayList<String> dias;
	/**
	 * Cantidad de feriados que caen en día de semana
	 */
	private int feriadosEnSemana;
	/**
	 * Cantidad de dias laborales
	 */
	private int diasLaborales;	
	
	public Anio() {		
	}	

	public Anio(ArrayList<String> feriados, ArrayList<String> dias, int feriadosEnSemana, int diasLaborales) {
		super();
		this.feriados = feriados;
		this.dias = dias;
		this.feriadosEnSemana = feriadosEnSemana;
		this.diasLaborales = diasLaborales;
	}

	public ArrayList<String> getFeriados() {
		return feriados;
	}

	public void setFeriados(ArrayList<String> feriados) {
		this.feriados = feriados;
	}

	public int getFeriadosEnSemana() {
		return feriadosEnSemana;
	}

	public void setFeriadosEnSemana(int feriadosEnSemana) {
		this.feriadosEnSemana = feriadosEnSemana;
	}

	public int getDiasLaborales() {
		return diasLaborales;
	}

	public void setDiasLaborales(int diasLaborales) {
		this.diasLaborales = diasLaborales;
	}

	public ArrayList<String> getDias() {
		return dias;
	}

	public void setDias(ArrayList<String> dias) {
		this.dias = dias;
	}			
}