package mx.unam.ciencias.edd;

import java.util.NoSuchElementException;

/**
 * <p>Clase abstracta para árboles binarios genéricos.</p>
 *
 * <p>La clase proporciona las operaciones básicas para árboles binarios, pero
 * deja la implementación de varias en manos de las subclases concretas.</p>
 */
public abstract class ArbolBinario<T> implements Coleccion<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class Vertice implements VerticeArbolBinario<T> {

        /** El elemento del vértice. */
        public T elemento;
        /** El padre del vértice. */
        public Vertice padre;
        /** El izquierdo del vértice. */
        public Vertice izquierdo;
        /** El derecho del vértice. */
        public Vertice derecho;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public Vertice(T elemento) {
            this.elemento = elemento;// Aquí va su código.
        }

        /**
         * Nos dice si el vértice tiene un padre.
         * @return <code>true</code> si el vértice tiene padre,
         *         <code>false</code> en otro caso.
         */
        @Override public boolean hayPadre() {
            return padre != null;// Aquí va su código.
        }

        /**
         * Nos dice si el vértice tiene un izquierdo.
         * @return <code>true</code> si el vértice tiene izquierdo,
         *         <code>false</code> en otro caso.
         */
        @Override public boolean hayIzquierdo() {
            return izquierdo != null;// Aquí va su código.
        }

        /**
         * Nos dice si el vértice tiene un derecho.
         * @return <code>true</code> si el vértice tiene derecho,
         *         <code>false</code> en otro caso.
         */
        @Override public boolean hayDerecho() {
            return derecho != null;// Aquí va su código.
        }

        /**
         * Regresa el padre del vértice.
         * @return el padre del vértice.
         * @throws NoSuchElementException si el vértice no tiene padre.
         */
        @Override public VerticeArbolBinario<T> padre() {
            if(!hayPadre())
	        throw new NoSuchElementException();
	    return padre;// Aquí va su código.
        }

        /**
         * Regresa el izquierdo del vértice.
         * @return el izquierdo del vértice.
         * @throws NoSuchElementException si el vértice no tiene izquierdo.
         */
        @Override public VerticeArbolBinario<T> izquierdo() {
            if(!hayIzquierdo())
		throw new NoSuchElementException();
	    return izquierdo;// Aquí va su código.
        }

        /**
         * Regresa el derecho del vértice.
         * @return el derecho del vértice.
         * @throws NoSuchElementException si el vértice no tiene derecho.
         */
        @Override public VerticeArbolBinario<T> derecho() {
            if(!hayDerecho())
		throw new NoSuchElementException();
	    return derecho;// Aquí va su código.
        }

	protected boolean esIzquierdo() {
	    if (padre == null)
		return false;
	    return padre.izquierdo == this;
	}

	protected boolean esDerecho() {
	    if (padre == null)
		return false;
	    return padre.derecho == this;
	}

	protected boolean esPadreCruzado() {
	    if (padre == null)
		return false;
	    return esIzquierdo() == padre.esDerecho();
	}

	protected VerticeArbolBinario<T> hermano() {
	    if (padre == null)
		return null;
	    return esIzquierdo() ? padre.derecho : padre.izquierdo;
	}

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
	    if (this == null)
		return -1;
	    if (!hayIzquierdo() && !hayDerecho())
		return 0;
	    if (hayIzquierdo() && hayDerecho())
		return 1 + Math.max(izquierdo.altura(), derecho.altura());
	    if (hayIzquierdo())
		return 1 + izquierdo.altura();
	    return 1 + derecho.altura();//Aquí va su código.
        } 

        /**
         * Regresa la profundidad del vértice.
         * @return la profundidad del vértice.
         */
        @Override public int profundidad() {
            if(!hayPadre())
		return 0; 
	    return 1 + padre.profundidad();// Aquí va su código.
        }

        /**
         * Regresa el elemento al que apunta el vértice.
         * @return el elemento al que apunta el vértice.
         */
        @Override public T get() {
            return elemento;// Aquí va su código.
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>. Las clases que extiendan {@link Vertice} deben
         * sobrecargar el método {@link Vertice#equals}.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link Vertice}, su elemento es igual al elemento de éste
         *         vértice, y los descendientes de ambos son recursivamente
         *         iguales; <code>false</code> en otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked") Vertice vertice = (Vertice)objeto;
	    if (elemento.equals(vertice.elemento)) {
		if (hayIzquierdo() && hayDerecho() && vertice.hayIzquierdo() && vertice.hayDerecho())
	            return izquierdo.equals(vertice.izquierdo) && derecho.equals(vertice.derecho);
		if (hayIzquierdo() && vertice.hayIzquierdo() && !hayDerecho() && !vertice.hayDerecho())
		    return izquierdo.equals(vertice.izquierdo);
		if (hayDerecho() && vertice.hayDerecho() && !hayIzquierdo() && !vertice.hayIzquierdo())
		    return derecho.equals(vertice.derecho());
		if (!hayIzquierdo() && !hayDerecho() && !vertice.hayIzquierdo() && !vertice.hayDerecho())
		    return true;
	    }
	    return false;// Aquí va su código.
        }

        /**
         * Regresa una representación en cadena del vértice.
         * @return una representación en cadena del vértice.
         */
        public String toString() {
            return elemento.toString();// Aquí va su código.
        }
    }

    /** La raíz del árbol. */
    protected Vertice raiz;
    /** El número de elementos */
    protected int elementos;

    /**
     * Constructor sin parámetros. Tenemos que definirlo para no perderlo.
     */
    public ArbolBinario() {}

    /**
     * Construye un árbol binario a partir de una colección. El árbol binario
     * tendrá los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario.
     */
    public ArbolBinario(Coleccion<T> coleccion) {
        for (T t : coleccion)
	    agrega(t);// Aquí va su código.
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link Vertice}. Para
     * crear vértices se debe utilizar este método en lugar del operador
     * <code>new</code>, para que las clases herederas de ésta puedan
     * sobrecargarlo y permitir que cada estructura de árbol binario utilice
     * distintos tipos de vértices.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    protected Vertice nuevoVertice(T elemento) {
        return new Vertice(elemento);
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol es la altura de su
     * raíz.
     * @return la altura del árbol.
     */
    public int altura() {
	if (esVacia())
	    return -1;
        return raiz().altura();// Aquí va su código.
    }

    /**
     * Regresa el número de elementos que se han agregado al árbol.
     * @return el número de elementos en el árbol.
     */
    @Override public int getElementos() {
        return elementos;// Aquí va su código.
    }

    /**
     * Nos dice si un elemento está en el árbol binario.
     * @param elemento el elemento que queremos comprobar si está en el árbol.
     * @return <code>true</code> si el elemento está en el árbol;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        if (busca(elemento) != null)
	    return true;
	return false;// Aquí va su código.
    }

    /**
     * Busca el vértice de un elemento en el árbol. Si no lo encuentra regresa
     * <code>null</code>.
     * @param elemento el elemento para buscar el vértice.
     * @return un vértice que contiene el elemento buscado si lo encuentra;
     *         <code>null</code> en otro caso.
     */
    public VerticeArbolBinario<T> busca(T elemento) {
        return busca(elemento, raiz);// Aquí va su código.
    }

    private Vertice busca(T elemento, Vertice v) {
	if (v == null)
	    return null;
	if (v.get().equals(elemento))
	    return v;
        Vertice w = busca(elemento, v.izquierdo);
	if (w != null)
	    return w;
	return busca(elemento, v.derecho);
    }

    /**
     * Regresa el vértice que contiene la raíz del árbol.
     * @return el vértice que contiene la raíz del árbol.
     * @throws NoSuchElementException si el árbol es vacío.
     */
    public VerticeArbolBinario<T> raiz() throws NoSuchElementException {
        if (esVacia())
            throw new NoSuchElementException();
	return raiz;// Aquí va su código.
    }

    /**
     * Nos dice si el árbol es vacío.
     * @return <code>true</code> si el árbol es vacío, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        return raiz == null;// Aquí va su código.
    }

    /**
     * Limpia el árbol de elementos, dejándolo vacío.
     */
    @Override public void limpia() {
        raiz = null;
	elementos = 0;// Aquí va su código.
    }

    /**
     * Compara el árbol con un objeto.
     * @param objeto el objeto con el que queremos comparar el árbol.
     * @return <code>true</code> si el objeto recibido es un árbol binario y los
     *         árboles son iguales; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked")
            ArbolBinario<T> arbol = (ArbolBinario<T>)objeto;
	if (esVacia() && arbol.esVacia())
	    return true;
	if (esVacia() || arbol.esVacia())
	    return false;
        return raiz.equals(arbol.raiz());// Aquí va su código.
    }

    /**
     * Regresa una representación en cadena del árbol.
     * @return una representación en cadena del árbol.
     */
    @Override public String toString() {
        if (esVacia())
            return "";
        boolean [] contador = new boolean [altura() + 1];
        for (int i = 0; i < altura(); i++)
            contador[i] = false;
        return toString(raiz, 0, contador);// Aquí va su código.
    }

    private String toString(Vertice v, int nivel, boolean [] binario) {
        String s = v.toString() + "\n";
        binario[nivel] = true;

        if (v.hayIzquierdo() && v.hayDerecho()) {
            s += dibujaEspacios(nivel, binario);
            s += "├─›";
            s += toString(v.izquierdo, nivel + 1, binario);
            s += dibujaEspacios(nivel, binario);
            s += "└─»";
            binario[nivel] = false;
            s += toString(v.derecho, nivel + 1, binario);
        } else if (v.hayIzquierdo()) {
            s += dibujaEspacios(nivel, binario);
            s += "└─›";
            binario[nivel] = false;
            s += toString(v.izquierdo, nivel + 1, binario);
        } else if (v.hayDerecho()) {
            s += dibujaEspacios(nivel, binario);
            s += "└─»";
            binario[nivel] = false;
            s += toString(v.derecho, nivel + 1, binario);
        }
        return s;
    }

    private String dibujaEspacios(int nivel, boolean [] binario) {
        String s = "";
        for(int i = 0; i < nivel; i++)
            if(binario[i])
                s += "│  ";
            else
                s += "   ";
        return s;
    }

    /**
     * Convierte el vértice (visto como instancia de {@link
     * VerticeArbolBinario}) en vértice (visto como instancia de {@link
     * Vertice}). Método auxiliar para hacer esta audición en un único lugar.
     * @param vertice el vértice de árbol binario que queremos como vértice.
     * @return el vértice recibido visto como vértice.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         Vertice}.
     */
    protected Vertice vertice(VerticeArbolBinario<T> vertice) {
        return (Vertice)vertice;
    }
}
