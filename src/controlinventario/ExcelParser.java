/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlinventario;

import controlinventario.Interfaces.ExcelModel;
import controlinventario.Interfaces.ReporteModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;

/**
 *
 * @author auxsistemas3
 */
public class ExcelParser {

    //metodo para leer el archivo excel
    public ObservableList<ExcelModel> parseExcel(String ruta) {
        //se selecciona el archivo con la ruta dada
        File archivoSeleccionado = new File(ruta);
        //se inicializa la var observableList
        ObservableList<ExcelModel> data = FXCollections.observableArrayList();
        //se crea un conducto para leer el archivo
        try ( FileInputStream fis = new FileInputStream(archivoSeleccionado);  Workbook workbook = WorkbookFactory.create(fis)) {
            //se abre la primera hoja
            Sheet sheet = workbook.getSheetAt(0);
            //var para no leer la primera fila
            boolean primeraFila = true;
            //se lee los registros de la hoja
            for (Row row : sheet) {
                //se vuela la primera fila
                if (primeraFila) {
                    primeraFila = false;
                    continue;
                }
                //se crea una var List para almacenar el registro
                List<String> rowData = new ArrayList<>();
                //iterador
                Iterator<Cell> cellIterator = row.cellIterator();
                //se verifica si hay y se convierte a string
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    //metodo que convierte a string
                    String cellValue = getCellValueAsString(cell);
                    rowData.add(cellValue);
                }
                //se verifica si el archivo cumple con la estructura
                if (rowData.size() == 10) {
                    //se obtiene la fila
                    String itemValueInicial = rowData.get(0), cantidadValueInicial = rowData.get(5),
                            centroDeCostoValueInicial = rowData.get(7);
                    //se verifica ñlos campos
                    if (itemValueInicial.isEmpty() || cantidadValueInicial.isEmpty() || centroDeCostoValueInicial.isEmpty()) {
                        System.err.println("Error: Existen datos en el archivo que estan vacios");
                        break;
                    }
                    //se castean  los datos
                    int itemValue = Integer.parseInt(itemValueInicial.trim().split("\\.")[0]),
                            cantidadValue = Integer.parseInt(cantidadValueInicial.trim().split("\\.")[0]),
                            centroDeCostoValue = Integer.parseInt(centroDeCostoValueInicial.trim().split("\\.")[0]);
                    //se almacena los datos en el objeto modelo del archivo excel
                    ExcelModel excelModel = new ExcelModel(itemValue, rowData.get(1), rowData.get(2),
                            rowData.get(3), rowData.get(4), cantidadValue, rowData.get(6),
                            centroDeCostoValue, rowData.get(8), rowData.get(9));
                    //se añade los datos a la var observable list
                    data.add(excelModel);
                }
            }
        } catch (IOException | EncryptedDocumentException ex) {
            Logger.getLogger(ExcelParser.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        //se retorna la var observableList
        return data;

    }

    //metodo para leer los archivo para reportes
    public ObservableList<ReporteModel> parseExcelToReport(String ruta) {
        //se selecciona el archivo con la ruta dada
        File archivoSeleccionado = new File(ruta);
        //se inicializa la var observableList
        ObservableList<ReporteModel> data = FXCollections.observableArrayList();
        //se crea un conducto para leer el archivo
        try ( FileInputStream fis = new FileInputStream(archivoSeleccionado);  Workbook workbook = WorkbookFactory.create(fis)) {
            //se abre la primera hoja
            Sheet sheet = workbook.getSheetAt(0);
            //var para no leer la primera fila
            boolean primeraFila = true;
            //se lee los registros de la hoja
            for (Row row : sheet) {
                //se vuela la primera fila
                if (primeraFila) {
                    primeraFila = false;
                    continue;
                }
                //se crea una var List para almacenar el registro
                List<String> rowData = new ArrayList<>();
                //iterador
                Iterator<Cell> cellIterator = row.cellIterator();
                //se verifica si hay y se convierte a string
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    //metodo que convierte a string
                    String cellValue = getCellValueAsString(cell);
                    rowData.add(cellValue);
                }
                //se verifica si el archivo cumple con la estructura
                if (rowData.size() == 10) {
                    //se obtiene la fila
                    String itemValueInicial = rowData.get(0), cantidadValueInicial = rowData.get(5),
                            centroDeCostoValueInicial = rowData.get(7);
                    //se verifica ñlos campos
                    if (itemValueInicial.isEmpty() || cantidadValueInicial.isEmpty() || centroDeCostoValueInicial.isEmpty()) {
                        System.err.println("Error: Existen datos en el archivo que estan vacios");
                        break;
                    }
                    //se castean  los datos
                    int itemValue = Integer.parseInt(itemValueInicial.trim().split("\\.")[0]),
                            cantidadValue = Integer.parseInt(cantidadValueInicial.trim().split("\\.")[0]),
                            centroDeCostoValue = Integer.parseInt(centroDeCostoValueInicial.trim().split("\\.")[0]);
                    //se almacena los datos en el objeto modelo del archivo excel
                    ReporteModel excelModel = new ReporteModel(itemValue, centroDeCostoValue, cantidadValue,
                            rowData.get(1), rowData.get(2), rowData.get(3), rowData.get(4),
                            rowData.get(6), rowData.get(8), rowData.get(9));
                    //se añade los datos a la var observable list
                    data.add(excelModel);
                }
            }
        } catch (IOException | EncryptedDocumentException ex) {
            Logger.getLogger(ExcelParser.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        //se retorna la var observableList
        return data;
    }
    
    //metodo para convertir las celdas a string
    private String getCellValueAsString(Cell cell) {
        //se verifica si la celda esta vacia
        if (cell == null) {
            return "";
        }
        //se obtiene el tipo de dato de la celda
        CellType cellType = cell.getCellType();
        if (null == cellType) {
            return "";
        } else {
            //se castea a string
            return switch (cellType) {
                case STRING ->
                    cell.getStringCellValue();
                case NUMERIC ->
                    String.valueOf(cell.getNumericCellValue());
                case BOOLEAN ->
                    String.valueOf(cell.getBooleanCellValue());
                case FORMULA ->
                    cell.getCellFormula();
                default ->
                    "";
            };
        }
    }

}
