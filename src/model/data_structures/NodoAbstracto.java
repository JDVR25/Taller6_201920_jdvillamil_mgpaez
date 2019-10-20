package model.data_structures;

import java.io.Serializable;
import java.util.Collection;

/**
 * Clase que unifica las propiedades comunes de todos los nodos
 * @param <K> tipo del identificador del elemento que se almacenar� en el nodo. El identificador debe poder ser comparable
 * @param <T> Tipo del elemento que almacenar� en el nodo
 */
public abstract class NodoAbstracto<K extends Comparable<K>, T> implements Serializable
{

	/**
	 * Constante de serializaci�n.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador del nodo
	 */
	//TODO 3.1.1 Declare el atributo identificador
	protected K identificador;

	/**
	 * Elemento que almacena el nodo
	 */
	//TODO 3.1.2 Declare el atrivuto elemento
	protected T elemento;

	/**
	 * Crea un nuevo nodo a partir del elemento que llega por par�metro
	 * @param identificador Identificador del elemento que se almacenar� en el nodo. identificador != null
	 * @param elemento el elemento que almacenar� el nodo. elemento != null
	 */
	public NodoAbstracto( K identificador, T elemento)
	{
		//TODO 3.1.3 inicialice el identificador y el elemento a partir del elemento que llega como par�metro   	
		this.identificador = identificador;
		this.elemento = elemento;
	}

	/**
	 * Devuelve el identificador del nodo
	 * @return identificador
	 */
	public K darIdentificador() 
	{
		//TODO 3.1.4 complete seg�n la documentaci�n
		return identificador;
	}

	/**
	 * Devuelve el elemento almacenado en el nodo
	 * @return elemento
	 */
	public T darElemento() 
	{
		//TODO 3.1.5 complete seg�n la documentaci�n
		return elemento;
	}

	/**
	 * Indica recursivamente si el �rbol que inicia en este nodo tiene el identificador
	 * @param id el identificador buscado. id != null
	 * @return true si el �rbol que inicia en el este nodo tiene el identificador o false en caso contrario
	 */
	public abstract boolean contieneIdentificador(K id);

	/**
	 * Indica recursivamente si el �rbol que inicia en este nodo contiene el elemento
	 * @param elem el elem buscado. id != null
	 * @return true si el �rbol que inicia en el este tiene el elemento o false en caso contrario
	 */
	public abstract boolean contieneElemento(T elem);

	/**
	 * Acumula recursivamente los nodos en la colecci�n que llega por par�metro. Los nodos se acumulan en inorden.
	 * @param nodos la colecci�n donde se acumulan los nodos. nodos != null
	 */
	public abstract void darNodos(Collection<NodoAbstracto<K, T>> nodos);

	/**
	 * Busca el elemento en el �rbol que inicia en el nodo y lo devuelve
	 * @param id el identificador del elemento buscado. id != null
	 * @return El elemento que tiene como id el que llega como par�metro o null en caso contrario
	 */
	public abstract T buscar(K id);

	/**
	 * Acumula recursivamente los identificadores en la colecci�n que llega por par�metro. Los identificadores se agregan en inorden.
	 * @param nodos la colecci�n donde se acumulan los identificadores. nodos != null
	 */
	public abstract void darIdentificadores(Collection<K> ids);

	/**
	 * Calcula recursivamente el peso del �rbol que inicia en el nodo.
	 * @return el peso del �rbol que inicia en el nodo.
	 */
	public abstract int darPeso();

	/**
	 * Acumula recursivamente en preorden los elementos en la colecci�n que llega por par�metro
	 * @param elems la colecci�n donde se acumulan los elementos. elems != null
	 */
	public abstract void darPreorden(Collection<T> elems);

	/**
	 * Agrega el elemento que llega por par�metro al �rbol.
	 * @param elem el elemento que se desea agregar. elem != null
	 * @param ids Los identificadores necesarios para agregar el elemento. id != null
	 */
	@SuppressWarnings("unchecked")
	public abstract void agregar(T elem, K... ids);

	/**
	 * Acumula recursivamente en posorden los elementos en la colecci�n que llega por par�metro.
	 * @param elems la colecci�n donde se acumulan los elementos. elems != null
	 */
	public abstract void darPosorden(Collection<T> elems);

	/**
	 * Construye el camino para llegar al nodo con el identificador que llega por par�metro. Los elementos los acumula en preorden en la colecci�n.
	 * @param id el identificador del nodo al que se quiere llegar. id != null
	 * @param elems la colecci�n donde se acumula el camino de elementos para llegar al nodo
	 */
	public abstract void darCamino(K id, Collection<T> elems);

	/**
	 * Devuelve el nodo que tiene el elemento con el id que llega como par�metro.
	 * @param id el identificador del elemento del que se quiere conocer el nodo.
	 * @return El nodo que tiene el elemento con el identificador que llega como par�metro.
	 */
	public abstract NodoAbstracto<K,T> darNodo(K id);

	
	//Basado en el ejercicio de nivel de apo2
}
