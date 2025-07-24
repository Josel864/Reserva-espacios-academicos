package reservasacademicas;

import java.util.Date;

public class Cancha extends EspacioAcademico {
    private String tipoSuperficie;
    
    public Cancha(String nombre, String tipoSuperficie) {
        super(nombre);
        this.tipoSuperficie = tipoSuperficie;
    }
    
    @Override
    public boolean verificarDisponibilidad(Date fecha) {
        return true;
    }
    
    public String getTipoSuperficie() {
        return tipoSuperficie;
    }
}