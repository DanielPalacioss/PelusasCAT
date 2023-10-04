/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;



/**
 *
 * @author aris-
 */
public class Modelo_Animal {

    private String nombre;
    private String raza;
    private String peso;
    private String caracteristicas;

    public Modelo_Animal(String nombre, String raza, String peso, String caracteristicas) {
        this.nombre = nombre;
        this.raza = raza;
        this.peso = peso;
        this.caracteristicas = caracteristicas;
    }
    
    public Modelo_Animal()
    {
        
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    
}
