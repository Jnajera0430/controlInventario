/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlinventario.absControlInventario;

/**
 *
 * @author auxsistemas3
 */
public enum TypeProcessEnum {
    INGRESO("INGRESO"),
    SALIDA("SALIDA");
    
    private String process;

    private TypeProcessEnum(String process) {
        this.process = process;
    }

    public static TypeProcessEnum getINGRESO() {
        return INGRESO;
    }

    public static TypeProcessEnum getSALIDA() {
        return SALIDA;
    }

    public String getProcess() {
        return process;
    }
    
    
            
}
