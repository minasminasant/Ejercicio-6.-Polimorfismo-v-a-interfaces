package modelo;

public interface Transaccional {
    boolean cobrar(double monto);
    boolean cobrar(double monto, String moneda);
    boolean reintegrar(double monto);
}
