package model.data_structures;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Clase que unifica las propiedades de todos los �rboles
 * @param <K> tipo del identificador de los elementos que se almacenar�n en el �rbol. El tipo de identificador debe ser comparable.
 * @param <T> tipo de los elementos que se almacenar�n en el �rbol.
 */
public abstract class Arbol <K extends Comparable<K>, T> implements Serializable
{

	/**
	 * Constante de serializaci�n
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Nodo que representa la ra�z del �rbol
	 */
	//TODO 3.2.1 Declare el nodo ra�z como raiz
	protected NodoAbstracto<K, T> raiz;

	/**
	 * Crea un �rbol vacio
	 */
	public Arbol()
	{
		//TODO 3.2.2 complete seg�n la documentaci�n
		raiz = null;
	}

	/**
	 * Elimina todos los nodos del �rbol
	 * pos: la raiz es nula
	 */
	public void vaciar() 
	{
		//TODO 3.2.3 complete seg�n la documentaci�n
		raiz = null;
	}

	/**
	 * Indica si el �rbol contiene el identificador que llega por par�metro
	 * @param identificador el identificador buscado. identificador != null
	 * @return true si el �rbol contiene el identificador o false en caso contrario
	 */
	public boolean contieneIdentificador(K identificador)
	{
		//TODO 3.2.4 complete seg�n la documentaci�n
		return raiz != null? raiz.contieneIdentificador(identificador): false;
	}

	/**
	 * Indica si el �rbol contiene el elemento que llega por par�metro
	 * @param elemento el elemento buscado. elemento != null
	 * @return true si el �rbol contiene el elemento o false en caso contrario
	 */
	public boolean contieneElemento(T elemento) 
	{
		//TODO 3.2.5 complete seg�n la documentaci�n
		return raiz != null? raiz.contieneElemento(elemento): false;
	}

	/**
	 * Devuelve una colecci�n con los nodos del �rbol
	 * @return una colecci�n con los nodos del �rbol o una colecci�n vac�a en caso que el �rbol este vac�o
	 */
	public Collection<NodoAbstracto<K, T>> darNodos() 
	{		
		//TODO 3.2.6 complete seg�n la documentaci�n
		Collection<NodoAbstracto<K, T>> lista = new ArrayList<NodoAbstracto<K, T>>();
		if(raiz != null)
			raiz.darNodos(lista);
		return lista;
	}

	/**
	 * Busca el elemento cuyo identificador llega por par�metro
	 * @param identificador el identificador del elemento buscado. identificador != null
	 * @return el elemento con el identificador buscado o null en caso que el identificador no exista en el �rbol
	 */
	public T buscar(K identificador)
	{
		//TODO 3.2.7 complete seg�n la documentaci�n
		return raiz != null? raiz.buscar(identificador): null;
	}

	/**
	 * Indica si el �rbol est� vac�o o no
	 * @return true en caso que el �rbol este vac�o o false en caso contrario
	 */
	public boolean estaVacio()
	{
		//TODO 3.2.8 complete seg�n la documentaci�n
		return raiz == null;
	}

	/**
	 * Devuelve un conjunto con los identificadores de los nodos del �rbol
	 * @return un conjunto con los identificadores de los nodos o un conjunto vac�o si el �rbol no tiene elementos
	 */
	public Set<K> darIdentificadores() 
	{
		HashSet<K> ids = new HashSet<K>();
		//TODO 3.2.9 complete seg�n la documentaci�n
		if(raiz != null)
			raiz.darIdentificadores(ids);
		return ids;
	}


	/**
	 * Devuelve el peso (n�mero de nodos) del �rbol
	 * @return el peso del �rbol
	 */
	public int darPeso() 
	{
		//TODO 3.2.10 complete seg�n la documentaci�n
		return raiz != null? raiz.darPeso(): 0;
	}

	/**
	 * Devuelve los elementos del �rbol en una colecci�n en pre-orden
	 * @return los elementos del �rbol ordenados en pre-orden
	 */
	public Collection<T> darPreorden() 
	{
		//TODO 3.2.11 complete seg�n la documentaci�n
		Collection<T> lista = new ArrayList<T>();
		if(raiz != null)
			raiz.darPreorden(lista);
		return lista;
	}


	/**
	 * Devuelve los elementos del �rbol en una colecci�n en pos-orden
	 * @return los elementos del �rbol ordenados en pos-orden
	 */
	public Collection<T> darPosorden() 
	{
		//TODO 3.2.12 complete seg�n la documentaci�n
		Collection<T> lista = new ArrayList<T>();
		if(raiz != null)
			raiz.darPosorden(lista);
		return lista;
	}

	/**
	 * Devuelve una colecci�n de los nodos que se deben seguir para llegar al nodo con identificador que llega por par�metro
	 * @param id identificador del nodo al que se quiere construir el camino. id != null
	 * @return colecci�n con los elementos que se deben seguir para llegar al nodo con el id por par�metro o una colecci�n 
	 * vac�a si el id no existe en el nodo 
	 */
	public Collection<T> darCamino(K id) 
	{
		//TODO 3.2.13 complete seg�n la documentaci�n
		Collection<T> lista = new ArrayList<T>();
		if(raiz != null && contieneIdentificador(id))
			raiz.darCamino(id, lista);
		return lista;
	}

	/**
	 * Devuelve la raiz del �rbol
	 * @return ra�z
	 */
	public NodoAbstracto<K, T> darRaiz()
	{
		//TODO 3.2.14 complete seg�n la documentaci�n
		return raiz;
	}

	/**
	 * Elimina el nodo cuyo identificador llega por par�metro
	 * @param identificador el identificador del nodo a eliminar. identificador != null
	 * @return colecci�n con todos los elementos eliminados del �rbol
	 * @throws NoSuchElementException Se lanza en caso que no exista un elemento con el identificador que llega como par�metro.
	 */
	public abstract void eliminar(K identificador) throws NoSuchElementException;

	//Basado en el ejercicio de nivel de apo2
}
