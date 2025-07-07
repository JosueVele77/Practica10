package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Sistema de Gestión Académica");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();

        JMenu docentesMenu = new JMenu("Docentes");
        JMenuItem gestionDocentesItem = new JMenuItem("Gestión de Docentes");
        gestionDocentesItem.addActionListener(this::mostrarVentanaDocentes);
        docentesMenu.add(gestionDocentesItem);

        JMenu cursosMenu = new JMenu("Cursos");
        JMenuItem gestionCursosItem = new JMenuItem("Gestión de Cursos");
        gestionCursosItem.addActionListener(this::mostrarVentanaCursos);
        cursosMenu.add(gestionCursosItem);

        JMenu estudiantesMenu = new JMenu("Estudiantes");
        JMenuItem gestionEstudiantesItem = new JMenuItem("Gestión de Estudiantes");
        gestionEstudiantesItem.addActionListener(this::mostrarVentanaEstudiantes);
        estudiantesMenu.add(gestionEstudiantesItem);

        menuBar.add(docentesMenu);
        menuBar.add(cursosMenu);
        menuBar.add(estudiantesMenu);

        setJMenuBar(menuBar);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JLabel lblBienvenida = new JLabel("Bienvenido al Sistema de Gestión Académica", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(lblBienvenida, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void mostrarVentanaDocentes(ActionEvent e) {
        DocentesView docenteView = new DocentesView();
        docenteView.setVisible(true);
    }

    private void mostrarVentanaCursos(ActionEvent e) {
        CursoView cursoView = new CursoView();
        cursoView.setVisible(true);
    }

    private void mostrarVentanaEstudiantes(ActionEvent e) {
        EstudianteView estudianteView = new EstudianteView();
        estudianteView.setVisible(true);
    }
}