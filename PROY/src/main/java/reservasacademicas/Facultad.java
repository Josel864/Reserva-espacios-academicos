package reservasacademicas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Facultad {
    private String nombre;
    private List<EspacioAcademico> espacios;
    
    public Facultad(String nombre) {
        this.nombre = nombre;
        this.espacios = new ArrayList<>();
        inicializarEspacios();
    }
    
private void inicializarEspacios() {
    // Asegúrate de que cada facultad tenga espacios
    espacios.add(new Aula("Aula 101", 30));
    espacios.add(new Aula("Aula 102", 25));
    espacios.add(new Laboratorio("Lab de Computación", 20));
    espacios.add(new Auditorio("Auditorio Principal", 100));
    espacios.add(new Cancha("Cancha de Fútbol", "Césped"));
}
    
    public List<EspacioAcademico> getEspaciosDisponibles(Date fecha) {
        List<EspacioAcademico> disponibles = new ArrayList<>();
        for (EspacioAcademico espacio : espacios) {
            if (espacio.verificarDisponibilidad(fecha)) {
                disponibles.add(espacio);
            }
        }
        return disponibles;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}