/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlinventario.absControlInventario;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author auxsistemas3
 */
public abstract class AbsControlInventario {

    abstract public void listar();

    abstract public void closeWindow();

    abstract public void listarMeses();
    
    abstract public void protegerArchivo();
    
    abstract public void limpiarFormulario();
    
    /**
     *
     */
    public void obtenerItemsDelArchivo(){};
    
    public ObservableList mesesList() {
        ObservableList mesesList = FXCollections.observableArrayList();
        mesesList.add("ENERO");
        mesesList.add("FEBRERO");
        mesesList.add("MARZO");
        mesesList.add("ABRIL");
        mesesList.add("MAYO");
        mesesList.add("JUNIO");
        mesesList.add("JULIO");
        mesesList.add("AGOSTO");
        mesesList.add("SEPTIEMBRE");
        mesesList.add("OCTUBRE");
        mesesList.add("NOVIEMBRE");
        mesesList.add("DICIENMBRE");
        return mesesList;
    }
}
