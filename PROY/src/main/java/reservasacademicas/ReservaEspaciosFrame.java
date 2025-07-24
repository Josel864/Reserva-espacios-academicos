package reservasacademicas;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class ReservaEspaciosFrame extends JFrame {
    // Lista principal de todas las reservas
    private List<Reserva> todasLasReservas = new ArrayList<>();
    
    // Mapa para organizar reservas por usuario
    private Map<String, List<Reserva>> reservasPorUsuario = new HashMap<>();
    
    // Componentes de la interfaz
    private JComboBox<Facultad> cbFacultades;
    private JComboBox<EspacioAcademico> cbEspacios;
    private JTextField txtNombre, txtIdentificacion, txtEvento;
    private JComboBox<String> cbTipoUsuario;
    private JSpinner spinnerEdad, spinnerFecha;
    private JButton btnReservar, btnListar;
    private JTextArea txtResultado;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public ReservaEspaciosFrame() {
        initComponents();
        setupFrame();
    }

    private void initComponents() {
        setTitle("Sistema de Reservas Académicas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Panel de formulario
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));

        // Componentes del formulario
        formPanel.add(new JLabel("Facultad:"));
        cbFacultades = new JComboBox<>();
        initFacultades();
        cbFacultades.addActionListener(e -> actualizarEspacios());
        formPanel.add(cbFacultades);

        formPanel.add(new JLabel("Espacio Académico:"));
        cbEspacios = new JComboBox<>();
        actualizarEspacios();
        formPanel.add(cbEspacios);

        formPanel.add(new JLabel("Nombre Completo:"));
        txtNombre = new JTextField();
        formPanel.add(txtNombre);

        formPanel.add(new JLabel("Identificación:"));
        txtIdentificacion = new JTextField();
        formPanel.add(txtIdentificacion);

        formPanel.add(new JLabel("Tipo de Usuario:"));
        cbTipoUsuario = new JComboBox<>(new String[]{"Estudiante", "Docente", "Administrativo"});
        formPanel.add(cbTipoUsuario);

        formPanel.add(new JLabel("Edad:"));
        spinnerEdad = new JSpinner(new SpinnerNumberModel(18, 1, 100, 1));
        formPanel.add(spinnerEdad);

        formPanel.add(new JLabel("Fecha y Hora:"));
        spinnerFecha = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinnerFecha, "dd/MM/yyyy HH:mm");
        spinnerFecha.setEditor(dateEditor);
        formPanel.add(spinnerFecha);

        formPanel.add(new JLabel("Evento/Motivo:"));
        txtEvento = new JTextField();
        formPanel.add(txtEvento);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        btnReservar = new JButton("Realizar Reserva");
        btnListar = new JButton("Ver Mis Reservas");

        // Estilo de botones
        btnReservar.setBackground(new Color(0, 102, 204));
        btnReservar.setForeground(Color.WHITE);
        btnListar.setBackground(new Color(70, 70, 70));
        btnListar.setForeground(Color.WHITE);

        // Asignar acciones a los botones
        btnReservar.addActionListener(e -> realizarReserva());
        btnListar.addActionListener(e -> listarReservas());

        buttonPanel.add(btnReservar);
        buttonPanel.add(btnListar);

        // Área de resultados
        txtResultado = new JTextArea(15, 50);
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Monospaced", Font.PLAIN, 14));
        txtResultado.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(txtResultado);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Detalles de Reservas"));

        // Organizar componentes
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void setupFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initFacultades() {
        Facultad[] facultades = {
            new Facultad("Agronomía"),
            new Facultad("Arquitectura, Artes, Diseño y Urbanismo"),
            new Facultad("Ciencias Económicas y Financieras"),
            new Facultad("Ciencias Farmacéuticas y Bioquímicas"),
            new Facultad("Ciencias Geológicas"),
            new Facultad("Ciencias Puras y Naturales"),
            new Facultad("Ciencias Sociales"),
            new Facultad("Derecho y Ciencias Políticas"),
            new Facultad("Humanidades y Ciencias de la Educación"),
            new Facultad("Ingeniería"),
            new Facultad("Medicina, Enfermería, Nutrición y Tecnología Médica"),
            new Facultad("Odontología"),
            new Facultad("Tecnología")
        };
        
        DefaultComboBoxModel<Facultad> model = new DefaultComboBoxModel<>(facultades);
        cbFacultades.setModel(model);
    }

    private void actualizarEspacios() {
        Facultad fac = (Facultad) cbFacultades.getSelectedItem();
        cbEspacios.removeAllItems();
        
        if (fac != null) {
            List<EspacioAcademico> disponibles = fac.getEspaciosDisponibles(new Date());
            DefaultComboBoxModel<EspacioAcademico> model = new DefaultComboBoxModel<>();
            
            for (EspacioAcademico espacio : disponibles) {
                model.addElement(espacio);
            }
            
            cbEspacios.setModel(model);
            cbEspacios.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                        boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof EspacioAcademico) {
                        EspacioAcademico espacio = (EspacioAcademico) value;
                        setText(espacio.getNombre() + " (" + espacio.getClass().getSimpleName() + ")");
                    }
                    return this;
                }
            });
        }
    }

    private void realizarReserva() {
        try {
            // Validaciones
            if (txtNombre.getText().trim().isEmpty() || 
                txtIdentificacion.getText().trim().isEmpty() || 
                txtEvento.getText().trim().isEmpty()) {
                
                JOptionPane.showMessageDialog(this, 
                    "Nombre, identificación y evento son campos obligatorios", 
                    "Datos incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int edad = (Integer) spinnerEdad.getValue();
            if (edad < 18) {
                JOptionPane.showMessageDialog(this, 
                    "Debe ser mayor de edad para realizar reservas", 
                    "Edad no válida", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Crear usuario
            Usuario usuario = new Usuario(
                txtNombre.getText().trim(),
                txtIdentificacion.getText().trim(),
                (String) cbTipoUsuario.getSelectedItem(),
                edad
            );
            
            // Obtener espacio seleccionado
            EspacioAcademico espacio = (EspacioAcademico) cbEspacios.getSelectedItem();
            if (espacio == null) {
                JOptionPane.showMessageDialog(this, 
                    "Debe seleccionar un espacio académico válido", 
                    "Espacio no válido", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Obtener fecha y evento
            Date fecha = (Date) spinnerFecha.getValue();
            String evento = txtEvento.getText().trim();
            
            // Crear reserva
            Reserva reserva = new Reserva(usuario, fecha, evento, espacio);
            
            // Almacenar en ambas estructuras
            todasLasReservas.add(reserva);
            
            // Almacenar por usuario
            String identificacion = usuario.getIdentificacion();
            if (!reservasPorUsuario.containsKey(identificacion)) {
                reservasPorUsuario.put(identificacion, new ArrayList<>());
            }
            reservasPorUsuario.get(identificacion).add(reserva);
            
            // Mostrar confirmación
            mostrarConfirmacionReserva(reserva);
            
            // Limpiar campos para nueva reserva
            txtEvento.setText("");
            spinnerFecha.setValue(new Date());
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al realizar reserva: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarConfirmacionReserva(Reserva reserva) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== RESERVA CONFIRMADA ===\n\n");
        sb.append("Facultad: ").append(((Facultad) cbFacultades.getSelectedItem()).getNombre()).append("\n");
        sb.append("Espacio: ").append(reserva.getEspacio().getNombre()).append("\n");
        sb.append("Tipo: ").append(reserva.getEspacio().getClass().getSimpleName()).append("\n");
        sb.append("Fecha: ").append(dateFormat.format(reserva.getFecha())).append("\n");
        sb.append("Evento: ").append(reserva.getEvento()).append("\n");
        sb.append("Usuario: ").append(reserva.getUsuario().getNombre()).append("\n");
        sb.append("Identificación: ").append(reserva.getUsuario().getIdentificacion()).append("\n\n");
        sb.append("Código de reserva: ").append(generarCodigoReserva(reserva)).append("\n");
        
        txtResultado.setText(sb.toString());
    }

    private String generarCodigoReserva(Reserva reserva) {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
        return reserva.getEspacio().getNombre().substring(0, 3).toUpperCase() + 
               "-" + 
               sdf.format(reserva.getFecha()) + 
               "-" + 
               reserva.getUsuario().getIdentificacion().substring(0, 3);
    }

    private void listarReservas() {
        try {
            String identificacion = txtIdentificacion.getText().trim();
            
            if (identificacion.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Ingrese su identificación para ver sus reservas",
                    "Identificación requerida", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            List<Reserva> misReservas = reservasPorUsuario.getOrDefault(identificacion, new ArrayList<>());
            
            if (misReservas.isEmpty()) {
                txtResultado.setText("No se encontraron reservas para la identificación: " + identificacion);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("=== MIS RESERVAS ===\n\n");
                sb.append("Usuario: ").append(misReservas.get(0).getUsuario().getNombre()).append("\n");
                sb.append("Identificación: ").append(identificacion).append("\n\n");
                
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                
                for (int i = 0; i < misReservas.size(); i++) {
                    Reserva r = misReservas.get(i);
                    sb.append("Reserva #").append(i+1).append(":\n");
                    sb.append("Facultad: ").append(((Facultad) cbFacultades.getSelectedItem()).getNombre()).append("\n");
                    sb.append("Espacio: ").append(r.getEspacio().getNombre()).append("\n");
                    sb.append("Tipo: ").append(r.getEspacio().getClass().getSimpleName()).append("\n");
                    sb.append("Fecha: ").append(sdf.format(r.getFecha())).append("\n");
                    sb.append("Evento: ").append(r.getEvento()).append("\n");
                    sb.append("Código: ").append(generarCodigoReserva(r)).append("\n");
                    sb.append("--------------------------------\n");
                }
                
                sb.append("\nTotal reservas: ").append(misReservas.size());
                txtResultado.setText(sb.toString());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error al listar reservas: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}