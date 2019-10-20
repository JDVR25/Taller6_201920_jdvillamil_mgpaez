package model.data_structures;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Clase que unifica las propiedades de todos los árboles
 * @param <K> tipo del identificador de los elementos que se almacenarán en el árbol. El tipo de identificador debe ser comparable.
 * @param <T> tipo de los elementos que se almacenarán en el árbol.
 */
public abstract class Arbol <K extends Comparable<K>, T> implements Serializable
{

	/**
	 * Constante de serialización
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Nodo que representa la raíz del árbol
	 */
	//TODO 3.2.1 Declare el nodo raíz como raiz
	protected NodoAbstracto<K, T> raiz;

	/**
	 * Crea un árbol vacio
	 */
	public Arbol()
	{
		//TODO 3.2.2 complete según la documentación
		raiz = null;
	}

	/**
	 * Elimina todos los nodos del árbol
	 * pos: la raiz es nula
	 */
	public void vaciar() 
	{
		//TODO 3.2.3 complete según la documentación
		raiz = null;
	}

	/**
	 * Indica si el árbol contiene el identificador que llega por parámetro
	 * @param identificador el identificador buscado. identificador != null
	 * @return true si el árbol contiene el identificador o false en caso contrario
	 */
	public boolean contieneIdentificador(K identificador)
	{
		//TODO 3.2.4 complete según la documentación
		return raiz != null? raiz.contieneIdentificador(identificador): false;
	}

	/**
	 * Indica si el árbol contiene el elemento que llega por parámetro
	 * @param elemento el elemento buscado. elemento != null
	 * @return true si el árbol contiene el elemento o false en caso contrario
	 */
	public boolean contieneElemento(T elemento) 
	{
		//TODO 3.2.5 complete según la documentación
		return raiz != null? raiz.contieneElemento(elemento): false;
	}

	/**
	 * Devuelve una colección con los nodos del árbol
	 * @return una colección con los nodos del árbol o una colección vacía en caso que el árbol este vacío
	 */
	public Collection<NodoAbstracto<K, T>> darNodos() 
	{		
		//TODO 3.2.6 complete según la documentación
		Collection<NodoAbstracto<K, T>> lista = new ArrayList<NodoAbstracto<K, T>>();
		if(raiz != null)
			raiz.darNodos(lista);
		return lista;
	}

	/**
	 * Busca el elemento cuyo identificador llega por parámetro
	 * @param identificador el identificador del elemento buscado. identificador != null
	 * @return el elemento con el identificador buscado o null en caso que el identificador no exista en el árbol
	 */
	public T buscar(K identificador)
	{
		//TODO 3.2.7 complete según la documentación
		return raiz != null? raiz.buscar(identificador): null;
	}

	/**
	 * Indica si el árbol está vacío o no
	 * @return true en caso que el árbol este vacío o false en caso contrario
	 */
	public boolean estaVacio()
	{
		//TODO 3.2.8 complete según la documentación
		return raiz == null;
	}

	/**
	 * Devuelve un conjunto con los identificadores de los nodos del árbol
	 * @return un conjunto con los identificadores de los nodos o un conjunto vacío si el árbol no tiene elementos
	 */
	public Set<K> darIdentificadores() 
	{
		HashSet<K> ids = new HashSet<K>();
		//TODO 3.2.9 complete según la documentación
		if(raiz != null)
			raiz.darIdentificadores(ids);
		return ids;
	}


	/**
	 * Devuelve el peso (número de nodos) del árbol
	 * @return el peso del árbol
	 */
	public int darPeso() 
	{
		//TODO 3.2.10 complete según la documentación
		return raiz != null? raiz.darPeso(): 0;
	}

	/**
	 * Devuelve los elementos del árbol en una colección en pre-orden
	 * @return los elementos del árbol ordenados en pre-orden
	 */
	public Collection<T> darPreorden() 
	{
		//TODO 3.2.11 complete según la documentación
		Collection<T> lista = new ArrayList<T>();
		if(raiz != null)
			raiz.darPreorden(lista);
		return lista;
	}


	/**
	 * Devuelve los elementos del árbol en una colección en pos-orden
	 * @return los elementos del árbol ordenados en pos-orden
	 */
	public Collection<T> darPosorden() 
	{
		//TODO 3.2.12 complete según la documentación
		Collection<T> lista = new ArrayList<T>();
		if(raiz != null)
			raiz.darPosorden(lista);
		return lista;
	}

	/**
	 * Devuelve una colección de los nodos que se deben seguir para llegar al nodo con identificador que llega por parámetro
	 * @param id identificador del nodo al que se quiere construir el camino. id != null
	 * @return colección con los elementos que se deben seguir para llegar al nodo con el id por parámetro o una colección 
	 * vacía si el id no existe en el nodo 
	 */
	public Collection<T> darCamino(K id) 
	{
		//TODO 3.2.13 complete según la documentación
		Collection<T> lista = new ArrayList<T>();
		if(raiz != null && contieneIdentificador(id))
			raiz.darCamino(id, lista);
		return lista;
	}

	/**
	 * Devuelve la raiz del árbol
	 * @return raíz
	 */
	public NodoAbstracto<K, T> darRaiz()
	{
		//TODO 3.2.14 complete según la documentación
		return raiz;
	}

	/**
	 * Elimina el nodo cuyo identificador llega por parámetro
	 * @param identificador el identificador del nodo a eliminar. identificador != null
	 * @return colección con todos los elementos eliminados del árbol
	 * @throws NoSuchElementException Se lanza en caso que no exista un elemento con el identificador que llega como parámetro.
	 */
	public abstract void eliminar(K identificador) throws NoSuchElementException;

	//Basado en el ejercicio de nivel de apo2
}
