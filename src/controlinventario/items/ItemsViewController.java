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
    private TableColumn<ExcelModel, Integer> clmnCantidadPorItem;

    @FXML
    private TableColumn<ExcelModel, String> clmnDescripcion;

    @FXML
    private TableColumn<ExcelModel, String> clmnFechaDeVencimiento;

    @FXML
    private TableColumn<ExcelModel, String> clmnItem;

    @FXML
    private TableColumn<ExcelModel, String> clmnLote;

    @FXML
    private TableColumn<ExcelModel, String> clmnNombre;

    @FXML
    private TableView<ExcelModel> tblItems;

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
                tblItems.setItems(data);
                clmnItem.setCellValueFactory(new PropertyValueFactory<>("item"));
                clmnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                clmnDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
                clmnLote.setCellValueFactory(new PropertyValueFactory<>("lote"));
                clmnFechaDeVencimiento.setCellValueFactory(new PropertyValueFactory<>("fechaDeVencimiento"));
                clmnCantidadPorItem.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
            }
        } catch (Exception ex) {
            Logger.getLogger(ItemsViewController.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

}
