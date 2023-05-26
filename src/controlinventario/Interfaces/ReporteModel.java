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
public class ReporteModel {

    private final IntegerProperty item, centroDeCosto, cantidadEnElInventario;
    private final IntegerProperty cantidadEnLaFatura = new SimpleIntegerProperty();
    private final IntegerProperty cantidadEnLaNotaCredito = new SimpleIntegerProperty();
    private final IntegerProperty consumo = new SimpleIntegerProperty();
    private final StringProperty descripcion, laboratorio, lote, fechaDeVencimiento,
            linea, sede, tipo,mes;

    public ReporteModel(int item, int centroDeCosto,
            int cantidadEnElInventario, int cantidadEnLaFatura,
            int cantidadEnLaNotaCredito, int consumo, String descripcion,
            String laboratorio, String lote, String fechaDeVencimiento,
            String linea, String sede, String tipo,String mes) {
        this.item = new SimpleIntegerProperty(item);
        this.centroDeCosto = new SimpleIntegerProperty(centroDeCosto);
        this.cantidadEnElInventario = new SimpleIntegerProperty(cantidadEnElInventario);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.laboratorio = new SimpleStringProperty(laboratorio);
        this.lote = new SimpleStringProperty(lote);
        this.fechaDeVencimiento = new SimpleStringProperty(fechaDeVencimiento);
        this.linea = new SimpleStringProperty(linea);
        this.sede = new SimpleStringProperty(sede);
        this.tipo = new SimpleStringProperty(tipo);
        this.mes = new SimpleStringProperty(mes);
    }

    public ReporteModel(int item, int centroDeCosto,
            int cantidadEnElInventario, String descripcion,
            String laboratorio, String lote, String fechaDeVencimiento,
            String linea, String sede, String tipo) {
        this.item = new SimpleIntegerProperty(item);
        this.centroDeCosto = new SimpleIntegerProperty(centroDeCosto);
        this.cantidadEnElInventario = new SimpleIntegerProperty(cantidadEnElInventario);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.laboratorio = new SimpleStringProperty(laboratorio);
        this.lote = new SimpleStringProperty(lote);
        this.fechaDeVencimiento = new SimpleStringProperty(fechaDeVencimiento);
        this.linea = new SimpleStringProperty(linea);
        this.sede = new SimpleStringProperty(sede);
        this.tipo = new SimpleStringProperty(tipo);
        this.mes = new SimpleStringProperty(null);
    }

    public void asignarCantidadFactura(int cantidadEnLaFatura) {
        this.cantidadEnLaFatura.set(cantidadEnLaFatura);
    }

    public void asignarCantidadNotaCredito(int cantidadNotaCredito) {
        this.cantidadEnLaNotaCredito.set(cantidadNotaCredito);
    }

    public void asignarConsumo(int consumo) {
        this.consumo.set(consumo);
    }

    public ReporteModel() {
        this.item = null;
        this.centroDeCosto = null;
        this.cantidadEnElInventario = new SimpleIntegerProperty(0);
        this.cantidadEnLaFatura.set(0);
        this.cantidadEnLaNotaCredito.set(0);
        this.consumo.set(0);
        this.descripcion = null;
        this.laboratorio = null;
        this.lote = null;
        this.fechaDeVencimiento = null;
        this.linea = null;
        this.sede = null;
        this.tipo = null;
        this.mes = null;
    }

    public int getItem() {
        return item.get();
    }

    public void setItem(int item) {
        this.item.set(item);
    }

    public int getCentroDeCosto() {
        return centroDeCosto.get();
    }

    public void setCentroDeCosto(int centroDeCosto) {
        this.centroDeCosto.set(centroDeCosto);
    }

    public int getCantidadEnElInventario() {
        return cantidadEnElInventario.get();
    }

    public void setCantidadEnElInventario(int cantidadEnElInventario) {
        this.cantidadEnElInventario.set(cantidadEnElInventario);
    }

    public int getCantidadEnLaFatura() {
        return cantidadEnLaFatura.get();
    }

    public void setCantidadEnLaFatura(int cantidadEnLaFatura) {
        this.cantidadEnLaFatura.set(cantidadEnLaFatura);
    }

    public int getCantidadEnLaNotaCredito() {
        return cantidadEnLaNotaCredito.get();
    }

    public void setCantidadEnLaNotaCredito(int cantidadEnLaNotaCredito) {
        this.cantidadEnLaNotaCredito.set(cantidadEnLaNotaCredito);
    }

    public int getConsumo() {
        return consumo.get();
    }

    public void setConsumo(int consumo) {
        this.consumo.set(consumo);
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

    public String getLinea() {
        return linea.get();
    }

    public void setLinea(String linea) {
        this.linea.set(linea);
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
    
    public String getMes() {
        return mes.get();
    }

    public void setTMes(String mes) {
        this.mes.set(mes);
    }

}
