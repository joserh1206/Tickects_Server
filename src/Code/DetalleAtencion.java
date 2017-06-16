package Code;

import java.util.Calendar;

/**
 * Created by Òscar Cortes on 15/06/2017.
 */
public class DetalleAtencion {
    public String usuario, comentario, tipoTicket;
    public int tInvertido;
    public Calendar fecha, hora;

    public DetalleAtencion(String pUsuario, String pComentario, String pTipoTicket, int pTInvertido, Calendar pFecha, Calendar pHora){
        this.usuario = pUsuario;
        this.comentario = pComentario;
        this.tipoTicket = pTipoTicket;
        this.tInvertido = pTInvertido;
        this.fecha = pFecha;
        this.hora = pHora;
    }

    /**
     * Devuelve la metrica del detalleAtencion del que se solicito
     * @return
     */
    public int obtenerMetrica(){
        if (tipoTicket == "Verde"){
            return tInvertido-10;
        } else if (tipoTicket == "Amarillo"){
            return  tInvertido*2-12;
        } else { //If rojo
            return tInvertido*3-15;

        }
    }
}
