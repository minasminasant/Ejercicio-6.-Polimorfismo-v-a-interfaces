package modelo;

public class ProveedorLogistico extends IntegracionAPI implements Consultable, Documentable {

    private double tarifaBase;        
    private double tarifaPorLibra;    
    private int contadorGuias;

    public ProveedorLogistico(String id, String nombre, String proveedor, int latenciaMs, boolean conectada, double tarifaBase, double tarifaPorLibra) {
        super(id, nombre, proveedor, latenciaMs, conectada);
        this.tarifaBase = tarifaBase;
        this.tarifaPorLibra = tarifaPorLibra;
        this.contadorGuias = 0;
    }

    @Override
    public String getTipo() {
        return "Proveedor Logistico";
    }

    @Override
    public String consultarEstado(String referencia) {
        if (!isConectada()) {
            return "No se pudo rastrear: " + getNombre() + " esta sin conexion.";
        }
        return "Paquete " + referencia + " con " + getNombre() + ": EN TRANSITO";
    }

    @Override
    public double cotizar(double valor) {
        return tarifaBase + (valor * tarifaPorLibra);
    }

    @Override
    public String generarDocumento(String referencia) {
        if (!isConectada()) {
            return "No se pudo generar la guia: " + getNombre() + " esta sin conexion.";
        }
        contadorGuias++;
        String xml = "Guia Envio";
        xml = xml + "Numero" + getId() + "-" + contadorGuias + "Numero";
        xml = xml + "Transportista" + getNombre() + "Transportista";
        xml = xml + "Referencia" + referencia + "Referencia";
        xml = xml + "Guia Envio";
        return xml;
    }

    @Override
    public String getParametros() {
        return super.getParametros() + "Tarifa base:  Q" + tarifaBase + "  Tarifa/libra: Q" + tarifaPorLibra;
    }

    public double getTarifaBase() {
        return tarifaBase;
    }

    public double getTarifaPorLibra() {
        return tarifaPorLibra;
    }
}
