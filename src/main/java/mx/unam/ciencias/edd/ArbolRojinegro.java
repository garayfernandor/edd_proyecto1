package mx.unam.ciencias.edd;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 *  <li>Todos los vértices son NEGROS o ROJOS.</li>
 *  <li>La raíz es NEGRA.</li>
 *  <li>Todas las hojas (<code>null</code>) son NEGRAS (al igual que la raíz).</li>
 *  <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 *  <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 *      mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros se autobalancean.
 */
public class ArbolRojinegro<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeRojinegro extends Vertice {

        /** El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {
            super(elemento);
	    color = Color.NINGUNO;// Aquí va su código.
        }

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * @return una representación en cadena del vértice rojinegro.
         */
        public String toString() {
            String s = "";
	    if (color == Color.ROJO)
		s = "R{" + elemento.toString() + "}";
	    if (color == Color.NEGRO)
                s = "N{" + elemento.toString() + "}";
	    return s;// Aquí va su código.
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de
         *         éste vértice, los descendientes de ambos son recursivamente
         *         iguales, y los colores son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked")
                VerticeRojinegro vertice = (VerticeRojinegro)objeto;
            return color == vertice.color && super.equals(vertice);// Aquí va su código.
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolRojinegro() {
        super();
    }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol
     * rojinegro tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        rojinegro.
     */
    public ArbolRojinegro(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link
     * VerticeRojinegro}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        return new VerticeRojinegro(elemento);// Aquí va su código.
    }

    /**
     * Regresa el color del vértice rojinegro.
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
        if (vertice.getClass() != VerticeRojinegro.class)
	    throw new ClassCastException();
	return ((VerticeRojinegro)vertice).color;// Aquí va su código.
    }

    private boolean esRojo(VerticeRojinegro v) {
        return v != null && v.color == Color.ROJO;
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        super.agrega(elemento);
	VerticeRojinegro v = (VerticeRojinegro)ultimoAgregado;
	v.color = Color.ROJO;
	balancea(v);// Aquí va su código.
    }

    private void balancea(VerticeRojinegro v) {
        if (!esRojo(v))
	    return;
	if (!v.hayPadre()) {
	    v.color = Color.NEGRO;
	    return;
	}
	VerticeRojinegro p = (VerticeRojinegro)v.padre;
	VerticeRojinegro a = (VerticeRojinegro)p.padre;
	VerticeRojinegro t = (VerticeRojinegro)p.hermano();

	if (!esRojo(p))
	    return;

	if (esRojo(t)) {
	    p.color = Color.NEGRO;
	    t.color = Color.NEGRO;
	    a.color = Color.ROJO;
	    balancea(a);
	    return;
	}
        
        if (v.esPadreCruzado()) {
	    if (p.esIzquierdo())
		super.giraIzquierda(p);
	    else
	        super.giraDerecha(p);
	    VerticeRojinegro temp = v;
	    v = p;
	    p = temp;
	}

        p.color = Color.NEGRO;
        a.color = Color.ROJO;
        if (v.esIzquierdo())
            super.giraDerecha(a);
        else
	    super.giraIzquierda(a);	
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        VerticeRojinegro v = (VerticeRojinegro)busca(elemento);
	if (v == null)
	    return;

	if (v.hayIzquierdo() && v.hayDerecho())
	    v = (VerticeRojinegro)intercambiaEliminable(v);

	if (!(v.hayIzquierdo() || v.hayDerecho())) {
	    VerticeRojinegro fantasma = (VerticeRojinegro)nuevoVertice(null);
	    v.izquierdo = fantasma;
	    fantasma.padre = v;
	    fantasma.color = Color.NEGRO;
	    elementos++;
	}

	VerticeRojinegro h;

        if (v.hayIzquierdo())
	    h = (VerticeRojinegro)v.izquierdo;
	else
	    h = (VerticeRojinegro)v.derecho;

        eliminaVertice(v);	

	if (esRojo(h)) 
	    h.color = Color.NEGRO;
	else if (!(esRojo(v) || esRojo(h))) 
	    rebalancea(h);
	
	if (h.elemento == null)
	    eliminaVertice(h);// Aquí va su código.
    }

    private void rebalancea(VerticeRojinegro v) {
        if (v == null || v.color != Color.NEGRO || !v.hayPadre())
	    return;

	VerticeRojinegro p = (VerticeRojinegro)v.padre;
        VerticeRojinegro h = (VerticeRojinegro)v.hermano();

	if (esRojo(h)) {
	    p.color = Color.ROJO;
	    h.color = Color.NEGRO;

	    if (v.esIzquierdo()) 
	        super.giraIzquierda(p);
	    else
		super.giraDerecha(p);
	    p = (VerticeRojinegro)v.padre;
	    h = (VerticeRojinegro)v.hermano();
	}

	VerticeRojinegro hi = (VerticeRojinegro)h.izquierdo;
	VerticeRojinegro hd = (VerticeRojinegro)h.derecho;

	if (!(esRojo(p) || esRojo(h) || esRojo(hi) || esRojo(hd))) {
            h.color = Color.ROJO;
	    rebalancea(p);
	    return;
	}

	if (!(esRojo(h) || esRojo(hi) || esRojo(hd)) && esRojo(p)) {
            h.color = Color.ROJO;
	    p.color = Color.NEGRO;
	    return;
	}

	if ((v.esIzquierdo() && esRojo(hi) && !esRojo(hd)) || (v.esDerecho() && !esRojo(hi) && esRojo(hd))) {
            h.color = Color.ROJO;
	    if (esRojo(hi))
		hi.color = Color.NEGRO;
	    else
		hd.color = Color.NEGRO;
	    if (v.esIzquierdo())
		super.giraDerecha(h);
	    else
		super.giraIzquierda(h);
	    h = (VerticeRojinegro)v.hermano();
	    hi = (VerticeRojinegro)h.izquierdo;
	    hd = (VerticeRojinegro)h.derecho;
	}

	h.color = p.color;
	p.color = Color.NEGRO;
	if (v.esIzquierdo()) {
            hd.color = Color.NEGRO;
	    super.giraIzquierda(p);
	} else {
            hi.color = Color.NEGRO;
	    super.giraDerecha(p);
	}
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la izquierda por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la izquierda " +
                                                "por el usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la derecha por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la derecha " +
                                                "por el usuario.");
    }
}
