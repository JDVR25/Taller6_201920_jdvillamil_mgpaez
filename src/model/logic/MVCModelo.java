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
import model.data_structures.MaxHeapCP;
import model.data_structures.Nodo;
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
	private ListaSencillamenteEncadenada<Viaje> horas;

	private ListaSencillamenteEncadenada<Viaje> dias;

	private ListaSencillamenteEncadenada<Viaje> meses;
	
	private ListaSencillamenteEncadenada<NodoMallaVial> nodos;
	
	private ListaSencillamenteEncadenada<Zona> zonas;
	//Para el 1A usar Tabla de hash separate chaining, que sea de tamano 27 para facilitar las cosas, la llave sera la letra inicial necesarios add set y getset
	//En el 2A Usar cola de prioridad para los nodos
	//Para el 3A usar arbloes binarios.

	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public MVCModelo()
	{
		horas = new ListaSencillamenteEncadenada<Viaje>();

		dias = new ListaSencillamenteEncadenada<Viaje>();

		meses = new ListaSencillamenteEncadenada<Viaje>();
		
		nodos = new ListaSencillamenteEncadenada<NodoMallaVial>();
		
		zonas = new ListaSencillamenteEncadenada<Zona>();

	}

	//Requerimiento funcional 1
	/**
	 * Carga los datos del trimestre. 
	 * @param trimestre. el numero del trimestre.
	 */
	public void cargarDatosCSV(int trimestre)
	{
		CSVReader reader = null;
		try
		{
			reader = new CSVReader(new FileReader("./data/bogota-cadastral-2018-" + trimestre + "-All-HourlyAggregate.csv"));
			for(String[] param : reader)
			{
				try
				{
					Viaje nuevo = new Viaje(Integer.parseInt(param[0]), Integer.parseInt(param[1]),
							Integer.parseInt(param[2]), Double.parseDouble(param[3]), Double.parseDouble(param[4]),
							Double.parseDouble(param[5]), Double.parseDouble(param[6]));
					horas.addLast(nuevo);
				}
				catch(NumberFormatException e)
				{

				}
			}

			reader = new CSVReader(new FileReader("./data/bogota-cadastral-2018-"+ trimestre + "-All-MonthlyAggregate.csv"));
			for(String[] param : reader)
			{
				try
				{
					Viaje nuevo = new Viaje(Integer.parseInt(param[0]), Integer.parseInt(param[1]),
							Integer.parseInt(param[2]), Double.parseDouble(param[3]), Double.parseDouble(param[4]),
							Double.parseDouble(param[5]), Double.parseDouble(param[6]));
					meses.addLast(nuevo);
				}
				catch(NumberFormatException e)
				{

				}
			}

			reader = new CSVReader(new FileReader("./data/bogota-cadastral-2018-"+ trimestre + "-All-WeeklyAggregate.csv"));
			for(String[] param : reader)
			{
				try
				{
					Viaje nuevo = new Viaje(Integer.parseInt(param[0]), Integer.parseInt(param[1]),
							Integer.parseInt(param[2]), Double.parseDouble(param[3]), Double.parseDouble(param[4]),
							Double.parseDouble(param[5]), Double.parseDouble(param[6]));
					dias.addLast(nuevo);
				}
				catch(NumberFormatException e)
				{

				}
			}

		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}

		}
	}
	
	public void cargarDatosNodos()
	{
		CSVReader reader = null;
		try
		{
			reader = new CSVReader(new FileReader("./data/Nodes_of_red_vial-wgs84_shp.txt"));
			for(String[] param : reader)
			{
				try
				{
					NodoMallaVial nuevo = new NodoMallaVial(Integer.parseInt(param[0]), Long.parseLong(param[1]), Long.parseLong(param[2]));
					nodos.addLast(nuevo);
				}
				catch(NumberFormatException e)
				{

				}
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}

		}
	}
	
	public void cargarDatosZonas()
	{
		
		String path = "./data/bogota_cadastral.json";
		try
		{
			zonas = readJsonStream(new FileInputStream(path));

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
		reader.endArray();
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
		Punto[] coordenadas = null;

		reader.beginObject();
		while (reader.hasNext())
		{
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
										coordenadas = (Punto[]) leerCoordenadas(reader).toArray();
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
				else if(name.equals("properties"))
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
			longitud = reader.nextLong();
			
			latitud = reader.nextLong();
		}
		reader.endArray();
		Punto coordenada = new Punto(longitud, latitud);
		return coordenada;
	}

	public int darNumViajesMes()
	{
		return meses.size();
	}

	public int darNumViajesHora()
	{
		return horas.size();
	}

	public int darNumViajesDia()
	{
		return dias.size();
	}
	
	public int darNumNodos()
	{
		return nodos.size();
	}
	
	public int darNumZonas()
	{
		return zonas.size();
	}
	
	//Parte A
	public ListaSencillamenteEncadenada<String> letrasMasFrecuentesNombreZona(int n)
	{
		return null;
	}
	
	public MaxHeapCP<NodoMallaVial> darNodosDelimitantesDeZona(double latitud, double longitud)
	{
		//TODO pedir aclaracion sobre lo que toca hacer el enunciado es confuso, otra vez
		return null;
	}
	
	public ListaSencillamenteEncadenada<Viaje> tiemposPrimerTrimestreDentroDeRango(double minimo, double maximo)
	{
		return null;
	}
	
	//Parte B
	//Nota el tipo de lo que retorna los metodos es sugerido, si se hacen cambios es posible que surjan errores en controller
	public ListaSencillamenteEncadenada<Zona> darZonasMasAlNorte(int n)
	{
		return null;
	}
	
	public MaxHeapCP<NodoMallaVial> darNodosMallaVial(double latitud, double longitud)
	{
		//TODO pedir aclaracion sobre lo que toca hacer el enunciado es confuso, otra vez
		return null;
	}
	
	public ListaSencillamenteEncadenada<Viaje> tiemposPrimerTrimestreConDesvEstandEnRango(double minimo, double maximo)
	{
		return null;
	}
	
	//Parte C
	public ListaSencillamenteEncadenada<Viaje> darTiemposZonaOrigenHora(int idOrigen, int hora)
	{
		return null;
	}
	
	public ListaSencillamenteEncadenada<Viaje> darTiemposZonaDestRangoHoras(int idOrigen, int horaMin, int horaMax)
	{
		return null;
	}
	
	public ListaSencillamenteEncadenada<Zona> zonasMasNodos(int n)
	{
		return null;
	}
	
	public ListaSencillamenteEncadenada<Double> datosFaltantesPrimerSemestre()
	{
		return null;
	}
}