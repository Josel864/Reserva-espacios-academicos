package reservasacademicas;

import javax.swing.UIManager; // Importación añadida
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Configurar el Look and Feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Usuario u = new Usuario("Ana Pérez", "987654", "Docente", 35);
        u.guardarEnBD();
        Usuario u1 = new Usuario("Prueba", "123", "Estudiante", 20);
        u.guardarEnBD();
        // Crear y mostrar la interfaz gráfica
        SwingUtilities.invokeLater(() -> {
            ReservaEspaciosFrame frame = new ReservaEspaciosFrame();
            frame.setVisible(true);
        });
        
    }
}