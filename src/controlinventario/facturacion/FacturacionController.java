/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlinventario.facturacion;

import controlinventario.ConexionMysql;
import controlinventario.ExcelParser;
import controlinventario.Interfaces.ArchivoModel;
import controlinventario.Interfaces.ExcelModel;
import controlinventario.ModifyExcel;
import controlinventario.absControlInventario.AbsControlInventario;
import controlinventario.absControlInventario.TypeProcessEnum;
import controlinventario.inventario.InventarioController;
import controlinventario.items.ItemsViewController;
import controlinventario.validaciones.Validaciones;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author auxsistemas3
 */
public class FacturacionController extends AbsControlInventario implements Initializable {

    ObservableList<ExcelModel> dataExcel = FXCollections.observableArrayList();
    ObservableList<ExcelModel> dataExcelFactura = FXCollections.observableArrayList();
    ExcelParser excelParser = new ExcelParser();
    ModifyExcel modificarExcel = new ModifyExcel();
    private final ObservableList<ArchivoModel> listArch = FXCollections.observableArrayList();
    Validaciones validate = new Validaciones();
    private File archivoSeleccionado;
    private String nomArchSeleccionado = null, rutaArchSelecccionado;
    Connection conn = new ConexionMysql().getConn();
    @FXML
    private TableView<ArchivoModel> tblFacturacion;
    @FXML
    private TableColumn<ArchivoModel, Integer> clmnId;
    @FXML
    private TableColumn<ArchivoModel, String> clmnNombre;
    @FXML
    private TableColumn<ArchivoModel, String> clmnExt;
    @FXML
    private TableColumn<ArchivoModel, String> clmnMes;
    @FXML
    private TableColumn<ArchivoModel, String> clmnCantidadDeItem;
    @FXML
    private Button btnCargaArch;
    @FXML
    private Button btnSubirArch;
    @FXML
    private Button btnLimpiarForm;
    @FXML
    private Button btnLimpiarYCerrar;
    @FXML
    private TextField txtArchivoSelect;
    @FXML
    private ChoiceBox<?> selectMes;
    @FXML
    private Button btnVerPorItem;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO      
        protegerArchivo();
        listarMeses();

        listar();

        obtenerItemsDelArchivo();
    }

    @FXML
    private void eventCargaDeArch(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo");

        // Filtrar por tipo de archivo si lo deseas
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos Excel", "*.xlsx", "*.xls"));

        // Mostrar el diálogo de selección de archivo
        Stage stage = null;
        archivoSeleccionado = fileChooser.showOpenDialog(stage);
        if (archivoSeleccionado != null) {
            nomArchSeleccionado = archivoSeleccionado.getName();
            rutaArchSelecccionado = archivoSeleccionado.getPath();
            txtArchivoSelect.setText(archivoSeleccionado.getName());
        }
    }

    @FXML
    private void eventSubirArch(ActionEvent event) {
        validate.archivoVacio(nomArchSeleccionado);
        if (nomArchSeleccionado != null) {
            try {
                String[] separarNombre = nomArchSeleccionado.split("\\.");
                String nombre = separarNombre[0], ext = separarNombre[1],
                        mes = (String) selectMes.getValue();
                File copiaDestino = new File(rutaArchSelecccionado);

                String rutaCarpeta = "C:/Users/auxsistemas3/Desktop/controlDeinventarios/";
                Date fecha = new Date(System.currentTimeMillis());
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String fechaFormateada = formato.format(fecha).replaceAll("/",
                        "").replaceAll(" ", "").replaceAll(":", "");
                File archivoOriginal = new File(rutaArchSelecccionado);
                String nombreArchivoFormateado = fechaFormateada + nomArchSeleccionado;
                File carpetaDestino = new File(rutaCarpeta);
                File archivoCopia = new File(carpetaDestino, nombreArchivoFormateado);
                Files.copy(archivoOriginal.toPath(), archivoCopia.toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
                ArchivoModel modelArch = new ArchivoModel(nombre, ext, archivoCopia.getPath(), mes);
                String sql = "INSERT INTO facturacion(nombre,extension,rutas,mes) VALUES(?,?,?,?)";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, modelArch.getNombre());
                statement.setString(2, modelArch.getExt());
                statement.setString(3, modelArch.getRuta());
                statement.setString(4, modelArch.getMes());
                statement.executeUpdate();
                System.out.println("Se subió");
                validate.archGuardado();
                String sql2 = "SELECT * FROM inventarioinicial WHERE estado = true";
                Statement statement2 = conn.createStatement();
                ResultSet result = statement2.executeQuery(sql2);
                while (result.next()) {
                    ArchivoModel dataArch = new ArchivoModel(result.getString("nombre"),
                            result.getInt("id"), result.getString("extension"),
                            result.getString("rutas"), result.getString("fechaEmision"),
                            result.getBoolean("estado"), result.getString("mes"));
                    dataExcelFactura = excelParser.parseExcel(archivoCopia.getPath());
                    modificarExcel.modicarExcel(dataArch.getRuta(), dataExcelFactura,TypeProcessEnum.SALIDA);
                }

            } catch (IOException | SQLException ex) {
                Logger.getLogger(FacturacionController.class.getName()).log(Level.SEVERE,
                        null, ex);
                System.out.println("no se subió");
            }

        }
        listar();
        limpiarFormulario();
    }

    @FXML
    private void eventLimpiarForm(ActionEvent event) {
        limpiarFormulario();
    }

    @FXML
    private void eventLimpiarYCerrar(ActionEvent event) {
        limpiarFormulario();
        closeWindow();
    }

    @Override
    public void listarMeses() {
        ObservableList mesesList = mesesList();
        if (mesesList != null) {
            this.selectMes.setItems(mesesList);
        }

    }

    @Override
    public void listar() {

        try {
            tblFacturacion.getItems().clear();
            String sql = "SELECT * FROM facturacion";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                ArchivoModel dataArch = new ArchivoModel(result.getString("nombre"),
                        result.getInt("id"), result.getString("extension"),
                        result.getString("rutas"), result.getString("fechaEmision"),
                        result.getBoolean("estado"), result.getString("mes"));

                String sqlXarch = "SELECT rutas FROM facturacion WHERE id = ?";
                PreparedStatement statementXarch = conn.prepareStatement(sqlXarch);
                statementXarch.setInt(1, dataArch.getId());
                ResultSet resultXarch = statementXarch.executeQuery();

                while (resultXarch.next()) {
                    String ruta = resultXarch.getString("rutas");
                    ObservableList<ExcelModel> datos = excelParser.parseExcel(ruta);
                    dataArch.AsignarCantidad(datos.size());
                }
                listArch.add(dataArch);
            }
            if (!listArch.isEmpty()) {
                tblFacturacion.setItems(listArch);
                clmnId.setCellValueFactory(new PropertyValueFactory<>("id"));
                clmnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                clmnExt.setCellValueFactory(new PropertyValueFactory<>("ext"));
                clmnMes.setCellValueFactory(new PropertyValueFactory<>("mes"));
                clmnCantidadDeItem.setCellValueFactory(new PropertyValueFactory<>("cantidadDeItem"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(FacturacionController.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    @Override
    public void closeWindow() {
        Stage stageInventario = (Stage) this.btnLimpiarYCerrar.getScene().getWindow();
        stageInventario.close();
    }

    @Override
    public void protegerArchivo() {
        txtArchivoSelect.setDisable(true);
        txtArchivoSelect.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                btnCargaArch.setDisable(false);
            } else {
                btnCargaArch.setDisable(true);
                archivoSeleccionado = null;
            }
        });
    }

    @Override
    public void limpiarFormulario() {
        txtArchivoSelect.setText("");
        ArchivoModel archivoModel = new ArchivoModel();
    }

    @FXML
    private void eventVerPorItem(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/controlinventario/items/ItemsView.fxml"));
            Parent root = loader.load();
            ItemsViewController controller = loader.getController();
            controller.listar(dataExcel);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            tblFacturacion.getSelectionModel().clearSelection();
        } catch (IOException ex) {
            Logger.getLogger(FacturacionController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void obtenerItemsDelArchivo() {
        tblFacturacion.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection,
                newSelection) -> {
            if (newSelection != null) {
                ArchivoModel data = newSelection;
                dataExcel = excelParser.parseExcel(data.getRuta());
            }
        }
        );
    }
}
