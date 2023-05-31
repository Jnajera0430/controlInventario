/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package controlinventario.absControlInventario;

/*
    enumerados que espicifica el tipo de datos que se requiere para el archivo
 */
public enum DatosRequeridoEnum {
    TODOS_LOS_DATOS("TODOS_LOS_DATOS"),
    POR_FACTURAS("FACTURACIONES"),
    POR_NOTASCREDITO("NOTAS_DE_CREDITO");
    
    private final String tipoDeDatoRequerido;

    private DatosRequeridoEnum(String typeDeDatoRequerido) {
        this.tipoDeDatoRequerido = typeDeDatoRequerido;
    }

    public String getTypeDeDatoRequerido() {
        return tipoDeDatoRequerido;
    }

    public static DatosRequeridoEnum getTODOS_LOS_DATOS() {
        return TODOS_LOS_DATOS;
    }

    public static DatosRequeridoEnum getPOR_FACTURAS() {
        return POR_FACTURAS;
    }

    public static DatosRequeridoEnum getPOR_NOTASCREDITO() {
        return POR_NOTASCREDITO;
    }
    
    
    
}
