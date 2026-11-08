package modelo;

public class PasarelaTarjeta extends PasarelaPago {

    private String redTarjeta;   

    public PasarelaTarjeta(String id, String nombre, String proveedor, int latenciaMs, boolean conectada, double porcentajeComision, String redTarjeta) {
        super(id, nombre, proveedor, latenciaMs, conectada, porcentajeComision);
        this.redTarjeta = redTarjeta;
    }

    @Override
    public String getTipo() {
        return "Pasarela Tarjeta";
    }

    @Override
    public String getParametros() {
        return super.getParametros() + "Red de tarjeta: " + redTarjeta;
    }

    public String getRedTarjeta() {
        return redTarjeta;
    }
}
