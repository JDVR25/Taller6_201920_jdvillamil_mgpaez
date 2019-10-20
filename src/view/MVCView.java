package view;

import model.logic.MVCModelo;

public class MVCView 
{
	    /**
	     * Metodo constructor
	     */
	    public MVCView()
	    {
	    	
	    }
	    
		public void printMenu()
		{
			System.out.println("1. Cargar los viajes de un trimestre");
			System.out.println("2. Consultar el tiempo promedio de un viaje entre dos zonas durante algun mes");
			System.out.println("3. Consultar los viajes con mayor tiempo promedio para algun mes");
			System.out.println("4. Comparar tiempos entre una zona y un rango de zonas para algun mes");
			System.out.println("5. Consultar el tiempo promedio de un viaje entre dos zonas durante algun dia");
			System.out.println("6. Consultar los viajes con mayor tiempo promedio para algun dia");
			System.out.println("7. Comparar tiempos entre una zona y un rango de zonas para algun dia");
			System.out.println("8. Consultar viajes entre una zona de origen y una zona destino en alguna franja horaria");
			System.out.println("9. Consultar los viajes con mayor tiempo promedio para alguna hora");
			System.out.println("10. Generar grafica ASCII del tiempo de los viajes entre una zona de origen y una zona destino de todas las horas");
			System.out.println("11. Exit");
			System.out.println("Dar el numero de opcion a resolver, luego oprimir tecla Return: (e.g., 1):");
		}

		public void printMessage(String mensaje) {

			System.out.println(mensaje);
		}		
		
		public void printModelo(MVCModelo modelo)
		{
			// TODO implementar
		}
}
