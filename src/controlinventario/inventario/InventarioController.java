/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlinventario.inventario;

import controlinventario.ConexionMysql;
import controlinventario.ExcelParser;
import controlinventario.Interfaces.ArchivoModel;
import controlinventario.Interfaces.ExcelModel;
import controlinventario.absControlInventario.AbsControlInventario;
import controlinventario.items.ItemsViewController;
import controlinventario.validaciones.Validaciones;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author auxsistemas3
 */
public class InventarioController extends AbsControlInventario implements Initializable {
    //se inicializan las variables observableList, las clase ExcelParser, ModifyExcel 
    //y los datos de los archivos seleccionado

    ExcelParser excelParser = new ExcelParser();
    private final ObservableList<ArchivoModel> listArch = FXCollections.observableArrayList();
    ObservableList<ExcelModel> dataExcel = FXCollections.observableArrayList();
    Validaciones validate = new Validaciones();
    Connection conn = new ConexionMysql().getConn();
    private File archivoSeleccionado;
    private String nomArchSeleccionado = null, rutaArchSelecccionado;

    @FXML
    private Button btnCargaArch;
    @FXML
    private Button btnSubirArch;
    @FXML
    private Button btnLimpiarForm;
    @FXML
    private Button btnLimpiarYCerrar;
    @FXML
    private TableView<ArchivoModel> tblInvrntario;
    @FXML
    private TableColumn<ArchivoModel, Integer> clmnInventarioId;
    @FXML
    private TableColumn<ArchivoModel, String> clmnInventarioNombre;
    @FXML
    private TableColumn<ArchivoModel, String> clmnInventarioExt;
    @FXML
    private TableColumn<ArchivoModel, String> clmnInventarioCantidadDeItem;
    @FXML
    private TableColumn<ArchivoModel, String> clmnInventarioMes;
    @FXML
    private TextField txtArchivoSelect;
    @FXML
    private ChoiceBox<?> selectInventarioMes;
    @FXML
    private Button btnVerItems;
    @FXML
    private TextField txtInventarioBuscar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // apenas se cargue la vista se ejecutan los siguientes metodos

        protegerArchivo();
        listarMeses();

        listar();

    }

    @FXML
    private void eventCargaDeArch(ActionEvent event) {
        //la clase FileChooser se utiliza para cargar un archivo
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo");

        // Filtrar por tipo de archivo si lo deseas
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos Excel", "*.xlsx", "*.xls"));

        // Mostrar el diálogo de selección de archivo
        Stage stage = null;
        //se muestra la pantalla
        archivoSeleccionado = fileChooser.showOpenDialog(stage);
        //se obtienen los datos de los archivos obtenidos
        if (archivoSeleccionado != null) {
            nomArchSeleccionado = archivoSeleccionado.getName();
            rutaArchSelecccionado = archivoSeleccionado.getPath();
            txtArchivoSelect.setText(archivoSeleccionado.getName());
        }
    }

    @FXML
    private void eventSubirArch(ActionEvent event) {
        //se validad los datos del archivo
        validate.archivoVacio(nomArchSeleccionado);
        //se valida si el archivo tiene nombre
        if (nomArchSeleccionado != null) {
            try {
                //se obtiene el nombre del archivo
                String[] separarNombre = nomArchSeleccionado.split("\\.");
                String nombre = separarNombre[0], ext = separarNombre[1],
                        mes = (String) selectInventarioMes.getValue();
                //File copiaDestino = new File(rutaArchSelecccionado);
                //ruta donde se va a guardar
                String rutaCarpeta = "C:/Users/auxsistemas3/Desktop/controlDeinventarios/";
                //se obtiene la fecha para concatenar con el nombre
                Date fecha = new Date(System.currentTimeMillis());
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String fechaFormateada = formato.format(fecha).replaceAll("/",
                        "").replaceAll(" ", "").replaceAll(":", "");
                //se obtiene los datos del archivo seleccionado
                File archivoOriginal = new File(rutaArchSelecccionado);
                //se formatea el nomrbre del archivo
                String nombreArchivoFormateado = fechaFormateada + nomArchSeleccionado;
                //se obtiene la carpeta destino
                File carpetaDestino = new File(rutaCarpeta);
                //se crea  el archivo en la carpeta destino
                File archivoCopia = new File(carpetaDestino, nombreArchivoFormateado);
                //se realiza una copia del archivo seleccionado dentro del archivo creado en la carpeta destino
                Files.copy(archivoOriginal.toPath(), archivoCopia.toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
                //se crea un modelo de la tabla facturas
                ArchivoModel modelArch = new ArchivoModel(nombre, ext, archivoCopia.getPath(), mes);
                //query para insertar datos
                String sql = "INSERT INTO inventarioinicial(nombre,extension,rutas,mes) VALUES(?,?,?,?)";
                //se crea la declaracion
                PreparedStatement statement = conn.prepareStatement(sql);
                //se añaden los parametros de la query
                statement.setString(1, modelArch.getNombre());
                statement.setString(2, modelArch.getExt());
                statement.setString(3, modelArch.getRuta());
                statement.setString(4, modelArch.getMes());
                //se ejecuta las query
                statement.executeUpdate();
                System.out.println("Se subió");
                validate.archGuardado();
            } catch (IOException | SQLException ex) {
                Logger.getLogger(InventarioController.class.getName()).log(Level.SEVERE,
                        null, ex);
                System.out.println("no se subió");
            }

        }
        //se lista los datos
        listar();
        //se limpia los archivos
        limpiarFormulario();
    }

    @FXML
    private void eventLimpiarForm(ActionEvent event) {
        //se limpia los inputs
        limpiarFormulario();
    }

    @FXML
    private void eventLimpiarYCerrar(ActionEvent event) {
        //se limpia y se cierra la ventana
        limpiarFormulario();
        closeWindow();
    }

    @FXML
    private void eventBuscarXItem(ActionEvent event) {
        //metodo para cargar la vista por item
        try {
            //se carga la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/controlinventario/items/ItemsView.fxml"));
            // se crea una anclaje y se carga
            Parent root = loader.load();
            //se obtienne el controlador de la vista
            ItemsViewController controller = loader.getController();
            //se ejecuta el metodo listar
            controller.listar(dataExcel);
             //se crea la escena y se ancla
            Scene scene = new Scene(root);
            //se crea un escenario
            Stage stage = new Stage();
            //se muestra
            stage.setScene(scene);
            stage.show();
            //se limpia la seleccion
            tblInvrntario.getSelectionModel().clearSelection();            
        } catch (IOException ex) {
            Logger.getLogger(InventarioController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void protegerArchivo() {
        //metodo para desabilitar los input
        txtArchivoSelect.setDisable(true);
        //se crea un evento para desbiliatar los eventos
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

    @Override
    public void closeWindow() {
        //metodo para cerrar las vistas
        Stage stageInventario = (Stage) this.btnLimpiarYCerrar.getScene().getWindow();
        stageInventario.close();
    }

    @Override
    public void listar() {
        try {
            //se limpia la tabla
            tblInvrntario.getItems().clear();
            //se obtienen los datos de la base datos
            String sql = "SELECT * FROM inventarioinicial";
            //se crea la declaracion
            Statement statement = conn.createStatement();
            //se almacenan los datos
            ResultSet result = statement.executeQuery(sql);
            //se recorren todos los datos
            while (result.next()) {
                //se crea un objeto de la tabla
                ArchivoModel dataArch = new ArchivoModel(result.getString("nombre"),
                        result.getInt("id"), result.getString("extension"),
                        result.getString("rutas"), result.getString("fechaEmision"),
                        result.getBoolean("estado"), result.getString("mes"));
                //se lee el archivo por la ruta
                dataExcel = excelParser.parseExcel(dataArch.getRuta());
                //se asigana la cantidad de item en dataArch
                dataArch.AsignarCantidad(dataExcel.size());
                //se añade a la var observable
                listArch.add(dataArch);
            }
            if (!listArch.isEmpty()) {
                //se añade la lista a la tabla
                tblInvrntario.setItems(listArch);
                //se ordenan los campos
                
                clmnInventarioId.setCellValueFactory(new PropertyValueFactory<>("id"));
                clmnInventarioNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                clmnInventarioExt.setCellValueFactory(new PropertyValueFactory<>("ext"));
                clmnInventarioMes.setCellValueFactory(new PropertyValueFactory<>("mes"));
                clmnInventarioCantidadDeItem.setCellValueFactory(new PropertyValueFactory<>("cantidadDeItem"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(InventarioController.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    @Override
    public void listarMeses() {
        //se listan los meses en el input tipo select
        ObservableList mesesList = mesesList();
        this.selectInventarioMes.setItems(mesesList);
    }

}
