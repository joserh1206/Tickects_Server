package Code;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import Code.conector;

/**
 * Created by Jose Luis Rodriguez on 21/5/2017.
 */

public class Empleado extends conector {
    public String email, contraseña, nombre, categoria;
    public Boolean estado;

    public Empleado(String email, String contraseña, String nombre, String categoria) {
        this.email = email;
        this.contraseña = contraseña;
        this.nombre = nombre;
        this.categoria = categoria;
    }

    public void ingreso(String datosIngreso[], DataOutputStream salida) throws IOException {

        //System.out.println("13");

        Empleado profe = new Empleado("eshuman@tec.ac.cr", "201403221", "Erika Marin",
                "ROJO");
        Empleado jose = new Empleado("joserh1206@gmail.com", "2016093725", "Joe Luis Rodriguez",
                "VERDE");
        Empleado randall = new Empleado("randal@gmail.com", "20160000", "Randall Delgado",
                "AMARILLO");

        Empleado empleados[] = new Empleado[3];
        //System.out.println("14");

        empleados[0] = profe;
        empleados[1] = jose;
        empleados[2] = randall;

        //System.out.println("15");

        //System.out.println("Datos ingreso");
        //System.out.println(datosIngreso[0]);
        //System.out.println(datosIngreso[0].length());
        //System.out.println(datosIngreso[1]);
        //System.out.println(datosIngreso[1].length());

        //System.out.println("Datos usuario");
        //System.out.println(empleados[0].email);
        //System.out.println(empleados[0].contraseña);
        //System.out.println(empleados[1].email);
        //System.out.println(empleados[1].contraseña);


        String respuesta = "Fail;0";
        //System.out.println("16");
        for (Empleado empleado : empleados) {
            //System.out.println("17");
            if (Objects.equals(empleado.email, datosIngreso[0])) {
                //System.out.println("18");
                if (Objects.equals(empleado.contraseña, datosIngreso[1])) {
                    //System.out.println("19");
                    //flag = true;
                    respuesta = "Exito;" + empleado.categoria;
                    //System.out.println("Si encontro el usuario");
                } else {
                    //System.out.println("No encontro contraseña del usuario");
                }
            } else {
                //System.out.println("No se encontró el nombre de usuario");
            }
        }

        salida.writeUTF(respuesta);
        //return flag;
    }
}