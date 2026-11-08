package modelo;

import java.util.ArrayList;

public class CertificadorFEL extends IntegracionAPI implements Documentable, Auditable {

    private String nitCertificador;
    private int contadorFacturas;
    private ArrayList<String> bitacora;

    public CertificadorFEL(String id, String nombre, String proveedor, int latenciaMs, boolean conectada, String nitCertificador) {
        super(id, nombre, proveedor, latenciaMs, conectada);
        this.nitCertificador = nitCertificador;
        this.contadorFacturas = 0;
        this.bitacora = new ArrayList<String>();
    }

    public String getTipo() {
        return "Certificador FEL";
    }

    public String generarDocumento(String referencia) {
        if (!isConectada()) {
            registrarAuditoria("FACTURA RECHAZADA (sin conexion) referencia:" + referencia);
            return "No se pudo certificar la factura: " + getNombre() + " esta sin conexion.";
        }
        contadorFacturas++;
        String numeroAutorizacion = "FEL-" + getId() + "-" + (1000 + contadorFacturas);
        registrarAuditoria("FACTURA OK " + numeroAutorizacion + " referencia: " + referencia);
        String xml = "FacturaElectronica";
        xml = xml + " NIT Certificador" + nitCertificador + "NIT Certificador";
        xml = xml + " NumeroAutorizacion" + numeroAutorizacion + "NumeroAutorizacion";
        xml = xml + "  Referencia" + referencia + "Referencia";
        xml = xml + "FacturaElectronica";
        return xml;
    }

    public void registrarAuditoria(String accion) {
        bitacora.add(getNombre() + " | " + accion);
    }

    @Override
    public ArrayList<String> getBitacora() {
        return bitacora;
    }

    @Override
    public String getParametros() {
        return super.getParametros() + "  NIT certificador: " + nitCertificador + "  Facturas emitidas: " + contadorFacturas;
    }

    public String getNitCertificador() {
        return nitCertificador;
    }

    public int getContadorFacturas() {
        return contadorFacturas;
    }
}
