/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlinventario;

import controlinventario.Interfaces.ReporteModel;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.collections.ObservableList;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author auxsistemas3
 */
public class CreateExcel {
    //metodo utlizado para crear reportes en formato excel
    public String createNewEcxel(ObservableList<ReporteModel> dataReport) {
        //se abre un nuevo libro vacio
        Workbook workbook = new XSSFWorkbook();
        //se crea una nueva hoja
        Sheet sheet = workbook.createSheet("Reporte");
        //se obtiene la primera fila
        Row headerRow = sheet.createRow(0);
        //se crean las columnas
        headerRow.createCell(0).setCellValue("ITEM");
        headerRow.createCell(1).setCellValue("DESCRIPCION");
        headerRow.createCell(2).setCellValue("LABORATORIO");
        headerRow.createCell(3).setCellValue("LOTE");
        headerRow.createCell(4).setCellValue("FECHA DE VENCIMIENTO");
        headerRow.createCell(5).setCellValue("LINEA");
        headerRow.createCell(6).setCellValue("CENTRO DE COSTO");
        headerRow.createCell(7).setCellValue("SEDE");
        headerRow.createCell(8).setCellValue("TIPO");
        headerRow.createCell(9).setCellValue("CANTIDAD EN EL INVENTARIO");
        headerRow.createCell(10).setCellValue("CANTIDAD EN LA FACTURA");
        headerRow.createCell(11).setCellValue("CANTIDAD EN LA NOTAS DE FACTURA");
        headerRow.createCell(12).setCellValue("CONSUMO");
        //se comienza a llenar los datos despues de la primera fila
        int rowNum = 1;
        for (ReporteModel data : dataReport) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(data.getItem());
            row.createCell(1).setCellValue(data.getDescripcion());
            row.createCell(2).setCellValue(data.getLaboratorio());
            row.createCell(3).setCellValue(data.getLote());
            row.createCell(4).setCellValue(data.getFechaDeVencimiento());
            row.createCell(5).setCellValue(data.getLinea());
            row.createCell(6).setCellValue(data.getCentroDeCosto());
            row.createCell(7).setCellValue(data.getSede());
            row.createCell(8).setCellValue(data.getTipo());
            row.createCell(9).setCellValue(data.getCantidadEnElInventario());
            row.createCell(10).setCellValue(data.getCantidadEnLaFatura());
            row.createCell(11).setCellValue(data.getCantidadEnLaNotaCredito());
            row.createCell(12).setCellValue(data.getConsumo());
        }
        //se obtiene la fecha formateada para unir con el nombre del archivo 
        Date fecha = new Date(System.currentTimeMillis());
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fechaFormateada = formato.format(fecha).replaceAll("/",
                "").replaceAll(" ", "").replaceAll(":", "");
        //se concatena el nombre con la fecha
        String nombreArchivo = fechaFormateada+"Reportes.xlsx";
        //se obtiene el path de la carpeta temporal
        String rutaTemporal = System.getProperty("java.io.tmpdir") + nombreArchivo;
        //se crea el conducto del archivo en la carpeta temporal
        try ( FileOutputStream fileOut = new FileOutputStream(rutaTemporal)) {
            //se sobre escribe el archivo
            workbook.write(fileOut);
            System.out.println("El archivo Excel se ha generado correctamente en la ubicación temporal.");
        } catch (IOException e) {
            System.out.println("Error al generar el archivo Excel: " + e.getMessage());
        }
        
        
        return rutaTemporal;
    }
}
