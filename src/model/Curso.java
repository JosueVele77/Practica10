package model;

public class Curso {
    private int id_curso;
    private String nombre;
    private int creditos;
    private int id_docente;

    public Curso() {
    }
    public Curso(int id_curso, String nombre, int creditos, int id_docente) {
        this.id_curso = id_curso;
        this.nombre = nombre;
        this.creditos = creditos;
        this.id_docente = id_docente;
    }

    public int getId_curso() { return id_curso; }
    public void setId_curso(int id_curso) { this.id_curso = id_curso; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getCreditos() { return creditos; }
    public void setCreditos(int creditos) { this.creditos = creditos; }
    public int getId_docente() { return id_docente; }
    public void setId_docente(int id_docente) { this.id_docente = id_docente; }
}