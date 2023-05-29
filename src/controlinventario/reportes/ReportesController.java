/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlinventario.reportes;

import controlinventario.CreateExcel;
import controlinventario.Interfaces.ReporteModel;
import controlinventario.absControlInventario.AbsControlInventario;
import controlinventario.absControlInventario.AbsSentenciasSQL;
import controlinventario.absControlInventario.DatosRequeridoEnum;
import controlinventario.items.ItemsViewController;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author auxsistemas3
 */
public class ReportesController extends AbsControlInventario implements Initializable {

    ObservableList<ReporteModel> dataReport = FXCollections.observableArrayList();
    CreateExcel crearExcel = new CreateExcel();
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
        try {
            String rutaTemporal = crearExcel.createNewEcxel(dataReport);
            String[] arrayArchivo = rutaTemporal.split("/");
            String nombreArchivo = arrayArchivo[arrayArchivo.length - 1].split("\\.")[0];
            File archivo = new File(rutaTemporal);

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar archivo");
            fileChooser.setInitialFileName(nombreArchivo);

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos Excel (*.xlsx)", "*.xlsx");
            fileChooser.getExtensionFilters().add(extFilter);
            File destino = fileChooser.showSaveDialog(btnGenerarReporte.getScene().getWindow());
            if (destino != null) {
                // Copia el archivo temporal al destino seleccionado
                Files.copy(archivo.toPath(), destino.toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
                System.out.println("El archivo Excel se ha descargado correctamente en: " + destino.getAbsolutePath());

                // Elimina el archivo temporal después de la descarga
                archivo.delete();
            }
        } catch (IOException ex) {
            Logger.getLogger(ReportesController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        AbsSentenciasSQL sentencias = new AbsSentenciasSQL();
        String mesInicial = "", mesFinal = "";
        System.err.println("select MES FINAL: " + selectMesFinal.getValue());
        System.err.println("select MES INICIAL: " + selectMesInicial.getValue());
        System.err.println("check TODO LOS MESES: " + checkAllYear.isSelected());
        System.err.println("btn TIPO DE DATO: " + btnBoxAllTypeDatos.isSelected());
        System.err.println("btn POR FACTURAS" + btnBoxFacturas.isSelected());
        System.err.println("btn POR NOTAS DE CREDITO" + btnBoxNotasDeCredito.isSelected());
        System.err.println("-------------------------------------------------");

        if (checkAllYear.isSelected() && btnBoxAllTypeDatos.isSelected()) {
            dataReport = sentencias.todosLosDatosConTodosLosMeses();
        }
        if (checkAllYear.isSelected() && btnBoxFacturas.isSelected()) {
            dataReport = sentencias.facturacionesConTodosLosMeses();
        }
        if (checkAllYear.isSelected() && btnBoxNotasDeCredito.isSelected()) {
            dataReport = sentencias.notasDeCreditoConTodosLosMeses();
        }

        if (selectMesInicial.getValue() != null) {
            mesInicial = selectMesInicial.getValue().toString();
        }
        if (selectMesFinal.getValue() != null) {
            mesFinal = selectMesFinal.getValue().toString();
        }

        if (!checkAllYear.isSelected() && btnBoxAllTypeDatos.isSelected()) {
            dataReport = sentencias.tipoDeDatoConRangoDeMeses(DatosRequeridoEnum.TODOS_LOS_DATOS,
                    mesInicial, mesFinal);
        }
        if (!checkAllYear.isSelected() && btnBoxNotasDeCredito.isSelected()) {
            dataReport = sentencias.tipoDeDatoConRangoDeMeses(DatosRequeridoEnum.POR_NOTASCREDITO,
                    mesInicial, mesFinal);
        }
        if (!checkAllYear.isSelected() && btnBoxFacturas.isSelected()) {
            dataReport = sentencias.tipoDeDatoConRangoDeMeses(DatosRequeridoEnum.POR_FACTURAS,
                    mesInicial, mesFinal);
        }
        System.err.println("DataReport: " + dataReport.isEmpty());
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
        for (ReporteModel dato : dataReport) {
            System.err.print("item: " + dato.getItem() + "cantidadEnElInventario: " + dato.getCantidadEnElInventario());
            System.err.print(" Facturada: " + dato.getCantidadEnLaFatura() + " Notas de credito: " + dato.getCantidadEnLaNotaCredito());
            System.err.print(" Consumo: " + dato.getConsumo());
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
