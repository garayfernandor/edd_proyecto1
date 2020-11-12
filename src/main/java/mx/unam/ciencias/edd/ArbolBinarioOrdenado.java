package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios ordenados. Los árboles son genéricos, pero
 * acotados a la interfaz {@link Comparable}.</p>
 *
 * <p>Un árbol instancia de esta clase siempre cumple que:</p>
 * <ul>
 *   <li>Cualquier elemento en el árbol es mayor o igual que todos sus
 *       descendientes por la izquierda.</li>
 *   <li>Cualquier elemento en el árbol es menor o igual que todos sus
 *       descendientes por la derecha.</li>
 * </ul>
 */
public class ArbolBinarioOrdenado<T extends Comparable<T>>
    extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Pila para recorrer los vértices en DFS in-order. */
        private Pila<Vertice> pila;

        /* Inicializa al iterador. */
        public Iterador() {
            pila = new Pila<Vertice>();
	    Vertice v = raiz;
	    while (v != null) {
	        pila.mete(v);
		v = v.izquierdo;
	    }// Aquí va su código.
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return !pila.esVacia();// Aquí va su código.
        }

        /* Regresa el siguiente elemento en orden DFS in-order. */
        @Override public T next() {
            Vertice v = pila.saca();
	    T t = v.get();
	    if (v.hayDerecho()) {
		v = v.derecho;
	        pila.mete(v);
		while (v.hayIzquierdo()) {
		    v = v.izquierdo;
	            pila.mete(v);
		}
	    }
	    return t;// Aquí va su código.
        }
    }

    /**
     * El vértice del último elemento agegado. Este vértice sólo se puede
     * garantizar que existe <em>inmediatamente</em> después de haber agregado
     * un elemento al árbol. Si cualquier operación distinta a agregar sobre el
     * árbol se ejecuta después de haber agregado un elemento, el estado de esta
     * variable es indefinido.
     */
    protected Vertice ultimoAgregado;

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioOrdenado() { super(); }

    /**
     * Construye un árbol binario ordenado a partir de una colección. El árbol
     * binario ordenado tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario ordenado.
     */
    public ArbolBinarioOrdenado(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden in-order.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) throws IllegalArgumentException {
        if (elemento == null)
	    throw new IllegalArgumentException();
	elementos++;
	if (esVacia()) {
	    raiz = ultimoAgregado = nuevoVertice(elemento); 
	    return;
	}
	agrega(raiz, elemento);// Aquí va su código.
    }

    private void agrega(Vertice v, T t) {
        if (t.compareTo(v.elemento) <= 0) {
	    if (!v.hayIzquierdo()) {    
		v.izquierdo = nuevoVertice(t);
	        v.izquierdo.padre = v;
		ultimoAgregado = v.izquierdo;
		return;
	    }
	    agrega(v.izquierdo, t);
	}
	else {
	    if (!v.hayDerecho()) {
	        v.derecho = nuevoVertice(t);
	        v.derecho.padre = v;
	        ultimoAgregado = v.derecho;
	        return;
	    }
	    agrega(v.derecho, t);
	}
    }

    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no hace nada; si
     * está varias veces, elimina el primero que encuentre (in-order). El árbol
     * conserva su orden in-order.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
	Vertice v = vertice(busca(elemento));
	if (v == null)
	    return;
	if (v.hayIzquierdo() && v.hayDerecho()) {
	    Vertice w = intercambiaEliminable(v);
	    eliminaVertice(w);
	    return;
	}
	eliminaVertice(v);// Aquí va su código.
    }

    /**
     * Intercambia el elemento de un vértice con dos hijos distintos de
     * <code>null</code> con el elemento de un descendiente que tenga a lo más
     * un hijo.
     * @param vertice un vértice con dos hijos distintos de <code>null</code>.
     * @return el vértice descendiente con el que vértice recibido se
     *         intercambió. El vértice regresado tiene a lo más un hijo distinto
     *         de <code>null</code>.
     */
    protected Vertice intercambiaEliminable(Vertice vertice) {
	Vertice w = maxEnSubarbol(vertice.izquierdo);
        vertice.elemento = w.elemento;
        return w;// Aquí va su código.
    }

    private Vertice maxEnSubarbol(Vertice v) {
	if (!v.hayDerecho())
            return v;
	return maxEnSubarbol(v.derecho);
    }

    /**
     * Elimina un vértice que a lo más tiene un hijo distinto de
     * <code>null</code> subiendo ese hijo (si existe).
     * @param vertice el vértice a eliminar; debe tener a lo más un hijo
     *                distinto de <code>null</code>.
     */
    protected void eliminaVertice(Vertice vertice) {
	if (vertice.hayIzquierdo() && vertice.hayDerecho()) //No funciona para vértices con dos hijos diferentes de null.
	    return;
	if (!vertice.hayIzquierdo() && !vertice.hayDerecho()) {
	    eliminaHoja(vertice);
	    return;   
	}
	if (vertice.hayIzquierdo()) {
	    vertice.izquierdo.padre = vertice.padre;
	    if (!vertice.hayPadre()) {
	        raiz = vertice.izquierdo;
		elementos--;
                return;		
	    }
	    if (vertice.esIzquierdo()) 
	        vertice.padre.izquierdo = vertice.izquierdo;
	    else
	        vertice.padre.derecho = vertice.izquierdo;
	    elementos--;
	    return;
	}
	vertice.derecho.padre = vertice.padre;
	if (!vertice.hayPadre()) {
	    raiz = vertice.derecho;
	    elementos--;
	    return;
	}
	if (vertice.esIzquierdo()) {
            vertice.padre.izquierdo = vertice.derecho;
	    elementos--;
            return;
	}
	vertice.padre.derecho = vertice.derecho;
	elementos--;// Aquí va su código.  
    }

    private void eliminaHoja(Vertice v) {
	if (!v.hayPadre()) {
	    raiz = null;
   	    elementos--;
	    return;
        }
	if (v.esIzquierdo())
	    v.padre.izquierdo = null; 
        else
	    v.padre.derecho = null;
	elementos--;	
    }

    /**
     * Busca un elemento en el árbol recorriéndolo in-order. Si lo encuentra,
     * regresa el vértice que lo contiene; si no, regresa <code>null</code>.
     * @param elemento el elemento a buscar.
     * @return un vértice que contiene al elemento buscado si lo
     *         encuentra; <code>null</code> en otro caso.
     */
    @Override public VerticeArbolBinario<T> busca(T elemento) {
        return busca(elemento, raiz);// Aquí va su código.
    }

    private Vertice busca(T elemento, Vertice v) {
        if (v == null || elemento == null)
	    return null;
	if (v.elemento.equals(elemento))
            return v;
        if (elemento.compareTo(v.elemento) < 0)
            return busca(elemento, v.izquierdo);
	return busca(elemento, v.derecho);
    }

    /**
     * Regresa el vértice que contiene el último elemento agregado al
     * árbol. Este método sólo se puede garantizar que funcione
     * <em>inmediatamente</em> después de haber invocado al método {@link
     * agrega}. Si cualquier operación distinta a agregar sobre el árbol se
     * ejecuta después de haber agregado un elemento, el comportamiento de este
     * método es indefinido.
     * @return el vértice que contiene el último elemento agregado al árbol, si
     *         el método es invocado inmediatamente después de agregar un
     *         elemento al árbol.
     */
    public VerticeArbolBinario<T> getUltimoVerticeAgregado() {
        return ultimoAgregado;// Aquí va su código.
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el vértice no
     * tiene hijo izquierdo, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
    	Vertice v = vertice(vertice);
        if (!v.hayIzquierdo() || vertice == null)
	    return;

	v.izquierdo.padre = v.padre;
	if (!v.hayPadre())
	    raiz = v.izquierdo;
	if (v.esIzquierdo())
	    v.padre.izquierdo = v.izquierdo;
	if (v.esDerecho())
	    v.padre.derecho = v.izquierdo;
        v.padre = v.izquierdo;
	if (v.padre.hayDerecho())
	    v.padre.derecho.padre = v;
	v.izquierdo = v.padre.derecho;
	v.padre.derecho = v;// Aquí va su código. 
    }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el vértice no
     * tiene hijo derecho, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
	Vertice v = vertice(vertice);
        if (!v.hayDerecho() || vertice == null)
	    return;

        v.derecho.padre = v.padre;
	if (!v.hayPadre())
	    raiz = v.derecho;
	if (v.esIzquierdo())
	    v.padre.izquierdo = v.derecho;
	if (v.esDerecho())
            v.padre.derecho = v.derecho;
	v.padre = v.derecho;
	if (v.padre.hayIzquierdo())
	    v.padre.izquierdo.padre = v;
	v.derecho = v.padre.izquierdo;
	v.padre.izquierdo = v;// Aquí va su código.
    }

    /**
     * Realiza un recorrido DFS <em>pre-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPreOrder(AccionVerticeArbolBinario<T> accion) {
        if (!esVacia())
            dfsPreOrder(raiz(), accion);// Aquí va su código.
    }

    private void dfsPreOrder(VerticeArbolBinario<T> v, AccionVerticeArbolBinario<T> accion) {
	accion.actua(v);
	if (v.hayIzquierdo())
	    dfsPreOrder(v.izquierdo(), accion);
	if (v.hayDerecho())
	dfsPreOrder(v.derecho(), accion);
    }

    /**
     * Realiza un recorrido DFS <em>in-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsInOrder(AccionVerticeArbolBinario<T> accion) {
        if (!esVacia())
	    dfsInOrder(raiz(), accion);// Aquí va su código.
    }

    private void dfsInOrder(VerticeArbolBinario<T> v, AccionVerticeArbolBinario<T> accion) {
	if (v.hayIzquierdo())
            dfsInOrder(v.izquierdo(), accion);
        accion.actua(v);
	if (v.hayDerecho())
            dfsInOrder(v.derecho(), accion);
    }

    public void dfsInOrder(AccionVerticeArbolBinario<T> accion, boolean reverse) {
        if (esVacia())
	    return;
	if (reverse)
            dfsInReverseOrder(raiz(), accion);
	else
            dfsInOrder(raiz(), accion);
    }
    private void dfsInReverseOrder(VerticeArbolBinario<T> v, AccionVerticeArbolBinario<T> accion) {
	if (v.hayDerecho())
            dfsInReverseOrder(v.derecho(), accion);
        accion.actua(v);
	if (v.hayIzquierdo())
            dfsInOrder(v.izquierdo(), accion);
    }

    /**
     * Realiza un recorrido DFS <em>post-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPostOrder(AccionVerticeArbolBinario<T> accion) {
        if (!esVacia())
            dfsPostOrder(raiz(), accion);// Aquí va su código.
    }

    private void dfsPostOrder(VerticeArbolBinario<T> v, AccionVerticeArbolBinario<T> accion) {
	if (v.hayIzquierdo())
            dfsPostOrder(v.izquierdo(), accion);
	if (v.hayDerecho())
            dfsPostOrder(v.derecho(), accion);
        accion.actua(v);
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
