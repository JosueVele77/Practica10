package model;

public class Estudiante {
    private int id_estudiante;
    private String nombre;
    private String email;
    private String carrera;
    private int anio_ingreso;

    // Getters y Setters
    public int getId_estudiante() { return id_estudiante; }
    public void setId_estudiante(int id_estudiante) { this.id_estudiante = id_estudiante; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }
    public int getAnio_ingreso() { return anio_ingreso; }
    public void setAnio_ingreso(int anio_ingreso) { this.anio_ingreso = anio_ingreso; }
}