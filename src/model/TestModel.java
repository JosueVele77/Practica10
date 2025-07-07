package model;

import connection.query;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TestModel {

    public static void main(String[] args) {
        System.out.println("=== VISUALIZACIÓN DE TABLAS DE LA BASE DE DATOS ACADÉMICA ===\n");

        // Mostrar contenido de cada tabla
        mostrarTablaDocentes();
        mostrarTablaCursos();
        mostrarTablaEstudiantes();
    }

    public static void mostrarTablaDocentes() {
        System.out.println("=== TABLA DOCENTES ===");
        DocenteDAO ddao = new DocenteDAO();
        List<Docente> docentes = ddao.getAllDocentes();

        if (docentes.isEmpty()) {
            System.out.println("No hay docentes registrados en la base de datos.\n");
            return;
        }

        // Encabezado de la tabla
        System.out.println("+------------+--------------------------+--------------------------+------------------+");
        System.out.println("| ID_DOCENTE |          NOMBRE          |          EMAIL           |   DEPARTAMENTO    |");
        System.out.println("+------------+--------------------------+--------------------------+------------------+");

        // Contenido de la tabla
        for (Docente d : docentes) {
            System.out.printf("| %-10d | %-24s | %-24s | %-16s |\n",
                    d.getId_docente(),
                    limitarCadena(d.getNombre(), 24),
                    limitarCadena(d.getEmail(), 24),
                    limitarCadena(d.getDepartamento(), 16));
        }

        System.out.println("+------------+--------------------------+--------------------------+------------------+");
        System.out.printf("Total de docentes: %d\n\n", docentes.size());
    }

    public static void mostrarTablaCursos() {
        System.out.println("=== TABLA CURSOS ===");
        CursoDAO cdao = new CursoDAO();
        List<Curso> cursos = cdao.getAllCursos();

        if (cursos.isEmpty()) {
            System.out.println("No hay cursos registrados en la base de datos.\n");
            return;
        }

        // Encabezado de la tabla
        System.out.println("+----------+--------------------------+-----------+------------+");
        System.out.println("| ID_CURSO |          NOMBRE          | CRÉDITOS  | ID_DOCENTE |");
        System.out.println("+----------+--------------------------+-----------+------------+");

        // Contenido de la tabla
        for (Curso c : cursos) {
            System.out.printf("| %-8d | %-24s | %-9d | %-10d |\n",
                    c.getId_curso(),
                    limitarCadena(c.getNombre(), 24),
                    c.getCreditos(),
                    c.getId_docente());
        }

        System.out.println("+----------+--------------------------+-----------+------------+");
        System.out.printf("Total de cursos: %d\n\n", cursos.size());
    }

    public static void mostrarTablaEstudiantes() {
        System.out.println("=== TABLA ESTUDIANTES ===");
        EstudianteDAO edao = new EstudianteDAO();
        List<Estudiante> estudiantes = edao.getAllEstudiantes();

        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados en la base de datos.\n");
            return;
        }

        // Encabezado de la tabla
        System.out.println("+---------------+--------------------------+--------------------------+--------------------------+-------------+");
        System.out.println("| ID_ESTUDIANTE |          NOMBRE          |          EMAIL           |         CARRERA          | AÑO INGRESO |");
        System.out.println("+---------------+--------------------------+--------------------------+--------------------------+-------------+");

        // Contenido de la tabla
        for (Estudiante e : estudiantes) {
            System.out.printf("| %-13d | %-24s | %-24s | %-24s | %-11d |\n",
                    e.getId_estudiante(),
                    limitarCadena(e.getNombre(), 24),
                    limitarCadena(e.getEmail(), 24),
                    limitarCadena(e.getCarrera(), 24),
                    e.getAnio_ingreso());
        }

        System.out.println("+---------------+--------------------------+--------------------------+--------------------------+-------------+");
        System.out.printf("Total de estudiantes: %d\n\n", estudiantes.size());
    }

    // Método auxiliar para limitar el tamaño de las cadenas
    private static String limitarCadena(String texto, int longitud) {
        if (texto == null) {
            return "";
        }
        if (texto.length() <= longitud) {
            return texto;
        }
        return texto.substring(0, longitud - 3) + "...";
    }
}