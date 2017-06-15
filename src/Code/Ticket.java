package Code;

import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

/**
 * Created by Randall on 15/06/2017.
 */
public class Ticket {

    public Date fechaYHoraRecepcion;
    public SimpleStringProperty idCliente = new SimpleStringProperty();
    public SimpleStringProperty asunto = new SimpleStringProperty();
    public static int idTicket = 0;
    public SimpleStringProperty categoria = new SimpleStringProperty();
    public SimpleStringProperty idEmpleado = new SimpleStringProperty();
    public Date fechaYHoraAtencion;
    public SimpleStringProperty tiempo = new SimpleStringProperty();
    public SimpleStringProperty comentario = new SimpleStringProperty();
    public SimpleStringProperty estado = new SimpleStringProperty();

    public Ticket(Date pFechaYHoraRecepcion, String pIdCliente, String pAsunto, int pIdTicket, String pCategoria, String pIdEmpleado, Date pFechaYHoraAtencion, String pTiempo, String pComentario, String pEstado){
        fechaYHoraRecepcion = pFechaYHoraRecepcion;
        idCliente.set(pIdCliente);
        asunto.set(pAsunto);
        idTicket = pIdTicket;
        categoria.set(pCategoria);
        idEmpleado.set(pIdEmpleado);
        fechaYHoraAtencion = pFechaYHoraAtencion;
        tiempo.set(pTiempo);
        comentario.set(pComentario);
        estado.set(pEstado);
    }

    public Ticket(Date pFechaYHoraRecepcion, String pIdCliente, String pAsunto){
        fechaYHoraRecepcion = pFechaYHoraRecepcion;
        idCliente.set(pIdCliente);
        asunto.set(pAsunto);
        idTicket++;
        estado.set("PENDIENTE");
    }







}
