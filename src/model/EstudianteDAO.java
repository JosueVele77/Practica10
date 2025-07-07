package model;

import connection.query;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO {
    private query q;

    public EstudianteDAO() {
        q = new query();
    }

    public List<Estudiante> getAllEstudiantes() {
        List<Estudiante> estudiantes = new ArrayList<>();
        String sql = "SELECT id_estudiante, nombre, email, carrera, anio_ingreso FROM estudiante";

        try {
            ResultSet rs = q.queryRest(sql);
            while (rs.next()) {
                Estudiante e = new Estudiante();
                e.setId_estudiante(rs.getInt("id_estudiante"));
                e.setNombre(rs.getString("nombre"));
                e.setEmail(rs.getString("email"));
                e.setCarrera(rs.getString("carrera"));
                e.setAnio_ingreso(rs.getInt("anio_ingreso"));
                estudiantes.add(e);
            }
            rs.close();
            q.closeConn();
        } catch (SQLException e) {
            System.err.println("Error al obtener estudiantes: " + e.getMessage());
        }
        return estudiantes;
    }

    public Estudiante getEstudianteById(int id) {
        String sql = "SELECT id_estudiante, nombre, email, carrera, anio_ingreso FROM estudiante WHERE id_estudiante = " + id;
        Estudiante estudiante = null;

        try {
            ResultSet rs = q.queryRest(sql);
            if (rs.next()) {
                estudiante = new Estudiante();
                estudiante.setId_estudiante(rs.getInt("id_estudiante"));
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setEmail(rs.getString("email"));
                estudiante.setCarrera(rs.getString("carrera"));
                estudiante.setAnio_ingreso(rs.getInt("anio_ingreso"));
            }
            rs.close();
            q.closeConn();
        } catch (SQLException e) {
            System.err.println("Error al obtener estudiante por ID: " + e.getMessage());
        }
        return estudiante;
    }

    public boolean insertEstudiante(Estudiante estudiante) {
        String sql = String.format(
                "INSERT INTO estudiante (nombre, email, carrera, anio_ingreso) VALUES ('%s', '%s', '%s', %d)",
                estudiante.getNombre(), estudiante.getEmail(), estudiante.getCarrera(), estudiante.getAnio_ingreso()
        );
        return q.queryUpdate(sql);
    }

    public boolean updateEstudiante(Estudiante estudiante) {
        String sql = String.format(
                "UPDATE estudiante SET nombre = '%s', email = '%s', carrera = '%s', anio_ingreso = %d WHERE id_estudiante = %d",
                estudiante.getNombre(), estudiante.getEmail(), estudiante.getCarrera(),
                estudiante.getAnio_ingreso(), estudiante.getId_estudiante()
        );
        return q.queryUpdate(sql);
    }

    public boolean deleteEstudiante(int id) {
        String sql = "DELETE FROM estudiante WHERE id_estudiante = " + id;
        return q.queryUpdate(sql);
    }

    public List<Estudiante> getEstudiantesByCarrera(String carrera) {
        List<Estudiante> estudiantes = new ArrayList<>();
        String sql = String.format("SELECT id_estudiante, nombre, email, carrera, anio_ingreso FROM estudiante WHERE carrera = '%s'", carrera);

        try {
            ResultSet rs = q.queryRest(sql);
            while (rs.next()) {
                Estudiante e = new Estudiante();
                e.setId_estudiante(rs.getInt("id_estudiante"));
                e.setNombre(rs.getString("nombre"));
                e.setEmail(rs.getString("email"));
                e.setCarrera(rs.getString("carrera"));
                e.setAnio_ingreso(rs.getInt("anio_ingreso"));
                estudiantes.add(e);
            }
            rs.close();
            q.closeConn();
        } catch (SQLException e) {
            System.err.println("Error al obtener estudiantes por carrera: " + e.getMessage());
        }
        return estudiantes;
    }
    public List<Estudiante> getEstudiantesByAnioIngreso(int anioIngreso) {
        List<Estudiante> estudiantes = new ArrayList<>();
        String sql = "SELECT id_estudiante, nombre, email, carrera, anio_ingreso FROM estudiante WHERE anio_ingreso = " + anioIngreso;

        try {
            ResultSet rs = q.queryRest(sql);
            while (rs.next()) {
                Estudiante e = new Estudiante();
                e.setId_estudiante(rs.getInt("id_estudiante"));
                e.setNombre(rs.getString("nombre"));
                e.setEmail(rs.getString("email"));
                e.setCarrera(rs.getString("carrera"));
                e.setAnio_ingreso(rs.getInt("anio_ingreso"));
                estudiantes.add(e);
            }
            rs.close();
            q.closeConn();
        } catch (SQLException e) {
            System.err.println("Error al obtener estudiantes por año de ingreso: " + e.getMessage());
        }
        return estudiantes;
    }

    public List<Estudiante> getEstudiantesByAnioIngresoAndCarrera(int anioIngreso, String carrera) {
        List<Estudiante> estudiantes = new ArrayList<>();
        String sql = String.format("SELECT id_estudiante, nombre, email, carrera, anio_ingreso FROM estudiante WHERE anio_ingreso = %d AND carrera = '%s'", anioIngreso, carrera);

        try {
            ResultSet rs = q.queryRest(sql);
            while (rs.next()) {
                Estudiante e = new Estudiante();
                e.setId_estudiante(rs.getInt("id_estudiante"));
                e.setNombre(rs.getString("nombre"));
                e.setEmail(rs.getString("email"));
                e.setCarrera(rs.getString("carrera"));
                e.setAnio_ingreso(rs.getInt("anio_ingreso"));
                estudiantes.add(e);
            }
            rs.close();
            q.closeConn();
        } catch (SQLException e) {
            System.err.println("Error al obtener estudiantes por año de ingreso y carrera: " + e.getMessage());
        }
        return estudiantes;
    }
}