package controller;

import java.util.List;
import model.Estudiante;
import model.EstudianteDAO;

public class EstudianteController {
    private EstudianteDAO estudianteDAO;

    public EstudianteController() {
        estudianteDAO = new EstudianteDAO();
    }

    public boolean registrarEstudiante(String nombre, String apellido, String carrera, int ciclo, String correo) {
        Estudiante estudiante = new Estudiante(0, nombre, apellido, carrera, ciclo, correo);
        return estudianteDAO.insertarEstudiante(estudiante);
    }

    public boolean actualizarEstudiante(int id, String nombre, String apellido, String carrera, int ciclo, String correo) {
        Estudiante estudiante = new Estudiante(id, nombre, apellido, carrera, ciclo, correo);
        return estudianteDAO.actualizarEstudiante(estudiante);
    }

    public boolean eliminarEstudiante(int id) {
        return estudianteDAO.eliminarEstudiante(id);
    }

    public List<Estudiante> listarEstudiantes() {
        return estudianteDAO.listarEstudiantes();
    }

    public Estudiante buscarEstudiantePorId(int id) {
        return estudianteDAO.buscarEstudiantePorId(id);
    }

    public List<Estudiante> buscarEstudiantesPorCarrera(String carrera) {
        return estudianteDAO.buscarEstudiantesPorCarrera(carrera);
    }
}