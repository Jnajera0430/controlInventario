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

    //metodo para modificar el archivo excel se recibe como parametros la ruta del inventario,,
    //un observablelist con los datos del archivo ya sea facturas o nota y el tipo del proceso a realizar  
    public void modicarExcel(String ruta, ObservableList<ExcelModel> datosDelArchivo, TypeProcessEnum process) {
        //se obtiene el arhivo del inventario
        File archivoSeleccionado = new File(ruta);
        //se abre el conducto papra leer el archivo y se abre el libro
        try ( FileInputStream fis = new FileInputStream(archivoSeleccionado);  Workbook workbook = WorkbookFactory.create(fis)) {
            //se obtiene la primera hoja
            Sheet sheet = workbook.getSheetAt(0);
            //se recorren los datos del archivo notas o de facturas
            for (ExcelModel registrosDelArchivo : datosDelArchivo) {
                //var para volarse la primera linea
                boolean primeraFila = true;
                //se recorren los registros del inventario
                for (Row row : sheet) {
                    //se vuela la primera fila
                    if (primeraFila) {
                        primeraFila = false;
                        continue;
                    }

                    //se obtienen las celdas necesarias
                    Cell itemCell = row.getCell(0), loteCell = row.getCell(3),
                            fechaDeVencimientoCell = row.getCell(4);

                    //se  castean las celdas
                    String itemValue = getCellValueAsString(itemCell),
                            loteValue = getCellValueAsString(loteCell),
                            fechaDeVencimientoValue = getCellValueAsString(fechaDeVencimientoCell);
                    //se verifican si no estan vacias
                    if (!itemValue.isEmpty()) {
                        //se formatean los valores 
                        int itemValueFinal = Integer.parseInt(itemValue.split("\\.")[0]);
                        //se verifican las coincidencias entre los registros
                        if (registrosDelArchivo.getItem() == itemValueFinal
                                && registrosDelArchivo.getLote().equals(loteValue)
                                && registrosDelArchivo.getFechaDeVencimiento().equals(fechaDeVencimientoValue)) {
                            //se obtiene la catidad
                            Cell cantidadCell = row.getCell(5);
                            //se verifica si no está vacia
                            if (cantidadCell != null) {
                                //obtiene el valor
                                int cantidadValue = Integer.parseInt(getCellValueAsString(cantidadCell).split("\\.")[0]);
                                int result = 0;
                                //se identifica el proceso a reallizar
                                if (process.getProcess().equals("SALIDA")) {
                                    //si es salida se resta al inventario
                                    result = cantidadValue - registrosDelArchivo.getCantidad();
                                   
                                } else if (process.getProcess().equals("INGRESO")) {
                                    //si es ingreso se suma
                                    result = cantidadValue + registrosDelArchivo.getCantidad();
                                }
                                
                                //se sobre la celda y se mofica su valor
                                cantidadCell.setCellValue(Integer.toString(result));
                            }
                        }
                    }

                }
            }
            //se crea un nuevo conducto con el libro
            try ( FileOutputStream fos = new FileOutputStream(archivoSeleccionado)) {
                //se escribe el libro modificado    
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
