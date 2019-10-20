package model.data_structures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Representa un �rbol binario ordenado
 * @param <K> tipo del identificador de los nodos del �rbol que debe ser comparable
 * @param <T> tipo de los elementos almacenados en el �rbol que deben ser unicamente identificados
 */
public class ArbolBinarioOrdenado <K extends Comparable<K>, T> extends Arbol<K, T>
{
	/**
	 * Constante de serializaci�n
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Crea un �rbol vacio
	 */
	public ArbolBinarioOrdenado()
	{
		super();
	}

	/**
	 * Agrega al �rbol el elemento que llega por par�metro.
	 * @param identificador el identificador del elemento a agregar. identificador != null
	 * @param elemento el elemento que se desae agregar. elemento != null
	 * @throws IllegalArgumentException Se lanza en caso que no se pueda agregar el nodo porque ya se encuentra un elemento con el identificador.
	 */
	public void agregar(K identificador, T elemento) throws IllegalArgumentException
	{
		if(raiz == null)
		{
			raiz = new NodoABO<K, T>(identificador, elemento);

			((NodoABO<K, T>) raiz).laRaizNo();
		}
		else
		{
			((NodoABO<K,T>) raiz).agregar(elemento, identificador);
			((NodoABO<K, T>) raiz).laRaizNo();
		}
	}

	/**
	 * Elimina el nodo cuyo identificador llega por par�metro
	 * @param identificador el identificador del nodo a eliminar. identificador != null
	 * @throws NoSuchElementException Se lanza en caso que no exista un elemento con el identificador dentro del �rbol.
	 */
	public void eliminar(K identificador) throws NoSuchElementException
	{
		if(raiz == null)
		{
			throw new NoSuchElementException();
		}
		else if(identificador.compareTo(raiz.darIdentificador()) == 0)
			raiz = null;
		else
		{
			((NodoABO<K,T>) raiz).eliminar(identificador);
		}
	}

	/**
	 * Devuelve un nuevo �rbol con los elementos cuyos identificadores son menores al identificador que llega por par�metro
	 * @param limite identificador que sirve como l�mite para construir el �rbol. limite != null
	 * @return el �rbol con los elementos cuyo identificador es menor al que llega por par�metro
	 * @throws Exception  Se lanza en caso que no se pueda agregar alg�n elemento al nuevo �rbol
	 */
	public ArbolBinarioOrdenado<K, T> arbolCabeza(K limite) throws Exception {
		ArbolBinarioOrdenado<K, T> cabeza = new ArbolBinarioOrdenado<K, T>();
		if(raiz != null)
			((NodoABO<K,T>) raiz).arbolCabeza(limite, cabeza);
		return cabeza;
	}

	/**
	 * Devuelve el menor identificador del �rbol
	 * @return el identificador menor o null si el �rbol est� vac�o
	 */
	public K darIdentificadorMenor()
	{
		K menor = null;
		if(raiz != null)
		{
			((NodoABO<K,T>) raiz).darIdentificadorMenor();
		}
		return menor;
	}

	/**
	 * Devuelve el mayor identificador del �rbol
	 * @return el identificador mayor o null si el �rbol est� vac�o
	 */
	public K darIdentificadorMayor()
	{
		K mayor = null;
		if(raiz != null)
		{
			((NodoABO<K,T>) raiz).darIdentificadorMayor();
		}
		return mayor;
	}

	/**
	 * Crea un nuevo �rbol que contiene solo los elementos cuyo identificador est� entre los dos identificadores por par�metro
	 * @param limiteInferior identificador menor para construir el �rbol - incluido
	 * @param limiteSuperior identificador mayor para construir el �rbol - exclusivo
	 * @return El �rbol con los nodos que est�n entre los identificadores
	 * @throws Exception  Se lanza en caso que no se pueda agregar alg�n elemento al nuevo �rbol
	 */
	public ArbolBinarioOrdenado<K, T> darSubArbol(K limiteInferior, K limiteSuperior) throws Exception 
	{
		ArbolBinarioOrdenado<K, T> sub = new ArbolBinarioOrdenado<K, T>();
		if(raiz != null)
			((NodoABO<K,T>) raiz).subArbol(limiteInferior, limiteSuperior, sub);
		return sub;
	}

	/**
	 * Devuelve un nuevo �rbol con los elementos cuyos identificador son mayores o iguales al identificador que llega por par�metro
	 * @param limite identificador que sirve como l�mite para construir el �rbol. limite != null
	 * @return el �rbol con los elementos cuyos identificadores son mayores o iguales al que llega por par�metro
	 * @throws Exception  Se lanza en caso que no se pueda agregar el nodo al nuevo �rbol
	 */
	public ArbolBinarioOrdenado<K, T> darArbolCola(K limiteInferior) throws Exception 
	{
		ArbolBinarioOrdenado<K, T> cola = new ArbolBinarioOrdenado<K, T>();
		if(raiz != null)
			((NodoABO<K,T>) raiz).arbolCola(limiteInferior, cola);
		return cola;
	}

	/**
	 * Devuelve una colecci�n inorden con los elementos del �rbol
	 * @return colecci�n con los elementos del �rbol en inorden
	 */
	public Collection<T> darInorden() 
	{
		Collection<T> elementos = new ArrayList<T>();
		if(raiz != null)
		{
			((NodoABO<K,T>) raiz).darInorden(elementos);
		}
		return elementos;
	}
	//Basado en el ejercicio de nivel de apo2
}
