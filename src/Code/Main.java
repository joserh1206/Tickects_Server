package Code;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;



public class Main extends Application {
    public static boolean conectado = false;
    public static ServerSocket server;
    public static Socket socket;
    public static DataOutputStream salida;
    public static DataInputStream entrada;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXMLs/VentanaIngresoServer.fxml"));
        Parent parent = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setTitle("Server Tickets");
        stage.setResizable(false);
        stage.show();
    }


    public static void main(String[] args) throws IOException {

        EditorExcel x = new EditorExcel();

        ArrayList<Ticket> t = new ArrayList<>();
        t.add(new Ticket(new Date(117, 5, 14, 10, 06, 54), "C-03",
                "Errores", "Por Definir"));
        t.add(new Ticket(new Date(117, 5, 15), "C-09", "Problemas", "Por definir"));
        t.add(new Ticket(new Date(117, 5, 16), "C-12", "Ayuda", "Por definir"));

        x.guardarCategorias(t);

        launch(args);
    }
}
