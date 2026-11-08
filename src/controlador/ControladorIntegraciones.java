package controlador;

import java.util.ArrayList;
import modelo.Auditable;
import modelo.CatalogoIntegraciones;
import modelo.Consultable;
import modelo.Documentable;
import modelo.IntegracionAPI;
import modelo.Transaccional;
import vista.VistaConsola;

public class ControladorIntegraciones {

    private CatalogoIntegraciones catalogo;
    private VistaConsola vista;

    public ControladorIntegraciones(CatalogoIntegraciones catalogo, VistaConsola vista) {
        this.catalogo = catalogo;
        this.vista = vista;
    }

    public void iniciar() {
        catalogo.cargarDatosIniciales();
        vista.mostrarMensaje("Carga inicial completa: " + catalogo.getCantidad() + " integraciones registradas.");
        int opcion = -1;
        while (opcion != 0) {
        vista.mostrarMenu();
        opcion = vista.leerEntero("Elija una opcion: ");
        switch (opcion) {
                case 1:
                    listarIntegraciones();
                    break;
                case 2:
                    buscarIntegracion();
                    break;
                case 3:
                    ordenarPorLatencia();
                    break;
                case 4:
                    opcionCobro();
                    break;
                case 5:
                    opcionReintegro();
                    break;
                case 6:
                    opcionDocumento();
                    break;
                case 7:
                    opcionConsulta();
                    break;
                case 8:
                    opcionBitacora();
                    break;
                case 9:
                    demoCobroYReintegroATodas();
                    break;
                case 10:
                    cambiarConexion();
                    break;
                case 0:
                    vista.mostrarMensaje("Saliendo del sistema. Hasta luego!");
                    break;
                default:
                    vista.mostrarMensaje("Opcion no valida, intente de nuevo.");
                    break;
        }
        if (opcion != 0) {
        vista.pausar();
        }
    }
        
    }

    private void listarIntegraciones() {
        vista.mostrarIntegraciones(catalogo.getIntegraciones());
    }

    private void buscarIntegracion() {
        String id = vista.leerTexto("Escriba el ID de la integracion: ");
        IntegracionAPI encontrada = catalogo.buscarPorId(id);
        if (encontrada == null) {
            vista.mostrarMensaje("No existe ninguna integracion con el ID '" + id + "'.");
        } 
        else {
            vista.mostrarMensaje("Parametros de configuracion:");
            vista.mostrarMensaje(encontrada.getParametros());
        }
    }

    private void ordenarPorLatencia() {
        String respuesta = vista.leerTexto("Ordenar de mayor a menor latencia?: ");
        if (respuesta.equalsIgnoreCase("s")) {
            catalogo.ordenarPorLatenciaDescendente();
            vista.mostrarMensaje("Catalogo ordenado de MAYOR a MENOR latencia");
        } 
        else {
            catalogo.ordenarPorLatencia();
            vista.mostrarMensaje("Catalogo ordenado de MENOR a MAYOR latencia.");
        }
        vista.mostrarIntegraciones(catalogo.getIntegraciones());
    }

    private void opcionCobro() {
        IntegracionAPI api = pedirIntegracion();
        if (api == null) {
            return;
        }
        double monto = vista.leerDecimal("Monto a cobrar: ");
        String moneda = vista.leerTexto("Moneda: ");

        if (moneda.equals("")) {
            procesarCobro(api, monto);
        } 
        else {
            procesarCobro(api, monto, moneda);
        }
    }

    private void opcionReintegro() {
        IntegracionAPI api = pedirIntegracion();
        if (api == null) {
            return;
        }
        double monto = vista.leerDecimal("Monto a reintegrar: ");
        procesarReintegro(api, monto);
    }

    private void opcionDocumento() {
        IntegracionAPI api = pedirIntegracion();
        if (api == null) {
            return;
        }
        String referencia = vista.leerTexto("Referencia de la orden: ");
        procesarDocumento(api, referencia);
    }

    private void opcionConsulta() {
        IntegracionAPI api = pedirIntegracion();
        if (api == null) {
            return;
        }
        if (api instanceof Consultable) {
            Consultable consultable = (Consultable) api;
            String referencia = vista.leerTexto("Referencia / numero de guia a consultar: ");
            vista.mostrarMensaje(consultable.consultarEstado(referencia));
            double valor = vista.leerDecimal("Valor a cotizar: ");
            if (valor >= 0) {
                vista.mostrarMensaje("Cotizacion: Q" + consultable.cotizar(valor));
            }
        } 
        else {
            mostrarNoSoportado(api, "consultas ni cotizaciones");
        }
    }

    private void opcionBitacora() {
        IntegracionAPI api = pedirIntegracion();
        if (api == null) {
            return;
        }
        if (api instanceof Auditable) {
            Auditable auditable = (Auditable) api;
            ArrayList<String> bitacora = auditable.getBitacora();
            if (bitacora.size() == 0) {
                vista.mostrarMensaje("La bitacora de " + api.getNombre() + " esta vacia.");
            } 
            else {
                vista.mostrarMensaje("Bitacora de " + api.getNombre() + ":");
                vista.mostrarLineas(bitacora);
            }
        } 
        else {
            mostrarNoSoportado(api, "auditoria");
        }
    }

    private void cambiarConexion() {
        IntegracionAPI api = pedirIntegracion();
        if (api == null) {
            return;
        }
        api.setConectada(!api.isConectada());
        vista.mostrarMensaje(api.getNombre() + " ahora esta: " + api.getEstadoTexto());
    }

    private void demoCobroYReintegroATodas() {
        vista.mostrarMensaje("Intentando cobrar Q100 y reintegrar Q50 en cada integracion");
        ArrayList<IntegracionAPI> lista = catalogo.getIntegraciones();
        for (int i = 0; i < lista.size(); i++) {
            IntegracionAPI api = lista.get(i); 
            vista.mostrarMensaje(api.getNombre());
            procesarCobro(api, 100);
            procesarReintegro(api, 50);
        }
        vista.mostrarMensaje("El proceso termino completo, sin errores ni excepciones.");
    }

    private void procesarCobro(IntegracionAPI api, double monto) {
        if (api instanceof Transaccional) {
            procesarCobro((Transaccional) api, monto);
        } 
        else {
            mostrarNoSoportado(api, "cobros");
        }
    }

    private void procesarCobro(IntegracionAPI api, double monto, String moneda) {
        if (api instanceof Transaccional) {
            procesarCobro((Transaccional) api, monto, moneda);
        } 
        else {
            mostrarNoSoportado(api, "cobros");
        }
    }

    private void procesarCobro(Transaccional pasarela, double monto) {
        boolean exito = pasarela.cobrar(monto);
        mostrarResultado(exito, "Cobro de Q" + monto);
    }

    private void procesarCobro(Transaccional pasarela, double monto, String moneda) {
        boolean exito = pasarela.cobrar(monto, moneda);
        mostrarResultado(exito, "Cobro de " + monto + " " + moneda);
    }

    private void procesarReintegro(IntegracionAPI api, double monto) {
        if (api instanceof Transaccional) {
            procesarReintegro((Transaccional) api, monto);
        } 
        else {
            mostrarNoSoportado(api, "reintegros");
        }
    }

    private void procesarReintegro(Transaccional pasarela, double monto) {
        boolean exito = pasarela.reintegrar(monto);
        mostrarResultado(exito, "Reintegro de Q" + monto);
    }

    private void procesarDocumento(IntegracionAPI api, String referencia) {
        if (api instanceof Documentable) {
            procesarDocumento((Documentable) api, referencia);
        } 
        else {
            mostrarNoSoportado(api, "documentos");
        }
    }

    private void procesarDocumento(Documentable generador, String referencia) {
        String documento = generador.generarDocumento(referencia);
        vista.mostrarMensaje("Resultado de la solicitud de documento:");
        vista.mostrarMensaje(documento);
    }

    private IntegracionAPI pedirIntegracion() {
        String id = vista.leerTexto("ID de la integracion: ");
        IntegracionAPI api = catalogo.buscarPorId(id);
        if (api == null) {
            vista.mostrarMensaje("No existe ninguna integracion con el ID '" + id + "'.");
        }
        return api;
    }

    private void mostrarNoSoportado(IntegracionAPI api, String operacion) {
        vista.mostrarMensaje("IGNORADO " + api.getNombre() + " (" + api.getTipo() + ") no soporta " + operacion + ". La peticion se omite y el proceso continua.");
    }

    private void mostrarResultado(boolean exito, String operacion) {
        if (exito) {
            vista.mostrarMensaje("OK " + operacion + " realizado correctamente.");
        } 
        else {
            vista.mostrarMensaje("RECHAZADO " + operacion + " no se pudo realizar " + "(sin conexion, monto invalido o limite superado).");
        }
    }
}
