package Code;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;
//import Code.conector;

/**
 * Created by Jose Luis Rodriguez on 21/5/2017.
 */

public class Empleado{
    public String email, contraseña, nombre, categoria;
    public Boolean estado;

    public Empleado(String email, String contraseña, String nombre, String categoria) throws IOException {
        this.email = email;
        this.contraseña = contraseña;
        this.nombre = nombre;
        this.categoria = categoria;
    }

    /**
     * Carga los datos de ingreso de cada empleado
     * @param datosIngreso
     * @param salida
     * @return
     * @throws IOException
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