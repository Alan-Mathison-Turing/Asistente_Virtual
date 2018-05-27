package edu.unlam.asistente.conversor_unidades;

public abstract class Tiempo extends Unidad{
	
	public abstract double toSegundo(double numero);
	public abstract double toMinuto(double numero);
	public abstract double toHora(double numero);
	public abstract double toDia(double numero); 
}
