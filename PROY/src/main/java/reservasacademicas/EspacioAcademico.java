package reservasacademicas;

import java.util.Date;

public abstract class EspacioAcademico {
    protected String nombre;
    
    public EspacioAcademico(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public abstract boolean verificarDisponibilidad(Date fecha);
}