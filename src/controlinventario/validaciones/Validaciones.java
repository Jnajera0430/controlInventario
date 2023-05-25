/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlinventario.validaciones;

import javafx.scene.control.Alert;

/**
 *
 * @author auxsistemas3
 */
public class Validaciones {

    Alert alertErr = new Alert(Alert.AlertType.ERROR);
    Alert alertInf = new Alert(Alert.AlertType.INFORMATION);

    public void archivoVacio(String nombreArch) {
        if (nombreArch == null) {
            alertInf.setHeaderText("Empty fields");
            alertInf.setTitle("Warning: ");
            alertInf.setContentText("Empty fields exist..");
            alertInf.show();
            return;
        }
    }

    public void archGuardado() {
        alertInf.setHeaderText("Save File");
        alertInf.setTitle("Success: ");
        alertInf.setContentText("The file has been saved successfully");
        alertInf.show();
    }
}
