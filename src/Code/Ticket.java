package Code;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

/**
 *Clase que permite manejar los atributos de los tickets en el sistema
 * @author Randall Delgado
 * @author José Luis Rodríguez
 * @author Óscar Cortés
 */

public class Ticket extends RecursiveTreeObject <Ticket> {

    public Date fechaYHoraRecepcion;
    public int idTicket;
    public SimpleStringProperty strFechaYHoraRecepcion = new SimpleStringProperty();
    public SimpleStringProperty idCliente = new SimpleStringProperty();
    public SimpleStringProperty asunto = new SimpleStringProperty();
    public SimpleStringProperty categoria = new SimpleStringProperty();
    public SimpleStringProperty idEmpleado = new SimpleStringProperty();
    public Date fechaYHoraAtencion;
    public SimpleStringProperty strFechaYHoraAtencion = new SimpleStringProperty();
    public SimpleStringProperty tiempo = new SimpleStringProperty();
    public SimpleStringProperty comentario = new SimpleStringProperty();
    public SimpleStringProperty estado = new SimpleStringProperty();

    /**
     * Constructor de Ticket
     * @param pFechaYHoraRecepcion
     * @param pIdCliente
     * @param pAsunto
     * @param pIdTicket
     * @param pCategoria
     * @param pIdEmpleado
     * @param pFechaYHoraAtencion
     * @param pTiempo
     * @param pComentario
     * @param pEstado
     */
    public Ticket(Date pFechaYHoraRecepcion, String pIdCliente, String pAsunto, int pIdTicket, String pCategoria,
                  String pIdEmpleado, Date pFechaYHoraAtencion, String pTiempo, String pComentario, String pEstado){
        fechaYHoraRecepcion = pFechaYHoraRecepcion;

        int min = fechaYHoraRecepcion.getMinutes();
        int s = fechaYHoraRecepcion.getSeconds();

        String minutos = Integer.toString(min);
        String segundos = Integer.toString(s);

        if (min < 10)
            minutos = "0" + minutos;
        if (s < 10)
            segundos = "0" + segundos;

        strFechaYHoraRecepcion.set(Integer.toString(fechaYHoraRecepcion.getDay()) + "/" +
                Integer.toString(fechaYHoraRecepcion.getMonth() + 1) + "/" +
                Integer.toString(fechaYHoraRecepcion.getYear() + 1900) + ", " +
                Integer.toString(fechaYHoraRecepcion.getHours()) + ":" + minutos + ":" + segundos);
        idCliente.set(pIdCliente);
        asunto.set(pAsunto);
        idTicket = pIdTicket;
        categoria.set(pCategoria);
        idEmpleado.set(pIdEmpleado);
        fechaYHoraAtencion = pFechaYHoraAtencion;

        min = fechaYHoraAtencion.getMinutes();
        s = fechaYHoraAtencion.getSeconds();

        minutos = Integer.toString(min);
        segundos = Integer.toString(s);

        if (min < 10)
            minutos = "0" + minutos;
        if (s < 10)
            segundos = "0" + segundos;


        strFechaYHoraAtencion.set(Integer.toString(fechaYHoraAtencion.getDay()) + "/" + Integer.toString(fechaYHoraRecepcion.getMonth() + 1) + "/" + Integer.toString(fechaYHoraRecepcion.getYear() + 1900) + ", " + Integer.toString(fechaYHoraAtencion.getHours()) + ":" + minutos + ":" + segundos);

        tiempo.set(pTiempo);
        comentario.set(pComentario);
        estado.set(pEstado);
    }

    public void setCategoria(String categoria) {
        this.categoria.set(categoria);
    }

    /**
     * Constructor sobrecargado de Ticket
     * @param pFechaYHoraRecepcion
     * @param pIdCliente
     * @param pAsunto
     */
    public Ticket(Date pFechaYHoraRecepcion, String pIdCliente, String pAsunto, String pCategoria){
        fechaYHoraRecepcion = pFechaYHoraRecepcion;

        int min = fechaYHoraRecepcion.getMinutes();
        int s = fechaYHoraRecepcion.getSeconds();

        String minutos = Integer.toString(min);
        String segundos = Integer.toString(s);

        if (min < 10)
            minutos = "0" + minutos;
        if (s < 10)
            segundos = "0" + segundos;

        strFechaYHoraRecepcion.set(Integer.toString(fechaYHoraRecepcion.getDay()) + "/" +
                Integer.toString(fechaYHoraRecepcion.getMonth() + 1) + "/" +
                Integer.toString(fechaYHoraRecepcion.getYear() + 1900) + ", " +
                Integer.toString(fechaYHoraRecepcion.getHours()) + ":" + minutos + ":" + segundos);
        idCliente.set(pIdCliente);
        asunto.set(pAsunto);
        categoria.set(pCategoria);
        //idTicket++;
        estado.set("PENDIENTE");
    }







}
