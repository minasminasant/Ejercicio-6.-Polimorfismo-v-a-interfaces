import controlador.ControladorIntegraciones;
import modelo.CatalogoIntegraciones;
import vista.VistaConsola;

public class Main {

    public static void main(String[] args) {
        CatalogoIntegraciones catalogo = new CatalogoIntegraciones();   
        VistaConsola vista = new VistaConsola();                         
        ControladorIntegraciones controlador = new ControladorIntegraciones(catalogo, vista);           
        controlador.iniciar();
    }
}
