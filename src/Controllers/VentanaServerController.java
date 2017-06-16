package Controllers;

import Code.*;
//import Code.conector;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTreeTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import static Code.Main.*;
//import static Code.Server.c;
import static Controllers.VentanaServerController.*;

/**
 * Clase de conexión. Utiliza el método call() para determinar el registro de actividad del sistema.
 * @author Randall Delgado
 * @author José Luis Rodríguez
 * @author Óscar Cortés
 */

class conector extends Task<String> {

    public static int idTicket = 0;

    public String datosIngreso[];
    public static String user = "";

    /**
     * Conector entre cliente y servidor. Se debe sobrecargar el método call() de
     * Task<> para que funcione en la conexión respectiva.
     * @return El resultado del trabajo realizado
     * @throws Exception excepción de cualquier clase
     */
    @Override
    protected String call() throws Exception, IOException {
        String mensaje;
        try {
            server = new ServerSocket(9000);
            log = log + "\n Se cargaron los archivos de forma exitosa";
            updateMessage(log);
            socket = server.accept();
            entrada = new DataInputStream(socket.getInputStream());
            salida = new DataOutputStream(socket.getOutputStream());
            mensaje = "Fail;0";
            while (Objects.equals(mensaje, "Fail;0")) {
                mensaje = entrada.readUTF();
                int num = mensaje.indexOf(";");
                datosIngreso = mensaje.split(";");
                Empleado e = new Empleado("admin", "admin", "admin", "admin");
                mensaje = e.ingreso(datosIngreso, salida);
            }
            datosIngreso = mensaje.split(";");
            user = datosIngreso[2];
            log = log + "\n " + user + "  se ha conectado!";
            updateMessage(log);
//            lblActivityLog.setText(lblActivityLog.getText() + "\n " + user + "  se ha conectado!");
            if (Objects.equals(user, "Erika Marin")) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        profe.estado.setValue("Conectado");
                    }
                });
            } else if (Objects.equals(user, "Jose Luis Rodriguez")) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        jose.estado.setValue("Conectado");
                    }
                });
            } else if (Objects.equals(user, "Randall Delgado")) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        randall.estado.setValue("Conectado");
                    }
                });
            }
            boolean primero;
            mensaje = "";
            String envio = "";
//            System.out.println("1");
            while (!Objects.equals(mensaje, "Desconectar")) {
//                System.out.println("2");
                mensaje = entrada.readUTF();
                datosIngreso = mensaje.split(";");
                mensaje = datosIngreso[1];
                if(Objects.equals(mensaje, "Actualizar")){
                    System.out.println("ENTRO2");
                    envio = "";
                    primero = true;
                    int tamanio = EditorExcel.tickets.size();
                    Ticket t;
                    for(int i=0; i<tamanio; i++){
                        System.out.println("ENTRO3");
                        t = EditorExcel.tickets.get(i);
//                        System.out.println(t.categoria+" TCATEGORIA");
                        if(Objects.equals(t.categoria.getValue().toLowerCase(), datosIngreso[0].toLowerCase())){
                            t.idTicket = idTicket;
                            idTicket++;
                            if(!primero) {
                                envio = envio + ";" + t.asunto.getValue() + ":" + t.idCliente.getValue() + ":" + String.valueOf(t.idTicket);
                                System.out.println("ENTRO");
                            }
                            else{
                                envio = t.asunto.getValue() + ":" + t.idCliente.getValue() + ":" + String.valueOf(t.idTicket);
                                primero = false;
                            }
                        }
                    }
                    salida.writeUTF(envio);
                    System.out.println("ENVIO: "+envio);
                }
                mensaje = entrada.readUTF();
                datosIngreso = mensaje.split(";");
                if(Objects.equals(datosIngreso[0], "Resuelto")){
                    String comentario = datosIngreso[1];
                    String id = datosIngreso[2];
                    Ticket t;
                    for(int i=0; i<EditorExcel.tickets.size(); i++){
                        t = EditorExcel.tickets.get(i);
                        if(Objects.equals(t.asunto.getValue(), id)){
                            System.out.println("LLEGOOO");
                            t.comentario.set(comentario);
                            t.estado.set("ATENDIDO");
                        }
                    }
                }
                datosIngreso = mensaje.split(";");
                mensaje = datosIngreso[1];
                //                mensaje = entrada.readUTF();
            }

        } catch (Exception e) {
            mensaje = datosIngreso[0];
            if (Objects.equals(mensaje, "Erika Marin")) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        profe.estado.setValue("Desconectado");
                    }
                });
            } else if (Objects.equals(mensaje, "Jose Luis Rodriguez")) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        jose.estado.setValue("Desconectado");
                    }
                });
            } else if (Objects.equals(mensaje, "Randall Delgado")) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        randall.estado.setValue("Desconectado");
                    }
                });
            }
            log = log + "\n " + mensaje + "  se ha desconectado!";
            updateMessage(log);
        }

        return user;
    }
}

/**
 * Clase controladora de la interfaz. Se encarga de actualizar las tablas, datos, entre otros.
 * @author Randall Delgado
 * @author José Luis Rodríguez
 * @author Óscar Cortés
 */

public class VentanaServerController{

    public static Funcionarios jose = new Funcionarios("José Luis Rodríguez", "Desconectado");
    public static Funcionarios randall = new Funcionarios("Randall Delgado", "Desconectado");
    public static Funcionarios profe = new Funcionarios("Erika Shumann", "Desconectado");

    public static String log = "";
    public EditorExcel x = new EditorExcel();

    @FXML
    private Label lblActivityLog;

    @FXML
    private JFXTreeTableView<Funcionarios> ttvEmpleados;

    @FXML
    private JFXTreeTableView<Ticket> ttvTicketsServer;

    @FXML
    private TextField txfEmpleadoEstadisticas;

    @FXML
    private JFXButton btnConsultarEstadisticas;

    @FXML
    private JFXButton btnDesconectarServer;

    @FXML
    private JFXButton btnConectar;

    @FXML
    private JFXButton btnGuardar;



    @FXML
    void ConsultarEstadisticasServer(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXMLs/VentanaEstadisticas.fxml"));
        Parent parent = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setTitle("Estadisticas");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void CambiarCategoria(ActionEvent event) {

    }

    @FXML
    void Conectar(ActionEvent event) throws IOException {
        btnDesconectarServer.setDisable(false);
        IniciarTabla();
        MostrarTickets();
//            System.out.println("0");
//        Server server = new Server(puerto);
        conector c = new conector();

        lblActivityLog.textProperty().bind(c.messageProperty());

        new Thread(c).start();


//            System.out.println("1");
//            c.iniciar();
//            c.Ingreso();
//            lblActivityLog.setText(lblActivityLog.getText() + "\n " + user + "  se ha conectado!");
//            jose.estado.setValue("Conectado");
//            System.out.println("2");
        btnConectar.setDisable(true);
    }



    @FXML
    void DesconectarServer(ActionEvent event) throws IOException {
        if (Main.conectado = true) {
            //            lblActivityLog.setText(lblActivityLog.getText() + "\n " + log + "  se ha conectado!");
            btnDesconectarServer.setDisable(true);
            System.out.println("1");
            Main.conectado = false;
            btnConectar.setDisable(false);
            //jose.estado.setValue("Desconectado");
        } else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Server");
            alert.setHeaderText("Mensaje del servidor");
            alert.setContentText("El servidor ya se encuentra desactivado");
            alert.showAndWait();
        }
    }
    public void IniciarTabla() {
        JFXTreeTableColumn<Funcionarios, String> colNombre = new JFXTreeTableColumn<>("Funcionario");
        colNombre.setPrefWidth(150);
        colNombre.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Funcionarios, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Funcionarios, String> param) {
                return param.getValue().getValue().nombre;
            }
        });

        JFXTreeTableColumn<Funcionarios, String> colEstado = new JFXTreeTableColumn<>("Estado");
        colEstado.setPrefWidth(150);
        colEstado.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Funcionarios, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Funcionarios, String> param) {
                return param.getValue().getValue().estado;
            }
        });


        ObservableList<Funcionarios> funcionarios = FXCollections.observableArrayList();
        funcionarios.add(jose);
        funcionarios.add(randall);
        funcionarios.add(profe);

        final TreeItem<Funcionarios> root = new RecursiveTreeItem<Funcionarios>(funcionarios, RecursiveTreeObject::getChildren);

        ttvEmpleados.getColumns().setAll(colNombre, colEstado);
        ttvEmpleados.setRoot(root);
        ttvEmpleados.setShowRoot(false);
        ttvEmpleados.autosize();

    }

    public void MostrarTickets() {
        JFXTreeTableColumn<Ticket, String> colFecha = new JFXTreeTableColumn<>("Fecha y Hora de Recepción");
        colFecha.setPrefWidth(170);
        colFecha.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Ticket, String> param) {
                return param.getValue().getValue().strFechaYHoraRecepcion;
            }
        });

        JFXTreeTableColumn<Ticket, String> colIDCliente = new JFXTreeTableColumn<>("ID de Cliente");
        colIDCliente.setPrefWidth(100);
        colIDCliente.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Ticket, String> param) {
                return param.getValue().getValue().idCliente;
            }
        });

        JFXTreeTableColumn<Ticket, String> colAsunto = new JFXTreeTableColumn<>("Asunto");
        colAsunto.setPrefWidth(100);
        colAsunto.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Ticket, String> param) {
                return param.getValue().getValue().asunto;
            }
        });

        JFXTreeTableColumn<Ticket, String> colCategoria = new JFXTreeTableColumn<>("Categoria");
        colCategoria.setPrefWidth(100);
        colCategoria.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Ticket, String> param) {
                return param.getValue().getValue().categoria;
            }
        });

        JFXTreeTableColumn<Ticket, String> colEstado = new JFXTreeTableColumn<>("Estado");
        colEstado.setPrefWidth(100);
        colEstado.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Ticket, String> param) {
                return param.getValue().getValue().estado;
            }
        });

        ObservableList<Ticket> tickets = FXCollections.observableArrayList();
        ObservableList<String> CategoriasTickets = FXCollections.observableArrayList("rojo", "amarillo", "verde");

        File arch = new File("Tickets.xls");
        if (!arch.exists()){
            try {
                arch.createNewFile();
            } catch (IOException ioe) {
                System.out.println("Error al crear el fichero nuevo" + ioe);
            }
        }

        x.tickets = x.cargarPendientes(arch);

        for (int i = 0; i < x.tickets.size(); i++){
            tickets.add(x.tickets.get(i));
        }

        final TreeItem<Ticket> root = new RecursiveTreeItem<Ticket>(tickets, RecursiveTreeObject::getChildren);

        colCategoria.setCellFactory(ComboBoxTreeTableCell.forTreeTableColumn(CategoriasTickets));

        colCategoria.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<Ticket, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<Ticket, String> event) {
                TreeItem<Ticket> EditarTicket = ttvTicketsServer.getTreeItem(event.getTreeTablePosition().getRow());
                EditarTicket.getValue().setCategoria(event.getNewValue());
            }
        });

        ttvTicketsServer.setEditable(true);
        ttvTicketsServer.getColumns().setAll(colFecha, colIDCliente, colAsunto, colCategoria, colEstado);
        ttvTicketsServer.setRoot(root);
        ttvTicketsServer.setShowRoot(false);
        ttvTicketsServer.autosize();

    }


    public void onGuardar(){
        x.guardarCategorias(x.tickets);
        System.out.println("Categorías guardadas");
        MostrarTickets();
    }


    public static class Funcionarios extends RecursiveTreeObject<Funcionarios>{

        public StringProperty nombre;
        public StringProperty estado;

        public Funcionarios (String nombre, String estado){
            this.nombre = new SimpleStringProperty(nombre);
            this.estado = new SimpleStringProperty(estado);
        }

    }

}