package modelo;

import java.util.ArrayList;

public interface Auditable {
    void registrarAuditoria(String accion);
    ArrayList<String> getBitacora();
}
