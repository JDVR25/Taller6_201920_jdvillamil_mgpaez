package controller;

import java.util.Iterator;
import java.util.Scanner;

import com.sun.tracing.dtrace.ModuleName;

import model.data_structures.IEstructura;
import model.data_structures.ListaSencillamenteEncadenada;

import model.logic.MVCModelo;
import model.logic.Zona;
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
					modelo.cargarDatosZonas();
					System.out.println("--------- \nSe cargaran los datos: ");
					System.out.println("Datos cargados:");
					System.out.println("Total de zonas: " + modelo.darNumZonas());
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
						System.out.println("--------- \nIngrese el id");
						int id = Integer.parseInt(lector.next());
						Zona temp = modelo.darZonaID(id);
						if(temp != null)
						{
							System.out.println(temp.getNombre() + " " + temp.getPerimetro() + " " + temp.getArea() + " " + temp.getCoordenadas().size());
						}
						else
						{
							System.out.println("La zona con el id " + id + " no se encuentra");
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
						System.out.println("--------- \nIngrese el id menor del rango");
						int menor = Integer.parseInt(lector.next());

						System.out.println("--------- \nIngrese el id mayor del rango");
						int mayor = Integer.parseInt(lector.next());

						Iterator<Zona> zonas = modelo.darZonasRangoID(menor, mayor);
						while(zonas.hasNext())
						{
							Zona temp = zonas.next();
							System.out.println(temp.getId() + " " + temp.getNombre() + " " + temp.getPerimetro() + " " + temp.getArea() + " " + temp.getCoordenadas().size());
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
