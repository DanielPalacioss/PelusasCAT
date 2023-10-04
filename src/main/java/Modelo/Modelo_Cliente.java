/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author aris-
 */
public class Modelo_Cliente extends Modelo_Persona {

    private String nombre_GrupoFamiliar;
    private String estado;
    private ArrayList<Modelo_Cliente> cliente = new ArrayList<Modelo_Cliente>();
    private Conexion con = new Conexion();

    public Modelo_Cliente(String nombres, String apellidos, String direccion, String barrio, String email, long telefono, String tipo_Id, long id, String nombre_GrupoFamiliar) {
        super(nombres, apellidos, direccion, barrio, email, telefono, tipo_Id, id);
        this.nombre_GrupoFamiliar = nombre_GrupoFamiliar;
    }

    public Modelo_Cliente(String tipo_Id, String nombres, String apellidos, String nombre_GrupoFamiliar, String barrio, String direccion, String email, long telefono, String estado) {
        super(nombres, apellidos, direccion, barrio, email, telefono, tipo_Id);
        this.nombre_GrupoFamiliar = nombre_GrupoFamiliar;
        this.estado = estado;
    }

    public Modelo_Cliente() {
    }

    public Object[] objeto() {
        Object[] objeto = new Object[]{getTipo_Id(), getNombres(), getApellidos(), getNombre_GrupoFamiliar(), getBarrio(), getDireccion(), getEmail(), getTelefono(), this.estado};
        return objeto;
    }

    public boolean consulta() {
        try {

            Connection sql = con.getConexion();
            Statement stmt = sql.createStatement();
            ResultSet rs; 
            rs = stmt.executeQuery("SELECT Tipo_Id, Nombres, Apellidos, Nombre_GrupoFamiliar, Barrio, Direccion, Email, Telefono, Estado FROM Clientes WHERE Id_Cliente =" + this.getId());
            if (!rs.next()) {
                rs.close();
                stmt.close();
                sql.close();
                return false;
            } else {
                rs.close();
                rs = stmt.executeQuery("SELECT Tipo_Id, Nombres, Apellidos, Nombre_GrupoFamiliar, Barrio, Direccion, Email, Telefono, Estado FROM Clientes WHERE Id_Cliente =" + this.getId());
            
                while (rs.next()) {
                    setTipo_Id(rs.getString(1));
                    setNombres(rs.getString(2));
                    setApellidos(rs.getString(3));
                    setNombre_GrupoFamiliar(rs.getString(4));
                    setBarrio(rs.getString(5));
                    setDireccion(rs.getString(6));
                    setEmail(rs.getString(7));
                    setTelefono(rs.getLong(8));
                    setEstado(rs.getString(9));
                    cliente.add(new Modelo_Cliente(getTipo_Id(), getNombres(), getApellidos(), getNombre_GrupoFamiliar(), getBarrio(), getDireccion(), getEmail(), getTelefono(), this.estado));
                }
                

            }
            rs.close();
            sql.close();
            stmt.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
    

    public boolean insertar() {
        try {
            Connection sql = con.getConexion();
            PreparedStatement insertCliente = sql.prepareStatement("INSERT INTO Clientes(Tipo_Id, Id_Cliente, Nombres, "
                    + "Apellidos, Nombre_Completo, Direccion, Barrio, Email, Telefono, Nombre_GrupoFamiliar) VALUES (?,?,?,"
                    + "?,?,?,?,?,?,?)");
            insertCliente.setString(1, getTipo_Id());
            insertCliente.setLong(2, getId());
            insertCliente.setString(3, getNombres());
            insertCliente.setString(4, getApellidos());
            insertCliente.setString(5, getNombres() + " " + getApellidos());
            insertCliente.setString(6, getDireccion());
            insertCliente.setString(7, getBarrio());
            insertCliente.setString(8, getEmail());
            insertCliente.setLong(9, getTelefono());
            insertCliente.setString(10, getNombre_GrupoFamiliar());
            insertCliente.executeUpdate();
            insertCliente.close();
            sql.close();
            JOptionPane.showMessageDialog(null, "EL CLIENTE FUE INGRESADO CORRECTAMENTE", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "VALIDE QUE EL CLIENTE NO EXISTA EN NUESTRA BASES DE DATOS","Advertencia",JOptionPane.WARNING_MESSAGE);
            System.out.println(e);
            return false;
        }
    }

    public ArrayList<Modelo_Cliente> getCliente() {
        return cliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre_GrupoFamiliar() {
        return nombre_GrupoFamiliar;
    }

    public void setNombre_GrupoFamiliar(String nombre_GrupoFamiliar) {
        this.nombre_GrupoFamiliar = nombre_GrupoFamiliar;
    }

    public boolean validarEntero(String Id) {
        try {
            long id = Long.parseLong(Id);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }

}
