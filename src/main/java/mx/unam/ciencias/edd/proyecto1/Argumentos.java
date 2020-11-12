package mx.unam.ciencias.edd.proyecto1;

import mx.unam.ciencias.edd.Arreglos;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Argumentos {
   
    private String[] args;
    private boolean reversa;
    private BufferedWriter out = null;

    public Argumentos(String[] args) {
        this.args = args;
	int i;
	if ((i = Arreglos.busquedaBinaria(args, "-r")) != -1) {
	    reversa = true;
	    args[i] = null;
	}
        if ((i = Arreglos.busquedaBinaria(args, "-o")) != -1) {
	    args[i] = null;
            try {
                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(args[i+1]))));
                args[i+1] = null;
            } catch (FileNotFoundException fnfe) {
                System.out.println("Disculpa, no pude encontrar el archivo a guardar.");
                System.exit(1);
            } catch (IOException ioe) {
                System.out.println("Disculpa, no pude crear el archivo a guardar.");
                System.exit(1);
            } catch (ArrayIndexOutOfBoundsException aioobe) {
                System.out.println("Disculpa, parece ser que no se me indicó el archivo donde guardar.");
                System.exit(1);
            }
	}	
    }

    public String[] getArgs() {
        return args;
    }

    public boolean esReversa() {
        return reversa;
    }

    public BufferedWriter salida() {
        return out;
    }

    public boolean esVacia() {
        boolean b = true;
	for (String s : args)
            if (s != null)
	        b = false;
	return (args.length == 0 || b);
    }
}
