package controlinventario;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author auxsistemas3
 */
public class Main extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //Metodo que se encarga de abrir la vista principal
        //se crea un pane principal y se carga la pantalla
        AnchorPane root = (AnchorPane) FXMLLoader.load(
                getClass().getResource("Inicio.fxml"));
        //Se crea la ecena
        Scene scene = new Scene(root);
        //Se crea y se muestra el escenario
        stage.setScene(scene);
        stage.show();
    }

}
