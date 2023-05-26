/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlinventario.absControlInventario;

import controlinventario.ConexionMysql;
import controlinventario.ExcelParser;
import controlinventario.Interfaces.ExcelModel;
import controlinventario.Interfaces.ReporteModel;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author auxsistemas3
 */
public class AbsSentenciasSQL {

    ExcelParser excelParser = new ExcelParser();
    private final ConexionMysql conexion = new ConexionMysql();
    private final Connection conn = conexion.getConn();
    ObservableList<ReporteModel> data = FXCollections.observableArrayList();
    ObservableList<ExcelModel> datoArchivoFact = FXCollections.observableArrayList();
    ObservableList<ExcelModel> datoArchivoNota = FXCollections.observableArrayList();
    ObservableList<ReporteModel> datosInventario = FXCollections.observableArrayList();
    private final String SentenciaParaObtenerInventario = "SELECT rutas FROM inventarioinicial WHERE estado = true";
    private final String SentenciaParaObetenerTodasLasFacturas = "SELECT rutas, mes FROM facturacion";
    private final String SentenciaParaObetenerTodasLasNotasCredito = "SELECT rutas, mes FROM notasdecredito";

    public AbsSentenciasSQL() {
        try {
            Statement statementInventario = conn.createStatement();
            ResultSet result = statementInventario.executeQuery(SentenciaParaObtenerInventario);
            while (result.next()) {
                String ruta = result.getString("rutas");
                datosInventario = excelParser.parseExcelToReport(ruta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AbsSentenciasSQL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ObservableList<ReporteModel> todosLosDatosConTodosLosMeses() {
        try {
            String rutaFact, rutaNota, mes;
            ResultSet resultFact, resultNota;
            Statement statementArchfact = conn.createStatement();
            Statement statementArchnota = conn.createStatement();
            resultFact = statementArchfact.executeQuery(SentenciaParaObetenerTodasLasFacturas);
            resultNota = statementArchnota.executeQuery(SentenciaParaObetenerTodasLasNotasCredito);

            while (resultFact.next()) {
                rutaFact = resultFact.getString("rutas");
                datoArchivoFact = excelParser.parseExcel(rutaFact);
                for (ReporteModel datoInventario : datosInventario) {
                    int cantidadFact = 0;
                    for (ExcelModel datoArch : datoArchivoFact) {
                        if (datoArch.getItem() == datoInventario.getItem()
                                && datoArch.getLote().equals(datoInventario.getLote())
                                && datoArch.getFechaDeVencimiento().equals(datoInventario.getFechaDeVencimiento())) {

                            cantidadFact = datoInventario.getCantidadEnLaFatura() + datoArch.getCantidad();
                        }
                        datoInventario.asignarCantidadFactura(cantidadFact);
                    }
                }
            }
            while (resultNota.next()) {
                rutaNota = resultNota.getString("rutas");
                datoArchivoNota = excelParser.parseExcel(rutaNota);
                for (ReporteModel datoInventario : datosInventario) {
                    int cantidadNota = 0;
                    for (ExcelModel datoArch : datoArchivoNota) {
                        if (datoArch.getItem() == datoInventario.getItem()
                                && datoArch.getLote().equals(datoInventario.getLote())
                                && datoArch.getFechaDeVencimiento().equals(datoInventario.getFechaDeVencimiento())) {
                            cantidadNota = datoInventario.getCantidadEnLaNotaCredito() + datoArch.getCantidad();
                        }
                        datoInventario.asignarCantidadNotaCredito(cantidadNota);
                    }
                }
            }

            for (ReporteModel datoInventario : datosInventario) {
                int consumo = datoInventario.getCantidadEnLaFatura() - datoInventario.getCantidadEnLaNotaCredito();
                datoInventario.asignarConsumo(consumo);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AbsSentenciasSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datosInventario;
    }

    public ObservableList<ReporteModel> todosLosDatosConRangoDeMeses() {

        return data;
    }

    public ObservableList<ReporteModel> facturacionesConTodosLosMeses() {

        return data;
    }

    public ObservableList<ReporteModel> facturacionesConRangoDeMeses() {

        return data;
    }

    public ObservableList<ReporteModel> notasDeCreditoConTodosLosMeses() {

        return data;
    }

    public ObservableList<ReporteModel> notasDeCreditoConRangoDeMeses() {

        return data;
    }
}
