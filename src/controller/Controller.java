package controller;

import java.util.Scanner;

import com.sun.tracing.dtrace.ModuleName;

import model.data_structures.IEstructura;
import model.data_structures.ListaSencillamenteEncadenada;
import model.data_structures.Queue;
import model.data_structures.Stack;
import model.logic.MVCModelo;
import model.logic.Viaje;
import view.MVCView;

public class Controller {

	/* Instancia del Modelo*/
	private MVCModelo modelo;

	/* Instancia de la Vista*/
	private MVCView view;

	private boolean cargaRealizada;


	/**
	 * Crear la vista y el modelo del proyecto
	 * @param capacidad tamaNo inicial del arreglo
	 */
	public Controller ()
	{
		view = new MVCView();
		modelo = new MVCModelo();
		cargaRealizada = false;
	}

	public void run() 
	{
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		String dato = "";
		String respuesta = "";

		while( !fin ){
			view.printMenu();

			int option = lector.nextInt();
			switch(option){
			case 1:
				if(!cargaRealizada)
				{
					System.out.println("--------- \nSe cargaran los datos");
					modelo = new MVCModelo(); 
					modelo.cargarDatosCSV(1);
					modelo.cargarDatosCSV(2);
					modelo.cargarDatosNodos();
					modelo.cargarDatosZonas();
					System.out.println("--------- \nSe cargaran los datos: ");
					System.out.println("Datos cargados:");
					System.out.println("Total de viajes por mes: " + modelo.darNumViajesMes());
					System.out.println("Total de viajes por dias: " + modelo.darNumViajesDia());
					System.out.println("Total de viajes por horas: " + modelo.darNumViajesHora());
					System.out.println("Total de zonas: " + modelo.darNumZonas());
					System.out.println("Total de nodos: " + modelo.darNumNodos());
				}
				else
				{
					System.out.println("--------- \nLos datos ya se cargaron");
				}
				break;

			case 2:
				//TODO pendiente
				try
				{
					if(cargaRealizada)
					{
						System.out.println("--------- \nIngrese el mes");
						int mes = Integer.parseInt(lector.next());
						if(mes > 0 && mes <= 12)
						{
							//Sujeto a cambios segun respuesta del profesor por correo
							System.out.println("--------- \nIngrese el identificador de la zona de origen");
							int zonaOrigen = Integer.parseInt(lector.next());

							System.out.println("--------- \nIngrese el identificador de la zona de destino");
							int zonaDestino = Integer.parseInt(lector.next());

							Viaje elViaje = modelo.consultarViajeMes(mes, zonaOrigen, zonaDestino);
							if(elViaje != null)
							{
								System.out.println("Tiempo promedio : " + elViaje.darTiempoViaje());
								System.out.println("Desviacion estandar del tiempo promedio : " + elViaje.darTiempoViaje());
							}
							else
							{
								System.out.println("No hay informacion de los viajes que durante el mes " + mes + " salieron de la zona " + zonaOrigen + " y llegaron a la zona "+ zonaDestino);
							}
						}
						else
						{
							System.out.println("--------- \nEl mes debe ser en numero entre 1 y 12 ");
						}
					}
					else
					{
						System.out.println("--------- \nNo se han cargado los datos");
					}
				}
				catch (NumberFormatException e)
				{
					System.out.println("--------- \nDebe ingresar los datos como numeros enteros");
				}
				break;

			case 3:
				//TODO pendiente
				try
				{
					if(cargaRealizada)
					{
						System.out.println("--------- \nIngrese el mes");
						int mes = Integer.parseInt(lector.next());
						if(mes > 0 && mes <= 12)
						{
							System.out.println("--------- \nIngrese la cantidad de viajes que desea consultar");
							int cantidad = Integer.parseInt(lector.next());
							IEstructura<Viaje> masDemorados = modelo.viajesMayorTiempoMes(mes, cantidad);
							for(Viaje temp: masDemorados)
							{
								System.out.println("Zona de origen: " + temp.darIDOrigen() + " Zona destino: " + temp.darIdDestino() + " Tiempo Promedio: " + temp.darTiempoViaje() + " Desviacion Estandar: " + temp.darDesviacionTiempo());
							}
						}
						else
						{
							System.out.println("--------- \nEl mes debe ser en numero entre 1 y 12 ");
						}
					}
					else
					{
						System.out.println("--------- \nNo se han cargado los datos");
					}
				}
				catch (NumberFormatException e)
				{
					System.out.println("--------- \nDebe ingresar los datos como numeros enteros");
				}
				break;

			case 4: 
				//TODO pendiente
				try
				{
					if(cargaRealizada)
					{
						System.out.println("--------- \nIngrese el mes");
						int mes = Integer.parseInt(lector.next());
						if(mes > 0 && mes <= 12)
						{
							System.out.println("--------- \nIngrese el identificador de una zona");
							int zona1 = Integer.parseInt(lector.next());

							System.out.println("--------- \nIngrese el identificador de la zona menor del rango de zonas");
							int zonaMenor = Integer.parseInt(lector.next());

							System.out.println("--------- \nIngrese el identificador de la zona mayor del rango de zonas");
							int zonaMayor = Integer.parseInt(lector.next());
							if(zonaMenor <= zonaMayor)
							{
								if(zonaMenor < zonaMenorExistente)
								{
									while(zonaMenor < zonaMenorExistente)
									{
										System.out.println("No hay viajes de " + zona1 + " a " + zonaMenor + " vs No hay viajes de " + zonaMenor + " a " + zona1);
										zonaMenor++;
									}
								}
								Stack<Viaje>[] pilas = modelo.darViajesRangoZonasMes(mes, zona1, zonaMenor, zonaMayor < zonaMayorExistente?zonaMayor: zonaMayorExistente);
								Viaje zonaRango = null;
								Viaje rangoZona = null;
								while(!pilas[0].isEmpty() && !pilas[1].isEmpty() && zonaMenor <= zonaMayor)
								{
									zonaRango = pilas[0].pop();
									rangoZona = pilas[1].pop();

									String lineaZonaRango = "";
									String lineaRangoZona = "";
									if(zonaRango != null)
									{
										lineaZonaRango += zonaRango.darTiempoViaje() + " de " + zonaRango.darIDOrigen() + " a " + zonaRango.darIdDestino();
									}
									else
									{
										lineaZonaRango += "No hay viajes de " + zona1 + " a " + zonaMenor;
									}
									if(rangoZona != null)
									{
										lineaRangoZona += rangoZona.darTiempoViaje() + " de " + rangoZona.darIDOrigen() + " a " + rangoZona.darIdDestino();
									}
									else
									{
										lineaRangoZona += "No hay viajes de " + zonaMenor + " a " + zona1;
									}
									System.out.println(lineaZonaRango +  " vs " + lineaRangoZona);

									zonaMenor++;
								}
								if(zonaMayor > zonaMayorExistente)
								{
									int i = zonaMayorExistente;
									while(i < zonaMayor)
									{
										System.out.println("No hay viajes de " + zona1 + " a " + i + " vs No hay viajes de " + i + " a " + zona1);
										i++;
									}
								}
							}
							else
							{
								System.out.println("--------- \nLa zona menor debe ser menor a la zona mayor");
							}
						}
						else
						{
							System.out.println("--------- \nEl mes debe ser en numero entre 1 y 12 ");
						}
					}
					else
					{
						System.out.println("--------- \nNo se han cargado los datos");
					}
				}
				catch (NumberFormatException e)
				{
					System.out.println("--------- \nDebe ingresar los datos como numeros enteros");
				}
				break;

			case 5: 
				//TODO pendiente
				try
				{
					if(cargaRealizada)
					{
						//Sujeto a cambios segun respuesta del profesor por correo
						System.out.println("--------- \nIngrese el dia");
						int dia = Integer.parseInt(lector.next());
						if(dia > 0 && dia <= 7)
						{
							System.out.println("--------- \nIngrese el identificador de la zona de origen");
							int zonaOrigen = Integer.parseInt(lector.next());

							System.out.println("--------- \nIngrese el identificador de la zona de destino");
							int zonaDestino = Integer.parseInt(lector.next());

							Viaje elViaje = modelo.consultarViajeDia(dia, zonaOrigen, zonaDestino);
							if(elViaje != null)
							{
								System.out.println("Tiempo promedio : " + elViaje.darTiempoViaje());
								System.out.println("Desviacion estandar del tiempo promedio : " + elViaje.darTiempoViaje());
							}
							else
							{
								System.out.println("No hay informacion de los viajes que durante el dia " + dia + " salieron de la zona " + zonaOrigen + " y llegaron a la zona "+ zonaDestino);
							}
						}
						else
						{
							System.out.println("--------- \nEl dia debe ser en numero entre 1 y 7 ");
						}
					}
					else
					{
						System.out.println("--------- \nNo se han cargado los datos");
					}
				}
				catch (NumberFormatException e)
				{
					System.out.println("--------- \nDebe ingresar los datos como numeros enteros");
				}
				break;

			case 6: 
				//TODO pendiente
				try
				{
					if(cargaRealizada)
					{
						System.out.println("--------- \nIngrese el dia");
						int dia = Integer.parseInt(lector.next());
						if(dia > 0 && dia <= 7)
						{
							System.out.println("--------- \nIngrese la cantidad de viajes que desea consultar");
							int cantidad = Integer.parseInt(lector.next());
							IEstructura<Viaje> masDemorados = modelo.viajesMayorTiempoDia(dia, cantidad);
							for(Viaje temp: masDemorados)
							{
								System.out.println("Zona de origen: " + temp.darIDOrigen() + " Zona destino: " + temp.darIdDestino() + " Tiempo Promedio: " + temp.darTiempoViaje() + " Desviacion Estandar: " + temp.darDesviacionTiempo());
							}
						}
						else
						{
							System.out.println("--------- \nEl dia debe ser en numero entre 1 y 7 ");
						}
					}
					else
					{
						System.out.println("--------- \nNo se han cargado los datos");
					}
				}
				catch (NumberFormatException e)
				{
					System.out.println("--------- \nDebe ingresar los datos como numeros enteros");
				}
				break;

			case 7: 
				//TODO pendiente
				try
				{
					if(cargaRealizada)
					{
						System.out.println("--------- \nIngrese el dia");
						int dia = Integer.parseInt(lector.next());
						if(dia > 0 && dia <= 7)
						{
							System.out.println("--------- \nIngrese el identificador de una zona");
							int zona1 = Integer.parseInt(lector.next());

							System.out.println("--------- \nIngrese el identificador de la zona menor del rango de zonas");
							int zonaMenor = Integer.parseInt(lector.next());

							System.out.println("--------- \nIngrese el identificador de la zona mayor del rango de zonas");
							int zonaMayor = Integer.parseInt(lector.next());
							if(zonaMenor <= zonaMayor)
							{
								if(zonaMenor < zonaMenorExistente)
								{
									while(zonaMenor < zonaMenorExistente)
									{
										System.out.println("No hay viajes de " + zona1 + " a " + zonaMenor + " vs No hay viajes de " + zonaMenor + " a " + zona1);
										zonaMenor++;
									}
								}
								Stack<Viaje>[] pilas = modelo.darViajesRangoZonasDia(dia, zona1, zonaMenor, zonaMayor < zonaMayorExistente?zonaMayor: zonaMayorExistente);
								Viaje zonaRango = null;
								Viaje rangoZona = null;
								while(!pilas[0].isEmpty() && !pilas[1].isEmpty() && zonaMenor <= zonaMayor)
								{
									zonaRango = pilas[0].pop();
									rangoZona = pilas[1].pop();

									String lineaZonaRango = "";
									String lineaRangoZona = "";
									if(zonaRango != null)
									{
										lineaZonaRango += zonaRango.darTiempoViaje() + " de " + zonaRango.darIDOrigen() + " a " + zonaRango.darIdDestino();
									}
									else
									{
										lineaZonaRango += "No hay viajes de " + zona1 + " a " + zonaMenor;
									}
									if(rangoZona != null)
									{
										lineaRangoZona += rangoZona.darTiempoViaje() + " de " + rangoZona.darIDOrigen() + " a " + rangoZona.darIdDestino();
									}
									else
									{
										lineaRangoZona += "No hay viajes de " + zonaMenor + " a " + zona1;
									}
									System.out.println(lineaZonaRango +  " vs " + lineaRangoZona);

									zonaMenor++;
								}
								if(zonaMayor > zonaMayorExistente)
								{
									int i = zonaMayorExistente;
									while(i < zonaMayor)
									{
										System.out.println("No hay viajes de " + zona1 + " a " + i + " vs No hay viajes de " + i + " a " + zona1);
										i++;
									}
								}
							}
							else
							{
								System.out.println("--------- \nLa zona menor debe ser menor a la zona mayor");
							}
						}
						else
						{
							System.out.println("--------- \nEl dia debe ser en numero entre 1 y 7 ");
						}
					}
					else
					{
						System.out.println("--------- \nNo se han cargado los datos");
					}
				}
				catch (NumberFormatException e)
				{
					System.out.println("--------- \nDebe ingresar los datos como numeros enteros");
				}
				break;

			case 8: 
				//TODO pendiente
				try
				{
					if(cargaRealizada)
					{
						System.out.println("--------- \nIngrese la hora inicial de la franja horaria");
						int horaInicial = Integer.parseInt(lector.next());

						System.out.println("--------- \nIngrese la hora final de la franja horaria");
						int horaFinal = Integer.parseInt(lector.next());
						if(horaInicial >= 0 && horaInicial < 24 && horaFinal >= 0 && horaFinal < 24)
						{
							System.out.println("--------- \nIngrese el identificador de la zona de origen");
							int zonaOrigen = Integer.parseInt(lector.next());

							System.out.println("--------- \nIngrese el identificador de la zona de destino");
							int zonaDestino = Integer.parseInt(lector.next());
							if(zonaOrigen < zonaMenorExistente || zonaOrigen > zonaMayorExistente || zonaDestino < zonaMenorExistente || zonaDestino > zonaMayorExistente)
							{
								System.out.println("No hay informacion acerca de los viajes que empiezan en la zona de origen " + zonaOrigen + " y terminan en la zona destino " + zonaDestino);
							}
							else
							{
								IEstructura<Viaje> viajes = modelo.viajesFranjaHoraria(horaInicial, horaFinal, zonaOrigen, zonaDestino);
								for(Viaje temp: viajes)
								{
									if(temp != null)
									{
										System.out.println("--------- \nHora: " + temp.darHoraOMesODia());
										System.out.println("Tiempo promedio : " + temp.darTiempoViaje());
										System.out.println("Desviacion estandar del tiempo promedio : " + temp.darTiempoViaje());
									}
									else
									{
										System.out.println("No hay informacion de los viajes que durante la hora " + horaInicial + " salieron de la zona " + zonaOrigen + " y llegaron a la zona "+ zonaDestino);
									}
									horaInicial++;
								}
							}
						}
						else
						{
							System.out.println("--------- \nLas hora deben ser en numeros entre 0 y 23 ");
						}
					}
					else
					{
						System.out.println("--------- \nNo se han cargado los datos");
					}
				}
				catch (NumberFormatException e)
				{
					System.out.println("--------- \nDebe ingresar los datos como numeros enteros");
				}
				break;

			case 9: 
				//TODO pendiente
				try
				{
					if(cargaRealizada)
					{
						System.out.println("--------- \nIngrese la hora");
						int hora = Integer.parseInt(lector.next());
						if(hora >= 0 && hora < 24)
						{
							System.out.println("--------- \nIngrese la cantidad de viajes que desea consultar");
							int cantidad = Integer.parseInt(lector.next());
							IEstructura<Viaje> masDemorados = modelo.viajesMayorTiempoHora(hora, cantidad);
							for(Viaje temp: masDemorados)
							{
								System.out.println("Zona de origen: " + temp.darIDOrigen() + " Zona destino: " + temp.darIdDestino() + " Tiempo Promedio: " + temp.darTiempoViaje() + " Desviacion Estandar: " + temp.darDesviacionTiempo());
							}
						}
						else
						{
							System.out.println("--------- \nLa hora debe ser en numero entre 0 y 23 ");
						}
					}
					else
					{
						System.out.println("--------- \nNo se han cargado los datos");
					}
				}
				catch (NumberFormatException e)
				{
					System.out.println("--------- \nDebe ingresar los datos como numeros enteros");
				}
				break;

			case 10: 
				//TODO pendiente
				try
				{
					if(cargaRealizada)
					{
						System.out.println("--------- \nIngrese el identificador de la zona de origen");
						int zonaOrigen = Integer.parseInt(lector.next());

						System.out.println("--------- \nIngrese el identificador de la zona de destino");
						int zonaDestino = Integer.parseInt(lector.next());

						Queue<Viaje> viajes = modelo.viajesDeTodaHora(zonaOrigen, zonaDestino);
						int hora = 00;

						System.out.println("Aproximación en minutos de viajes entre zona origen y zona destino.");
						System.out.println("Trimestre " + trimestre + " del 2018 detallado por cada hora del día");
						System.out.println("Zona Origen: " + zonaOrigen);
						System.out.println("Zona Destino: " + zonaDestino);
						System.out.println("Hora|  # de minutos");
						while(!viajes.isEmpty())
						{
							Viaje temp = viajes.dequeue();
							String minutos = "";
							if(temp != null)
							{
								int segundos = (int) temp.darTiempoViaje();
								for(; segundos > 60; segundos -= 60)
								{
									minutos += "*";
								}
								if(segundos >= 30)
								{
									minutos += "*";
								}
								
							}
							else
							{
								minutos = "hora sin viajes";
							}
							System.out.println(hora + "  |  " + minutos);
							hora++;
						}
					}
					else
					{
						System.out.println("--------- \nNo se han cargado los datos");
					}
				}
				catch (NumberFormatException e)
				{
					System.out.println("--------- \nDebe ingresar los datos como numeros enteros");
				}
				break;

			case 11: 
				System.out.println("--------- \n Hasta pronto !! \n---------"); 
				lector.close();
				fin = true;
				break; 

			default: 
				System.out.println("--------- \n Opcion Invalida !! \n---------");
				break;
			}
		}

	}	
}
