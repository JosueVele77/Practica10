package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import controller.CursoController;
import controller.DocenteController;
import model.Curso;
import model.Docente;

public class CursoView extends JFrame {
    private CursoController cursoController;
    private DocenteController docenteController;
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> carreraComboBox;
    private JComboBox<Docente> docenteComboBox;

    public CursoView() {
        cursoController = new CursoController();
        docenteController = new DocenteController();
        initComponents();
        cargarCursos();
        cargarDocentesEnComboBox();
    }

    private void initComponents() {
        setTitle("Gestión de Cursos");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel de formulario
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));

        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField();

        JLabel lblCreditos = new JLabel("Créditos:");
        JSpinner spnCreditos = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

        JLabel lblCarrera = new JLabel("Carrera:");
        carreraComboBox = new JComboBox<>(new String[]{"Ingeniería de Sistemas", "Ingeniería Civil", "Medicina", "Derecho", "Administración"});

        JLabel lblCiclo = new JLabel("Ciclo:");
        JSpinner spnCiclo = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

        JLabel lblDocente = new JLabel("Docente:");
        docenteComboBox = new JComboBox<>();

        JButton btnGuardar = new JButton("Guardar");
        JButton btnLimpiar = new JButton("Limpiar");

        formPanel.add(lblNombre);
        formPanel.add(txtNombre);
        formPanel.add(lblCreditos);
        formPanel.add(spnCreditos);
        formPanel.add(lblCarrera);
        formPanel.add(carreraComboBox);
        formPanel.add(lblCiclo);
        formPanel.add(spnCiclo);
        formPanel.add(lblDocente);
        formPanel.add(docenteComboBox);
        formPanel.add(btnGuardar);
        formPanel.add(btnLimpiar);

        // Panel de tabla
        JPanel tablePanel = new JPanel(new BorderLayout());

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Créditos");
        tableModel.addColumn("Carrera");
        tableModel.addColumn("Ciclo");
        tableModel.addColumn("Docente");

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
                int creditos = (int) spnCreditos.getValue();
                String carrera = (String) carreraComboBox.getSelectedItem();
                int ciclo = (int) spnCiclo.getValue();
                Docente docente = (Docente) docenteComboBox.getSelectedItem();

                if (nombre.isEmpty() || docente == null) {
                    JOptionPane.showMessageDialog(CursoView.this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean resultado = cursoController.registrarCurso(nombre, creditos, carrera, ciclo, docente.getId());

                if (resultado) {
                    JOptionPane.showMessageDialog(CursoView.this, "Curso registrado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos(txtNombre, spnCreditos, spnCiclo);
                    cargarCursos();
                } else {
                    JOptionPane.showMessageDialog(CursoView.this, "Error al registrar curso", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos(txtNombre, spnCreditos, spnCiclo);
            }
        });

        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(CursoView.this, "Seleccione un curso para editar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int id = (int) tableModel.getValueAt(selectedRow, 0);
                Curso curso = cursoController.buscarCursoPorId(id);

                if (curso != null) {
                    txtNombre.setText(curso.getNombre());
                    spnCreditos.setValue(curso.getCreditos());
                    carreraComboBox.setSelectedItem(curso.getCarrera());
                    spnCiclo.setValue(curso.getCiclo());

                    // Buscar y seleccionar el docente correspondiente
                    for (int i = 0; i < docenteComboBox.getItemCount(); i++) {
                        Docente d = docenteComboBox.getItemAt(i);
                        if (d.getId() == curso.getDocenteId()) {
                            docenteComboBox.setSelectedIndex(i);
                            break;
                        }
                    }

                    int confirm = JOptionPane.showConfirmDialog(CursoView.this, "¿Desea actualizar este curso?", "Confirmar", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        String nombre = txtNombre.getText();
                        int creditos = (int) spnCreditos.getValue();
                        String carrera = (String) carreraComboBox.getSelectedItem();
                        int ciclo = (int) spnCiclo.getValue();
                        Docente docente = (Docente) docenteComboBox.getSelectedItem();

                        if (nombre.isEmpty() || docente == null) {
                            JOptionPane.showMessageDialog(CursoView.this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        boolean resultado = cursoController.actualizarCurso(id, nombre, creditos, carrera, ciclo, docente.getId());

                        if (resultado) {
                            JOptionPane.showMessageDialog(CursoView.this, "Curso actualizado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                            limpiarCampos(txtNombre, spnCreditos, spnCiclo);
                            cargarCursos();
                        } else {
                            JOptionPane.showMessageDialog(CursoView.this, "Error al actualizar curso", "Error", JOptionPane.ERROR_MESSAGE);
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
                    JOptionPane.showMessageDialog(CursoView.this, "Seleccione un curso para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int id = (int) tableModel.getValueAt(selectedRow, 0);

                int confirm = JOptionPane.showConfirmDialog(CursoView.this, "¿Está seguro de eliminar este curso?", "Confirmar", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    boolean resultado = cursoController.eliminarCurso(id);

                    if (resultado) {
                        JOptionPane.showMessageDialog(CursoView.this, "Curso eliminado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        cargarCursos();
                    } else {
                        JOptionPane.showMessageDialog(CursoView.this, "Error al eliminar curso", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarCursos();
            }
        });

        btnFiltrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String carreraFiltro = (String) filterCarreraComboBox.getSelectedItem();

                if (carreraFiltro.equals("Todas")) {
                    cargarCursos();
                } else {
                    cargarCursosPorCarrera(carreraFiltro);
                }
            }
        });
    }

    private void cargarCursos() {
        tableModel.setRowCount(0);
        List<Curso> cursos = cursoController.listarCursos();

        for (Curso curso : cursos) {
            Docente docente = docenteController.buscarDocentePorId(curso.getDocenteId());
            String nombreDocente = (docente != null) ? docente.getNombre() + " " + docente.getApellido() : "Desconocido";

            Object[] row = {
                    curso.getId(),
                    curso.getNombre(),
                    curso.getCreditos(),
                    curso.getCarrera(),
                    curso.getCiclo(),
                    nombreDocente
            };
            tableModel.addRow(row);
        }
    }

    private void cargarCursosPorCarrera(String carrera) {
        tableModel.setRowCount(0);
        List<Curso> cursos = cursoController.buscarCursosPorCarrera(carrera);

        for (Curso curso : cursos) {
            Docente docente = docenteController.buscarDocentePorId(curso.getDocenteId());
            String nombreDocente = (docente != null) ? docente.getNombre() + " " + docente.getApellido() : "Desconocido";

            Object[] row = {
                    curso.getId(),
                    curso.getNombre(),
                    curso.getCreditos(),
                    curso.getCarrera(),
                    curso.getCiclo(),
                    nombreDocente
            };
            tableModel.addRow(row);
        }
    }

    private void cargarDocentesEnComboBox() {
        List<Docente> docentes = docenteController.listarDocentes();
        docenteComboBox.removeAllItems();

        for (Docente docente : docentes) {
            docenteComboBox.addItem(docente);
        }
    }

    private void limpiarCampos(JTextField txtNombre, JSpinner spnCreditos, JSpinner spnCiclo) {
        txtNombre.setText("");
        spnCreditos.setValue(1);
        spnCiclo.setValue(1);
    }
}