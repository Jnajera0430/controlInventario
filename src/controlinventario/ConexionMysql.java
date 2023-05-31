/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlinventario;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

/**
 *
 * @author auxsistemas3
 */
public class ConexionMysql {

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private Connection conn;
    private String url = null;

    public ConexionMysql() {
        try {
            //se obtiene la ruta del archivo creedenciales
            Path filePath = Paths.get("extra/credenciales.txt");
            //se lee el archivo
            List<String> lines = Files.readAllLines(filePath);
            //se saca el string connect
            for (String line : lines) {
                this.url = line;
            }
            //Se crea la conexion utilizando el driver jdbc de mysql
            //antes se verifica el archivo credenciales si esta correcto
            if (url == null) {
                alert.setHeaderText("Driver of connection not found");
                alert.setTitle("Error: ");
                alert.setContentText("The problem is on the file credentiales.txt,"
                        + " modify your credentials");
                alert.showAndWait();
            }
            //se obtiene el driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //se crea la conexion
            conn = DriverManager.getConnection(url);
            System.out.println("db conectada");
        } catch (ClassNotFoundException | SQLException | IOException ex) {
            if (ex.getClass().toString().contentEquals("java.sql.SQLException")) {
                alert.setHeaderText("Driver of connection not found");
                alert.setTitle("Error: ");
                alert.setContentText("The problem is on the file credentiales.txt,"
                        + " modify your credentials");
                alert.showAndWait();
            }
            Logger.getLogger(ConexionMysql.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Connection getConn() {
        return conn;
    }

    public void closeConexion() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
