package model;

import connection.query;
import model.Docente;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DocenteDAO {
    private query q;

    public DocenteDAO() {
        q = new query();
    }

    public List<Docente> getAllDocentes() {
        List<Docente> docentes = new ArrayList<>();
        String sql = "SELECT * FROM docente";

        try {
            ResultSet rs = q.queryRest(sql);
            while (rs.next()) {
                Docente d = new Docente();
                d.setId_docente(rs.getInt("id_docente"));
                d.setNombre(rs.getString("nombre"));
                d.setEmail(rs.getString("email"));
                d.setDepartamento(rs.getString("departamento"));
                docentes.add(d);
            }
            rs.close();
            q.closeConn();
        } catch (SQLException e) {
            System.err.println("Error al obtener docentes: " + e.getMessage());
        }
        return docentes;
    }

    public Docente getDocenteById(int id) {
        String sql = "SELECT * FROM docente WHERE id_docente = " + id;
        Docente docente = null;

        try {
            ResultSet rs = q.queryRest(sql);
            if (rs.next()) {
                docente = new Docente();
                docente.setId_docente(rs.getInt("id_docente"));
                docente.setNombre(rs.getString("nombre"));
                docente.setEmail(rs.getString("email"));
                docente.setDepartamento(rs.getString("departamento"));
            }
            rs.close();
            q.closeConn();
        } catch (SQLException e) {
            System.err.println("Error al obtener docente por ID: " + e.getMessage());
        }
        return docente;
    }

    public boolean insertDocente(Docente docente) {
        String sql = String.format(
                "INSERT INTO docente (nombre, email, departamento) VALUES ('%s', '%s', '%s')",
                docente.getNombre(), docente.getEmail(), docente.getDepartamento()
        );
        return q.queryUpdate(sql);
    }

    public boolean updateDocente(Docente docente) {
        String sql = String.format(
                "UPDATE docente SET nombre = '%s', email = '%s', departamento = '%s' WHERE id_docente = %d",
                docente.getNombre(), docente.getEmail(), docente.getDepartamento(), docente.getId_docente()
        );
        return q.queryUpdate(sql);
    }

    public boolean deleteDocente(int id) {
        String sql = "DELETE FROM docente WHERE id_docente = " + id;
        return q.queryUpdate(sql);
    }
}