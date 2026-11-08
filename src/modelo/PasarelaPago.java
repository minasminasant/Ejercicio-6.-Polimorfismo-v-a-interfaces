package modelo;

import java.util.ArrayList;

public abstract class PasarelaPago extends IntegracionAPI implements Transaccional, Consultable, Auditable {
    private static final double TASA_USD = 7.80;

    private double porcentajeComision;   
    private double totalCobrado;         
    private int contadorOperaciones;     
    private ArrayList<String> bitacora;

    public PasarelaPago(String id, String nombre, String proveedor, int latenciaMs, boolean conectada, double porcentajeComision) {
        super(id, nombre, proveedor, latenciaMs, conectada);
        this.porcentajeComision = porcentajeComision;
        this.totalCobrado = 0;
        this.contadorOperaciones = 0;
        this.bitacora = new ArrayList<String>();
    }

    public boolean cobrar(double monto) {
        return cobrar(monto, "GTQ");
    }

    @Override
    public boolean cobrar(double monto, String moneda) {
        if (!isConectada()) {
            registrarAuditoria("COBRO RECHAZADO (sin conexion) monto=" + monto + " " + moneda);
            return false;
        }
        if (monto <= 0) {
            registrarAuditoria("COBRO RECHAZADO (monto invalido) monto=" + monto);
            return false;
        }
        double montoEnQuetzales = convertirAQuetzales(monto, moneda);
        totalCobrado = totalCobrado + montoEnQuetzales;
        contadorOperaciones++;
        registrarAuditoria("COBRO OK " + monto + " " + moneda + " (Q" + montoEnQuetzales + ")");
        return true;
    }

    protected double convertirAQuetzales(double monto, String moneda) {
        if (moneda.equalsIgnoreCase("USD")) {
            return monto * TASA_USD;
        }
        return monto;
    }

    @Override
    public boolean reintegrar(double monto) {
        if (!isConectada()) {
            registrarAuditoria("REINTEGRO RECHAZADO (sin conexion) monto=" + monto);
            return false;
        }
        if (monto <= 0 || monto > totalCobrado) {
            registrarAuditoria("REINTEGRO RECHAZADO (monto invalido o mayor al cobrado) monto=" + monto);
            return false;
        }
        totalCobrado = totalCobrado - monto;
        contadorOperaciones++;
        registrarAuditoria("REINTEGRO OK Q" + monto);
        return true;
    }

    @Override
    public String consultarEstado(String referencia) {
        if (!isConectada()) {
            return "No se pudo consultar: " + getNombre() + " esta sin conexion.";
        }
        return "Transaccion " + referencia + " en " + getNombre() + ": APROBADA";
    }

    @Override
    public double cotizar(double valor) {
        return valor * porcentajeComision / 100;
    }

    @Override
    public void registrarAuditoria(String accion) {
        bitacora.add(getNombre() + " | " + accion);
    }

    @Override
    public ArrayList<String> getBitacora() {
        return bitacora;
    }

    @Override
    public String getParametros() {
        return super.getParametros() + "Comision:" + porcentajeComision + "  Total cobrado: Q" + totalCobrado;
    }

    public double getPorcentajeComision() {
        return porcentajeComision;
    }

    public void setPorcentajeComision(double porcentajeComision) {
        this.porcentajeComision = porcentajeComision;
    }

    public double getTotalCobrado() {
        return totalCobrado;
    }

    public int getContadorOperaciones() {
        return contadorOperaciones;
    }
}
