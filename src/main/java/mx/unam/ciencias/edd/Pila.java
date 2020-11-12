package mx.unam.ciencias.edd;

/**
 * Clase para pilas genéricas.
 */
public class Pila<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la pila.
     * @return una representación en cadena de la pila.
     */
    @Override public String toString() {
        String s = "";
        if(esVacia())
            return s;
        Nodo n = cabeza;

        while(n.siguiente != null) {
            s += n.elemento.toString() + "\n";
            n = n.siguiente;
        }
        s += n.elemento.toString() + "\n";
        return s;// Aquí va su código.
    }

    /**
     * Agrega un elemento al tope de la pila.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void mete(T elemento) throws IllegalArgumentException {
        if(elemento == null)
	    throw new IllegalArgumentException();	
	Nodo n = new Nodo(elemento);
	n.siguiente = cabeza;
	cabeza = n;// Aquí va su código.
    }
}
