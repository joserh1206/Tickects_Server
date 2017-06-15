package Controllers;

import Code.Empleado;
import Code.Main;
import Code.Server;
//import Code.conector;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import static Code.Main.*;
//import static Code.Server.c;
import static Controllers.VentanaServerController.*;

class conector extends Task<String> {

    public String datosIngreso[];
    public static String user = "";

    /**
     * Invoked when the Task is executed, the call method must be overridden and
     * implemented by subclasses. The call method actually performs the
     * background thread logic. Only the updateProgress, updateMessage, updateValue and
     * updateTitle methods of Task may be called from code within this method.
     * Any other interaction with the Task from the background thread will result
     * in runtime exceptions.
     *
     * @return The result of the background work, if any.
     * @throws Exception an unhandled exception which occurred during the
     *                   background operation
     */
    @Override
    protected String call() throws Exception {
        try {
            server = new ServerSocket(9000);
            socket = server.accept();
            entrada = new DataInputStream(socket.getInputStream());
            salida = new DataOutputStream(socket.getOutputStream());
            //System.out.println("Conexion establecida exitosamente");
            String mensaje = "Fail;0";
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

            mensaje = "Conectar";
            System.out.println("1");
            while (!Objects.equals(mensaje, "Desconectar")) {
                System.out.println("2");
                mensaje = entrada.readUTF();
                int num = mensaje.indexOf(";");
                datosIngreso = mensaje.split(";");
                mensaje = datosIngreso[1];
                System.out.println(mensaje + " MSJ");
            }
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Se produjo un error al conectar");
        }

        return user;
    }
}


public class VentanaServerController{

    public static Funcionarios jose = new Funcionarios("José Luis Rodríguez", "Desconectado");
    public static Funcionarios randall = new Funcionarios("Randall Delgado", "Desconectado");
    public static Funcionarios profe = new Funcionarios("Erika Shumann", "Desconectado");

    public static String log = "";

    @FXML
    private Label lblActivityLog;

    @FXML
    private JFXTreeTableView<Funcionarios> ttvEmpleados;

    @FXML
    private JFXTreeTableView<?> ttvTicketsServer;

    @FXML
    private TextField txfEmpleadoEstadisticas;

    @FXML
    private JFXButton btnConsultarEstadisticas;

    @FXML
    private JFXButton btnDesconectarServer;

    @FXML
    private JFXButton btnConectar;

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
    void Conectar(ActionEvent event) throws IOException {
        btnDesconectarServer.setDisable(false);
        IniciarTabla();
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


    public static class Funcionarios extends RecursiveTreeObject<Funcionarios>{

        public StringProperty nombre;
        public StringProperty estado;

        public Funcionarios (String nombre, String estado){
            this.nombre = new SimpleStringProperty(nombre);
            this.estado = new SimpleStringProperty(estado);
        }

    }

}