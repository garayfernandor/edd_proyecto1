package mx.unam.ciencias.edd.proyecto1;

import mx.unam.ciencias.edd.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

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

