package model;

import connection.query;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {
    private query q;

    public CursoDAO() {
        q = new query();
    }

    public List<Curso> getAllCursos() {
        List<Curso> cursos = new ArrayList<>();
        String sql = "SELECT id_curso, nombre, creditos, id_docente FROM curso";

        try {
            ResultSet rs = q.queryRest(sql);
            while (rs.next()) {
                Curso c = new Curso();
                c.setId_curso(rs.getInt("id_curso"));
                c.setNombre(rs.getString("nombre"));
                c.setCreditos(rs.getInt("creditos"));
                c.setId_docente(rs.getInt("id_docente"));
                cursos.add(c);
            }
            rs.close();
            q.closeConn();
        } catch (SQLException e) {
            System.err.println("Error al obtener cursos: " + e.getMessage());
        }
        return cursos;
    }

    public Curso getCursoById(int id) {
        String sql = "SELECT id_curso, nombre, creditos, id_docente FROM curso WHERE id_curso = " + id;
        Curso curso = null;

        try {
            ResultSet rs = q.queryRest(sql);
            if (rs.next()) {
                curso = new Curso();
                curso.setId_curso(rs.getInt("id_curso"));
                curso.setNombre(rs.getString("nombre"));
                curso.setCreditos(rs.getInt("creditos"));
                curso.setId_docente(rs.getInt("id_docente"));
            }
            rs.close();
            q.closeConn();
        } catch (SQLException e) {
            System.err.println("Error al obtener curso por ID: " + e.getMessage());
        }
        return curso;
    }

    public boolean insertCurso(Curso curso) {
        String sql = String.format(
                "INSERT INTO curso (nombre, creditos, id_docente) VALUES ('%s', %d, %d)",
                curso.getNombre(), curso.getCreditos(), curso.getId_docente()
        );
        return q.queryUpdate(sql);
    }

    public boolean updateCurso(Curso curso) {
        String sql = String.format(
                "UPDATE curso SET nombre = '%s', creditos = %d, id_docente = %d WHERE id_curso = %d",
                curso.getNombre(), curso.getCreditos(), curso.getId_docente(), curso.getId_curso()
        );
        return q.queryUpdate(sql);
    }

    public boolean deleteCurso(int id) {
        String sql = "DELETE FROM curso WHERE id_curso = " + id;
        return q.queryUpdate(sql);
    }
    public List<Curso> getCursosByNombre(String nombre) {
        List<Curso> cursos = new ArrayList<>();
        String sql = String.format("SELECT id_curso, nombre, creditos, id_docente FROM curso WHERE nombre LIKE '%%%s%%'", nombre);

        try {
            ResultSet rs = q.queryRest(sql);
            while (rs.next()) {
                Curso c = new Curso();
                c.setId_curso(rs.getInt("id_curso"));
                c.setNombre(rs.getString("nombre"));
                c.setCreditos(rs.getInt("creditos"));
                c.setId_docente(rs.getInt("id_docente"));
                cursos.add(c);
            }
            rs.close();
            q.closeConn();
        } catch (SQLException e) {
            System.err.println("Error al obtener cursos por nombre: " + e.getMessage());
        }
        return cursos;
    }

    public List<Curso> getCursosByCreditos(int creditos) {
        List<Curso> cursos = new ArrayList<>();
        String sql = "SELECT id_curso, nombre, creditos, id_docente FROM curso WHERE creditos = " + creditos;

        try {
            ResultSet rs = q.queryRest(sql);
            while (rs.next()) {
                Curso c = new Curso();
                c.setId_curso(rs.getInt("id_curso"));
                c.setNombre(rs.getString("nombre"));
                c.setCreditos(rs.getInt("creditos"));
                c.setId_docente(rs.getInt("id_docente"));
                cursos.add(c);
            }
            rs.close();
            q.closeConn();
        } catch (SQLException e) {
            System.err.println("Error al obtener cursos por créditos: " + e.getMessage());
        }
        return cursos;
    }

    public List<Curso> getCursosByNombreAndCreditos(String nombre, int creditos) {
        List<Curso> cursos = new ArrayList<>();
        String sql = String.format("SELECT id_curso, nombre, creditos, id_docente FROM curso WHERE nombre LIKE '%%%s%%' AND creditos = %d", nombre, creditos);

        try {
            ResultSet rs = q.queryRest(sql);
            while (rs.next()) {
                Curso c = new Curso();
                c.setId_curso(rs.getInt("id_curso"));
                c.setNombre(rs.getString("nombre"));
                c.setCreditos(rs.getInt("creditos"));
                c.setId_docente(rs.getInt("id_docente"));
                cursos.add(c);
            }
            rs.close();
            q.closeConn();
        } catch (SQLException e) {
            System.err.println("Error al obtener cursos por nombre y créditos: " + e.getMessage());
        }
        return cursos;
    }
}