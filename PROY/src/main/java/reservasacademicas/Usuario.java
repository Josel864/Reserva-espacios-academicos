package reservasacademicas;

import java.sql.*;
import java.util.*;

public class Usuario {
    private String nombre;
    private String identificacion;
    private String tipo;
    private int edad;

    public Usuario(String nombre, String identificacion, String tipo, int edad) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.tipo = tipo;
        this.edad = edad;
    }

    public boolean esMayorDeEdad() {
        return edad >= 18;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getTipo() {
        return tipo;
    }

    public int getEdad() {
        return edad;
    }

    // Guardar este usuario en la base de datos
    public void guardarEnBD() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO usuarios (nombre, identificacion, tipo, edad) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre);
            stmt.setString(2, identificacion);
            stmt.setString(3, tipo);
            stmt.setInt(4, edad);
            stmt.executeUpdate();
            System.out.println("Usuario guardado: " + nombre);
        } catch (SQLException e) {
            System.err.println("Error al guardar el usuario: " + e.getMessage());
        }
    }

    // Obtener todos los usuarios de la base de datos
    public static List<Usuario> obtenerUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM usuarios";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String identificacion = rs.getString("identificacion");
                String tipo = rs.getString("tipo");
                int edad = rs.getInt("edad");
                lista.add(new Usuario(nombre, identificacion, tipo, edad));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public String toString() {
        return "Usuario{" +
               "nombre='" + nombre + '\'' +
               ", identificacion='" + identificacion + '\'' +
               ", tipo='" + tipo + '\'' +
               ", edad=" + edad +
               '}';
    }
}
