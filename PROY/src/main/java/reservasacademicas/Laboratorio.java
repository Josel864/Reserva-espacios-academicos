package reservasacademicas;

import java.util.Date;

public class Laboratorio extends EspacioAcademico {
    private int numEquipos;
    
    public Laboratorio(String nombre, int numEquipos) {
        super(nombre);
        this.numEquipos = numEquipos;
    }
    
    @Override
    public boolean verificarDisponibilidad(Date fecha) {
        return true;
    }
    
    public int getNumEquipos() {
        return numEquipos;
    }
}