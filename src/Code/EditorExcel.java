package Code;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;

import java.io.*;
import java.util.Date;
import java.util.ArrayList;

/**
 * Clase que maneja la lectura y escritura de los archivos en el servidor
 * @author Randall Delgado
 * @author José Luis Rodríguez
 * @author Óscar Cortés
 */

public class EditorExcel {
    public static ArrayList <Ticket> tickets = new ArrayList(); //guarda la lista de libros registrados
//    public static int tamaniof;
//    public static int tamanioc;
    //public static ArrayList <Revista> revistas = new ArrayList();

    /**
     * Método que carga tickets pendientes o sin estado
     * @param arch (archivo del cual se cargan los tickets)
     * @return un ArrayList con los tickets
     */
   public ArrayList<Ticket> cargarPendientes(File arch){
        InputStream archivo = null;     //inicializa puntero al archivo
        try {
            archivo = new FileInputStream(arch);    //toma el archivo a modificar
            HSSFWorkbook workbook = new HSSFWorkbook(archivo);  //crea un nuevo libro de trabajo
            HSSFSheet pagLibros = workbook.getSheetAt(0);  //selecciona la página a leer
            HSSFRow filaActual;     //crea una fila, esta va a ser utilizada para recorrer la tabla
            int filas = pagLibros.getLastRowNum();     //toma la cantidad de filas
//            tamaniof = filas;
            //System.out.println("Filas: " + filas);

            String fechaYHoraRecepcion = "";
            String idCliente = "";
            String asunto = "";
            String estado = "";

            tickets.clear();             //limpia el arrayList para evitar duplicados, no hay problema pues un libro no se debería poder borrar del sistema
            for (int f = 1; f <= filas; f++) {
                filaActual = pagLibros.getRow(f);      //se ubica en la posición f de las filas
                if (filaActual == null)         //para evitar que se caiga si está vacío
                    break;
                //System.out.println(filaActual.getRowNum());
                int columnas = filaActual.getLastCellNum();         //toma el número de columnas
//                tamanioc = columnas;
                //System.out.println("Columnas: " + columnas);
                //System.out.println("Fila: " + f);

                for (int c = 0; c < columnas; c++) {
                    switch (c) {         //evalúa cada posible valor para c
                        case 0:
                            fechaYHoraRecepcion = filaActual.getCell(c).getStringCellValue();
                            break;
                        case 1:
                            idCliente = filaActual.getCell(c).getStringCellValue();
                            break;
                        case 2:
                            asunto = filaActual.getCell(c).getStringCellValue();
                            break;
                        case 8:
                            if (filaActual.getCell(c).getStringCellValue() != "" || filaActual.getCell(c).getStringCellValue() != "PENDIENTE")
                                estado = filaActual.getCell(c).getStringCellValue();
                            break;
                    }
                    //System.out.println();
                }
                //VentanaPrincipalController.libros.add(libro);  //se agrega al arrayList

                if (filaActual == null)      //evalúa la fila actual fuera del for
                    System.out.println("No hay ningún ticket registrado.");
                else
                    System.out.println("¡Leído correctamente!");

                Date d = new Date(fechaYHoraRecepcion);

                if (estado == "") {
                    Ticket t = new Ticket(d, idCliente, asunto, "Categoria");     //crea el ticket con los parámetros recibidos
                    tickets.add(t);
                }
            }

        } catch (FileNotFoundException fileNotFoundException) {         //error si no encuentra el archivo
            System.out.println("No se encontró el fichero: " + fileNotFoundException);
        } catch (IOException ex) {          //reporta algún error inesperado
            System.out.println("Error al procesar el fichero: " + ex);
        } finally {
            try {
                archivo.close();        //CERRARLO, fundamental
             } catch (IOException ex) {         //en caso de que haya error al cerrarlo
                System.out.println("Error al procesar el fichero después de cerrarlo: " + ex);
            }
        }
        return tickets;
    }

    /**
     * Método que guarda los tickets en diferentes pestañas del archivo de Excel según su categoría
     * @param ticketsActualizados (ArrayList que se desea guardar)
     */
    public void guardarCategorias(ArrayList<Ticket> ticketsActualizados){
       try{
           FileOutputStream arch = new FileOutputStream("Tickets.xls");
           HSSFWorkbook workbook = new HSSFWorkbook();
           HSSFSheet wsInbox = workbook.createSheet("INBOX");

           // index from 0,0... cell A1 is cell(0,0)
           HSSFRow filaTitulo = wsInbox.createRow(0);

           HSSFCellStyle cellStyle = workbook.createCellStyle();
           Font f = workbook.createFont();
           f.setBoldweight(Font.BOLDWEIGHT_BOLD);
           cellStyle.setFont(f);

           for (int c = 0; c < 9; c++){
               HSSFCell cell = filaTitulo.createCell(c);
               cell.setCellStyle(cellStyle);
               switch(c){
                   case 0:
                       cell.setCellValue("Fecha y Hora de Recepción");
                       break;
                   case 1:
                       cell.setCellValue("ID de Cliente");
                       break;
                   case 2:
                       cell.setCellValue("Asunto");
                       break;
                   case 3:
                       cell.setCellValue("ID de Ticket");
                       break;
               }
           }

            for (int i = 0; i < ticketsActualizados.size(); i++){
               HSSFRow filaDatos = wsInbox.createRow(i+1);
                for (int j = 0; j < 10; j++){
                    HSSFCell cell = filaDatos.createCell(j);

                    switch(j){
                        case 0:
                            cell.setCellValue(ticketsActualizados.get(i).fechaYHoraRecepcion.toString());
                            break;
                        case 1:
                            cell.setCellValue(ticketsActualizados.get(i).idCliente.get());
                            break;
                        case 2:
                            cell.setCellValue(ticketsActualizados.get(i).asunto.get());
                            break;
                        case 3:
                            cell.setCellValue(ticketsActualizados.get(i).idTicket);
                            break;
                    }
                }
            }

           HSSFSheet wsVerde = workbook.createSheet("Verde (leve)");

           filaTitulo = wsVerde.createRow(0);

           for (int c = 0; c < 9; c++){
               HSSFCell cell = filaTitulo.createCell(c);
               cell.setCellStyle(cellStyle);
               switch(c){
                   case 0:
                       cell.setCellValue("Fecha y Hora de Recepción");
                       break;
                   case 1:
                       cell.setCellValue("ID de Cliente");
                       break;
                   case 2:
                       cell.setCellValue("Asunto");
                       break;
                   case 3:
                       cell.setCellValue("ID de Ticket");
                       break;
                   case 4:
                       cell.setCellValue("ID de Empleado");
                       break;
                   case 5:
                       cell.setCellValue("Fecha y Hora de Atención");
                       break;
                   case 6:
                       cell.setCellValue("Tiempo");
                       break;
                   case 7:
                       cell.setCellValue("Comentario");
                       break;
                   case 8:
                       cell.setCellValue("Estado");
                       break;
               }
           }

           for (int i = 0; i < ticketsActualizados.size(); i++){
               if (ticketsActualizados.get(i).categoria.get() == "Verde") {
                   HSSFRow filaDatos = wsVerde.createRow(i + 1);
                   for (int j = 0; j < 9; j++) {
                       HSSFCell cell = filaDatos.createCell(j);
                       switch (j) {
                           case 0:
                               cell.setCellValue(ticketsActualizados.get(i).fechaYHoraRecepcion.toString());
                               break;
                           case 1:
                               cell.setCellValue(ticketsActualizados.get(i).idCliente.get());
                               break;
                           case 2:
                               cell.setCellValue(ticketsActualizados.get(i).asunto.get());
                               break;
                           case 3:
                               cell.setCellValue(ticketsActualizados.get(i).idTicket);
                               break;
                           case 4:
                               cell.setCellValue(ticketsActualizados.get(i).idEmpleado.get());
                               break;
                           case 5:
                               if (ticketsActualizados.get(i).fechaYHoraAtencion == null)
                                   cell.setCellValue("");
                               else
                                   cell.setCellValue(ticketsActualizados.get(i).fechaYHoraAtencion.toString());
                               break;
                           case 6:
                               cell.setCellValue(ticketsActualizados.get(i).tiempo.get());
                               break;
                           case 7:
                               cell.setCellValue(ticketsActualizados.get(i).comentario.get());
                               break;
                           case 8:
                               cell.setCellValue(ticketsActualizados.get(i).estado.get());
                               break;
                       }
                   }
               }
           }

           HSSFSheet wsAmarilla = workbook.createSheet("Amarillo (medio)");

           filaTitulo = wsAmarilla.createRow(0);

           for (int c = 0; c < 9; c++){
               HSSFCell cell = filaTitulo.createCell(c);
               cell.setCellStyle(cellStyle);
               switch(c){
                   case 0:
                       cell.setCellValue("Fecha y Hora de Recepción");
                       break;
                   case 1:
                       cell.setCellValue("ID de Cliente");
                       break;
                   case 2:
                       cell.setCellValue("Asunto");
                       break;
                   case 3:
                       cell.setCellValue("ID de Ticket");
                       break;
                   case 4:
                       cell.setCellValue("ID de Empleado");
                       break;
                   case 5:
                       cell.setCellValue("Fecha y Hora de Atención");
                       break;
                   case 6:
                       cell.setCellValue("Tiempo");
                       break;
                   case 7:
                       cell.setCellValue("Comentario");
                       break;
                   case 8:
                       cell.setCellValue("Estado");
                       break;
               }
           }

           for (int i = 0; i < ticketsActualizados.size(); i++){
               if (ticketsActualizados.get(i).categoria.get() == "Amarillo") {
                   HSSFRow filaDatos = wsAmarilla.createRow(i + 1);
                   for (int j = 0; j < 9; j++) {
                       HSSFCell cell = filaDatos.createCell(j);
                       switch (j) {
                           case 0:
                               cell.setCellValue(ticketsActualizados.get(i).fechaYHoraRecepcion.toString());
                               break;
                           case 1:
                               cell.setCellValue(ticketsActualizados.get(i).idCliente.get());
                               break;
                           case 2:
                               cell.setCellValue(ticketsActualizados.get(i).asunto.get());
                               break;
                           case 3:
                               cell.setCellValue(ticketsActualizados.get(i).idTicket);
                               break;
                           case 4:
                               cell.setCellValue(ticketsActualizados.get(i).idEmpleado.get());
                               break;
                           case 5:
                               if (ticketsActualizados.get(i).fechaYHoraAtencion == null)
                                   cell.setCellValue("");
                               else
                                   cell.setCellValue(ticketsActualizados.get(i).fechaYHoraAtencion.toString());
                               break;
                           case 6:
                               cell.setCellValue(ticketsActualizados.get(i).tiempo.get());
                               break;
                           case 7:
                               cell.setCellValue(ticketsActualizados.get(i).comentario.get());
                               break;
                           case 8:
                               cell.setCellValue(ticketsActualizados.get(i).estado.get());
                               break;
                       }
                   }
               }
           }

           HSSFSheet wsRoja = workbook.createSheet("Rojo (urgente)");

           filaTitulo = wsRoja.createRow(0);

           for (int c = 0; c < 9; c++){
               HSSFCell cell = filaTitulo.createCell(c);
               cell.setCellStyle(cellStyle);
               switch(c){
                   case 0:
                       cell.setCellValue("Fecha y Hora de Recepción");
                       break;
                   case 1:
                       cell.setCellValue("ID de Cliente");
                       break;
                   case 2:
                       cell.setCellValue("Asunto");
                       break;
                   case 3:
                       cell.setCellValue("ID de Ticket");
                       break;
                   case 4:
                       cell.setCellValue("ID de Empleado");
                       break;
                   case 5:
                       cell.setCellValue("Fecha y Hora de Atención");
                       break;
                   case 6:
                       cell.setCellValue("Tiempo");
                       break;
                   case 7:
                       cell.setCellValue("Comentario");
                       break;
                   case 8:
                       cell.setCellValue("Estado");
                       break;
               }
           }

           for (int i = 0; i < ticketsActualizados.size(); i++){
               if (ticketsActualizados.get(i).categoria.get() == "Rojo") {
                   HSSFRow filaDatos = wsRoja.createRow(i + 1);
                   for (int j = 0; j < 9; j++) {
                       HSSFCell cell = filaDatos.createCell(j);
                       switch (j) {
                           case 0:
                               cell.setCellValue(ticketsActualizados.get(i).fechaYHoraRecepcion.toString());
                               break;
                           case 1:
                               cell.setCellValue(ticketsActualizados.get(i).idCliente.get());
                               break;
                           case 2:
                               cell.setCellValue(ticketsActualizados.get(i).asunto.get());
                               break;
                           case 3:
                               cell.setCellValue(ticketsActualizados.get(i).idTicket);
                               break;
                           case 4:
                               cell.setCellValue(ticketsActualizados.get(i).idEmpleado.get());
                               break;
                           case 5:
                               if (ticketsActualizados.get(i).fechaYHoraAtencion == null)
                                   cell.setCellValue("");
                               else
                                   cell.setCellValue(ticketsActualizados.get(i).fechaYHoraAtencion.toString());
                               break;
                           case 6:
                               cell.setCellValue(ticketsActualizados.get(i).tiempo.get());
                               break;
                           case 7:
                               cell.setCellValue(ticketsActualizados.get(i).comentario.get());
                               break;
                           case 8:
                               cell.setCellValue(ticketsActualizados.get(i).estado.get());
                               break;
                       }
                   }
               }
           }

           workbook.write(arch);

           System.out.println("Datos guardados");

           arch.close();

       } catch (FileNotFoundException e) {
           System.out.println("Archivo no encontrado");
       } catch (IOException e) {
           System.out.println("Error: " + e);
       }
   }

    /**
     * Método que carga tickets de la categoría indicada
     * @param arch (archivo del cual se cargan los tickets)
     * @param categoria (categoría deseada por el usuario)
     * @return un ArrayList con los tickets
     */
    public ArrayList<Ticket> cargarCategoria(File arch, String categoria){
        InputStream archivo = null;     //inicializa puntero al archivo
        try {
            archivo = new FileInputStream(arch);    //toma el archivo a modificar
            HSSFWorkbook workbook = new HSSFWorkbook(archivo);  //crea un nuevo libro de trabajo
            HSSFSheet pagLibros = null;

            switch (categoria) {
                case "Verde":
                    pagLibros = workbook.getSheetAt(1);  //selecciona la página a leer
                    break;
                case "Amarillo":
                    pagLibros = workbook.getSheetAt(2);  //selecciona la página a leer
                    break;
                case "Rojo":
                    pagLibros = workbook.getSheetAt(3);  //selecciona la página a leer
                    break;
            }

            HSSFRow filaActual;     //crea una fila, esta va a ser utilizada para recorrer la tabla
            int filas = pagLibros.getLastRowNum();     //toma la cantidad de filas
            //System.out.println("Filas: " + filas);

            String fechaYHoraRecepcion = "";
            String idCliente = "";
            String asunto = "";
            int idTicket = 0;
            String estado = "";

            tickets.clear();             //limpia el arrayList para evitar duplicados, no hay problema pues un libro no se debería poder borrar del sistema
            for (int f = 1; f <= filas; f++) {
                filaActual = pagLibros.getRow(f);      //se ubica en la posición f de las filas
                if (filaActual == null)         //para evitar que se caiga si está vacío
                    break;
                //System.out.println(filaActual.getRowNum());
                int columnas = filaActual.getLastCellNum();         //toma el número de columnas
                //System.out.println("Columnas: " + columnas);
                //System.out.println("Fila: " + f);

                for (int c = 0; c < columnas; c++) {
                    switch (c) {         //evalúa cada posible valor para c
                        case 0:
                            fechaYHoraRecepcion = filaActual.getCell(c).getStringCellValue();
                            break;
                        case 1:
                            idCliente = filaActual.getCell(c).getStringCellValue();
                            break;
                        case 2:
                            asunto = filaActual.getCell(c).getStringCellValue();
                            break;
                        case 3:
                            idTicket = Integer.parseInt(filaActual.getCell(c).getStringCellValue());
                            break;
                        case 8:
                            if (filaActual.getCell(c).getStringCellValue() != "PENDIENTE")
                                estado = filaActual.getCell(c).getStringCellValue();
                            break;

                    }
                    //System.out.println();
                }
                //VentanaPrincipalController.libros.add(libro);  //se agrega al arrayList

                if (filaActual == null)      //evalúa la fila actual fuera del for
                    System.out.println("No hay ningún ticket registrado.");
                else
                    System.out.println("¡Leído correctamente!");

                Date d = new Date(fechaYHoraRecepcion);

                if (estado == "") {
                    Ticket t = new Ticket(d, idCliente, asunto, "Categoria");     //crea el ticket con los parámetros recibidos
                    tickets.add(t);
                }
            }

        } catch (FileNotFoundException fileNotFoundException) {         //error si no encuentra el archivo
            System.out.println("No se encontró el fichero: " + fileNotFoundException);
        } catch (IOException ex) {          //reporta algún error inesperado
            System.out.println("Error al procesar el fichero: " + ex);
        } finally {
            try {
                archivo.close();        //CERRARLO, fundamental
            } catch (IOException ex) {         //en caso de que haya error al cerrarlo
                System.out.println("Error al procesar el fichero después de cerrarlo: " + ex);
            }
        }
        return tickets;
    }

}

