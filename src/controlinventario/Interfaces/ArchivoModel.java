/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlinventario.Interfaces;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/*
    Clase modelo de las tablas de la base de datos contienen su metodos setter y getter 
    sus propiedades son del tipoProperty y estan inicializados en susu contructores
    el constructor  que no recibe parametros vuelve las propiedades null

*/
public class ArchivoModel {

    private IntegerProperty id;
    private StringProperty nombre;
    private StringProperty ext;
    private StringProperty ruta;
    private BooleanProperty estado;
    private StringProperty fechaDeEmision;
    private StringProperty mes;
    private IntegerProperty cantidadDeItem;

    public ArchivoModel(String nombre, int id, String ext, String ruta, String fechaDeEmision, boolean estado, String mes) {
        this.nombre = new SimpleStringProperty(nombre);
        this.id = new SimpleIntegerProperty(id);
        this.ext = new SimpleStringProperty(ext);
        this.ruta = new SimpleStringProperty(ruta);
        this.estado = new SimpleBooleanProperty(estado);
        this.fechaDeEmision = new SimpleStringProperty(fechaDeEmision);
        this.mes = new SimpleStringProperty(mes);
    }

    public ArchivoModel(String nombre, String ext, String ruta, String fechaDeEmision, boolean estado, String mes) {
        this.nombre = new SimpleStringProperty(nombre);
        this.ext = new SimpleStringProperty(ext);
        this.ruta = new SimpleStringProperty(ruta);
        this.estado = new SimpleBooleanProperty(estado);
        this.fechaDeEmision = new SimpleStringProperty(fechaDeEmision);
        this.mes = new SimpleStringProperty(mes);
    }

    public ArchivoModel(String nombre, String ext, String ruta, String mes) {
        this.nombre = new SimpleStringProperty(nombre);
        this.ext = new SimpleStringProperty(ext);
        this.ruta = new SimpleStringProperty(ruta);
        this.mes = new SimpleStringProperty(mes);
    }

    public ArchivoModel(int id) {
        this.id = new SimpleIntegerProperty(id);
    }
    
    public void AsignarCantidad(int cantidad) {
        this.cantidadDeItem = new SimpleIntegerProperty(cantidad);
    }

    public ArchivoModel() {
        this.nombre = null;
        this.id = null;
        this.ext = null;
        this.ruta = null;
        this.estado = null;
        this.fechaDeEmision = null;
        this.mes = null;
        this.cantidadDeItem = null;
    }

    public IntegerProperty getIdProperty() {
        return id;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public StringProperty getNombreProperty() {
        return nombre;
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public StringProperty getExtProperty() {
        return ext;
    }

    public String getExt() {
        return ext.get();
    }

    public void setExt(String ext) {
        this.ext.set(ext);
    }

    public StringProperty getRutaProperty() {
        return ruta;
    }

    public String getRuta() {
        return ruta.get();
    }

    public void setRuta(String ruta) {
        this.ruta.set(ruta);
    }

    public BooleanProperty getEstadoproperty() {
        return estado;
    }

    public boolean getEstado() {
        return estado.get();
    }

    public void setEstado(boolean estado) {
        this.estado.set(estado);
    }

    public StringProperty getFechaDeEmisionProperty() {
        return fechaDeEmision;
    }

    public String getFechaDeEmision() {
        return fechaDeEmision.get();
    }

    public void setFechaDeEmision(String fechaDeEmision) {
        this.fechaDeEmision.set(fechaDeEmision);
    }

    public String getMes() {
        return mes.get();
    }

    public void setMes(String mes) {
        this.mes.set(mes);
    }

    public int getCantidadDeItem() {
        return cantidadDeItem.get();
    }

    public void setCantidadDeItem(int cantidadDeItem) {
        this.cantidadDeItem.set(cantidadDeItem);
    }
    
}
