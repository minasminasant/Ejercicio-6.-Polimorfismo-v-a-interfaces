package modelo;

public abstract class IntegracionAPI implements Comparable<IntegracionAPI> {
    private String id;
    private String nombre;
    private String proveedor;
    private int latenciaMs;      
    private boolean conectada;   

    public IntegracionAPI(String id, String nombre, String proveedor, int latenciaMs) {
        this.id = id;
        this.nombre = nombre;
        this.proveedor = proveedor;
        this.latenciaMs = latenciaMs;
        this.conectada = true;
    }

    public IntegracionAPI(String id, String nombre, String proveedor, int latenciaMs, boolean conectada) {
        this(id, nombre, proveedor, latenciaMs);
        this.conectada = conectada;
    }

    public abstract String getTipo();

    public String getParametros() {
        String texto = "";
        texto = texto + "ID:" + id;
        texto = texto + "Nombre:" + nombre;
        texto = texto + "Tipo:" + getTipo();
        texto = texto + "Proveedor:" + proveedor;
        texto = texto + "Latencia:" + latenciaMs;
        texto = texto + "Estado:" + getEstadoTexto();
        return texto;
    }

    public String getEstadoTexto() {
        if (conectada) {
            return "CONECTADA";
        } 
        else {
            return "SIN CONEXION";
        }
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getProveedor() {
        return proveedor;
    }

    public int getLatenciaMs() {
        return latenciaMs;
    }

    public void setLatenciaMs(int latenciaMs) {
        this.latenciaMs = latenciaMs;
    }

    public boolean isConectada() {
        return conectada;
    }

    public void setConectada(boolean conectada) {
        this.conectada = conectada;
    }

    public int compareTo(IntegracionAPI otra) {
        int resultado = Integer.compare(this.latenciaMs, otra.latenciaMs);
        if (resultado == 0) {
            resultado = this.id.compareToIgnoreCase(otra.id);
        }
        return resultado;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }
        if (objeto == null || getClass() != objeto.getClass()) {
            return false;
        }
        IntegracionAPI otra = (IntegracionAPI) objeto;
        return this.id.equalsIgnoreCase(otra.id);
    }

    @Override
    public int hashCode() {
        return id.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return String.format("[%-6s] %-26s %-26s %5d ms  %s", id, nombre, "(" + getTipo() + ")", latenciaMs, getEstadoTexto());
    }
}
