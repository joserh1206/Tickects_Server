package Code;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;

import java.io.*;
import java.util.Date;
import java.util.ArrayList;

/**
 * Clase que maneja la lectura y escritura de los archivos en ClasesBiblioteca
 * @author Randall Delgado
 * @author José Luis Rodríguez
 * @author Óscar Cortés
 */

public class EditorExcel {
    public ArrayList <Ticket> tickets = new ArrayList(); //guarda la lista de libros registrados
    //public static ArrayList <Revista> revistas = new ArrayList();

    /**
     * Método que lee un archivo .xls y mete sus datos al ArrayList de la clase
     * 
     * @param arch Entrada de tipo File, recibe el nombre del archivo o el path en forma de string para leerlo
     */
    
   public ArrayList<Ticket> cargar(File arch){
        InputStream archivo = null;     //inicializa puntero al archivo
        try {
            archivo = new FileInputStream(arch);    //toma el archivo a modificar
            HSSFWorkbook workbook = new HSSFWorkbook(archivo);  //crea un nuevo libro de trabajo
            HSSFSheet pagLibros = workbook.getSheetAt(0);  //selecciona la página a leer
            HSSFRow filaActual;     //crea una fila, esta va a ser utilizada para recorrer la tabla
            int filas = pagLibros.getLastRowNum();     //toma la cantidad de filas
            //System.out.println("Filas: " + filas);

            String fechaYHoraRecepcion = "";
            String idCliente = "";
            String asunto = "";

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
                    }
                    //System.out.println();
                }
                //VentanaPrincipalController.libros.add(libro);  //se agrega al arrayList

                if (filaActual == null)      //evalúa la fila actual fuera del for
                    System.out.println("No hay ningún ticket registrado.");
                else
                    System.out.println("¡Leído correctamente!");

                Date d = new Date(fechaYHoraRecepcion);

                Ticket t = new Ticket (d, idCliente, asunto);     //crea el libro con los parámetros recibidos
                tickets.add(t);
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

    public void guardar(ArrayList<Ticket> ticketsActualizados){
       try{
           FileOutputStream arch = new FileOutputStream("Tickets.xls");
           HSSFWorkbook workbook = new HSSFWorkbook();
           HSSFSheet worksheet = workbook.createSheet("Tickets");

           // index from 0,0... cell A1 is cell(0,0)
           HSSFRow filaTitulo = worksheet.createRow(0);

           HSSFCellStyle cellStyle = workbook.createCellStyle();
           Font f = workbook.createFont();
           f.setBoldweight(Font.BOLDWEIGHT_BOLD);
           cellStyle.setFont(f);

           for (int c = 0; c < 10; c++){
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
                       cell.setCellValue("Categoría");
                       break;
                   case 5:
                       cell.setCellValue("ID de Empleado");
                       break;
                   case 6:
                       cell.setCellValue("Fecha y Hora de Atención");
                       break;
                   case 7:
                       cell.setCellValue("Tiempo");
                       break;
                   case 8:
                       cell.setCellValue("Comentario");
                       break;
                   case 9:
                       cell.setCellValue("Estado");
                       break;
               }
           }

            for (int i = 0; i < ticketsActualizados.size(); i++){
               HSSFRow filaDatos = worksheet.createRow(i+1);
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
                        /*case 3:
                            cell.setCellValue(ticketsActualizados.get(i).idTicket);
                            break;
                        case 4:
                            cell.setCellValue(ticketsActualizados.get(i).categoria.get());
                            break;
                        case 5:
                            cell.setCellValue(ticketsActualizados.get(i).idEmpleado.get());
                            break;
                        case 6:
                            cell.setCellValue(ticketsActualizados.get(i).fechaYHoraAtencion);
                            break;
                        case 7:
                            cell.setCellValue(ticketsActualizados.get(i).tiempo.get());
                            break;
                        case 8:
                            cell.setCellValue(ticketsActualizados.get(i).comentario.get());
                            break;
                        case 9:
                            cell.setCellValue(ticketsActualizados.get(i).estado.get());
                            break;
                        */
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

}

