package mx.unam.ciencias.edd.proyecto1;

import mx.unam.ciencias.edd.ArbolRojinegro;
import java.io.BufferedWriter;
import java.io.IOException;

public class Escritor {

    private ArbolRojinegro<Linea> lineas;
    private BufferedWriter out;
    private boolean reversa;

    public Escritor(ArbolRojinegro<Linea> lineas, BufferedWriter out, boolean reversa) {
        this.lineas = lineas;
	this.out = out;
	this.reversa = reversa;
    }

    public void escribe() {
        if (out == null)
            escribeEnTerminal();
	else
            escribeArchivo();
    }

    private void escribeEnTerminal() {
       lineas.dfsInOrder(v -> System.out.println(v.get() + "\n"), reversa); 
    }

    private void escribeArchivo() {
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
}
