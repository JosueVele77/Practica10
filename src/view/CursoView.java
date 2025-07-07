package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
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
    private JComboBox<Docente> docenteComboBox;

    private JTextField txtNombre;
    private JSpinner spnCreditos;

    private JTextField txtFiltroNombre;
    private JSpinner spnFiltroCreditos;
    private JButton btnFiltrar;


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

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Curso"));

        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField();

        JLabel lblCreditos = new JLabel("Créditos:");
        spnCreditos = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

        JLabel lblDocente = new JLabel("Docente:");
        docenteComboBox = new JComboBox<>();

        JButton btnGuardar = new JButton("Guardar");
        JButton btnLimpiar = new JButton("Limpiar");

        formPanel.add(lblNombre);
        formPanel.add(txtNombre);
        formPanel.add(lblCreditos);
        formPanel.add(spnCreditos);
        formPanel.add(lblDocente);
        formPanel.add(docenteComboBox);
        formPanel.add(btnGuardar);
        formPanel.add(btnLimpiar);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filtrar Cursos"));
        JLabel lblFiltroNombre = new JLabel("Nombre:");
        txtFiltroNombre = new JTextField(15);
        JLabel lblFiltroCreditos = new JLabel("Créditos:");
        spnFiltroCreditos = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1)); // 0 para indicar "todos" o sin filtro
        btnFiltrar = new JButton("Filtrar");

        filterPanel.add(lblFiltroNombre);
        filterPanel.add(txtFiltroNombre);
        filterPanel.add(lblFiltroCreditos);
        filterPanel.add(spnFiltroCreditos);
        filterPanel.add(btnFiltrar);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(filterPanel, BorderLayout.NORTH);


        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Créditos");
        tableModel.addColumn("Docente");

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        centerPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new FlowLayout());

        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnActualizar = new JButton("Actualizar Lista");

        actionPanel.add(btnEditar);
        actionPanel.add(btnEliminar);
        actionPanel.add(btnActualizar);

        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        add(mainPanel);

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText();
                int creditos = (int) spnCreditos.getValue();
                Docente docente = (Docente) docenteComboBox.getSelectedItem();

                if (nombre.isEmpty() || docente == null) {
                    JOptionPane.showMessageDialog(CursoView.this, "Los campos Nombre y Docente son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean resultado = cursoController.registrarCurso(nombre, creditos, docente.getId_docente());

                if (resultado) {
                    JOptionPane.showMessageDialog(CursoView.this, "Curso registrado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarCursos();
                } else {
                    JOptionPane.showMessageDialog(CursoView.this, "Error al registrar curso", "Error", JOptionPane.ERROR_MESSAGE);
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
                    JOptionPane.showMessageDialog(CursoView.this, "Seleccione un curso para editar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int id = (int) tableModel.getValueAt(selectedRow, 0);
                Curso curso = cursoController.buscarCursoPorId(id);

                if (curso != null) {
                    txtNombre.setText(curso.getNombre());
                    spnCreditos.setValue(curso.getCreditos());

                    // Buscar y seleccionar el docente correspondiente
                    for (int i = 0; i < docenteComboBox.getItemCount(); i++) {
                        Docente d = docenteComboBox.getItemAt(i);
                        if (d.getId_docente() == curso.getId_docente()) {
                            docenteComboBox.setSelectedIndex(i);
                            break;
                        }
                    }

                    int confirm = JOptionPane.showConfirmDialog(CursoView.this, "¿Desea actualizar este curso?", "Confirmar", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        String nombre = txtNombre.getText();
                        int creditos = (int) spnCreditos.getValue();
                        Docente docente = (Docente) docenteComboBox.getSelectedItem();

                        if (nombre.isEmpty() || docente == null) {
                            JOptionPane.showMessageDialog(CursoView.this, "Los campos Nombre y Docente son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        boolean resultado = cursoController.actualizarCurso(id, nombre, creditos, docente.getId_docente());

                        if (resultado) {
                            JOptionPane.showMessageDialog(CursoView.this, "Curso actualizado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                            limpiarCampos();
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

        btnFiltrar.addActionListener(e -> {
            String nombreFiltro = txtFiltroNombre.getText();
            int creditosFiltro = (int) spnFiltroCreditos.getValue();
            cargarCursos(nombreFiltro, creditosFiltro);
        });
    }

    private void cargarCursos() {
        cargarCursos("", 0);
    }

    private void cargarCursos(String nombreFiltro, int creditosFiltro) {
        tableModel.setRowCount(0);
        List<Curso> cursos;

        boolean nombreVacio = (nombreFiltro == null || nombreFiltro.trim().isEmpty());
        boolean creditosInvalidos = (creditosFiltro <= 0);

        if (nombreVacio && creditosInvalidos) {
            cursos = cursoController.listarCursos();
        } else {
            cursos = cursoController.buscarCursosPorNombreYCreditos(nombreFiltro, creditosFiltro);
        }

        for (Curso curso : cursos) {
            Docente docente = docenteController.buscarDocentePorId(curso.getId_docente());
            String nombreDocente = (docente != null) ? docente.getNombre() : "Desconocido";

            Object[] row = {
                    curso.getId_curso(),
                    curso.getNombre(),
                    curso.getCreditos(),
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
        docenteComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Docente) {
                    Docente doc = (Docente) value;
                    setText(doc.getNombre());
                }
                return this;
            }
        });
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        spnCreditos.setValue(1);
        if (docenteComboBox.getItemCount() > 0) {
            docenteComboBox.setSelectedIndex(0);
        }
        txtFiltroNombre.setText("");
        spnFiltroCreditos.setValue(0);
    }
}