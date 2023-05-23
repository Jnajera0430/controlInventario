/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlinventario.Interfaces;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author auxsistemas3
 */
public class ExcelModel {
    private StringProperty item;
    private StringProperty cantidad;
    private StringProperty lote;
    private StringProperty  fechaDeVencimiento;

    public ExcelModel(String item, String cantidad, String lote, String fechaDeVencimiento) {
        this.item = new SimpleStringProperty(item);
        this.cantidad = new SimpleStringProperty(cantidad);
        this.lote = new SimpleStringProperty(lote);
        this.fechaDeVencimiento = new SimpleStringProperty(fechaDeVencimiento);
    }
    public ExcelModel() {
        this.item = null;
        this.cantidad = null;
        this.lote = null;
        this.fechaDeVencimiento = null;
    }

    public String getItem() {
        return item.get();
    }

    public void setItem(String item) {
        this.item.set(item);
    }

    public String getCantidad() {
        return cantidad.get();
    }

    public void setCantidad(String cantidad) {
        this.cantidad.set(cantidad);
    }

    public String getLote() {
        return lote.get();
    }

    public void setLote(String lote) {
        this.lote.set(lote);
    }

    public String getFechaDeVencimiento() {
        return fechaDeVencimiento.get();
    }

    public void setFechaDeVencimiento(String fechaDeVencimiento) {
        this.fechaDeVencimiento.set(fechaDeVencimiento);
    }
    
    
}
