package Code;

/**
 * Created by Jose Luis Rodriguez on 21/5/2017.
 */

import Code.Empleado;
import java.net.*;
import java.io.*;
import java.util.Objects;



import java.net.*;
import java.io.*;

public class conector {

    ServerSocket server;
    Socket socket;
    DataOutputStream salida;
    DataInputStream entrada;
    int puerto = 9000;
    public String datosIngreso[];

    public void iniciar(){
        try{
            //System.out.println("0");
            server = new ServerSocket(puerto);
            //System.out.println("1");
            socket = new Socket();
            //System.out.println("2");
            socket = server.accept();
            //System.out.println("3");
            entrada = new DataInputStream(socket.getInputStream());
            //System.out.println("4");
            String mensaje= entrada.readUTF();
            //System.out.println("5");
            int num = mensaje.indexOf(";");
            //System.out.println("6");
            datosIngreso = mensaje.split(";");
            //System.out.println("7");
            Empleado e = new Empleado("admin", "admin", "admin", "admin");
            //System.out.println("8");
            //System.out.println(mensaje);
            salida = new DataOutputStream(socket.getOutputStream());
            e.ingreso(datosIngreso, salida);
            //System.out.println("9");
            //System.out.println("10");
            //System.out.println("11");
            salida = new DataOutputStream(socket.getOutputStream());
            //System.out.println("12");
            //salida.writeUTF("Fail");

            entrada.close();
            salida.close();
            server.close();
            socket.close();
        } catch (Exception e){
            //System.out.println("Se produjo un error al conectar");
        };
    }
}
