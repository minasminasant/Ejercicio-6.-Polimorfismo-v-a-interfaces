package vista;

import java.util.ArrayList;
import java.util.Scanner;
import modelo.IntegracionAPI;

public class VistaConsola {

    private Scanner teclado;

    public VistaConsola() {
        teclado = new Scanner(System.in);
    }

    public void mostrarMenu() {
        System.out.println();
        System.out.println("-------------------------------------------------");
        System.out.println("GESTOR DE INTEGRACIONES");
        System.out.println("-----------------------------------------------");
        System.out.println(" 1. Listar integraciones y su estado");
        System.out.println(" 2. Buscar integracion por ID (parametros)");
        System.out.println(" 3. Ordenar por latencia promedio");
        System.out.println(" 4. Procesar un cobro");
        System.out.println(" 5. Procesar un reintegro");
        System.out.println(" 6. Generar documento (factura FEL / guia)");
        System.out.println(" 7. Consultar estado / cotizar");
        System.out.println(" 8. Ver bitacora de auditoria");
        System.out.println(" 9. cobro y reintegro en TODAS las integraciones");
        System.out.println("10. Conectar / desconectar una integracion");
        System.out.println(" 0. Salir");
    }

    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return teclado.nextLine().trim();
    }

    public int leerEntero(String mensaje) {
        String texto = leerTexto(mensaje);
        try {
            return Integer.parseInt(texto);
        } 
        catch (NumberFormatException e) {
            return -1;
        }
    }

    public double leerDecimal(String mensaje) {
        String texto = leerTexto(mensaje);
        try {
            return Double.parseDouble(texto);
        } 
        catch (NumberFormatException e) {
            return -1;
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarIntegraciones(ArrayList<IntegracionAPI> lista) {
        System.out.println();
        System.out.println(String.format("%-8s %-26s %-26s %8s  %s", "ID", "NOMBRE", "TIPO", "LATENCIA", "ESTADO"));
        for (int i = 0; i < lista.size(); i++) {
            System.out.println(lista.get(i));
        }
        System.out.println("Total: " + lista.size() + " integraciones");
    }

    public void mostrarLineas(ArrayList<String> lineas) {
        for (int i = 0; i < lineas.size(); i++) {
            System.out.println("  " + lineas.get(i));
        }
    }

    public void pausar() {
        System.out.print("Presione ENTER para continuar");
        teclado.nextLine();
    }
}
