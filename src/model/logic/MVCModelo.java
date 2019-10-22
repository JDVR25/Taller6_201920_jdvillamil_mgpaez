package model.logic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.opencsv.CSVReader;
import model.data_structures.IEstructura;
import model.data_structures.ListaSencillamenteEncadenada;
import model.data_structures.Nodo;
import model.data_structures.RedBlackBST;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

/**
 * Definicion del modelo del mundo
 *
 */
public class MVCModelo 
{
	/**
	 * Atributos del modelo del mundo
	 */
	private RedBlackBST<Integer, Zona> zonas;
	
	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public MVCModelo()
	{

		
		zonas = new RedBlackBST<Integer, Zona>();

	}

	public void cargarDatosZonas()
	{
		ListaSencillamenteEncadenada<Zona> temp = new ListaSencillamenteEncadenada<Zona>();
		String path = "./data/bogota_cadastral.json";
		try
		{
			temp = readJsonStream(new FileInputStream(path));
			for(Zona zon: temp)
			{
				zonas.put(zon.getId(), zon);
			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public ListaSencillamenteEncadenada<Zona> readJsonStream(InputStream in) throws IOException
	{
		JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
		try
		{
			return leerTodo(reader);
		}
		finally
		{
			reader.close();
		}
	}
	
	public ListaSencillamenteEncadenada<Zona> leerTodo(JsonReader reader) throws IOException
	{
		ListaSencillamenteEncadenada<Zona> zonas = new ListaSencillamenteEncadenada<Zona>();

		reader.beginObject();
		while(reader.hasNext())
		{
			String name = reader.nextName();
			if (name.equals("type") && reader.nextString().equals("FeatureCollection"))
			{
				name = reader.nextName();
				if(name.equals("features"))
				{
					zonas = leerZonas(reader);
				}
			}
			else
			{
				reader.skipValue();
			}
		}
		reader.endObject();
		return zonas;
	}
	
	public ListaSencillamenteEncadenada<Zona> leerZonas(JsonReader reader) throws IOException
	{
		ListaSencillamenteEncadenada<Zona> zonas = new ListaSencillamenteEncadenada<Zona>();

		reader.beginArray();
		while(reader.hasNext())
		{
			zonas.addLast(leerZona(reader));
		}
		reader.endArray();
		return zonas;
	}

	public Zona leerZona(JsonReader reader) throws IOException
	{
		int id = -1;
		String nombreZona = null;
		long perimetro = -1;
		long area = -1;
		ListaSencillamenteEncadenada<Punto> coordenadas = null;

		reader.beginObject();
		while (reader.hasNext())
		{
			reader.beginObject();
			String name = reader.nextName();
			if (name.equals("type") && reader.nextString().equals("Feature"))
			{
				name = reader.nextName();
				if(name.equals("geometry"))
				{
					reader.beginObject();
					while (reader.hasNext())
					{
						name = reader.nextName();
						if (name.equals("type") && reader.nextString().equals("MultiPolygon"))
						{
							name = reader.nextName();
							if(name.equals("coordinates"))
							{
								reader.beginArray();
								while (reader.hasNext())
								{
									reader.beginArray();
									while (reader.hasNext())
									{
										coordenadas = leerCoordenadas(reader);
									}
									reader.endArray();
								}
								reader.endArray();
							}
						}
						else
						{
							reader.skipValue();
						}
					}
					reader.endObject();
				}
				if(name.equals("properties"))
				{
					name = reader.nextName();
					if(name.equals("MOVEMENT_ID"))
					{
						id = Integer.parseInt(reader.nextString());
					}
					else if(name.equals("scanombre"))
					{
						nombreZona = reader.nextString();
					}
					else if(name.equals("shape_leng"))
					{
						perimetro = reader.nextLong();
					}
					else if(name.equals("shape_area"))
					{
						area = reader.nextLong();
					}
					else
					{
						reader.skipValue();
					}
				}
				else
				{
					reader.skipValue();
				}
			}
			else
			{
				reader.skipValue();
			}
		}
		reader.endObject();
		Zona zona = new Zona(nombreZona, perimetro, area, id, coordenadas);
		return zona;
	}
	
	public ListaSencillamenteEncadenada<Punto> leerCoordenadas(JsonReader reader) throws IOException
	{
		ListaSencillamenteEncadenada<Punto> coordenadas = new ListaSencillamenteEncadenada<Punto>();

		reader.beginArray();
		while(reader.hasNext())
		{
			coordenadas.addLast(leerCoordenada(reader));
		}
		reader.endArray();
		return coordenadas;
	}
	
	public Punto leerCoordenada(JsonReader reader) throws IOException
	{
		long longitud = -1;
		
		long latitud = -1;
		
		reader.beginArray();
		while(reader.hasNext())
		{
			longitud = (long) reader.nextDouble();
			
			latitud = (long) reader.nextDouble();
		}
		reader.endArray();
		Punto coordenada = new Punto(longitud, latitud);
		return coordenada;
	}

	public int darNumZonas()
	{
		return zonas.size();
	}
	
	public int idMin()
	{
		return zonas.min();
	}
	
	public int idMax()
	{
		return zonas.max();
	}
	
	public Zona darZonaID(int pId)
	{
		return zonas.get(pId);
	}
	
	public Iterator<Zona> darZonasRangoID(int lo, int hi)
	{
		return zonas.valuesInRange(lo, hi);
	}
}