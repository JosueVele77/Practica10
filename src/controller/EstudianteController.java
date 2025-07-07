package controller;

import java.util.List;
import model.Estudiante;
import model.EstudianteDAO;

public class EstudianteController {
    private EstudianteDAO estudianteDAO;

    public EstudianteController() {
        estudianteDAO = new EstudianteDAO();
    }

    public boolean registrarEstudiante(String nombre, String email, String carrera, int anio_ingreso) {
        // Validación básica
        if (nombre == null || nombre.trim().isEmpty() || email == null || email.trim().isEmpty() ||
                carrera == null || carrera.trim().isEmpty()) {
            return false;
        }
        if (anio_ingreso <= 0) {
            return false;
        }

        Estudiante estudiante = new Estudiante(0, nombre, email, carrera, anio_ingreso);
        return estudianteDAO.insertEstudiante(estudiante);
    }

    public boolean actualizarEstudiante(int id, String nombre, String email, String carrera, int anio_ingreso) {
        // Validación básica
        if (id <= 0 || nombre == null || nombre.trim().isEmpty() || email == null || email.trim().isEmpty() ||
                carrera == null || carrera.trim().isEmpty()) {
            return false;
        }
        if (anio_ingreso <= 0) {
            return false;
        }

        Estudiante estudiante = new Estudiante(id, nombre, email, carrera, anio_ingreso);
        return estudianteDAO.updateEstudiante(estudiante);
    }

    public boolean eliminarEstudiante(int id) {
        if (id <= 0) {
            return false;
        }
        return estudianteDAO.deleteEstudiante(id);
    }

    public List<Estudiante> listarEstudiantes() {
        return estudianteDAO.getAllEstudiantes();
    }

    public Estudiante buscarEstudiantePorId(int id) {
        if (id <= 0) {
            return null;
        }
        return estudianteDAO.getEstudianteById(id);
    }

    public List<Estudiante> buscarEstudiantesPorCarrera(String carrera) {
        if (carrera == null || carrera.trim().isEmpty()) {
            return listarEstudiantes(); // Si el filtro está vacío, mostrar todos
        }
        return estudianteDAO.getEstudiantesByCarrera(carrera);
    }

    // Nuevo método para buscar estudiantes por año de ingreso
    public List<Estudiante> buscarEstudiantesPorAnioIngreso(int anioIngreso) {
        if (anioIngreso <= 0) {
            return listarEstudiantes(); // Si el filtro no es válido, mostrar todos
        }
        return estudianteDAO.getEstudiantesByAnioIngreso(anioIngreso);
    }

    // Nuevo método para buscar estudiantes por año de ingreso y carrera
    public List<Estudiante> buscarEstudiantesPorAnioIngresoYCarrera(int anioIngreso, String carrera) {
        boolean anioInvalido = (anioIngreso <= 0);
        boolean carreraVacia = (carrera == null || carrera.trim().isEmpty() || carrera.equals("Todas")); // 'Todas' también implica filtro vacío

        if (anioInvalido && carreraVacia) {
            return listarEstudiantes(); // Si ambos filtros están vacíos/inválidos, mostrar todos
        } else if (anioInvalido) {
            return estudianteDAO.getEstudiantesByCarrera(carrera);
        } else if (carreraVacia) {
            return estudianteDAO.getEstudiantesByAnioIngreso(anioIngreso);
        } else {
            return estudianteDAO.getEstudiantesByAnioIngresoAndCarrera(anioIngreso, carrera);
        }
    }
}