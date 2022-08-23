package com.bi.desafioferiados.model;

import java.util.ArrayList;

public class Anio {
	
	private ArrayList<String> feriados;
	private int feriadosEnSemana;
	private int diasLaborales;
	
	public Anio() {		
	}
	
	public Anio(ArrayList<String> feriados, int feriadosEnSemana, int diasLaborales) {
		this.feriados = feriados;
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
}
