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

    // Componentes de formulario
    private JTextField txtNombre;
    private JTextField txtEmail;
    private JTextField txtDepartamento;

    // Componentes de filtro
    private JTextField txtFiltroDepartamento;
    private JButton btnFiltrar;


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

        // Panel de formulario (Norte)
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Docente"));

        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField();

        JLabel lblEmail = new JLabel("Email:");
        txtEmail = new JTextField();

        JLabel lblDepartamento = new JLabel("Departamento:");
        txtDepartamento = new JTextField();

        JButton btnGuardar = new JButton("Guardar");
        JButton btnLimpiar = new JButton("Limpiar");

        formPanel.add(lblNombre);
        formPanel.add(txtNombre);
        formPanel.add(lblEmail);
        formPanel.add(txtEmail);
        formPanel.add(lblDepartamento);
        formPanel.add(txtDepartamento);
        formPanel.add(btnGuardar);
        formPanel.add(btnLimpiar);

        // Panel de filtros (Centro - Arriba de la tabla)
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filtrar Docentes"));
        JLabel lblFiltroDepartamento = new JLabel("Departamento:");
        txtFiltroDepartamento = new JTextField(15);
        btnFiltrar = new JButton("Filtrar");

        filterPanel.add(lblFiltroDepartamento);
        filterPanel.add(txtFiltroDepartamento);
        filterPanel.add(btnFiltrar);


        // Panel que contendrá el filtro y la tabla (Centro)
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(filterPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Email");
        tableModel.addColumn("Departamento");

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
        btnGuardar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            String email = txtEmail.getText();
            String departamento = txtDepartamento.getText();

            if (validarCampos(nombre, email, departamento)) {
                boolean resultado = controller.registrarDocente(nombre, email, departamento);
                manejarResultadoOperacion(resultado, "registrar", txtNombre, txtEmail, txtDepartamento);
            }
        });

        btnLimpiar.addActionListener(e -> limpiarCampos(txtNombre, txtEmail, txtDepartamento));

        btnEditar.addActionListener(e -> editarDocente());

        btnEliminar.addActionListener(e -> eliminarDocente());

        btnActualizar.addActionListener(e -> cargarDocentes());

        // Listener para el botón de filtro
        btnFiltrar.addActionListener(e -> {
            String departamentoFiltro = txtFiltroDepartamento.getText();
            cargarDocentes(departamentoFiltro); // Cargar docentes aplicando el filtro
        });
    }

    private boolean validarCampos(String... campos) {
        for (String campo : campos) {
            if (campo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        // Validación de email básico
        if (!campos[1].matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            JOptionPane.showMessageDialog(this, "Ingrese un correo electrónico válido", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
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
        txtFiltroDepartamento.setText(""); // Limpiar también el campo de filtro
    }

    private void editarDocente() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un docente para editar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        Docente docente = controller.buscarDocentePorId(id);

        if (docente != null) {
            txtNombre.setText(docente.getNombre());
            txtEmail.setText(docente.getEmail());
            txtDepartamento.setText(docente.getDepartamento());

            int confirm = JOptionPane.showConfirmDialog(this, "¿Desea actualizar este docente?", "Confirmar", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                String nombre = txtNombre.getText();
                String email = txtEmail.getText();
                String departamento = txtDepartamento.getText();

                if (validarCampos(nombre, email, departamento)) {
                    boolean resultado = controller.actualizarDocente(id, nombre, email, departamento);
                    manejarResultadoOperacion(resultado, "actualizar", txtNombre, txtEmail, txtDepartamento);
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
        cargarDocentes(""); // Cargar todos los docentes por defecto
    }

    private void cargarDocentes(String departamentoFiltro) {
        tableModel.setRowCount(0);
        List<Docente> docentes;

        if (departamentoFiltro == null || departamentoFiltro.trim().isEmpty()) {
            docentes = controller.listarDocentes();
        } else {
            docentes = controller.buscarDocentesPorDepartamento(departamentoFiltro);
        }

        for (Docente docente : docentes) {
            Object[] row = {
                    docente.getId_docente(),
                    docente.getNombre(),
                    docente.getEmail(),
                    docente.getDepartamento()
            };
            tableModel.addRow(row);
        }
    }
}