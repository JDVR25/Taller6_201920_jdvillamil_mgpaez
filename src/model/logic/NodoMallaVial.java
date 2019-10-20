package model.logic;

public class NodoMallaVial implements Comparable<NodoMallaVial>
{
	private int id;
	
	private Punto coordenada;

	public NodoMallaVial(int pId, long pLongitud, long pLatitud)
	{
		id = pId;
		
		coordenada = new Punto(pLongitud, pLatitud);
	}
	
	public int darId()
	{
		return id;
	}
	
	public Punto darCoordenada()
	{
		return coordenada;
	}
	
	//TODO pendiente
	@Override
	public int compareTo(NodoMallaVial o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
