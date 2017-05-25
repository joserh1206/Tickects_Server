package Code;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;



public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        /*FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXMLs/VentanaIngresoServer.fxml"));
        Parent parent = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setTitle("Server Tickets");
        stage.setResizable(false);
        stage.show();*/
    }


    public static void main(String[] args) throws IOException {

        conector c = new conector();
        System.out.println("Servidor iniciado correctamente!");
        System.out.println("Esperando conexion con el cliente...");
        c.iniciar();

        //String n = "Jose Luis";
        //String arreglo[] = n.split(" ");
        //System.out.println(arreglo[0]);
        //System.out.println(arreglo[1]);

        launch(args);
    }
}
