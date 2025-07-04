package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import controller.DocenteController;
import model.Docente;

public class DocentesView extends JFrame {
    private DocenteController controller;
    private JTable table;
    private DefaultTableModel tableModel;

    public DocentesView() {
        controller = new DocenteController();
        initComponents();
        cargarDocentes();
    }

    private void initComponents() {
        setTitle("Gestión de Docentes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel de formulario
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));

        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField();

        JLabel lblApellido = new JLabel("Apellido:");
        JTextField txtApellido = new JTextField();

        JLabel lblEspecialidad = new JLabel("Especialidad:");
        JTextField txtEspecialidad = new JTextField();

        JLabel lblGrado = new JLabel("Grado Académico:");
        JTextField txtGrado = new JTextField();

        JButton btnGuardar = new JButton("Guardar");
        JButton btnLimpiar = new JButton("Limpiar");

        formPanel.add(lblNombre);
        formPanel.add(txtNombre);
        formPanel.add(lblApellido);
        formPanel.add(txtApellido);
        formPanel.add(lblEspecialidad);
        formPanel.add(txtEspecialidad);
        formPanel.add(lblGrado);
        formPanel.add(txtGrado);
        formPanel.add(btnGuardar);
        formPanel.add(btnLimpiar);

        // Panel de tabla
        JPanel tablePanel = new JPanel(new BorderLayout());

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Apellido");
        tableModel.addColumn("Especialidad");
        tableModel.addColumn("Grado Académico");

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

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
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Listeners
        btnGuardar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String especialidad = txtEspecialidad.getText();
            String grado = txtGrado.getText();

            if (validarCampos(nombre, apellido, especialidad, grado)) {
                boolean resultado = controller.registrarDocente(nombre, apellido, especialidad, grado);
                manejarResultadoOperacion(resultado, "registrar", txtNombre, txtApellido, txtEspecialidad, txtGrado);
            }
        });

        btnLimpiar.addActionListener(e -> limpiarCampos(txtNombre, txtApellido, txtEspecialidad, txtGrado));

        btnEditar.addActionListener(e -> editarDocente(txtNombre, txtApellido, txtEspecialidad, txtGrado));

        btnEliminar.addActionListener(e -> eliminarDocente());

        btnActualizar.addActionListener(e -> cargarDocentes());
    }

    private boolean validarCampos(String... campos) {
        for (String campo : campos) {
            if (campo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    private void manejarResultadoOperacion(boolean resultado, String operacion, JTextField... campos) {
        if (resultado) {
            JOptionPane.showMessageDialog(this, "Docente " + (operacion.equals("registrar") ? "registrado" : "actualizado") + " con éxito",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos(campos);
            cargarDocentes();
        } else {
            JOptionPane.showMessageDialog(this, "Error al " + operacion + " docente", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos(JTextField... campos) {
        for (JTextField campo : campos) {
            campo.setText("");
        }
    }

    private void editarDocente(JTextField txtNombre, JTextField txtApellido, JTextField txtEspecialidad, JTextField txtGrado) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un docente para editar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        Docente docente = controller.buscarDocentePorId(id);

        if (docente != null) {
            txtNombre.setText(docente.getNombre());
            txtApellido.setText(docente.getApellido());
            txtEspecialidad.setText(docente.getEspecialidad());
            txtGrado.setText(docente.getGradoAcademico());

            int confirm = JOptionPane.showConfirmDialog(this, "¿Desea actualizar este docente?", "Confirmar", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                String nombre = txtNombre.getText();
                String apellido = txtApellido.getText();
                String especialidad = txtEspecialidad.getText();
                String grado = txtGrado.getText();

                if (validarCampos(nombre, apellido, especialidad, grado)) {
                    boolean resultado = controller.actualizarDocente(id, nombre, apellido, especialidad, grado);
                    manejarResultadoOperacion(resultado, "actualizar", txtNombre, txtApellido, txtEspecialidad, txtGrado);
                }
            }
        }
    }

    private void eliminarDocente() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un docente para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este docente?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean resultado = controller.eliminarDocente(id);

            if (resultado) {
                JOptionPane.showMessageDialog(this, "Docente eliminado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDocentes();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar docente", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cargarDocentes() {
        tableModel.setRowCount(0);
        List<Docente> docentes = controller.listarDocentes();

        for (Docente docente : docentes) {
            Object[] row = {
                    docente.getId(),
                    docente.getNombre(),
                    docente.getApellido(),
                    docente.getEspecialidad(),
                    docente.getGradoAcademico()
            };
            tableModel.addRow(row);
        }
    }
}