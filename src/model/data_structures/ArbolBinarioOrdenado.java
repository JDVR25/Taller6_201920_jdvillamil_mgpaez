package model.data_structures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Representa un árbol binario ordenado
 * @param <K> tipo del identificador de los nodos del árbol que debe ser comparable
 * @param <T> tipo de los elementos almacenados en el árbol que deben ser unicamente identificados
 */
public class ArbolBinarioOrdenado <K extends Comparable<K>, T> extends Arbol<K, T>
{
	/**
	 * Constante de serialización
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Crea un árbol vacio
	 */
	public ArbolBinarioOrdenado()
	{
		super();
	}

	/**
	 * Agrega al árbol el elemento que llega por parámetro.
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
	 * Elimina el nodo cuyo identificador llega por parámetro
	 * @param identificador el identificador del nodo a eliminar. identificador != null
	 * @throws NoSuchElementException Se lanza en caso que no exista un elemento con el identificador dentro del árbol.
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
	 * Devuelve un nuevo árbol con los elementos cuyos identificadores son menores al identificador que llega por parámetro
	 * @param limite identificador que sirve como límite para construir el árbol. limite != null
	 * @return el árbol con los elementos cuyo identificador es menor al que llega por parámetro
	 * @throws Exception  Se lanza en caso que no se pueda agregar algún elemento al nuevo árbol
	 */
	public ArbolBinarioOrdenado<K, T> arbolCabeza(K limite) throws Exception {
		ArbolBinarioOrdenado<K, T> cabeza = new ArbolBinarioOrdenado<K, T>();
		if(raiz != null)
			((NodoABO<K,T>) raiz).arbolCabeza(limite, cabeza);
		return cabeza;
	}

	/**
	 * Devuelve el menor identificador del árbol
	 * @return el identificador menor o null si el árbol está vacío
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
	 * Devuelve el mayor identificador del árbol
	 * @return el identificador mayor o null si el árbol está vacío
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
	 * Crea un nuevo árbol que contiene solo los elementos cuyo identificador está entre los dos identificadores por parámetro
	 * @param limiteInferior identificador menor para construir el árbol - incluido
	 * @param limiteSuperior identificador mayor para construir el árbol - exclusivo
	 * @return El árbol con los nodos que están entre los identificadores
	 * @throws Exception  Se lanza en caso que no se pueda agregar algún elemento al nuevo árbol
	 */
	public ArbolBinarioOrdenado<K, T> darSubArbol(K limiteInferior, K limiteSuperior) throws Exception 
	{
		ArbolBinarioOrdenado<K, T> sub = new ArbolBinarioOrdenado<K, T>();
		if(raiz != null)
			((NodoABO<K,T>) raiz).subArbol(limiteInferior, limiteSuperior, sub);
		return sub;
	}

	/**
	 * Devuelve un nuevo árbol con los elementos cuyos identificador son mayores o iguales al identificador que llega por parámetro
	 * @param limite identificador que sirve como límite para construir el árbol. limite != null
	 * @return el árbol con los elementos cuyos identificadores son mayores o iguales al que llega por parámetro
	 * @throws Exception  Se lanza en caso que no se pueda agregar el nodo al nuevo árbol
	 */
	public ArbolBinarioOrdenado<K, T> darArbolCola(K limiteInferior) throws Exception 
	{
		ArbolBinarioOrdenado<K, T> cola = new ArbolBinarioOrdenado<K, T>();
		if(raiz != null)
			((NodoABO<K,T>) raiz).arbolCola(limiteInferior, cola);
		return cola;
	}

	/**
	 * Devuelve una colección inorden con los elementos del árbol
	 * @return colección con los elementos del árbol en inorden
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
