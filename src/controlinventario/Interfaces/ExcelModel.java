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
    private final StringProperty descripcion;
    private final StringProperty laboratorio;
    private final IntegerProperty cantidad;
    private final StringProperty lote;
    private final StringProperty fechaDeVencimiento;
    private final StringProperty linea;
    private final IntegerProperty centroDeCosto;
    private final StringProperty sede;
    private final StringProperty tipo;

    public ExcelModel(int item, String descripcion, String laboratorio,
             String lote, String fechaDeVencimiento,int cantidad, String linea,
            int centroDeCosto, String sede, String tipo) {
        
        this.item = new SimpleIntegerProperty(item);
        this.laboratorio = new SimpleStringProperty(laboratorio);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.cantidad = new SimpleIntegerProperty(cantidad);
        this.lote = new SimpleStringProperty(lote);
        this.fechaDeVencimiento = new SimpleStringProperty(fechaDeVencimiento);
        this.linea = new SimpleStringProperty(linea);
        this.sede = new SimpleStringProperty(sede);
        this.centroDeCosto = new SimpleIntegerProperty(centroDeCosto);
        this.tipo = new SimpleStringProperty(tipo);

    }

    public ExcelModel() {
        this.item = null;
        this.cantidad = null;
        this.lote = null;
        this.fechaDeVencimiento = null;
        this.laboratorio = null;
        this.descripcion = null;
        this.linea = null;
        this.sede = null;
        this.centroDeCosto = null;
        this.tipo = null;
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

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public String getLaboratorio() {
        return laboratorio.get();
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio.set(laboratorio);
    }

    public String getLinea() {
        return linea.get();
    }

    public void setLinea(String linea) {
        this.linea.set(linea);
    }

    public int getCentroDeCosto() {
        return centroDeCosto.get();
    }

    public void setCentroDeCosto(int centroDeCosto) {
        this.centroDeCosto.set(centroDeCosto);
    }

    public String getSede() {
        return sede.get();
    }

    public void setSede(String sede) {
        this.sede.set(sede);
    }

    public String getTipo() {
        return tipo.get();
    }

    public void setTipo(String tipo) {
        this.tipo.set(tipo);
    }

}
