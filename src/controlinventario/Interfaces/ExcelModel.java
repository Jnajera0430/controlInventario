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

    private final IntegerProperty item;
    private final StringProperty nombre;
    private final StringProperty descripcion;
    private final IntegerProperty cantidad;
    private final StringProperty lote;
    private final StringProperty fechaDeVencimiento;

    public ExcelModel(int item, String nombre, String descripcion, int cantidad, String lote, String fechaDeVencimiento) {
        this.item = new SimpleIntegerProperty(item);
        this.nombre = new SimpleStringProperty(nombre);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.cantidad = new SimpleIntegerProperty(cantidad);
        this.lote = new SimpleStringProperty(lote);
        this.fechaDeVencimiento = new SimpleStringProperty(fechaDeVencimiento);
    }

    public ExcelModel() {
        this.item = null;
        this.cantidad = null;
        this.lote = null;
        this.fechaDeVencimiento = null;
        this.nombre = null;
        this.descripcion = null;
    }

    public int getItem() {
        return item.get();
    }

    public void setItem(int item) {
        this.item.set(item);
    }

    public int getCantidad() {
        return cantidad.get();
    }

    public void setCantidad(int cantidad) {
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

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

}
