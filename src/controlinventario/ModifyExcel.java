/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlinventario;

import controlinventario.Interfaces.ExcelModel;
import controlinventario.absControlInventario.TypeProcessEnum;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author auxsistemas3
 */
public class ModifyExcel {

    public void modicarExcel(String ruta, ObservableList<ExcelModel> datosDelArchivo, TypeProcessEnum process) {
        File archivoSeleccionado = new File(ruta);

        try ( FileInputStream fis = new FileInputStream(archivoSeleccionado);  Workbook workbook = WorkbookFactory.create(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (ExcelModel registrosDelArchivo : datosDelArchivo) {
                boolean primeraFila = true;
                for (Row row : sheet) {
                    if (primeraFila) {
                        primeraFila = false;
                        continue;
                    }
                    Cell itemCell = row.getCell(0), loteCell = row.getCell(3),
                            fechaDeVencimientoCell = row.getCell(4);
                    String itemValue = getCellValueAsString(itemCell),
                            loteValue = getCellValueAsString(loteCell),
                            fechaDeVencimientoValue = getCellValueAsString(fechaDeVencimientoCell);
                    if (!itemValue.isEmpty()) {
                        int itemValueFinal = Integer.parseInt(itemValue.split("\\.")[0]);
                        if (registrosDelArchivo.getItem() == itemValueFinal
                                && registrosDelArchivo.getLote().equals(loteValue)
                                && registrosDelArchivo.getFechaDeVencimiento().equals(fechaDeVencimientoValue)) {
                            Cell cantidadCell = row.getCell(5);
                            if (cantidadCell != null) {
                                int cantidadValue = Integer.parseInt(getCellValueAsString(cantidadCell).split("\\.")[0]);
                                int result = 0;
                                if(process.getProcess().equals("SALIDA")){
                                    result = cantidadValue - registrosDelArchivo.getCantidad();
                                }else if(process.getProcess().equals("INGRESO")){
                                    result = cantidadValue + registrosDelArchivo.getCantidad();
                                }                                
                                cantidadCell.setCellValue(Integer.toString(result));
                            }
                        }
                    }

                }
            }

            try ( FileOutputStream fos = new FileOutputStream(archivoSeleccionado)) {
                workbook.write(fos);
                System.out.println("Archivo Excel modificado correctamente.");
            }

        } catch (IOException | EncryptedDocumentException ex) {
            Logger.getLogger(ModifyExcel.class.getName()).log(Level.SEVERE,
                    null, ex);

        }

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
