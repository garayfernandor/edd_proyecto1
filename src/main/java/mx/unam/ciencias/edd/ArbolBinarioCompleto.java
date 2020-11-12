package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios completos.</p>
 *
 * <p>Un árbol binario completo agrega y elimina elementos de tal forma que el
 * árbol siempre es lo más cercano posible a estar lleno.</p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Cola para recorrer los vértices en BFS. */
        private Cola<Vertice> cola;

        /* Inicializa al iterador. */
        public Iterador() {
            Cola<Vertice> cola = new Cola<Vertice>();
	    if (raiz != null)
	        cola.mete(raiz);
	    this.cola = cola;// Aquí va su código.
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return !cola.esVacia();// Aquí va su código.
        }

        /* Regresa el siguiente elemento en orden BFS. */
        @Override public T next() {
		
	    Vertice v = cola.saca();

	    if (v.hayIzquierdo())
		cola.mete(v.izquierdo);
	    if (v.hayDerecho())
		cola.mete(v.derecho);
	    return v.get();// Aquí va su código.
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioCompleto() { super(); }

    /**
     * Construye un árbol binario completo a partir de una colección. El árbol
     * binario completo tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario completo.
     */
    public ArbolBinarioCompleto(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un elemento al árbol binario completo. El nuevo elemento se coloca
     * a la derecha del último nivel, o a la izquierda de un nuevo nivel.
     * @param elemento el elemento a agregar al árbol.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) throws IllegalArgumentException {
        if (elemento == null)
	    throw new IllegalArgumentException();
	Vertice v = nuevoVertice(elemento);
	if (esVacia()) {
            raiz = v;
	    elementos++;
            return;
	}
        
	elementos++;
        Cola<Vertice> cola;
        cola = new Cola<Vertice>();
        cola.mete(raiz);

        while (!cola.esVacia()) {
            Vertice w = cola.saca();
            if (!w.hayIzquierdo()) {
                w.izquierdo = v;
                v.padre = w;
		return;
	    }
            if (!w.hayDerecho()) {
                w.derecho = v;
	        v.padre = w;
		return;
	    }
	    cola.mete(w.izquierdo);
            cola.mete(w.derecho);
        }// Aquí va su código.
    }

    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con
     * el último elemento del árbol al recorrerlo por BFS, y entonces es
     * eliminado.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
	if (busca(elemento) == null)
            return;		
        Vertice v = vertice(busca(elemento));
	elementos--;
	if (elementos == 0) {
	    raiz = null;
	    return;
	}
	Cola<Vertice> cola;
        cola = new Cola<Vertice>();
        cola.mete(raiz);
	Vertice w = raiz;
        while (!cola.esVacia()) {
            w = cola.saca();
            if (w.hayIzquierdo())
                cola.mete(w.izquierdo);
            if (w.hayDerecho())
                cola.mete(w.derecho);
        }
        v.elemento = w.elemento;
        if (w.esDerecho())
            w.padre.derecho = null;
	else
	    w.padre.izquierdo = null;// Aquí va su código.
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol binario completo
     * siempre es ⌊log<sub>2</sub><em>n</em>⌋.
     * @return la altura del árbol.
     */
    @Override public int altura() {
        if (raiz == null)
	    return -1;
        int h = (int) Math.floor(Math.log(elementos)/Math.log(2));
        return h;// Aquí va su código.
    }

    /**
     * Realiza un recorrido BFS en el árbol, ejecutando la acción recibida en
     * cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void bfs(AccionVerticeArbolBinario<T> accion) {
        if (esVacia())
	    return;
	Cola<VerticeArbolBinario<T>> cola;
	cola = new Cola<VerticeArbolBinario<T>>();
	cola.mete(raiz);
	
	while (!cola.esVacia()) {
	    VerticeArbolBinario<T> v = cola.saca();
	    accion.actua(v);
	    if (v.hayIzquierdo())
		cola.mete(v.izquierdo());
	    if (v.hayDerecho())
		cola.mete(v.derecho());
	}// Aquí va su código.
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden BFS.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
