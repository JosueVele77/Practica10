package model;

public class Docente {
    private int id_docente;
    private String nombre;
    private String email;
    private String departamento;

    public Docente() {
    }

    public Docente(int id_docente, String nombre, String email, String departamento) {
        this.id_docente = id_docente;
        this.nombre = nombre;
        this.email = email;
        this.departamento = departamento;
    }
    public int getId_docente() { return id_docente; }
    public void setId_docente(int id_docente) { this.id_docente = id_docente; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
}