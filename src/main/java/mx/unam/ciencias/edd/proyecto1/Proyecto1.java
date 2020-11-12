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
      

   /**
    * Escribe las líneas recibidas en el archivo de salida
    * recibida.
    * @param l la lista con las líneas a cargar.
    * @param bw el archivo de salida. Si es igual a
    *        <code>null</code> el método no hace nada. 
    */
   private static void escribeEnArchivo(ArbolRojinegro<Linea> lineas, BufferedWriter out, boolean reversa) {
      if (out == null)
         return;
      try {
         lineas.dfsInOrder(v -> {
		 try {
	             out.write(v.get() + "\n");
		 } catch (IOException ioe) {
		     System.out.printf("Disculpa, por alguna razón no pude guardar en su archivo.");
                     System.exit(1);
		 }
	     }, reversa);
	 out.close(); 
      } catch (IOException ioe) {
            System.out.printf("Disculpa, por alguna razón no pude guardar en su archivo.");
	    System.exit(1);
      }
   }

   /**
    * Carga las líneas ordenadas de los documentos recibidos
    * como parámetro en una lista.
    * @param l la lista donde vienen los documentos a leer.
    * @param reversa si las líneas se ordenan al revés o no.
    * @return una lista con las líneas ordenadas de los documentos
    * recibidos.
    */  
   private static ArbolRojinegro<Linea> cargaLineas(String[] args) {
      ArbolRojinegro<Linea> lineas = new ArbolRojinegro<Linea>();
      String s;

      for (String file : args) {
         try {
             BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(file))));
             while ((s = in.readLine()) != null)
                 lineas.agrega(new Linea(s));
         } catch (NullPointerException npe) { //Ignoramos.
	 } catch (FileNotFoundException fnfe) {
             System.out.println("Disculpa, no pude encontrar el archivo " + file);
             System.exit(1);
         } catch (IOException ioe) {
             System.out.println("Disculpa, no pude cargar la información del archivo " + file);
             System.exit(1);
         }
      }
      return lineas;
   }
}

