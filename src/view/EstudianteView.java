package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.List;
import controller.EstudianteController;
import model.Estudiante;
import java.util.Calendar;

public class EstudianteView extends JFrame {
    private EstudianteController controller;
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> carreraComboBox;

    // Componentes de formulario
    private JTextField txtNombre;
    private JTextField txtEmail;
    private JSpinner spnAnioIngreso;

    // Componentes de filtro
    private JSpinner spnFiltroAnioIngreso;
    private JComboBox<String> filterCarreraComboBox;
    private JButton btnFiltrar;


    public EstudianteView() {
        controller = new EstudianteController();
        initComponents();
        cargarEstudiantes();
    }

    private void initComponents() {
        setTitle("Gestión de Estudiantes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel de formulario (Norte)
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Estudiante"));

        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField();

        JLabel lblEmail = new JLabel("Email:");
        txtEmail = new JTextField();

        JLabel lblCarrera = new JLabel("Carrera:");
        carreraComboBox = new JComboBox<>(new String[]{"Ingeniería de Sistemas", "Ingeniería Civil", "Medicina", "Derecho", "Administración"});

        JLabel lblAnioIngreso = new JLabel("Año Ingreso:");
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        spnAnioIngreso = new JSpinner(new SpinnerNumberModel(currentYear, 1990, currentYear + 5, 1)); // Rango de años de 1990 al actual + 5
        // Configurar el formato del JSpinner para que el año no tenga decimales
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spnAnioIngreso, "#");
        spnAnioIngreso.setEditor(editor);


        JButton btnGuardar = new JButton("Guardar");
        JButton btnLimpiar = new JButton("Limpiar");

        formPanel.add(lblNombre);
        formPanel.add(txtNombre);
        formPanel.add(lblEmail);
        formPanel.add(txtEmail);
        formPanel.add(lblCarrera);
        formPanel.add(carreraComboBox);
        formPanel.add(lblAnioIngreso);
        formPanel.add(spnAnioIngreso);
        formPanel.add(btnGuardar);
        formPanel.add(btnLimpiar);

        // Panel de filtros (Centro - Arriba de la tabla)
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filtrar Estudiantes"));

        JLabel lblFiltrarAnioIngreso = new JLabel("Año Ingreso:");
        spnFiltroAnioIngreso = new JSpinner(new SpinnerNumberModel(0, 0, currentYear + 5, 1)); // 0 para "todas" o sin filtro
        // Configurar el formato del JSpinner de filtro también
        JSpinner.NumberEditor filterEditor = new JSpinner.NumberEditor(spnFiltroAnioIngreso, "#");
        spnFiltroAnioIngreso.setEditor(filterEditor);


        JLabel lblFiltrarCarrera = new JLabel("Carrera:");
        filterCarreraComboBox = new JComboBox<>(new String[]{"Todas", "Ingeniería de Sistemas", "Ingeniería Civil", "Medicina", "Derecho", "Administración"});
        btnFiltrar = new JButton("Filtrar");

        filterPanel.add(lblFiltrarAnioIngreso);
        filterPanel.add(spnFiltroAnioIngreso);
        filterPanel.add(lblFiltrarCarrera);
        filterPanel.add(filterCarreraComboBox);
        filterPanel.add(btnFiltrar);

        // Panel que contendrá el filtro y la tabla (Centro)
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(filterPanel, BorderLayout.NORTH);


        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Email");
        tableModel.addColumn("Carrera");
        tableModel.addColumn("Año Ingreso");

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        centerPanel.add(scrollPane, BorderLayout.CENTER);


        // Panel de botones de acción (Sur)
        JPanel actionPanel = new JPanel(new FlowLayout());

        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnActualizar = new JButton("Actualizar Lista");

        actionPanel.add(btnEditar);
        actionPanel.add(btnEliminar);
        actionPanel.add(btnActualizar);

        // Añadir paneles al panel principal
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Listeners
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText();
                String email = txtEmail.getText();
                String carrera = (String) carreraComboBox.getSelectedItem();
                int anioIngreso = (int) spnAnioIngreso.getValue();

                if (nombre.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(EstudianteView.this, "Los campos Nombre y Email son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                    JOptionPane.showMessageDialog(EstudianteView.this, "Ingrese un correo válido", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean resultado = controller.registrarEstudiante(nombre, email, carrera, anioIngreso);

                if (resultado) {
                    JOptionPane.showMessageDialog(EstudianteView.this, "Estudiante registrado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarEstudiantes();
                } else {
                    JOptionPane.showMessageDialog(EstudianteView.this, "Error al registrar estudiante", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(EstudianteView.this, "Seleccione un estudiante para editar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int id = (int) tableModel.getValueAt(selectedRow, 0);
                Estudiante estudiante = controller.buscarEstudiantePorId(id);

                if (estudiante != null) {
                    txtNombre.setText(estudiante.getNombre());
                    txtEmail.setText(estudiante.getEmail());
                    carreraComboBox.setSelectedItem(estudiante.getCarrera());
                    spnAnioIngreso.setValue(estudiante.getAnio_ingreso());

                    int confirm = JOptionPane.showConfirmDialog(EstudianteView.this, "¿Desea actualizar este estudiante?", "Confirmar", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        String nombre = txtNombre.getText();
                        String email = txtEmail.getText();
                        String carrera = (String) carreraComboBox.getSelectedItem();
                        int anioIngreso = (int) spnAnioIngreso.getValue();

                        if (nombre.isEmpty() || email.isEmpty()) {
                            JOptionPane.showMessageDialog(EstudianteView.this, "Los campos Nombre y Email son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                            JOptionPane.showMessageDialog(EstudianteView.this, "Ingrese un correo válido", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        boolean resultado = controller.actualizarEstudiante(id, nombre, email, carrera, anioIngreso);

                        if (resultado) {
                            JOptionPane.showMessageDialog(EstudianteView.this, "Estudiante actualizado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                            limpiarCampos();
                            cargarEstudiantes();
                        } else {
                            JOptionPane.showMessageDialog(EstudianteView.this, "Error al actualizar estudiante", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(EstudianteView.this, "Seleccione un estudiante para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int id = (int) tableModel.getValueAt(selectedRow, 0);

                int confirm = JOptionPane.showConfirmDialog(EstudianteView.this, "¿Está seguro de eliminar este estudiante?", "Confirmar", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    boolean resultado = controller.eliminarEstudiante(id);

                    if (resultado) {
                        JOptionPane.showMessageDialog(EstudianteView.this, "Estudiante eliminado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        cargarEstudiantes();
                    } else {
                        JOptionPane.showMessageDialog(EstudianteView.this, "Error al eliminar estudiante", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarEstudiantes();
            }
        });

        // Listener para el botón de filtro
        btnFiltrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int anioIngresoFiltro = (int) spnFiltroAnioIngreso.getValue();
                String carreraFiltro = (String) filterCarreraComboBox.getSelectedItem();

                // Llama al método de carga con los filtros
                cargarEstudiantes(anioIngresoFiltro, carreraFiltro);
            }
        });
    }

    private void cargarEstudiantes() {
        // Cargar todos los estudiantes por defecto (0 para año de ingreso indica sin filtro)
        cargarEstudiantes(0, "Todas");
    }

    private void cargarEstudiantes(int anioIngresoFiltro, String carreraFiltro) {
        tableModel.setRowCount(0);
        List<Estudiante> estudiantes;

        boolean anioInvalido = (anioIngresoFiltro <= 0); // 0 significa "sin filtro"
        boolean carreraVacia = (carreraFiltro == null || carreraFiltro.trim().isEmpty() || carreraFiltro.equals("Todas"));


        if (anioInvalido && carreraVacia) {
            estudiantes = controller.listarEstudiantes();
        } else {
            estudiantes = controller.buscarEstudiantesPorAnioIngresoYCarrera(anioIngresoFiltro, carreraFiltro);
        }


        for (Estudiante estudiante : estudiantes) {
            Object[] row = {
                    estudiante.getId_estudiante(),
                    estudiante.getNombre(),
                    estudiante.getEmail(),
                    estudiante.getCarrera(),
                    estudiante.getAnio_ingreso()
            };
            tableModel.addRow(row);
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtEmail.setText("");
        spnAnioIngreso.setValue(Calendar.getInstance().get(Calendar.YEAR)); // Restablecer al año actual
        if (carreraComboBox.getItemCount() > 0) {
            carreraComboBox.setSelectedIndex(0); // Restablecer a la primera opción de carrera
        }

        spnFiltroAnioIngreso.setValue(0); // Limpiar filtros
        filterCarreraComboBox.setSelectedItem("Todas"); // Limpiar filtro de carrera
    }
}