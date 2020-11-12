package mx.unam.ciencias.edd;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase genérica para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas no aceptan a <code>null</code> como elemento.</p>
 *
 * @param <T> El tipo de los elementos de la lista.
 */
public class Lista<T> implements Coleccion<T> {

    /* Clase interna privada para nodos. */
    private class Nodo {
        /* El elemento del nodo. */
        public T elemento;
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nodo con un elemento. */
        public Nodo(T elemento) {
            this.elemento = elemento;// Aquí va su código.
        }

	private boolean hayAnterior() {
	    return anterior != null;
	}

	private boolean haySiguiente() {
	    return siguiente != null;
	}
    }

    /* Clase interna privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nuevo iterador. */
        public Iterador() {
            siguiente = cabeza;
	    anterior = null;// Aquí va su código.
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return siguiente != null;// Aquí va su código.
        }

        /* Nos da el elemento siguiente. */
        @Override public T next() {
            if(!hasNext())
	        throw new NoSuchElementException();
	    T t = siguiente.elemento;
	    anterior = siguiente;
	    siguiente = siguiente.siguiente;
            
	    return t;// Aquí va su código.
        }

        /* Nos dice si hay un elemento anterior. */
        @Override public boolean hasPrevious() {
            return anterior != null;// Aquí va su código.
        }

        /* Nos da el elemento anterior. */
        @Override public T previous() {
            if(!hasPrevious())
	        throw new NoSuchElementException();
	    T t = anterior.elemento;
	    siguiente = anterior;
	    anterior = anterior.anterior;
	    return t;// Aquí va su código.
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override public void start() {
            siguiente = cabeza;
	    anterior = null;// Aquí va su código.
        }

        /* Mueve el iterador al final de la lista. */
        @Override public void end() {
            siguiente = null;
	    anterior = rabo;// Aquí va su código.
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista. El método es idéntico a {@link
     * #getElementos}.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        return longitud;// Aquí va su código.
    }

    /**
     * Regresa el número elementos en la lista. El método es idéntico a {@link
     * #getLongitud}.
     * @return el número elementos en la lista.
     */
    @Override public int getElementos() {
        return getLongitud();// Aquí va su código.
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        return longitud == 0;// Aquí va su código.
    }

    /**
     * Agrega un elemento a la lista. Si la lista no tiene elementos, el
     * elemento a agregar será el primero y último. El método es idéntico a
     * {@link #agregaFinal}.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        if(elemento == null)
            throw new IllegalArgumentException();
        Nodo n = new Nodo(elemento);
        longitud++;

        if(longitud == 1) {
            cabeza = rabo = n;
        } else { 
        rabo.siguiente = n; 
        n.anterior = rabo; 
        rabo = n;
        }// Aquí va su código.
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) throws IllegalArgumentException {
        agrega(elemento);// Aquí va su código.
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) throws IllegalArgumentException {
        if(elemento==null)
            throw new IllegalArgumentException();
        Nodo n = new Nodo(elemento);
        longitud++;

        if(longitud == 1){
            cabeza = rabo = n;
        } else {
            cabeza.anterior = n; 
            n.siguiente = cabeza; 
            cabeza = n;
        }// Aquí va su código.
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio
     * de la lista. Si el índice es mayor o igual que el número de elementos en
     * la lista, el elemento se agrega al fina de la misma. En otro caso,
     * después de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al inicio de la lista, y si es mayor o igual
     *          que el número de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void inserta(int i, T elemento) throws IllegalArgumentException {
        if(elemento == null)
            throw new IllegalArgumentException();
        if(i <= 0){
            agregaInicio(elemento);
            return;
        }
        if(i >= longitud){
            agregaFinal(elemento);
            return;
        }
        Nodo n = new Nodo(elemento);
        Nodo aux = cabeza;
        for(int j = 0; j != i; j++){
            aux = aux.siguiente;
        }
        n.anterior = aux.anterior;
        n.siguiente = aux;
        aux.anterior.siguiente = n;
        aux.anterior = n;
        longitud++;// Aquí va su código.
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
	Nodo n = cabeza;
	while (n != null) {
	    if (n.elemento.equals(elemento)) {
		if (!n.hayAnterior() && !n.haySiguiente()) {
		    cabeza = rabo = null;
		} else if (!n.hayAnterior()) {
		    n.siguiente.anterior = null;
		    cabeza = n.siguiente;
		} else if (!n.haySiguiente()) {
		    n.anterior.siguiente = null;
		    rabo = n.anterior;
		} else {
		    n.anterior.siguiente = n.siguiente;
                    n.siguiente.anterior = n.anterior;
		}
		longitud--;
		return;
	    }
	    n = n.siguiente;  
	}// Aquí va su código.
    }

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() throws NoSuchElementException {
        if (esVacia())
            throw new NoSuchElementException();
        T t = cabeza.elemento;
        elimina(t);
        return t;// Aquí va su código.
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() throws NoSuchElementException {
        if (esVacia())
            throw new NoSuchElementException();
        T t = rabo.elemento;
	Nodo n = cabeza;
        while (n.siguiente != null)
	    n = n.siguiente;
	if (n.hayAnterior()) {
	    n.anterior.siguiente = null;
	    rabo = n.anterior;
	    longitud--;
	} else {
	    limpia();
	}
	return t;// Aquí va su código.
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <code>true</code> si <code>elemento</code> está en la lista,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        Nodo n = cabeza;
        while (n != null){
            if (n.elemento.equals(elemento))
       	        return true;
            n = n.siguiente;
        }
        return false;// Aquí va su código.
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        Iterador i = new Iterador();
        Lista<T> l = new Lista<T>();
        i.end(); 
        while(i.hasPrevious())
            l.agregaFinal(i.previous());
        return l;// Aquí va su código.
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
        Iterador i = new Iterador();
        Lista<T> l = new Lista<T>();
        while(i.hasNext())
            l.agregaFinal(i.next());
        return l;// Aquí va su código.
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    @Override public void limpia() {
        cabeza = rabo = null;
	longitud = 0;// Aquí va su código.
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() throws NoSuchElementException {
        if(longitud == 0)
	    throw new NoSuchElementException();
	return cabeza.elemento;// Aquí va su código.
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() throws NoSuchElementException {
        if(longitud == 0)
	    throw new NoSuchElementException();
	return rabo.elemento;// Aquí va su código.
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public T get(int i) throws ExcepcionIndiceInvalido {
        if(i < 0 || i >= longitud)
            throw new ExcepcionIndiceInvalido();
        int  c = 0;
        Nodo n = cabeza;
        while (c < i) {
            n = n.siguiente;
            c++;
        }
        return n.elemento;// Aquí va su código.
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
        if(!contiene(elemento))
            return -1;
        Nodo n = cabeza;
        int i = 0;
        while(!n.elemento.equals(elemento)){
      	    i++;
            n = n.siguiente;
        }
        return i;// Aquí va su código.
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
        String s = "[";
        Nodo n = cabeza;

        if (longitud == 0)
 	    return "[]";
        while (n != rabo) {
	    s += String.format("%s, ", n.elemento);
            n = n.siguiente;
        }
        s += String.format("%s", n.elemento);
        return s+"]";// Aquí va su código.
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la lista es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>)objeto;
        if (lista == null)
            return false;
	if (esVacia() == true && lista == null)
            return false;
       	if (lista.esVacia() == true && esVacia() == true)
            return true;
	Nodo a = cabeza;
        Nodo b = lista.cabeza;
        while (a.elemento.equals(b.elemento)){
             a = a.siguiente;
	     b = b.siguiente;
        if (a == null && b == null)
            return true;
        if (a == null || b == null)
            break;
        }
        return false;// Aquí va su código.
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }

    /**
     * Regresa una copia de la lista, pero ordenada. Para poder hacer el
     * ordenamiento, el método necesita una instancia de {@link Comparator} para
     * poder comparar los elementos de la lista.
     * @param comparador el comparador que la lista usará para hacer el
     *                   ordenamiento.
     * @return una copia de la lista, pero ordenada.
     */
    public Lista<T> mergeSort(Comparator<T> comparador) {
        Lista<T> l = copia();	    
        return Lista.mergeSort(l, comparador);// Aquí va su código.
    }

    private static <T> Lista<T> mergeSort(Lista<T> l, Comparator<T> comparador) {
        if (l.longitud < 2)
            return l;
        Lista<T> left = l.subLista(0, l.longitud/2 - 1);
        Lista<T> right = l.subLista(l.longitud/2, l.longitud-1);

        left = Lista.mergeSort(left, comparador);
        right = Lista.mergeSort(right, comparador);

        return Lista.mezcla(left, right, comparador);
    }

    private static <T> Lista<T> mezcla(Lista<T> left, Lista<T> right, Comparator<T> comparador) {
        Lista<T> l = new Lista<T>();
        Iterator<T> i = left.iterator();
        Iterator<T> j = right.iterator();

        T tleft = i.hasNext() ? i.next() : null;
        T tright = j.hasNext() ? j.next() : null;

        while (tleft != null && tright != null) {
            if (comparador.compare(tleft, tright) <= 0) {
                l.agregaFinal(tleft);
	        tleft = i.hasNext() ? i.next() : null;
	    } else {
	        l.agregaFinal(tright);
	        tright = j.hasNext() ? j.next() : null;
	    }

        }
        if (tleft != null) {
            l.agregaFinal(tleft);
            while (i.hasNext())
                l.agregaFinal(i.next());
        }
        if (tright != null) {
            l.agregaFinal(tright);
            while (j.hasNext())
                l.agregaFinal(j.next());
        }

        return l;
    }

    private Lista<T> subLista(int left, int right) throws ExcepcionIndiceInvalido {
        Lista<T> l = new Lista<T>();
        try {
        l.agregaFinal(get(left));
            while (left < right) {
                left++;
                l.agregaFinal(get(left));
            }
        } catch(ExcepcionIndiceInvalido eii) {
            throw eii;
        }
        return l;
     }

    /**
     * Regresa una copia de la lista recibida, pero ordenada. La lista recibida
     * tiene que contener nada más elementos que implementan la interfaz {@link
     * Comparable}.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista que se ordenará.
     * @return una copia de la lista recibida, pero ordenada.
     */
    public static <T extends Comparable<T>>
    Lista<T> mergeSort(Lista<T> lista) {
        return lista.mergeSort((a, b) -> a.compareTo(b));
    }

    /**
     * Busca un elemento en la lista ordenada, usando el comparador recibido. El
     * método supone que la lista está ordenada usando el mismo comparador.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador con el que la lista está ordenada.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public boolean busquedaLineal(T elemento, Comparator<T> comparador) {
        int left = 0;
	int right = longitud - 1;
		
        while (left <= right) {
	    int mid = (left + right) / 2;

            if (comparador.compare(get(mid), elemento) == 0)
                return true;
            if (comparador.compare(get(mid), elemento) < 0)
                left = mid + 1;
	    else
	        right = mid - 1;
        }
        return false;// Aquí va su código.
    }

    /**
     * Busca un elemento en una lista ordenada. La lista recibida tiene que
     * contener nada más elementos que implementan la interfaz {@link
     * Comparable}, y se da por hecho que está ordenada.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista donde se buscará.
     * @param elemento el elemento a buscar.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public static <T extends Comparable<T>>
    boolean busquedaLineal(Lista<T> lista, T elemento) {
        return lista.busquedaLineal(elemento, (a, b) -> a.compareTo(b));
    }
}
