package mx.unam.ciencias.edd.proyecto1;

import mx.unam.ciencias.edd.ArbolRojinegro;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Lector {
    private String[] args;
    private ArbolRojinegro<Linea> lineas;

    public Lector(String[] args) {
        this.args = args;
    }

    public ArbolRojinegro<Linea> getLineas() {
        return lineas;
    }

    public ArbolRojinegro<Linea> leeArchivos() {
	lineas.limpia();
        String s;
        for (String file : args) {
           try {
               BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(file))));
               while ((s = in.readLine()) != null)
                   lineas.agrega(new Linea(s));
           } catch (NullPointerException ignore) { //Ignoramos.
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

    public ArbolRojinegro<Linea> leeEstandar() {
	lineas.limpia();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s;
        try {
            while((s = br.readLine()) != null)
                lineas.agrega(new Linea(s));
        } catch (IOException ioe) {
            System.out.printf("Disculpa, por alguna razón no pude leer lo que escribías.");
            System.exit(1);
        }
	return lineas;
    }
}
