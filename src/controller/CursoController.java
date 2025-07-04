package controller;

import java.util.List;
import model.Curso;
import model.CursoDAO;

public class CursoController {
    private CursoDAO cursoDAO;

    public CursoController() {
        cursoDAO = new CursoDAO();
    }

    public boolean registrarCurso(String nombre, int creditos, int docenteId) {
        // Validación básica
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        if (creditos <= 0) {
            return false;
        }
        if (docenteId <= 0) {
            return false;
        }

        Curso curso = new Curso();
        curso.setNombre(nombre);
        curso.setCreditos(creditos);
        curso.setId_docente(docenteId);

        return cursoDAO.insertCurso(curso);
    }

    public boolean actualizarCurso(int id, String nombre, int creditos, int docenteId) {
        // Validación básica
        if (id <= 0) {
            return false;
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        if (creditos <= 0) {
            return false;
        }
        if (docenteId <= 0) {
            return false;
        }

        Curso curso = new Curso();
        curso.setId_curso(id);
        curso.setNombre(nombre);
        curso.setCreditos(creditos);
        curso.setId_docente(docenteId);

        return cursoDAO.updateCurso(curso);
    }

    public boolean eliminarCurso(int id) {
        if (id <= 0) {
            return false;
        }
        return cursoDAO.deleteCurso(id);
    }

    public List<Curso> listarTodosCursos() {
        return cursoDAO.getAllCursos();
    }

    public Curso obtenerCursoPorId(int id) {
        if (id <= 0) {
            return null;
        }
        return cursoDAO.getCursoById(id);
    }

    // Método adicional si necesitas buscar cursos por docente
    public List<Curso> buscarCursosPorDocente(int idDocente) {
        if (idDocente <= 0) {
            return null;
        }
        return cursoDAO.getCursosByDocente(idDocente);
    }
}