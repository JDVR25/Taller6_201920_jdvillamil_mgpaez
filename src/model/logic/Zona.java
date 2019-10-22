package model.logic;

import model.data_structures.ListaSencillamenteEncadenada;

public class Zona
{
	private String nombre;
	
	private long perimetro;
	
	private long area;
	
	private int id;
	
	private ListaSencillamenteEncadenada<Punto> coordenadas;
	
	public Zona(String pNombre, long pPerimetro, long pArea, int pId, ListaSencillamenteEncadenada<Punto>  pCoord)
	{
		nombre = pNombre;
		
		perimetro = pPerimetro;
		
		area = pArea;
		
		id = pId;
		
		coordenadas = pCoord;
	}

	public ListaSencillamenteEncadenada<Punto>  getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(ListaSencillamenteEncadenada<Punto>  coordenadas) {
		this.coordenadas = coordenadas;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public long getPerimetro() {
		return perimetro;
	}

	public void setPerimetro(long perimetro) {
		this.perimetro = perimetro;
	}

	public long getArea() {
		return area;
	}

	public void setArea(long area) {
		this.area = area;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
