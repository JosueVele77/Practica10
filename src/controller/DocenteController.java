package controller;

import java.util.List;
import model.Docente;
import model.DocenteDAO;

public class DocenteController {
    private DocenteDAO docenteDAO;

    public DocenteController() {
        docenteDAO = new DocenteDAO();
    }

    public boolean registrarDocente(String nombre, String apellido, String especialidad, String gradoAcademico) {
        Docente docente = new Docente(0, nombre, apellido, especialidad, gradoAcademico);
        return docenteDAO.insertarDocente(docente);
    }

    public boolean actualizarDocente(int id, String nombre, String apellido, String especialidad, String gradoAcademico) {
        Docente docente = new Docente(id, nombre, apellido, especialidad, gradoAcademico);
        return docenteDAO.actualizarDocente(docente);
    }

    public boolean eliminarDocente(int id) {
        return docenteDAO.eliminarDocente(id);
    }

    public List<Docente> listarDocentes() {
        return docenteDAO.listarDocentes();
    }

    public Docente buscarDocentePorId(int id) {
        return docenteDAO.buscarDocentePorId(id);
    }
}