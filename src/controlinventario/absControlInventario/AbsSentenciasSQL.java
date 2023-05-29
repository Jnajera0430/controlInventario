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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author auxsistemas3
 */
public class AbsSentenciasSQL extends AbsControlInventario {

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
    private String SentenciaParaObtenerFacturasConRangoDeMeses = "SELECT rutas FROM facturacion WHERE mes IN ";
    private String SentenciaParaObtenerNotaCreditoConRangoDeMeses = "SELECT rutas FROM notasdecredito WHERE mes IN ";

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
                    int cantidadNota = datoInventario.getCantidadEnLaNotaCredito();
                    for (ExcelModel datoArch : datoArchivoNota) {
                        if (datoArch.getItem() == datoInventario.getItem()
                                && datoArch.getLote().equals(datoInventario.getLote())
                                && datoArch.getFechaDeVencimiento().equals(datoInventario.getFechaDeVencimiento())) {
                            cantidadNota = cantidadNota + datoArch.getCantidad();
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

    public ObservableList<ReporteModel> facturacionesConTodosLosMeses() {
        try {
            String rutaFact;
            ResultSet resultFact;
            Statement statementArchfact = conn.createStatement();
            resultFact = statementArchfact.executeQuery(SentenciaParaObetenerTodasLasFacturas);
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

        } catch (SQLException ex) {
            Logger.getLogger(AbsSentenciasSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datosInventario;
    }

    public ObservableList<ReporteModel> notasDeCreditoConTodosLosMeses() {
        try {
            String rutaNota;
            ResultSet resultNota;
            Statement statementArchnota = conn.createStatement();
            resultNota = statementArchnota.executeQuery(SentenciaParaObetenerTodasLasNotasCredito);
            while (resultNota.next()) {
                rutaNota = resultNota.getString("rutas");
                datoArchivoNota = excelParser.parseExcel(rutaNota);
                for (ReporteModel datoInventario : datosInventario) {
                    int cantidadNota = datoInventario.getCantidadEnLaNotaCredito();
                    for (ExcelModel datoArch : datoArchivoNota) {
                        if (datoArch.getItem() == datoInventario.getItem()
                                && datoArch.getLote().equals(datoInventario.getLote())
                                && datoArch.getFechaDeVencimiento().equals(datoInventario.getFechaDeVencimiento())) {
                            cantidadNota = cantidadNota + datoArch.getCantidad();
                        }
                        datoInventario.asignarCantidadNotaCredito(cantidadNota);
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(AbsSentenciasSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datosInventario;
    }

    public ObservableList<ReporteModel> tipoDeDatoConRangoDeMeses(DatosRequeridoEnum datoRequerido,
            String mesInical, String mesFinal) {
        try {

            int rangoLong = 0;
            if (mesInical.isEmpty()) {
                mesInical = "ENERO";
            }
            if (mesFinal.isEmpty()) {
                mesFinal = "DICIEMBRE";
            }

            ObservableList mesesList = mesesList();
            int indexInicial = mesesList.indexOf(mesInical);
            int indexFinal = mesesList.indexOf(mesFinal);
            if (indexInicial != -1 && indexFinal != -1) {
                List<String> rangoMeses;
                if (indexFinal < indexInicial) {
                    rangoMeses = mesesList.subList(indexFinal, indexInicial + 1);
                } else {
                    rangoMeses = mesesList.subList(indexInicial, indexFinal + 1);
                }
                rangoLong = rangoMeses.size();
                String parentesisInicial = "(";
                String parentesisFinal = ")";
                for (String mes : rangoMeses) {
                    if (mes.equals(rangoMeses.get(0))) {
                        SentenciaParaObtenerNotaCreditoConRangoDeMeses
                                = SentenciaParaObtenerNotaCreditoConRangoDeMeses + parentesisInicial;
                    }
                    if (mes.equals(rangoMeses.get(rangoLong - 1))) {
                        SentenciaParaObtenerNotaCreditoConRangoDeMeses
                                = SentenciaParaObtenerNotaCreditoConRangoDeMeses + "'" + mes + "'";

                        SentenciaParaObtenerNotaCreditoConRangoDeMeses
                                = SentenciaParaObtenerNotaCreditoConRangoDeMeses + parentesisFinal;
                    } else {
                        SentenciaParaObtenerNotaCreditoConRangoDeMeses
                                = SentenciaParaObtenerNotaCreditoConRangoDeMeses + "'" + mes + "'" + ",";
                    }
                }
                for (String mes : rangoMeses) {
                    if (mes.equals(rangoMeses.get(0))) {
                        SentenciaParaObtenerFacturasConRangoDeMeses
                                = SentenciaParaObtenerFacturasConRangoDeMeses + parentesisInicial;
                    }
                    if (mes.equals(rangoMeses.get(rangoLong - 1))) {
                        SentenciaParaObtenerFacturasConRangoDeMeses
                                = SentenciaParaObtenerFacturasConRangoDeMeses + "'" + mes + "'";

                        SentenciaParaObtenerFacturasConRangoDeMeses
                                = SentenciaParaObtenerFacturasConRangoDeMeses + parentesisFinal;
                    } else {
                        SentenciaParaObtenerFacturasConRangoDeMeses
                                = SentenciaParaObtenerFacturasConRangoDeMeses + "'" + mes + "'" + ",";
                    }
                }

                switch (datoRequerido.getTypeDeDatoRequerido()) {
                    case "NOTAS_DE_CREDITO" -> {
                        String rutaNota;
                        ResultSet resultNota;
                        Statement statementArchnota = conn.createStatement();
                        resultNota = statementArchnota.executeQuery(SentenciaParaObtenerNotaCreditoConRangoDeMeses);
                        while (resultNota.next()) {
                            rutaNota = resultNota.getString("rutas");
                            datoArchivoNota = excelParser.parseExcel(rutaNota);
                            for (ReporteModel datoInventario : datosInventario) {
                                int cantidadNota = datoInventario.getCantidadEnLaNotaCredito();
                                for (ExcelModel datoArch : datoArchivoNota) {
                                    if (datoArch.getItem() == datoInventario.getItem()
                                            && datoArch.getLote().equals(datoInventario.getLote())
                                            && datoArch.getFechaDeVencimiento().equals(datoInventario.getFechaDeVencimiento())) {
                                        cantidadNota = cantidadNota + datoArch.getCantidad();
                                    }
                                    datoInventario.asignarCantidadNotaCredito(cantidadNota);
                                }
                            }
                        }
                    }
                    case "FACTURACIONES" -> {
                        String rutaFact;
                        ResultSet resultFact;
                        Statement statementArchfact = conn.createStatement();

                        resultFact = statementArchfact.executeQuery(SentenciaParaObtenerFacturasConRangoDeMeses);
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
                    }
                    case "TODOS_LOS_DATOS" -> {
                        String rutaFact, rutaNota;
                        ResultSet resultFact, resultNota;
                        Statement statementArchfact = conn.createStatement();
                        Statement statementArchnota = conn.createStatement();
                        System.err.println("facturas: " + SentenciaParaObtenerFacturasConRangoDeMeses + "\n" + "notas: "
                                + SentenciaParaObtenerNotaCreditoConRangoDeMeses);
                        resultNota = statementArchnota.executeQuery(SentenciaParaObtenerNotaCreditoConRangoDeMeses);
                        resultFact = statementArchfact.executeQuery(SentenciaParaObtenerFacturasConRangoDeMeses);
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
                                int cantidadNota = datoInventario.getCantidadEnLaNotaCredito();
                                for (ExcelModel datoArch : datoArchivoNota) {
                                    if (datoArch.getItem() == datoInventario.getItem()
                                            && datoArch.getLote().equals(datoInventario.getLote())
                                            && datoArch.getFechaDeVencimiento().equals(datoInventario.getFechaDeVencimiento())) {
                                        cantidadNota = cantidadNota + datoArch.getCantidad();
                                    }
                                    datoInventario.asignarCantidadNotaCredito(cantidadNota);
                                }
                            }
                        }
                        for (ReporteModel datoInventario : datosInventario) {
                            int consumo = datoInventario.getCantidadEnLaFatura() - datoInventario.getCantidadEnLaNotaCredito();
                            datoInventario.asignarConsumo(consumo);
                        }
                    }
                    default -> {
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AbsSentenciasSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datosInventario;
    }

    @Override
    public void listar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void closeWindow() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void listarMeses() {
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
