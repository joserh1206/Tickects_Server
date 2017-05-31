package Code;

/**
 * Created by Jose Luis Rodriguez on 21/5/2017.
 */

import Code.Main;
import Code.Empleado;
import java.net.*;
import java.io.*;
import java.util.Objects;



import java.net.*;
import java.io.*;

public class conector extends Main {

    int puerto = 9000;
    public String datosIngreso[];

    public void iniciar(){
        String mensaje = "Fail;0";
        try{
            server = new ServerSocket(puerto);
            //System.out.println("2");
            socket = server.accept();
            entrada = new DataInputStream(socket.getInputStream());
            salida = new DataOutputStream(socket.getOutputStream());
            System.out.println("Conexion establecida exitosamente");
            while(Objects.equals(mensaje, "Fail;0")){
                //System.out.println("4");
                mensaje= entrada.readUTF();
                int num = mensaje.indexOf(";");
                datosIngreso = mensaje.split(";");
                Empleado e = new Empleado("admin", "admin", "admin", "admin");
                //System.out.println("5");
                mensaje = e.ingreso(datosIngreso, salida);
            }
        } catch (Exception e){
            System.out.println("Se produjo un error al conectar");
        };
    }
}
