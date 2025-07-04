package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import controller.EstudianteController;
import model.Estudiante;

public class EstudianteView extends JFrame {
    private EstudianteController controller;
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> carreraComboBox;

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

        // Panel de formulario
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));

        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField();

        JLabel lblApellido = new JLabel("Apellido:");
        JTextField txtApellido = new JTextField();

        JLabel lblCarrera = new JLabel("Carrera:");
        carreraComboBox = new JComboBox<>(new String[]{"Ingeniería de Sistemas", "Ingeniería Civil", "Medicina", "Derecho", "Administración"});

        JLabel lblCiclo = new JLabel("Ciclo:");
        JSpinner spnCiclo = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

        JLabel lblCorreo = new JLabel("Correo:");
        JTextField txtCorreo = new JTextField();

        JButton btnGuardar = new JButton("Guardar");
        JButton btnLimpiar = new JButton("Limpiar");

        formPanel.add(lblNombre);
        formPanel.add(txtNombre);
        formPanel.add(lblApellido);
        formPanel.add(txtApellido);
        formPanel.add(lblCarrera);
        formPanel.add(carreraComboBox);
        formPanel.add(lblCiclo);
        formPanel.add(spnCiclo);
        formPanel.add(lblCorreo);
        formPanel.add(txtCorreo);
        formPanel.add(btnGuardar);
        formPanel.add(btnLimpiar);

        // Panel de tabla
        JPanel tablePanel = new JPanel(new BorderLayout());

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Apellido");
        tableModel.addColumn("Carrera");
        tableModel.addColumn("Ciclo");
        tableModel.addColumn("Correo");

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Panel de filtros
        JPanel filterPanel = new JPanel(new FlowLayout());

        JLabel lblFiltrarCarrera = new JLabel("Filtrar por carrera:");
        JComboBox<String> filterCarreraComboBox = new JComboBox<>(new String[]{"Todas", "Ingeniería de Sistemas", "Ingeniería Civil", "Medicina", "Derecho", "Administración"});
        JButton btnFiltrar = new JButton("Filtrar");

        filterPanel.add(lblFiltrarCarrera);
        filterPanel.add(filterCarreraComboBox);
        filterPanel.add(btnFiltrar);

        // Panel de botones de acción
        JPanel actionPanel = new JPanel(new FlowLayout());

        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnActualizar = new JButton("Actualizar Lista");

        actionPanel.add(btnEditar);
        actionPanel.add(btnEliminar);
        actionPanel.add(btnActualizar);

        // Añadir paneles al panel principal
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(filterPanel, BorderLayout.CENTER);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Listeners
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText();
                String apellido = txtApellido.getText();
                String carrera = (String) carreraComboBox.getSelectedItem();
                int ciclo = (int) spnCiclo.getValue();
                String correo = txtCorreo.getText();

                if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty()) {
                    JOptionPane.showMessageDialog(EstudianteView.this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!correo.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                    JOptionPane.showMessageDialog(EstudianteView.this, "Ingrese un correo válido", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean resultado = controller.registrarEstudiante(nombre, apellido, carrera, ciclo, correo);

                if (resultado) {
                    JOptionPane.showMessageDialog(EstudianteView.this, "Estudiante registrado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos(txtNombre, txtApellido, spnCiclo, txtCorreo);
                    cargarEstudiantes();
                } else {
                    JOptionPane.showMessageDialog(EstudianteView.this, "Error al registrar estudiante", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos(txtNombre, txtApellido, spnCiclo, txtCorreo);
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
                    txtApellido.setText(estudiante.getApellido());
                    carreraComboBox.setSelectedItem(estudiante.getCarrera());
                    spnCiclo.setValue(estudiante.getCiclo());
                    txtCorreo.setText(estudiante.getCorreo());

                    int confirm = JOptionPane.showConfirmDialog(EstudianteView.this, "¿Desea actualizar este estudiante?", "Confirmar", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        String nombre = txtNombre.getText();
                        String apellido = txtApellido.getText();
                        String carrera = (String) carreraComboBox.getSelectedItem();
                        int ciclo = (int) spnCiclo.getValue();
                        String correo = txtCorreo.getText();

                        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty()) {
                            JOptionPane.showMessageDialog(EstudianteView.this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if (!correo.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                            JOptionPane.showMessageDialog(EstudianteView.this, "Ingrese un correo válido", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        boolean resultado = controller.actualizarEstudiante(id, nombre, apellido, carrera, ciclo, correo);

                        if (resultado) {
                            JOptionPane.showMessageDialog(EstudianteView.this, "Estudiante actualizado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                            limpiarCampos(txtNombre, txtApellido, spnCiclo, txtCorreo);
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

        btnFiltrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String carreraFiltro = (String) filterCarreraComboBox.getSelectedItem();

                if (carreraFiltro.equals("Todas")) {
                    cargarEstudiantes();
                } else {
                    cargarEstudiantesPorCarrera(carreraFiltro);
                }
            }
        });
    }

    private void cargarEstudiantes() {
        tableModel.setRowCount(0);
        List<Estudiante> estudiantes = controller.listarEstudiantes();

        for (Estudiante estudiante : estudiantes) {
            Object[] row = {
                    estudiante.getId(),
                    estudiante.getNombre(),
                    estudiante.getApellido(),
                    estudiante.getCarrera(),
                    estudiante.getCiclo(),
                    estudiante.getCorreo()
            };
            tableModel.addRow(row);
        }
    }

    private void cargarEstudiantesPorCarrera(String carrera) {
        tableModel.setRowCount(0);
        List<Estudiante> estudiantes = controller.buscarEstudiantesPorCarrera(carrera);

        for (Estudiante estudiante : estudiantes) {
            Object[] row = {
                    estudiante.getId(),
                    estudiante.getNombre(),
                    estudiante.getApellido(),
                    estudiante.getCarrera(),
                    estudiante.getCiclo(),
                    estudiante.getCorreo()
            };
            tableModel.addRow(row);
        }
    }

    private void limpiarCampos(JTextField txtNombre, JTextField txtApellido, JSpinner spnCiclo, JTextField txtCorreo) {
        txtNombre.setText("");
        txtApellido.setText("");
        spnCiclo.setValue(1);
        txtCorreo.setText("");
    }
}