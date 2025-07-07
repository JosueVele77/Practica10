package controller;

import java.util.List;
import model.Docente;
import model.DocenteDAO;

public class DocenteController {
    private DocenteDAO docenteDAO;

    public DocenteController() {
        docenteDAO = new DocenteDAO();
    }

    public boolean registrarDocente(String nombre, String email, String departamento) {
        if (nombre == null || nombre.trim().isEmpty() || email == null || email.trim().isEmpty() || departamento == null || departamento.trim().isEmpty()) {
            return false;
        }
        Docente docente = new Docente(0, nombre, email, departamento);
        return docenteDAO.insertDocente(docente);
    }

    public boolean actualizarDocente(int id, String nombre, String email, String departamento) {
        if (id <= 0 || nombre == null || nombre.trim().isEmpty() || email == null || email.trim().isEmpty() || departamento == null || departamento.trim().isEmpty()) {
            return false;
        }
        Docente docente = new Docente(id, nombre, email, departamento);
        return docenteDAO.updateDocente(docente);
    }

    public boolean eliminarDocente(int id) {
        if (id <= 0) {
            return false;
        }
        return docenteDAO.deleteDocente(id);
    }

    public List<Docente> listarDocentes() {
        return docenteDAO.getAllDocentes();
    }

    public Docente buscarDocentePorId(int id) {
        return docenteDAO.getDocenteById(id);
    }

    // Nuevo método para buscar docentes por departamento
    public List<Docente> buscarDocentesPorDepartamento(String departamento) {
        if (departamento == null || departamento.trim().isEmpty()) {
            return docenteDAO.getAllDocentes(); // Si el filtro está vacío, mostrar todos
        }
        return docenteDAO.getDocentesByDepartamento(departamento);
    }
}