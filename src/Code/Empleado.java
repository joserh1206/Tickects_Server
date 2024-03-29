package Code;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
//import Code.conector;

/**
 * Clase de empleado, que que permite el ingreso al sistema y conectarse con el servidor.
 * @author Randall Delgado
 * @author José Luis Rodríguez
 * @author Óscar Cortés
 */

public class Empleado{
    public String email, contraseña, nombre, categoria;
    public Boolean estado;

    public Empleado(){

    }

    public Empleado(String email, String contraseña, String nombre, String categoria) throws IOException {
        this.email = email;
        this.contraseña = contraseña;
        this.nombre = nombre;
        this.categoria = categoria;
    }

    /**
     * Método que permite el ingreso al sistema para cada empleado
     * @param datosIngreso (datos que ingresa el empleado para hacer login)
     * @param salida (permite escribir la respuesta de la conexión)
     * @return String (retorna la respuesta de conexión al servidor
     * @throws IOException (en caso de haber error al conectar)
     */
    public String ingreso(String datosIngreso[], DataOutputStream salida) throws IOException {

        Empleado profe = new Empleado("eshuman@tec.ac.cr", "2014032210", "Erika Marin",
                "ROJO");
        Empleado jose = new Empleado("joserh1206@gmail.com", "2016093725", "Jose Luis Rodriguez",
                "VERDE");
        Empleado randall = new Empleado("rdelgadom23@gmail.com", "2016238520", "Randall Delgado",
                "AMARILLO");

        Empleado empleados[] = new Empleado[3];

        empleados[0] = profe;
        empleados[1] = jose;
        empleados[2] = randall;

        String respuesta = "Fail;0";
        for (Empleado empleado : empleados) {
            if (Objects.equals(empleado.email, datosIngreso[0])) {
                if (Objects.equals(empleado.contraseña, datosIngreso[1])) {
                    respuesta = "Exito;" + empleado.categoria+";"+empleado.nombre;
                }
            }
        }
        salida.writeUTF(respuesta);
        return respuesta;
    }
}