package Code;

import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Calendar;

import static Code.Main.server;
import static Code.Main.socket;

/**
 * Created by Jose Luis Rodriguez on 14/6/2017.
 */
public class Server implements Runnable {

    int puerto;
    Thread threadserver;

    //public static conector c;


    public Server(int puerto){
        threadserver = new Thread(this, "server");
        threadserver.start();
        this.puerto = puerto;
    }
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        try {
            server = new ServerSocket(puerto);

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
            while(true){
                socket = server.accept();
//                c = new conector();
//                new Thread(c).start();

            }
        } catch (IOException e) {
        }
    }

    public void leerExcel(){

    }

    public void cargarNuevosTickets(){

    }

    public int tiempoPromedioAtencion(){
        return  0;
    }

    public int tiempoPromedio(String login){
        return 0;
    }

    public ArrayList ticketsAtendidos(Calendar fechaInicial, Calendar fechaFinal){
        return new ArrayList();
    }

    public ArrayList ticketsAtendidos(String login){
        return new ArrayList();
    }

}
