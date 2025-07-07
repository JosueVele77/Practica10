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
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        if (creditos <= 0 || docenteId <= 0) {
            return false;
        }

        Curso curso = new Curso();
        curso.setNombre(nombre);
        curso.setCreditos(creditos);
        curso.setId_docente(docenteId);

        return cursoDAO.insertCurso(curso);
    }

    public boolean actualizarCurso(int id, String nombre, int creditos, int docenteId) {
        if (id <= 0 || nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        if (creditos <= 0 || docenteId <= 0) {
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

    public List<Curso> listarCursos() {
        return cursoDAO.getAllCursos();
    }

    public Curso buscarCursoPorId(int id) {
        if (id <= 0) {
            return null;
        }
        return cursoDAO.getCursoById(id);
    }

    public List<Curso> buscarCursosPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return listarCursos();
        }
        return cursoDAO.getCursosByNombre(nombre);
    }

    public List<Curso> buscarCursosPorCreditos(int creditos) {
        if (creditos <= 0) {
            return listarCursos();
        }
        return cursoDAO.getCursosByCreditos(creditos);
    }

    public List<Curso> buscarCursosPorNombreYCreditos(String nombre, int creditos) {
        boolean nombreVacio = (nombre == null || nombre.trim().isEmpty());
        boolean creditosInvalidos = (creditos <= 0);

        if (nombreVacio && creditosInvalidos) {
            return listarCursos();
        } else if (nombreVacio) {
            return cursoDAO.getCursosByCreditos(creditos);
        } else if (creditosInvalidos) {
            return cursoDAO.getCursosByNombre(nombre);
        } else {
            return cursoDAO.getCursosByNombreAndCreditos(nombre, creditos);
        }
    }
}