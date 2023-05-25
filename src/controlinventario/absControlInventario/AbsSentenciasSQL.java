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
    ObservableList<ExcelModel> datoArchivo = FXCollections.observableArrayList();
    ObservableList<ExcelModel> datoInventario = FXCollections.observableArrayList();
    private final String SentenciaParaObtenerInventario = "SELECT rutas FROM inventarioinicial WHERE estado = true";
    private final String SentenciaParaObetenerTodasLasFacturas = "SELECT rutas FROM facturacion";
    public AbsSentenciasSQL() {
        try {
            Statement statementInventario = conn.createStatement();
            ResultSet result = statementInventario.executeQuery(SentenciaParaObtenerInventario);
            while(result.next()){
                String ruta = result.getString("rutas");
                datoInventario = excelParser.parseExcel(ruta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AbsSentenciasSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public ObservableList<ReporteModel> todosLosDatosConTodosLosMeses(){
        try {
            Statement statementArch = conn.createStatement();
            ResultSet result = statementArch.executeQuery(SentenciaParaObetenerTodasLasFacturas);
            while(result.next()){
                
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(AbsSentenciasSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    public ObservableList<ReporteModel> todosLosDatosConRangoDeMeses(){
        
        
        
        return data;
    }
    
    public ObservableList<ReporteModel> facturacionesConTodosLosMeses(){
        
        
        
        return data;
    }
    
    public ObservableList<ReporteModel> facturacionesConRangoDeMeses(){
        
        
        
        return data;
    }
    
    public ObservableList<ReporteModel> notasDeCreditoConTodosLosMeses(){
        
        
        
        return data;
    }
    
    public ObservableList<ReporteModel> notasDeCreditoConRangoDeMeses(){
        
        
        
        return data;
    }
}
