/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlinventario.items;

import controlinventario.Interfaces.ExcelModel;
import controlinventario.inventario.InventarioController;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author auxsistemas3
 */
public class ItemsViewController implements Initializable {
    @FXML
    private TableColumn<ExcelModel, String> clmnDescripcion;
    @FXML
    private TableColumn<ExcelModel, String> clmnFechaDeVencimiento;
    @FXML
    private TableColumn<ExcelModel, Integer> clmnItem;
    @FXML
    private TableColumn<ExcelModel, String> clmnLote;
    @FXML
    private TableView<ExcelModel> tblItems;
    @FXML
    private TableColumn<ExcelModel, String> clmnLaboratorio;
    @FXML
    private TableColumn<ExcelModel, Integer> clmnCantidad;
    @FXML
    private TableColumn<ExcelModel, String> clmnLinea;
    @FXML
    private TableColumn<ExcelModel, Integer> clmnCentroDeCosto;
    @FXML
    private TableColumn<ExcelModel, String> clmnSede;
    @FXML
    private TableColumn<ExcelModel, String> clmnTipo;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    public void listar(ObservableList<ExcelModel> data) {
        try {
            tblItems.getItems().clear();
            if (!data.isEmpty()) {
                System.err.println(data.isEmpty());
                tblItems.setItems(data);
                clmnItem.setCellValueFactory(new PropertyValueFactory<>("item"));               
                clmnDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
                clmnLaboratorio.setCellValueFactory(new PropertyValueFactory<>("laboratorio"));
                clmnLote.setCellValueFactory(new PropertyValueFactory<>("lote"));
                clmnFechaDeVencimiento.setCellValueFactory(new PropertyValueFactory<>("fechaDeVencimiento"));
                clmnCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
                clmnLinea.setCellValueFactory(new PropertyValueFactory<>("linea"));
                clmnCentroDeCosto.setCellValueFactory(new PropertyValueFactory<>("centroDeCosto"));
                clmnSede.setCellValueFactory(new PropertyValueFactory<>("sede"));
                clmnTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
            }
        } catch (Exception ex) {
            Logger.getLogger(ItemsViewController.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

}
