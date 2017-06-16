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

    /**
     * Se encarga de leer los daots del excel cuando se inicia el servidor
     */
    public void leerExcel(){

    }

    /**
     * Simula la carga de nuevos tickets
     */
    public void cargarNuevosTickets(){

    }


    /**
     * Devuelve el tiempo promedio de atencion en general de todos los registrados
     * @return tiempo promedio
     */
    public int tiempoPromedioAtencion(){
        return  0;
    }

    /**
     * Devuelve el tiempo promedio de atencion de un solo empleado
     * @param login Login del cliente que solicita el tiempoPromedio
     * @return tiempo promedio de dicho empleado
     */
    public int tiempoPromedio(String login){
        return 0;
    }

    /**
     * Lista los tiquesAtendidos en un rango de fechas
     * @param fechaInicial fecha de inicio para buscar
     * @param fechaFinal   fecha final donde buscar
     * @return  la lista de tiquets atendidos
     */
    public ArrayList ticketsAtendidos(Calendar fechaInicial, Calendar fechaFinal){
        return new ArrayList();
    }

    /**
     * Lista los tickets atendididos de un solo empleado
     * @param login el empleado del cual se desea consultar
     * @return listado de tickets
     */
    public ArrayList ticketsAtendidos(String login){
        return new ArrayList();
    }

    /**
     * Entrega todos los tiquets que no se han atendido de una categoria
     * @param categoria
     * @return
     */
    public ArrayList entregarTickets(String categoria){
        return new ArrayList();
    }

    /**
     * Reserva el ticket para garantizar que nadie más podra tomarlo
     * @param numTicket el numero del ticket a reservar
     * @return  Indica con true que estaba libre y se pudo apartar, con false lo contrario
     */
    public boolean reservarTicket(int numTicket){
        return false;
    }

    /**
     * Libera un ticket para que quede disponible de nuevo por si alguien más
     * puede solucionarlo
     * @param numTicket El numero del ticket para identificarlo
     */
    public void liberarTicket(int numTicket){

    }

    /**
     * Devuelve el reporte de atención de un empleado en una fecha especifica
     * @param login Del empleado
     * @param fecha De la cual se desea la información
     * @return  Devuelve el reporte de atencion
     */
    public  int reporteAtencion(String login, Calendar fecha){
        return 0;
    }

}
