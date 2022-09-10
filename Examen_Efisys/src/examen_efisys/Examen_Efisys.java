package examen_efisys;

import controller.Datos_Iniciales;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author eder0
 */
public class Examen_Efisys {


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        Datos_Iniciales datos = new Datos_Iniciales();
        ArrayList<Map<String, String>> informacion = datos.llenarListaMovimientos("", "", "", "");
        ArrayList<Map<String, Double>> datos_saldos_diarios = datos.calcularSaldoDiario(informacion, 0, 0, 0);
        double saldo_promedio = datos.calcularSaldosPromedio(datos_saldos_diarios);
    }

}
