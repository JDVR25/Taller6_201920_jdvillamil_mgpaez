package model.data_structures;

import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Nodo del �rbol binario
 * @param <K> tipo del identificador del elemento que se almacenar� en el nodo. El tipo debe poder ser comparable.
 * @param <T> Tipo del elemento que almacena el nodo.
 */
public class NodoABO <K extends Comparable<K>, T> extends NodoAbstracto<K, T>
{
	/**
	 * Constante de serializaci�n
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Hijo izquierdo del nodo
	 */
	private NodoABO<K, T> izq;

	/**
	 * Hijo derecho del nodo
	 */
	private NodoABO<K, T> der;

	private boolean conexionRoja;

	/**
	 * Crea un nuevo nodo a partir del elemento y su identificador que llega por par�metro.
	 * <b>pos:</b>
	 * - Se ha asignado el identificador y el elemento al nodo.
	 * - Se han inicializado los dos hijos como null.
	 * @param identificador identificador del elemento que se almacenar� en el nodo. identificador != null
	 * @param elem el elemento que almacenar� el nodo. elem != null
	 */
	public NodoABO( K identificador, T elem)
	{
		super(identificador, elem);
		izq = null;
		der = null;
		conexionRoja = true;
	}

	/**
	 * Devuelve el hijo de la izquierda
	 * @return izq
	 */
	public NodoABO<K,T> darIzquierda() 
	{
		return izq;
	}

	/**
	 * Devuelve el hijo de la derecha
	 * @return der
	 */
	public NodoABO<K,T> darDerecha() 
	{
		return der;
	}
	
	public boolean esRojo()
	{
		return conexionRoja;
	}
	
	public void laRaizNo()
	{
		conexionRoja = false;
	}

	@Override
	public boolean contieneIdentificador(K id)
	{
		boolean respuesta = false;
		if(identificador.compareTo(id) == 0)
		{
			respuesta = true;
		}
		else if(identificador.compareTo(id) > 0 && izq != null)
		{
			respuesta = izq.contieneIdentificador(id);
		}
		else if(der != null)
			respuesta = der.contieneIdentificador(id);
		return respuesta;
	}

	@Override
	public boolean contieneElemento(T elem)
	{
		boolean respuesta = false;
		if(elemento.equals(elem))
		{
			respuesta = true;
		}
		else
		{
			respuesta = izq != null? izq.contieneElemento(elem): (der != null?der.contieneElemento(elem):false);
		}
		return respuesta;
	}

	@Override
	public void darNodos(Collection<NodoAbstracto<K,T>> nodos) 
	{
		if(izq != null)
		{
			izq.darNodos(nodos);
		}
		nodos.add(this);
		if(der != null)
		{
			der.darNodos(nodos);
		}
	}


	@Override
	public T buscar(K id)
	{
		T respuesta = null;
		if(identificador.compareTo(id) == 0)
			respuesta = elemento;

		else if(identificador.compareTo(id) > 0 && izq != null)
			respuesta = izq.buscar(id);

		else if(der != null)
			respuesta = der.buscar(id);

		return respuesta;
	}

	/**
	 * Elimina el nodo cuyo identificador llega por par�metro. Al eliminar se debe buscar un reemplazo conservando el orden del �rbol.
	 * En caso de tener 2 hijos el reemplazo ser� el menor de los mayores, en caso de s�lo tener un hijo retorna al otro hijo.
	 * @param id el identificador del elemento a eliminar. id != null
	 * @return Le debe informar al padre qu� nodo debe quedar en su posici�n.
	 * 			- Si el elemento a eliminar no es �l, se retorna a s� mismo.
	 * 			<b>Si el elemento a eliminar es �l:</b>
	 * 				- Si no tiene hijos, retorna null.
	 * 				- Si no tiene hijo derecho, retorna el hijo izquierdo.
	 * 				- Si no tiene hijo izquierdo, retorna el hijo derecho.
	 * 				- Si tiene ambos hijos, busca el menor de los mayores, lo coloca en su posici�n y lo retorna.
	 * @throws NoSuchElementException Se lanza en caso de que el elemento no exista en el �rbol.
	 */
	public NodoABO<K, T> eliminar(K id) throws NoSuchElementException
	{
		NodoABO<K, T> enMiPos = this;

		if(identificador.compareTo(id) == 0)
		{
			if(der == null && izq == null)
				enMiPos = null;

			else if(der == null)
				enMiPos = izq;

			else if(izq == null)
				enMiPos = der;

			else
			{
				enMiPos = der.darMenor();
				eliminar(enMiPos.darIdentificador());
				enMiPos.izq = izq;
				enMiPos.der = der;
			}
		}
		else if(identificador.compareTo(id) > 0 && izq != null)
		{
			izq = izq.eliminar(id);
		}
		else if(identificador.compareTo(id) < 0 && der != null)
		{
			der = der.eliminar(id);
		}
		else
		{
			throw new NoSuchElementException();
		}

		return enMiPos;
	}

	/**
	 * Devuelve el identificador menor del �rbol que inicia en este nodo
	 * @return identificador menor
	 */
	public K darIdentificadorMenor() 
	{
		K respuesta = identificador;
		if(izq != null)
		{
			respuesta = izq.darIdentificadorMenor();
		}
		return respuesta;
	}

	/**
	 * Acumula todos los nodos con identificador menor al l�mite en el �rbol que llega por par�metro
	 * @param limite el identificador l�mite para construir el �rbol. l�mite  != null
	 * @param cabeza el �rbol donde se acumulan los nodos
	 * @throws Exception Se lanza en caso de que ocurra una excepci�n al agregar elementos al �rbol.
	 */
	public void arbolCabeza(K limite, ArbolBinarioOrdenado<K, T> cabeza) throws Exception
	{
		if(identificador.compareTo(limite) > 0 || identificador.compareTo(limite) == 0)
		{
			if(izq != null)
			{
				izq.arbolCabeza(limite, cabeza);
			}
		}
		else
		{
			cabeza.agregar(identificador, elemento);
			if(izq != null)
			{
				izq.arbolCabeza(limite, cabeza);
			}
			if(der != null)
			{
				der.arbolCabeza(limite, cabeza);
			}
		}

	}

	/**
	 * Devuelve el identificador mayor del �rbol que inicia en este nodo
	 * @return identificador mayor
	 */
	public K darIdentificadorMayor() 
	{
		K respuesta = identificador;
		if(der != null)
		{
			respuesta = der.darIdentificadorMayor();
		}
		return respuesta;
	}

	/**
	 * Acumula todos los nodos con identificador entre los l�mites en el �rbol que llega por par�metro.
	 * @param limiteInferior el identificador l�mite inferior - incluido. limiteInferior  != null
	 * @param limiteSuperior el identificador l�mite superior - excluido. limiteSuperior  != null
	 * @param sub el �rbol donde se acumular�n los nodos
	 * @throws Exception  Se lanza en caso que no se pueda agregar el nodo al nuevo �rbol
	 */
	public void subArbol(K limiteInferior, K limiteSuperior, ArbolBinarioOrdenado<K, T> sub) throws Exception 
	{
		if(identificador.compareTo(limiteInferior) > 0 && identificador.compareTo(limiteSuperior) < 0)
		{
			sub.agregar(identificador, elemento);
			if(izq != null)
			{
				izq.subArbol(limiteInferior, limiteSuperior, sub);
			}
			if(der != null)
			{
				der.subArbol(limiteInferior, limiteSuperior, sub);
			}
		}
		else if(identificador.compareTo(limiteSuperior) > 0 || identificador.compareTo(limiteSuperior) == 0)
		{
			if(izq != null)
			{
				izq.subArbol(limiteInferior, limiteSuperior, sub);
			}
		}
		else if(identificador.compareTo(limiteInferior) < 0 || identificador.compareTo(limiteInferior) == 0)
		{
			if(identificador.compareTo(limiteInferior) == 0)
			{
				sub.agregar(identificador, elemento);
			}
			if(der != null)
			{
				der.subArbol(limiteInferior, limiteSuperior, sub);
			}
		}

	}

	/**
	 * Acumula todos los nodos con identificador mayor o igual al l�mite en el �rbol que llega por par�metro
	 * @param limite el identificador l�mite para construir el �rbol. limite  != null
	 * @param cola el �rbol donde se acumulan los nodos
	 * @throws Exception  Se lanza en caso que no se pueda agregar el nodo al nuevo �rbol.
	 */
	public void arbolCola(K limite, ArbolBinarioOrdenado<K, T> cola) throws Exception 
	{
		if(identificador.compareTo(limite) > 0)
		{
			cola.agregar(identificador, elemento);
			if(izq != null)
			{
				izq.arbolCola(limite, cola);
			}
			if(der != null)
			{
				der.arbolCola(limite, cola);
			}

		}
		else
		{
			if(identificador.compareTo(limite) == 0)
				cola.agregar(identificador, elemento);
			if(der != null)
			{
				der.arbolCola(limite, cola);
			}
		}
	}

	/**
	 * Acumula una colecci�n inorden de los elementos del �rbol que inicia en este nodo
	 * @param elementos la colecci�n donde se almacenan los elementos
	 */
	public void darInorden(Collection<T> elementos) 
	{
		if(izq != null)
		{
			izq.darInorden(elementos);
		}
		elementos.add(elemento);
		if(der != null)
		{
			der.darInorden(elementos);
		}
	}


	/**
	 * Devuelve el menor nodo a partir del �rbol que inicia en este nodo
	 * @return el nodo actual si no hay hijo izquierdo o el nodo m�s de la izquierda
	 */
	public NodoABO<K, T> darMenor()
	{
		NodoABO<K, T> respuesta = this;
		if(izq != null)
		{
			respuesta = izq.darMenor();
		}
		return respuesta;
	}


	@Override
	public void darIdentificadores(Collection<K> ids) 
	{
		if(izq != null)
		{
			izq.darIdentificadores(ids);
		}
		ids.add(identificador);
		if(der != null)
		{
			der.darIdentificadores(ids);
		}
	}

	@Override
	public int darPeso() 
	{
		int peso = 1;
		if(izq != null)
			peso += izq.darPeso();
		if(der != null)
			peso += der.darPeso();
		return peso;
	}

	@Override
	public void darPreorden(Collection<T> elementos) 
	{
		elementos.add(elemento);
		if(izq != null)
		{
			elementos.add(izq.elemento);
		}
		if(der != null)
		{
			elementos.add(der.elemento);
		}
	}

	@Override
	public void darPosorden(Collection<T> elementos) 
	{
		if(izq != null)
		{
			elementos.add(izq.elemento);
		}
		if(der != null)
		{
			elementos.add(der.elemento);
		}
		elementos.add(elemento);
	}

	/**
	 * Agrega orenadamente el elemento con su id que llegan por par�metro
	 * @param elem el elemento que se almacenar� en el �rbol. elem != null
	 * @param ids identificadores necesarios para agregar el nodo. ids[0] tiene el id del elemento a agregar.
	 * @throws IllegalArgumentException Se lanza en caso que el identificador ya exista en el �rbol
	 */
	@SafeVarargs
	public final void agregar(T elem, K... ids) throws IllegalArgumentException
	{
		K idElementoAAgregar = ids[0];
		if(contieneIdentificador(idElementoAAgregar))
			throw new IllegalArgumentException();
		else if(identificador.compareTo(idElementoAAgregar) > 0)
		{
			if(izq != null)
				izq.agregar(elem, ids);
			else
				izq = new NodoABO<K, T>(idElementoAAgregar, elem);
		}
		else
		{
			if(der != null)
				der.agregar(elem, ids);
			else
				der = new NodoABO<K, T>(idElementoAAgregar, elem);
		}
	}

	@Override
	public void darCamino(K id, Collection<T> elems) 
	{
		if(id.compareTo(identificador) != 0)
		{
			if(der != null && der.contieneIdentificador(id))
			{
				elems.add(elemento);
				der.darCamino(id, elems);
			}
			else if(izq != null && izq.contieneIdentificador(id))
			{
				elems.add(elemento);
				izq.darCamino(id, elems);
			}
		}
		else
		{
			elems.add(elemento);
		}
	}

	@Override
	public NodoAbstracto<K, T> darNodo(K id) 
	{
		NodoAbstracto<K, T> buscado = null;
		if(identificador.compareTo(id) == 0)
			buscado = this;
		else if(identificador.compareTo(id) > 0 && izq != null)
			buscado = izq.darNodo(id);
		else if(identificador.compareTo(id) < 0 && der != null)
			buscado = der.darNodo(id);
		return buscado;
	}
	
	//Basado en el ejercicio de nivel de apo2
}
