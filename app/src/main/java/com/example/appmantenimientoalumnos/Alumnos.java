package com.example.appmantenimientoalumnos; // Cambia por tu paquete real

public class Alumnos {
    private int id;
    private String codigo;
    private String nombres;
    private String apellidos;
    private String email;

    // Constructor vacío (necesario para SQLite)
    public Alumnos() {
    }

    // Constructor con todos los atributos
    public Alumnos(int id, String codigo, String nombres, String apellidos, String email) {
        this.id = id;
        this.codigo = codigo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}