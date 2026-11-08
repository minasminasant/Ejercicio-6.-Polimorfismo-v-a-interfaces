package modelo;

public class BilleteraDigital extends PasarelaPago {
    private double limitePorTransaccion;  

    public BilleteraDigital(String id, String nombre, String proveedor, int latenciaMs, boolean conectada, double porcentajeComision, double limitePorTransaccion) {
        super(id, nombre, proveedor, latenciaMs, conectada, porcentajeComision);
        this.limitePorTransaccion = limitePorTransaccion;
    }

    public boolean cobrar(double monto, String moneda) {
        double montoEnQuetzales = convertirAQuetzales(monto, moneda);
        if (montoEnQuetzales > limitePorTransaccion) {
            registrarAuditoria("COBRO RECHAZADO (supera el limite de Q" + limitePorTransaccion + ")");
            return false;
        }
        return super.cobrar(monto, moneda);
    }

    public String getTipo() {
        return "Billetera Digital";
    }

    public String getParametros() {
        return super.getParametros() + "  Limite/transaccion: Q" + limitePorTransaccion;
    }

    public double getLimitePorTransaccion() {
        return limitePorTransaccion;
    }

    public void setLimitePorTransaccion(double limitePorTransaccion) {
        this.limitePorTransaccion = limitePorTransaccion;
    }
}
