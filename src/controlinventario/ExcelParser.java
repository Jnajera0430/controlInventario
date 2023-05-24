/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlinventario;

import controlinventario.Interfaces.ExcelModel;
import controlinventario.inventario.InventarioController;
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

    public ObservableList<ExcelModel> parseExcel(String ruta) {
        File archivoSeleccionado = new File(ruta);
        ObservableList<ExcelModel> data = FXCollections.observableArrayList();

        try ( FileInputStream fis = new FileInputStream(archivoSeleccionado);  Workbook workbook = WorkbookFactory.create(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            boolean primeraFila = true;
            for (Row row : sheet) {
                if (primeraFila) {
                    primeraFila = false;
                    continue; 
                }
                List<String> rowData = new ArrayList<>();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    String cellValue = getCellValueAsString(cell);
                    rowData.add(cellValue);
                }
                if (rowData.size() == 6) {
                    ExcelModel excelModel = new ExcelModel(Integer.parseInt(rowData.get(0).split("\\.")[0]),
                            rowData.get(1), rowData.get(2),
                            Integer.parseInt(rowData.get(3).split("\\.")[0]),
                            rowData.get(4),rowData.get(5));
                    data.add(excelModel);
                }
            }
        } catch (IOException | EncryptedDocumentException ex) {
            Logger.getLogger(ExcelParser.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return data;

    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        CellType cellType = cell.getCellType();
        if (null == cellType) {
            return "";
        } else {
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
