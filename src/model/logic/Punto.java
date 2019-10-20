package model.logic;

public class Punto
{
	private long longitud;
	
	private long latitud;
	
	public Punto(long pLong, long pLatitud)
	{
		longitud = pLong;
		
		latitud = pLatitud;
	}

	public long getLongitud() {
		return longitud;
	}

	public void setLongitud(long longitud) {
		this.longitud = longitud;
	}

	public long getLatitud() {
		return latitud;
	}

	public void setLatitud(long latitud) {
		this.latitud = latitud;
	}
}
