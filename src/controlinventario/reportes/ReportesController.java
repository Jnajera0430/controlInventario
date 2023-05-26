/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlinventario.reportes;

import controlinventario.Interfaces.ReporteModel;
import controlinventario.absControlInventario.AbsControlInventario;
import controlinventario.absControlInventario.AbsSentenciasSQL;
import controlinventario.items.ItemsViewController;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author auxsistemas3
 */
public class ReportesController extends AbsControlInventario implements Initializable {
    ObservableList<ReporteModel> dataReport = FXCollections.observableArrayList();
    AbsSentenciasSQL sentencias = new AbsSentenciasSQL();
    @FXML
    private ComboBox<?> selectMesInicial;
    @FXML
    private ComboBox<?> selectMesFinal;
    @FXML
    private Button btnGenerarReporte;
    @FXML
    private CheckBox checkAllYear;
    @FXML
    private Button btnLimpiarFormulario;
    @FXML
    private RadioButton btnBoxAllTypeDatos;
    @FXML
    private RadioButton btnBoxFacturas;
    @FXML
    private RadioButton btnBoxNotasDeCredito;
    @FXML
    private TextField txtBuscarList;
    @FXML
    private TableView<ReporteModel> tblReportes;
    @FXML
    private TableColumn<ReporteModel, Integer> clmnItem;
    @FXML
    private TableColumn<ReporteModel, String> clmnDescripcion;
    @FXML
    private TableColumn<ReporteModel, String> clmnLaboratorio;
    @FXML
    private TableColumn<ReporteModel, String> clmnLote;
    @FXML
    private TableColumn<ReporteModel, String> clmnFechaDeVencimiento;
    @FXML
    private TableColumn<ReporteModel, String> clmnLinea;
    @FXML
    private TableColumn<ReporteModel, Integer> clmnCentroDeCosto;
    @FXML
    private TableColumn<ReporteModel, String> clmnSede;
    @FXML
    private TableColumn<ReporteModel, String> clmnTipo;
    @FXML
    private TableColumn<ReporteModel, Integer> clmnCantidadDeInventario;
    @FXML
    private TableColumn<ReporteModel, Integer> clmnCantidadFactura;
    @FXML
    private TableColumn<ReporteModel, Integer> clmnCantidadNotasDeCredito;
    @FXML
    private TableColumn<ReporteModel, Integer> clmnCantidadConsumo;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        valoresPredeterminado();
        desabilitarCampos();
        listarMeses();
    }

    @FXML
    private void eventGenerarReporte(ActionEvent event) {
    }

    @FXML
    private void eventLimpiarFormulario(ActionEvent event) {
    }

    public void valoresPredeterminado() {
        checkAllYear.setSelected(true);
        btnBoxAllTypeDatos.setSelected(true);
    }

    public void desabilitarCampos() {
        if (checkAllYear.isSelected()) {
            selectMesInicial.setDisable(true);
            selectMesFinal.setDisable(true);
        }
        checkAllYear.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                selectMesInicial.setDisable(false);
                selectMesFinal.setDisable(false);
            } else {
                selectMesInicial.setDisable(true);
                selectMesFinal.setDisable(true);
            }
            eventOnChangedQueGeneraElReporte();
        });

        selectMesInicial.setOnAction(event -> {
            var selectedValue = selectMesInicial.getSelectionModel().getSelectedItem();
            eventOnChangedQueGeneraElReporte();
        });

        selectMesFinal.setOnAction(event -> {
            var selectedValue = selectMesFinal.getSelectionModel().getSelectedItem();
            eventOnChangedQueGeneraElReporte();
        });

        btnBoxAllTypeDatos.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                btnBoxFacturas.setSelected(false);
                btnBoxNotasDeCredito.setSelected(false);
            }
        });

        btnBoxFacturas.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                btnBoxAllTypeDatos.setSelected(false);
                btnBoxNotasDeCredito.setSelected(false);
            }
        });

        btnBoxNotasDeCredito.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                btnBoxFacturas.setSelected(false);
                btnBoxAllTypeDatos.setSelected(false);
            }
        });
        eventOnChangedQueGeneraElReporte();
    }

    public void eventOnChangedQueGeneraElReporte() {        
        System.err.println("select MES FINAL: "+selectMesFinal.getValue());
        System.err.println("select MES INICIAL: "+selectMesInicial.getValue());
        System.err.println("check TODO LOS MESES: "+checkAllYear.isSelected());
        System.err.println("btn TIPO DE DATO: "+btnBoxAllTypeDatos.isSelected());
        System.err.println("-------------------------------------------------");
        
        if(checkAllYear.isSelected() && btnBoxAllTypeDatos.isSelected()){
            dataReport = sentencias.todosLosDatosConTodosLosMeses();
        }
        
        
        listar();
    }

    @Override
    public void listarMeses() {
        ObservableList mesesList = mesesList();
        this.selectMesFinal.setItems(mesesList);
        this.selectMesInicial.setItems(mesesList);
    }

    @Override
    public void listar() {
        for(ReporteModel dato : dataReport){
            System.err.print("item: " + dato.getItem() + "cantidadEnElInventario: " + dato.getCantidadEnElInventario() );
            System.err.print(" Facturada: " + dato.getCantidadEnLaFatura() + " Notas de credito: " + dato.getCantidadEnLaNotaCredito());
            System.err.print(" Consumo: "+ dato.getConsumo());
            System.err.println("");
        }
        
        try {
            tblReportes.getItems().clear();
            if (!dataReport.isEmpty()) {               
                tblReportes.setItems(dataReport);
                clmnItem.setCellValueFactory(new PropertyValueFactory<>("item"));               
                clmnDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
                clmnLaboratorio.setCellValueFactory(new PropertyValueFactory<>("laboratorio"));
                clmnLote.setCellValueFactory(new PropertyValueFactory<>("lote"));
                clmnFechaDeVencimiento.setCellValueFactory(new PropertyValueFactory<>("fechaDeVencimiento"));
                clmnLinea.setCellValueFactory(new PropertyValueFactory<>("linea"));
                clmnCentroDeCosto.setCellValueFactory(new PropertyValueFactory<>("centroDeCosto"));
                clmnSede.setCellValueFactory(new PropertyValueFactory<>("sede"));
                clmnTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
                clmnCantidadDeInventario.setCellValueFactory(new PropertyValueFactory<>("cantidadEnElInventario"));
                clmnCantidadFactura.setCellValueFactory(new PropertyValueFactory<>("cantidadEnLaFatura"));
                clmnCantidadNotasDeCredito.setCellValueFactory(new PropertyValueFactory<>("cantidadEnLaNotaCredito"));
                clmnCantidadConsumo.setCellValueFactory(new PropertyValueFactory<>("consumo"));
            }
        } catch (Exception ex) {
            Logger.getLogger(ItemsViewController.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    @Override
    public void closeWindow() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void protegerArchivo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void limpiarFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
