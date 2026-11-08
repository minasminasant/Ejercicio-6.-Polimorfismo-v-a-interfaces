package modelo;

public class PasarelaTransferencia extends PasarelaPago {

    private String banco;
    private int horasAcreditacion;  

    public PasarelaTransferencia(String id, String nombre, String proveedor, int latenciaMs, boolean conectada, double porcentajeComision, String banco, int horasAcreditacion) {
        super(id, nombre, proveedor, latenciaMs, conectada, porcentajeComision);
        this.banco = banco;
        this.horasAcreditacion = horasAcreditacion;
    }

    @Override
    public String getTipo() {
        return "Pasarela Transferencia";
    }

    @Override
    public String getParametros() {
        return super.getParametros() + "Banco:" + banco + " Acreditacion:" + horasAcreditacion + " horas";
    }

    public String getBanco() {
        return banco;
    }

    public int getHorasAcreditacion() {
        return horasAcreditacion;
    }
}
