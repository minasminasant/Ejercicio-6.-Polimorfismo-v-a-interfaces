package modelo;

import java.util.ArrayList;
import java.util.Collections;

public class CatalogoIntegraciones {
    private ArrayList<IntegracionAPI> integraciones;

    public CatalogoIntegraciones() {
        integraciones = new ArrayList<IntegracionAPI>();
    }

    public boolean agregar(IntegracionAPI nueva) {
        if (integraciones.contains(nueva)) {
            return false;
        }
        integraciones.add(nueva);
        return true;
    }

    public IntegracionAPI buscarPorId(String id) {
        for (int i = 0; i < integraciones.size(); i++) {
            IntegracionAPI actual = integraciones.get(i);
            if (actual.getId().equalsIgnoreCase(id)) {
                return actual;
            }
        }
        return null;
    }

    public void ordenarPorLatencia() {
        Collections.sort(integraciones);
    }

    public void ordenarPorLatenciaDescendente() {
        Collections.sort(integraciones);
        Collections.reverse(integraciones);
    }

    public ArrayList<IntegracionAPI> getIntegraciones() {
        return integraciones;
    }

    public int getCantidad() {
        return integraciones.size();
    }

    public void cargarDatosIniciales(){
        agregar(new PasarelaTarjeta("PAY01", "Credomatic Pay", "BAC Credomatic", 180, true, 3.5, "Visa/Mastercard"));
        agregar(new PasarelaTarjeta("PAY02", "VisaNet Guatemala", "VisaNet", 240, true, 3.2, "Visa"));

        agregar(new PasarelaTransferencia("TRF01", "Banrural ACH", "Banrural", 520, true, 1.0, "Banrural", 24));
        agregar(new PasarelaTransferencia("TRF02", "Transferencia BAM", "Banco Agromercantil", 610, false, 1.2, "BAM", 12));

        agregar(new BilleteraDigital("WAL01", "Tigo Money", "Tigo", 150, true, 2.5, 5000));
        agregar(new BilleteraDigital("WAL02", "PayPal GT", "PayPal", 330, true, 4.4, 20000));

        agregar(new ProveedorLogistico("LOG01", "Cargo Expreso", "Cargo Expreso S.A.", 410, true, 25.0, 3.5));
        agregar(new ProveedorLogistico("LOG02", "Guatex", "Guatex", 380, true, 30.0, 3.0));
        agregar(new ProveedorLogistico("LOG03", "Forza Delivery", "Forza", 890, false, 20.0, 4.0));

        agregar(new CertificadorFEL("FEL01", "Infile", "Infile S.A.", 700, true, "1234567-8"));
        agregar(new CertificadorFEL("FEL02", "Megaprint", "Megaprint S.A.", 450, true, "8765432-1"));
        agregar(new CertificadorFEL("FEL03", "Digifact", "Digifact S.A.", 295, true, "5555555-5"));
    }
}
