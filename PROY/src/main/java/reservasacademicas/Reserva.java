package reservasacademicas;

import java.util.Date;
import java.text.SimpleDateFormat; // Importación añadida

public class Reserva {
    private Usuario usuario;
    private Date fecha;
    private String evento;
    private EspacioAcademico espacio;
    
    public Reserva(Usuario usuario, Date fecha, String evento, EspacioAcademico espacio) {
        this.usuario = usuario;
        this.fecha = fecha;
        this.evento = evento;
        this.espacio = espacio;
    }
    
    public Usuario getUsuario() { return usuario; }
    public Date getFecha() { return fecha; }
    public String getEvento() { return evento; }
    public EspacioAcademico getEspacio() { return espacio; }
       @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return String.format(
            "Espacio: %s (%s)%nFecha: %s%nEvento: %s%nUsuario: %s%n--------------------------------",
            espacio.getNombre(),
            espacio.getClass().getSimpleName(),
            sdf.format(fecha),
            evento,
            usuario.getNombre()
        );
    }
    
}