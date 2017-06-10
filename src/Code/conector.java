package Code;

/**
 * Created by Jose Luis Rodriguez on 21/5/2017.
 */


import Code.Empleado;
import Controllers.VentanaServerController;
import com.sun.javafx.tk.Toolkit;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.net.*;
import java.io.*;
import java.util.Objects;



import java.net.*;
import java.io.*;

import static Code.Main.*;
import static Controllers.VentanaServerController.*;

public class conector extends Thread {

    public conector (String conexion){
        super(conexion);
    }

    int puerto = 9000;
    public String datosIngreso[];
    public static String user;

    public void run(){
        try{
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Mensaje del servidor");
                    alert.setHeaderText("Por favor espere");
                    alert.setContentText("Por favor espere a que se conecte un empleado");
                    alert.showAndWait();
                }
            });
            server = new ServerSocket(puerto);
            socket = server.accept();
            entrada = new DataInputStream(socket.getInputStream());
            salida = new DataOutputStream(socket.getOutputStream());
            System.out.println("Conexion establecida exitosamente");
            String mensaje = "Fail;0";
            while (Objects.equals(mensaje, "Fail;0")) {
                mensaje = entrada.readUTF();
                int num = mensaje.indexOf(";");
                datosIngreso = mensaje.split(";");
                Empleado e = new Empleado("admin", "admin", "admin", "admin");
                mensaje = e.ingreso(datosIngreso, salida);
            }
            datosIngreso = mensaje.split(";");
            user = datosIngreso[2];
//            lblActivityLog.setText(lblActivityLog.getText() + "\n " + user + "  se ha conectado!");
            if(Objects.equals(user, "Erika Marin")){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        profe.estado.setValue("Conectado");
                    }
                });
            }
            else if(Objects.equals(user, "Jose Luis Rodriguez")){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        jose.estado.setValue("Conectado");
                    }
                });
            }
            else if(Objects.equals(user, "Randall Delgado")){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        randall.estado.setValue("Conectado");
                    }
                });
            }

            mensaje = "Conectar";
            while (!Objects.equals(mensaje, "Desconectar")) {
                mensaje = entrada.readUTF();
                int num = mensaje.indexOf(";");
                datosIngreso = mensaje.split(";");
                mensaje = datosIngreso[1];
                System.out.println(mensaje);
            }
            mensaje = datosIngreso[0];
            if(Objects.equals(mensaje, "Erika Marin")){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        profe.estado.setValue("Desconectado");
                    }
                });
            }
            else if(Objects.equals(mensaje, "Jose Luis Rodriguez")){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        jose.estado.setValue("Desconectado");
                    }
                });
            }
            else if(Objects.equals(mensaje, "Randall Delgado")){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        randall.estado.setValue("Desconectado");
                    }
                });
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Se produjo un error al conectar");
        };
    }

//    public void Ingreso(){
//        try {
//            String mensaje = "Fail;0";
//            while (Objects.equals(mensaje, "Fail;0")) {
//                //System.out.println("4");
//                mensaje = entrada.readUTF();
//                int num = mensaje.indexOf(";");
//                datosIngreso = mensaje.split(";");
//                Empleado e = new Empleado("admin", "admin", "admin", "admin");
//                //System.out.println("5");
//                mensaje = e.ingreso(datosIngreso, salida);
//            }
//            datosIngreso = mensaje.split(";");
//            user = datosIngreso[2];
//
//        } catch (Exception e){
//            System.out.println("Se produjo un error al ingresar");
//        }
//    }
//
//    public void run(){
//        try {
//            String mensaje = "Conectar";
//            while (!Objects.equals(mensaje, "Desconectar")) {
//                mensaje = entrada.readUTF();
//                int num = mensaje.indexOf(";");
//                datosIngreso = mensaje.split(";");
//                mensaje = datosIngreso[1];
//                System.out.println(mensaje);
//            }
//            VentanaServerController.jose.estado.setValue("Desconectado");
//
//        } catch (Exception e){
//            System.out.println("Se produjo un error al ingresar");
//        }
//    }
//
    public void Desconectar(){
        try {
            entrada.close();
            salida.close();
            server.close();
            socket.close();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Server");
            alert.setHeaderText("El servidor se encuentra desactivado");
            alert.setContentText("El servidor ya se encuentra desactivado");
            alert.showAndWait();
        }

    }
}
