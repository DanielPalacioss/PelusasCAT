/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author aris-
 */
public class Modelo_Persona {

    private String nombres;
    private String apellidos;
    private String direccion;
    private String barrio;
    private String email;
    private long telefono;
    private String tipo_Id;
    private long id;

    public Modelo_Persona(String nombres, String apellidos, String direccion, String barrio, String email, long telefono, String tipo_Id, long id) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.barrio = barrio;
        this.email = email;
        this.telefono = telefono;
        this.tipo_Id = tipo_Id;
        this.id = id;
    }

    public Modelo_Persona(String nombres, String apellidos, String direccion, String barrio, String email, long telefono, String tipo_Id) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.barrio = barrio;
        this.email = email;
        this.telefono = telefono;
        this.tipo_Id = tipo_Id;
    }

    public Modelo_Persona() {

    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public String getTipo_Id() {
        return tipo_Id;
    }

    public void setTipo_Id(String tipo_Id) {
        this.tipo_Id = tipo_Id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
