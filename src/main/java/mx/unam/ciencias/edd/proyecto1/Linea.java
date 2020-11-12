package mx.unam.ciencias.edd.proyecto1;

import java.text.Collator;

/**
 * Clase para comparar lexicográficamente dos líneas. Una
 * línea cuenta con una cadena. La clase implementa {@link Comparable}
 * por lo que dos instancias de esta se pueden comparar.
 */
public class Linea implements Comparable<Linea> {

       /**
	* Cadena de la línea
	*/
       private String linea;

       /**
	* Define la cadena inicial de una línea.
	*/
       public Linea(String linea) {
          this.linea = linea;
       }

       /**
	* Regresa la cadena de la línea.
	* @return la cadena de la línea.
	*/
       public String get() {
          return linea;
       }

       /**
	* Compara lexicográficamente dos líneas.
	* @param l la línea con la cual comparar.
	* @return un número menor a 0 si la línea a
	* comparar es mayor a la que llama el método.
	* un número mayor a 0 si la línea a comparar
	* es menor a la que llama el método. 0 si ambas
	* líneas son iguales.
	*/
       @Override public int compareTo(Linea l) {
          Collator c = Collator.getInstance();
	  c.setStrength(Collator.PRIMARY);
          return c.compare(linea.replaceAll("[ñÑ]", "n").replaceAll("[¿?!¡]", ""),
		                  l.linea.replaceAll("[ñÑ]", "n").replaceAll("[¿?!¡]", ""));
       }
}
