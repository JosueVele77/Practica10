package model;

import connection.query;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TestModel {

    public static void main(String[] args) {
        // Primero probamos la conexión directamente
        testConexion();

        // Luego probamos cada DAO por separado
        testDocenteDAO();
        testCursoDAO();
        testEstudianteDAO();
    }

    public static void testConexion() {
        System.out.println("\n=== TEST DE CONEXIÓN ===");
        query q = new query();

        try {
            // Probamos una consulta simple a la base de datos
            ResultSet rs = q.queryRest("SELECT 1");
            if (rs.next()) {
                System.out.println("✅ Conexión a la base de datos exitosa");
            } else {
                System.out.println("❌ No se pudo ejecutar consulta de prueba");
            }
            rs.close();
            q.closeConn();
        } catch (SQLException e) {
            System.err.println("❌ Error en la conexión: " + e.getMessage());
        }
    }

    public static void testDocenteDAO() {
        System.out.println("\n=== TEST DOCENTE DAO ===");
        DocenteDAO ddao = new DocenteDAO();

        try {
            // 1. Obtener todos los docentes
            List<Docente> docentes = ddao.getAllDocentes();

            if (docentes.isEmpty()) {
                System.out.println("⚠️ No se encontraron docentes en la base de datos");
            } else {
                System.out.println("✅ Listado de docentes obtenido correctamente");
                System.out.println("Total de docentes: " + docentes.size());

                // Mostrar los primeros 3 docentes como muestra
                System.out.println("\n--- Muestra de docentes (primeros 3) ---");
                int limit = Math.min(3, docentes.size());
                for (int i = 0; i < limit; i++) {
                    Docente d = docentes.get(i);
                    System.out.println("ID: " + d.getId_docente() +
                            " | Nombre: " + d.getNombre() +
                            " | Email: " + d.getEmail());
                }
            }

            // 2. Probar búsqueda por ID
            if (!docentes.isEmpty()) {
                int idPrueba = docentes.get(0).getId_docente();
                Docente docente = ddao.getDocenteById(idPrueba);

                if (docente != null) {
                    System.out.println("\n✅ Docente encontrado por ID (" + idPrueba + "): " + docente.getNombre());
                } else {
                    System.out.println("\n❌ No se pudo encontrar docente por ID");
                }
            }

        } catch (Exception e) {
            System.err.println("❌ Error en DocenteDAO: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void testCursoDAO() {
        System.out.println("\n=== TEST CURSO DAO ===");
        CursoDAO cdao = new CursoDAO();

        try {
            // 1. Obtener todos los cursos
            List<Curso> cursos = cdao.getAllCursos();

            if (cursos.isEmpty()) {
                System.out.println("⚠️ No se encontraron cursos en la base de datos");
            } else {
                System.out.println("✅ Listado de cursos obtenido correctamente");
                System.out.println("Total de cursos: " + cursos.size());

                // Mostrar los primeros 3 cursos como muestra
                System.out.println("\n--- Muestra de cursos (primeros 3) ---");
                int limit = Math.min(3, cursos.size());
                for (int i = 0; i < limit; i++) {
                    Curso c = cursos.get(i);
                    System.out.println("ID: " + c.getId_curso() +
                            " | Nombre: " + c.getNombre() +
                            " | Créditos: " + c.getCreditos());
                }
            }

            // 2. Probar búsqueda por ID
            if (!cursos.isEmpty()) {
                int idPrueba = cursos.get(0).getId_curso();
                Curso curso = cdao.getCursoById(idPrueba);

                if (curso != null) {
                    System.out.println("\n✅ Curso encontrado por ID (" + idPrueba + "): " + curso.getNombre());
                } else {
                    System.out.println("\n❌ No se pudo encontrar curso por ID");
                }
            }

        } catch (Exception e) {
            System.err.println("❌ Error en CursoDAO: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void testEstudianteDAO() {
        System.out.println("\n=== TEST ESTUDIANTE DAO ===");
        EstudianteDAO edao = new EstudianteDAO();

        try {
            // 1. Obtener todos los estudiantes
            List<Estudiante> estudiantes = edao.getAllEstudiantes();

            if (estudiantes.isEmpty()) {
                System.out.println("⚠️ No se encontraron estudiantes en la base de datos");
            } else {
                System.out.println("✅ Listado de estudiantes obtenido correctamente");
                System.out.println("Total de estudiantes: " + estudiantes.size());

                // Mostrar los primeros 3 estudiantes como muestra
                System.out.println("\n--- Muestra de estudiantes (primeros 3) ---");
                int limit = Math.min(3, estudiantes.size());
                for (int i = 0; i < limit; i++) {
                    Estudiante e = estudiantes.get(i);
                    System.out.println("ID: " + e.getId_estudiante() +
                            " | Nombre: " + e.getNombre() +
                            " | Carrera: " + e.getCarrera());
                }
            }

            // 2. Probar búsqueda por ID
            if (!estudiantes.isEmpty()) {
                int idPrueba = estudiantes.get(0).getId_estudiante();
                Estudiante estudiante = edao.getEstudianteById(idPrueba);

                if (estudiante != null) {
                    System.out.println("\n✅ Estudiante encontrado por ID (" + idPrueba + "): " + estudiante.getNombre());
                } else {
                    System.out.println("\n❌ No se pudo encontrar estudiante por ID");
                }
            }

        } catch (Exception e) {
            System.err.println("❌ Error en EstudianteDAO: " + e.getMessage());
            e.printStackTrace();
        }
    }
}