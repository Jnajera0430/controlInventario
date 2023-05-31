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

    //se inivializan las var, las querys, conexion a la base datos y las clases
    //para realizar el proceso de la generacion de reportes
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
            //se obtiene los datos del inventario
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

    //se obtiene los datos con todos los datos en todos los meses
    public ObservableList<ReporteModel> todosLosDatosConTodosLosMeses() {
        try {
            //Se inicializa las variables 
            String rutaFact, rutaNota, mes;
            //var que obtienen los resultados de la base de datos
            ResultSet resultFact, resultNota;
            //se crean declaraciones de consultas
            Statement statementArchfact = conn.createStatement();
            Statement statementArchnota = conn.createStatement();
            //se ejeutan las declaraciones de consultas
            resultFact = statementArchfact.executeQuery(SentenciaParaObetenerTodasLasFacturas);
            resultNota = statementArchnota.executeQuery(SentenciaParaObetenerTodasLasNotasCredito);
            //se obtienen los resultados
            while (resultFact.next()) {
                //se obtiene la ruta
                rutaFact = resultFact.getString("rutas");
                //se lee el archivo excel
                datoArchivoFact = excelParser.parseExcel(rutaFact);
                //se recorre el inventario
                for (ReporteModel datoInventario : datosInventario) {
                    //se obtiene la cantidad
                    int cantidadFact = 0;
                    //se recorre los datos del archivo de facturas
                    for (ExcelModel datoArch : datoArchivoFact) {
                        //se verifica las coincidencias del archivo
                        if (datoArch.getItem() == datoInventario.getItem()
                                && datoArch.getLote().equals(datoInventario.getLote())
                                && datoArch.getFechaDeVencimiento().equals(datoInventario.getFechaDeVencimiento())) {
                            //se calcula las cantidad
                            cantidadFact = datoInventario.getCantidadEnLaFatura() + datoArch.getCantidad();
                        }
                        //se asigna la cantidad al item en el inventario
                        datoInventario.asignarCantidadFactura(cantidadFact);
                    }
                }
            }
            while (resultNota.next()) {
                //se obtiene la ruta
                rutaNota = resultNota.getString("rutas");
                //se lee el archivo excel
                datoArchivoNota = excelParser.parseExcel(rutaNota);
                //se recorre el inventario
                for (ReporteModel datoInventario : datosInventario) {
                    //se obtiene la cantidad
                    int cantidadNota = datoInventario.getCantidadEnLaNotaCredito();
                    //se recorre los datos del archivo de notas
                    for (ExcelModel datoArch : datoArchivoNota) {
                        //se verifica las coincidencias del archivo
                        if (datoArch.getItem() == datoInventario.getItem()
                                && datoArch.getLote().equals(datoInventario.getLote())
                                && datoArch.getFechaDeVencimiento().equals(datoInventario.getFechaDeVencimiento())) {
                            //se calcula las cantidad
                            cantidadNota = cantidadNota + datoArch.getCantidad();
                        }
                        //se asigna la cantidad al item en el inventario
                        datoInventario.asignarCantidadNotaCredito(cantidadNota);
                    }
                }
            }
            //se calcula el consumo
            for (ReporteModel datoInventario : datosInventario) {
                int consumo = datoInventario.getCantidadEnLaFatura() - datoInventario.getCantidadEnLaNotaCredito();
                datoInventario.asignarConsumo(consumo);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AbsSentenciasSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        //se retorna la var observableList
        return datosInventario;
    }

    public ObservableList<ReporteModel> facturacionesConTodosLosMeses() {
        try {
            //Se inicializa las variables 
            String rutaFact;
            //var que obtienen los resultados de la base de datos
            ResultSet resultFact;
            //se crean declaraciones de consultas
            Statement statementArchfact = conn.createStatement();
            //se ejeutan las declaraciones de consultas
            resultFact = statementArchfact.executeQuery(SentenciaParaObetenerTodasLasFacturas);
            //se obtienen los resultados
            while (resultFact.next()) {
                //se obtiene la ruta
                rutaFact = resultFact.getString("rutas");
                //se lee el archivo excel
                datoArchivoFact = excelParser.parseExcel(rutaFact);
                //se recorre el inventario
                for (ReporteModel datoInventario : datosInventario) {
                    //se obtiene la cantidad
                    int cantidadFact = 0;
                    //se recorre los datos del archivo de facturas
                    for (ExcelModel datoArch : datoArchivoFact) {
                        //se verifica las coincidencias del archivo
                        if (datoArch.getItem() == datoInventario.getItem()
                                && datoArch.getLote().equals(datoInventario.getLote())
                                && datoArch.getFechaDeVencimiento().equals(datoInventario.getFechaDeVencimiento())) {
                            //se calcula las cantidad
                            cantidadFact = datoInventario.getCantidadEnLaFatura() + datoArch.getCantidad();
                        }
                        //se asigna la cantidad al item en el inventario
                        datoInventario.asignarCantidadFactura(cantidadFact);
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(AbsSentenciasSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        //se retorna la var observableList
        return datosInventario;
    }

    public ObservableList<ReporteModel> notasDeCreditoConTodosLosMeses() {
        try {
            //Se inicializa las variables 
            String rutaNota;
            //var que obtienen los resultados de la base de datos
            ResultSet resultNota;
            //se crean declaraciones de consultas
            Statement statementArchnota = conn.createStatement();
            //se ejeutan las declaraciones de consultas
            resultNota = statementArchnota.executeQuery(SentenciaParaObetenerTodasLasNotasCredito);
            //se obtienen los resultados
            while (resultNota.next()) {
                //se obtiene la ruta
                rutaNota = resultNota.getString("rutas");
                //se lee el archivo excel
                datoArchivoNota = excelParser.parseExcel(rutaNota);
                //se recorre el inventario
                for (ReporteModel datoInventario : datosInventario) {
                    //se obtiene la cantidad
                    int cantidadNota = datoInventario.getCantidadEnLaNotaCredito();
                    //se recorre los datos del archivo de notas
                    for (ExcelModel datoArch : datoArchivoNota) {
                        //se verifica las coincidencias del archivo
                        if (datoArch.getItem() == datoInventario.getItem()
                                && datoArch.getLote().equals(datoInventario.getLote())
                                && datoArch.getFechaDeVencimiento().equals(datoInventario.getFechaDeVencimiento())) {
                            //se calcula las cantidad
                            cantidadNota = cantidadNota + datoArch.getCantidad();
                        }
                        //se asigna la cantidad al item en el inventario
                        datoInventario.asignarCantidadNotaCredito(cantidadNota);
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(AbsSentenciasSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        //se retorna la var observableList
        return datosInventario;
    }

    public ObservableList<ReporteModel> tipoDeDatoConRangoDeMeses(DatosRequeridoEnum datoRequerido,
            String mesInical, String mesFinal) {
        try {
            //defines var para la cantidad del rango
            int rangoLong;
            //si los meses estan vacios se le asiganan valores predeterminados
            if (mesInical.isEmpty()) {
                mesInical = "ENERO";
            }
            if (mesFinal.isEmpty()) {
                mesFinal = "DICIEMBRE";
            }
            //se obtieene la lista de meses
            ObservableList mesesList = mesesList();
            //se obtienen los indices de los meses obtenidos  como parametros
            int indexInicial = mesesList.indexOf(mesInical);
            int indexFinal = mesesList.indexOf(mesFinal);
            //se verifican si los meses se encuentran dentro de la lista
            if (indexInicial != -1 && indexFinal != -1) {
                //se define una lista de los meses
                List<String> rangoMeses;
                //se formatea para obtener el rango de meses correcto
                if (indexFinal < indexInicial) {
                    //se obtiene una sublista con el rango de meses escojidos
                    rangoMeses = mesesList.subList(indexFinal, indexInicial + 1);
                } else {
                    //se obtiene una sublista con el rango de meses escojidos
                    rangoMeses = mesesList.subList(indexInicial, indexFinal + 1);
                }
                //longitud del rango de meses
                rangoLong = rangoMeses.size();
                //define var para modificar query
                String parentesisInicial = "(";
                String parentesisFinal = ")";
                //  se recorre elrango de meses
                for (String mes : rangoMeses) {
                    //se formatea el query con los rango de meses
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
                    //se formatea el query con los rango de meses
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
                //se define el tipo de proceso para obntener el reporte
                switch (datoRequerido.getTypeDeDatoRequerido()) {
                    case "NOTAS_DE_CREDITO" -> {
                        //Se inicializa las variables 
                        String rutaNota;
                        //var que obtienen los resultados de la base de datos
                        ResultSet resultNota;
                        //se crean declaraciones de consultas
                        Statement statementArchnota = conn.createStatement();
                        //se ejeutan las declaraciones de consultas
                        resultNota = statementArchnota.executeQuery(SentenciaParaObtenerNotaCreditoConRangoDeMeses);
                        //se obtienen los resultados
                        while (resultNota.next()) {
                            //se obtiene la ruta
                            rutaNota = resultNota.getString("rutas");
                            //se lee el archivo excel
                            datoArchivoNota = excelParser.parseExcel(rutaNota);
                            //se recorre el inventario
                            for (ReporteModel datoInventario : datosInventario) {
                                //se obtiene la cantidad
                                int cantidadNota = datoInventario.getCantidadEnLaNotaCredito();
                                //se recorre los datos del archivo de notas
                                for (ExcelModel datoArch : datoArchivoNota) {
                                    //se verifica las coincidencias del archivo
                                    if (datoArch.getItem() == datoInventario.getItem()
                                            && datoArch.getLote().equals(datoInventario.getLote())
                                            && datoArch.getFechaDeVencimiento().equals(datoInventario.getFechaDeVencimiento())) {
                                        //se calcula las cantidad
                                        cantidadNota = cantidadNota + datoArch.getCantidad();
                                    }
                                    //se asigna la cantidad al item en el inventario
                                    datoInventario.asignarCantidadNotaCredito(cantidadNota);
                                }
                            }
                        }
                    }
                    case "FACTURACIONES" -> {
                        //Se inicializa las variables 
                        String rutaFact;
                        //var que obtienen los resultados de la base de datos
                        ResultSet resultFact;
                        //se crean declaraciones de consultas
                        Statement statementArchfact = conn.createStatement();
                        //se ejeutan las declaraciones de consultas
                        resultFact = statementArchfact.executeQuery(SentenciaParaObtenerFacturasConRangoDeMeses);
                        //se obtienen los resultados
                        while (resultFact.next()) {
                            //se obtiene la ruta
                            rutaFact = resultFact.getString("rutas");
                            //se lee el archivo excel
                            datoArchivoFact = excelParser.parseExcel(rutaFact);
                            //se recorre el inventario
                            for (ReporteModel datoInventario : datosInventario) {
                                //se obtiene la cantidad
                                int cantidadFact = 0;
                                //se recorre los datos del archivo de notas
                                for (ExcelModel datoArch : datoArchivoFact) {
                                    //se verifica las coincidencias del archivo
                                    if (datoArch.getItem() == datoInventario.getItem()
                                            && datoArch.getLote().equals(datoInventario.getLote())
                                            && datoArch.getFechaDeVencimiento().equals(datoInventario.getFechaDeVencimiento())) {
                                        //se calcula las cantidad
                                        cantidadFact = datoInventario.getCantidadEnLaFatura() + datoArch.getCantidad();
                                    }
                                    //se asigna la cantidad al item en el inventario
                                    datoInventario.asignarCantidadFactura(cantidadFact);
                                }
                            }
                        }
                    }
                    case "TODOS_LOS_DATOS" -> {
                        //Se inicializa las variables 
                        String rutaFact, rutaNota;
                        //var que obtienen los resultados de la base de datos
                        ResultSet resultFact, resultNota;
                        //se crean declaraciones de consultas
                        Statement statementArchfact = conn.createStatement();
                        Statement statementArchnota = conn.createStatement();
                        //se crean declaraciones de consultas
                        System.err.println("facturas: " + SentenciaParaObtenerFacturasConRangoDeMeses + "\n" + "notas: "
                                + SentenciaParaObtenerNotaCreditoConRangoDeMeses);
                        //se ejeutan las declaraciones de consultas
                        resultNota = statementArchnota.executeQuery(SentenciaParaObtenerNotaCreditoConRangoDeMeses);
                        resultFact = statementArchfact.executeQuery(SentenciaParaObtenerFacturasConRangoDeMeses);
                        //se obtienen los resultados
                        while (resultFact.next()) {
                            //se obtiene la ruta
                            rutaFact = resultFact.getString("rutas");
                            //se lee el archivo excel
                            datoArchivoFact = excelParser.parseExcel(rutaFact);
                            //se recorre el inventario
                            for (ReporteModel datoInventario : datosInventario) {
                                //se obtiene la cantidad
                                int cantidadFact = 0;
                                //se recorre los datos del archivo de notas
                                for (ExcelModel datoArch : datoArchivoFact) {
                                    //se verifica las coincidencias del archivo
                                    if (datoArch.getItem() == datoInventario.getItem()
                                            && datoArch.getLote().equals(datoInventario.getLote())
                                            && datoArch.getFechaDeVencimiento().equals(datoInventario.getFechaDeVencimiento())) {
                                        //se calcula las cantidad
                                        cantidadFact = datoInventario.getCantidadEnLaFatura() + datoArch.getCantidad();
                                    }
                                    //se asigna la cantidad al item en el inventario
                                    datoInventario.asignarCantidadFactura(cantidadFact);
                                }
                            }
                        }
                        //se obtienen los resultados
                        while (resultNota.next()) {
                            //se obtiene la ruta
                            rutaNota = resultNota.getString("rutas");
                            //se lee el archivo excel
                            datoArchivoNota = excelParser.parseExcel(rutaNota);
                            //se recorre el inventario
                            for (ReporteModel datoInventario : datosInventario) {
                                //se obtiene la cantidad
                                int cantidadNota = datoInventario.getCantidadEnLaNotaCredito();
                                //se recorre los datos del archivo de notas
                                for (ExcelModel datoArch : datoArchivoNota) {
                                    //se verifica las coincidencias del archivo
                                    if (datoArch.getItem() == datoInventario.getItem()
                                            && datoArch.getLote().equals(datoInventario.getLote())
                                            && datoArch.getFechaDeVencimiento().equals(datoInventario.getFechaDeVencimiento())) {
                                        //se calcula las cantidad
                                        cantidadNota = cantidadNota + datoArch.getCantidad();
                                    }
                                    //se asigna la cantidad al item en el inventario
                                    datoInventario.asignarCantidadNotaCredito(cantidadNota);
                                }
                            }
                        }
                        //se calcula el consumo
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
        //se retorna la var observableList
        return datosInventario;
    }
    //metodos sin utilizar pero no se pueden borrar
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
