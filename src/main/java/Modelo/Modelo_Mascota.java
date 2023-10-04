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
public class Modelo_Mascota extends Modelo_Animal {

    private long id_Mascota;
    private long id_Cliente;
    private long id_ClienteS;
    private String dueño;
    private String dueño2;
    private String estado;
    private ArrayList<Modelo_Mascota> mascota = new ArrayList<Modelo_Mascota>();
    private Conexion con = new Conexion();

    public Modelo_Mascota(long id_Mascota, String nombre, String raza, String peso, String caracteristicas, String estado, String dueño, String dueño2) {
        super(nombre, raza, peso, caracteristicas);
        this.id_Mascota = id_Mascota;
        this.dueño = dueño;
        this.dueño2 = dueño2;
        this.estado = estado;
    }

    public Modelo_Mascota() {
    }

    public Object[] objeto() {
        Object[] objeto = new Object[]{getId_Mascota(), getNombre(), getRaza(), getPeso(), getCaracteristicas(), getEstado(), getDueño(), getDueño2()};
        return objeto;
    }

    public boolean consulta() {
        try {

            Connection sql = con.getConexion();
            Statement stmt = sql.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT M.Id_Mascota, M.Nombre, M.Raza, M.Peso, M.Caracteristicas, M.Estado,CASE WHEN M.Id_Dueño = " + getId_Cliente() + " THEN C.Nombres ELSE '' END AS Dueño_Principal, CASE WHEN M.Id_Dueño2 = " + getId_Cliente() + " THEN C2.Nombres ELSE '' END AS Dueño_Secundario FROM Mascotas M LEFT JOIN Clientes C ON M.Id_Dueño = C.Id_Cliente LEFT JOIN Clientes C2 ON M.Id_Dueño2 = C2.Id_Cliente WHERE M.Nombre = '" + getNombre() + "'");
            if (rs.getRow() != 0) {
                rs.close();
                stmt.close();
                sql.close();
                return false;
            } else {
                while (rs.next()) {
                    setId_Mascota(rs.getLong(1));
                    setNombre(rs.getString(2));
                    setRaza(rs.getString(3));
                    setPeso(rs.getString(4));
                    setCaracteristicas(rs.getString(5));
                    setEstado(rs.getString(6));
                    setDueño(rs.getString(7));
                    setDueño2(rs.getString(8));
                    this.mascota.add(new Modelo_Mascota(getId_Mascota(), getNombre(), getRaza(), getPeso(), getCaracteristicas(), getEstado(), getDueño(), getDueño2()));
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
            if (id_ClienteS == 0) {
                throw new Exception("Excepción forzada");
            }
            Connection sql = con.getConexion();
            PreparedStatement insertMascota = sql.prepareStatement("INSERT INTO Mascotas(Nombre,Raza,Peso,Caracteristicas,Id_Dueño,Id_Dueño2) VALUES(?,?,?,?,?,?)");
            Statement stmt = sql.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Id_Cliente FROM Clientes Where Id_Cliente = " + this.id_Cliente);
            if (!(rs.next())) {
                JOptionPane.showMessageDialog(null, "POR FAVOR VALIDE QUE EL ID DEL CLIENTE PRINCIPAL SEA CORRECTO", "Error", 0);
                rs.close();
                return false;
            }
            rs.close();
            ResultSet rsM = stmt.executeQuery("SELECT C.Nombres, M.Id_Mascota FROM Mascotas M, Clientes C WHERE M.Nombre = '" + getNombre() + "' AND (M.Id_Dueño = " + getId_Cliente() + " OR M.Id_Dueño2 = " + getId_Cliente() + ") AND C.Id_Cliente = " + getId_Cliente());
            if (rsM.next()) {
                JOptionPane.showMessageDialog(null, "EL CLIENTE " + rsM.getString(1) + " YA TIENE UNA MASCOTA LLAMADA " + getNombre() + " CON ID " + rsM.getLong(2), "Error", 0);
                rsM.close();
                return false;
            }
            rsM.close();
            ResultSet rs2 = stmt.executeQuery("SELECT Id_Cliente FROM Clientes Where Id_Cliente = " + this.id_ClienteS);
            if (!(rs2.next())) {
                JOptionPane.showMessageDialog(null, "POR FAVOR VALIDE QUE EL ID DEL CLIENTE SECUNDARIO SEA CORRECTO", "Error", 0);
                rs2.close();
                return false;
            } else {
                rs.close();
                insertMascota.setString(1, getNombre().toUpperCase());
                insertMascota.setString(2, getRaza());
                insertMascota.setString(3, getPeso());
                insertMascota.setString(4, getCaracteristicas());
                insertMascota.setLong(5, getId_Cliente());
                insertMascota.setLong(6, getId_ClienteS());
                insertMascota.executeUpdate();
                insertMascota.close();
                rs = stmt.executeQuery("SELECT TOP 1 Id_Mascota FROM Mascotas ORDER BY Id_Mascota ASC");
                if (rs.next()) {
                    id_Mascota = rs.getLong(1);
                    JOptionPane.showMessageDialog(null, "LA MASCOTA SE INGRESO EXITOSAMENTE, EL ID DE SU MASCOTA ES " + this.id_Mascota, "EXITOSO", 1);
                    return true;
                }
            }
            rs.close();
            sql.close();
        } catch (Exception e) {

            try {
                Connection sql = con.getConexion();
                PreparedStatement insertMascota = sql.prepareStatement("INSERT INTO Mascotas(Nombre,Raza,Peso,Caracteristicas,Id_Dueño) VALUES(?,?,?,?,?)");
                Statement stmt = sql.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT Id_Cliente FROM Clientes Where Id_Cliente = " + this.id_Cliente);
                if (!(rs.next())) {
                    JOptionPane.showMessageDialog(null, "POR FAVOR VALIDE QUE EL ID DEL CLIENTE PRINCIPAL SEA CORRECTO", "EXITOSO", 1);
                    return false;
                }
                rs.close();
                ResultSet rsM = stmt.executeQuery("SELECT C.Nombres, M.Id_Mascota FROM Mascotas M, Clientes C WHERE M.Nombre = '" + getNombre() + "' AND (M.Id_Dueño = " + getId_Cliente() + " OR M.Id_Dueño2 = " + getId_Cliente() + ") AND C.Id_Cliente = " + getId_Cliente());
                if (rsM.next()) {
                    JOptionPane.showMessageDialog(null, "EL CLIENTE " + rsM.getString(1) + " YA TIENE UNA MASCOTA LLAMADA " + getNombre() + " CON ID " + rsM.getLong(2), "Error", 0);
                    rsM.close();
                    return false;
                } else {
                    rs.close();
                    insertMascota.setString(1, getNombre());
                    insertMascota.setString(2, getRaza());
                    insertMascota.setString(3, getPeso());
                    insertMascota.setString(4, getCaracteristicas());
                    insertMascota.setLong(5, getId_Cliente());
                    insertMascota.executeUpdate();
                    insertMascota.close();
                    rs = stmt.executeQuery("SELECT TOP 1 Id_Mascota FROM Mascotas ORDER BY Id_Mascota ASC");
                    if (rs.next()) {
                        id_Mascota = rs.getLong(1);
                        JOptionPane.showMessageDialog(null, "LA MASCOTA SE INGRESO EXITOSAMENTE, EL ID DE SU MASCOTA ES " + this.id_Mascota, "EXITOSO", 1);
                        rs.close();
                    }
                    rsM.close();
                    rs.close();
                    sql.close();
                    return true;
                }

            } catch (Exception es) {
                System.out.println(es);
            }

        }
        return false;
    }

    public ArrayList<Modelo_Mascota> getMascota() {
        return mascota;
    }

    public String getDueño() {
        return dueño;
    }

    public void setDueño(String dueño) {
        this.dueño = dueño;
    }

    public String getDueño2() {
        return dueño2;
    }

    public void setDueño2(String dueño2) {
        this.dueño2 = dueño2;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public long getId_ClienteS() {
        return id_ClienteS;
    }

    public void setId_ClienteS(long id_ClienteS) {
        this.id_ClienteS = id_ClienteS;
    }

    public long getId_Mascota() {
        return id_Mascota;
    }

    public void setId_Mascota(long id_Mascota) {
        this.id_Mascota = id_Mascota;
    }

    public long getId_Cliente() {
        return id_Cliente;
    }

    public void setId_Cliente(long id_Cliente) {
        this.id_Cliente = id_Cliente;
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
