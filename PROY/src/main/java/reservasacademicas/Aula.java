package reservasacademicas;

import java.util.Date;

public class Aula extends EspacioAcademico {
    private int capacidad;
    
    public Aula(String nombre, int capacidad) {
        super(nombre);
        this.capacidad = capacidad;
    }
    
    @Override
    public boolean verificarDisponibilidad(Date fecha) {
        return true;
    }
    
    public int getCapacidad() {
        return capacidad;
    }
}