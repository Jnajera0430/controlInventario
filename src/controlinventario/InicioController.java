/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlinventario;

import controlinventario.facturacion.FacturacionController;
import controlinventario.inventario.InventarioController;
import controlinventario.notasDeCredito.NotasDeCreditoController;
import controlinventario.reportes.ReportesController;
import java.io.IOException;
import java.sql.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author auxsistemas3
 */
public class InicioController implements Initializable {

    ConexionMysql conexion = new ConexionMysql();
    private Connection conn = conexion.getConn();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private Button btnSubirFacturacion;

    @FXML
    private Button btnSubirInventario;

    @FXML
    private Button btnSubirNotasDeCreditos;

    @FXML
    private Button btnVerReportes;

    @FXML
    void eventSubirFacturacion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/controlinventario/facturacion/facturacionView.fxml"));
            Parent root = loader.load();
            FacturacionController  controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(InicioController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void eventSubirInventario(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/controlinventario/inventario/inventarioView.fxml"));
            Parent root = loader.load();
            InventarioController  controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(InicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void eventSubirNotasDeCredito(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/controlinventario/notasDeCredito/notasDeCreditoView.fxml"));
            Parent root = loader.load();
            NotasDeCreditoController  controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(InicioController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void eventVerReporte(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/controlinventario/reportes/reportesView.fxml"));
            Parent root = loader.load();
            ReportesController  controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(InicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
