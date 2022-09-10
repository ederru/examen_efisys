package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 *
 * @author eder0
 */
public class Datos_Iniciales {

    public static ArrayList<Map<String, String>> llenarListaMovimientos(String mov_fecha, String mov_natura, String mov_cant,
            String mov_descri) {

        ArrayList<Map<String, String>> lista_movimientos = new ArrayList();
        Map<String, String> mapa_datos = new HashMap();

        mapa_datos.put("Mov_Fecha", mov_fecha);
        mapa_datos.put("Mov_Natura", mov_natura);
        mapa_datos.put("Mov_Cant", mov_cant);
        mapa_datos.put("Mov_Descri", mov_descri);

        return lista_movimientos;
    }

    public static ArrayList<Map<String, Double>> calcularSaldoDiario(ArrayList<Map<String, String>> lista_movimientos, int dias_mes_actual, int mes_actual, int anio_actual ) {
        ArrayList<Map<String, Double>> lista_saldos_diarios = new ArrayList();
        Map<String, Double>  mapa_saldo_diario = new HashMap();
        double saldo_diario = 0;
        for (int i = 1; i < dias_mes_actual; i++) {
            LocalDate fecha_actual = LocalDate.of(anio_actual, mes_actual, i);

            ArrayList<Map<String, String>> lista_movimientos_dia = new ArrayList();
            lista_movimientos_dia.addAll(lista_movimientos
                    .stream()
                    .filter(m -> LocalDate.parse(m.get("mov_fecha")).isEqual(fecha_actual))
                    .collect(Collectors.toList()));

            ArrayList<Map<String, String>> lista_abonos = new ArrayList();
            lista_abonos.addAll(lista_movimientos_dia
                    .stream()
                    .filter(m -> m.get("Mov_Natura").contains("Abono"))
                    .collect(Collectors.toList()));

            ArrayList<Map<String, String>> lista_cargos = new ArrayList(); 
            lista_cargos.addAll(lista_movimientos_dia
                    .stream()
                    .filter(m -> m.get("Mov_Natura").contains("Cargo"))
                    .collect(Collectors.toList()));

            double suma_abonos = lista_abonos
                    .stream()
                    .mapToDouble(m -> Double.parseDouble(m.get("Mov_Cant"))).sum();

            double suma_cargos = lista_cargos
                    .stream()
                    .mapToDouble(m -> Double.parseDouble(m.get("Mov_Cant"))).sum();

            saldo_diario = suma_abonos - suma_cargos;
            
            mapa_saldo_diario.put("saldo_diario", saldo_diario);
            lista_saldos_diarios.add(mapa_saldo_diario);
        }
        
        return lista_saldos_diarios;
    }
    
    public static double calcularSaldosPromedio(ArrayList<Map<String, Double>> lista_saldos_diarios) {
        double suma_saldos = lista_saldos_diarios
                    .stream()
                    .mapToDouble(m -> m.get("saldo_diario")).sum();
        
        return suma_saldos / lista_saldos_diarios.size();
    }
}
