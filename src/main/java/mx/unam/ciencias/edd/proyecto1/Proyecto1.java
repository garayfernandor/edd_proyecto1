package mx.unam.ciencias.edd.proyecto1;

import mx.unam.ciencias.edd.ArbolRojinegro;

/**
 * Proyecto 1: Ordenador lexicográfico.
 */
public class Proyecto1 {

    public static void main(String[] argumentos) {
       Argumentos args = new Argumentos(argumentos);
       Lector lector = new Lector(args.getArgs());
       ArbolRojinegro<Linea> lineas;
       if (args.esVacia()) 
	   lineas = lector.leeEstandar();
       else
	   lineas = lector.leeArchivos();

       Escritor escritor = new Escritor(lineas, args.salida(), 
		                        args.esReversa());
       escritor.escribe();
       System.exit(0);
   }
}

