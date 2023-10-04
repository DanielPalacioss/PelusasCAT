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
public class Modelo_Facturar {

    private long id_Cliente;
    private String cliente;
    private float total;
    private long id_Factura;
    private String Detalles;
    private long id_Registro;
    private String estado;
    private ArrayList<Modelo_Facturar> facturar = new ArrayList<Modelo_Facturar>();
    private Conexion con = new Conexion();

    public Modelo_Facturar(long id_Factura, String cliente, String Detalles, float total, String estado, long id_Registro) {
        this.cliente = cliente;
        this.total = total;
        this.id_Factura = id_Factura;
        this.Detalles = Detalles;
        this.id_Registro = id_Registro;
        this.estado = estado;
    }

    public Modelo_Facturar() {
    }

    public Object[] objeto() {
        Object[] objeto = new Object[]{this.id_Factura, this.cliente, this.Detalles, this.total, this.estado, this.id_Registro};
        return objeto;
    }

    public boolean consulta() {
        try {

            Connection sql = con.getConexion();
            Statement stmt = sql.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT F.Id_Factura, C.Nombres, F.Descripcion, F.Valor_Total, F.Estado FROM Facturas F, Clientes C WHERE F.Id_Factura = " + this.id_Factura + "AND C.Id_Cliente = F.Id_Cliente");
            if (rs.getRow() != 0) {
                rs.close();
                stmt.close();
                sql.close();

                return false;
            } else {
                System.out.println(this.id_Factura);
                while (rs.next()) {
                    System.out.println("aqui");
                    setId_Factura(rs.getLong(1));
                    setCliente(rs.getString(2));
                    setDetalles(rs.getString(3));
                    setTotal(rs.getFloat(4));
                    setEstado(rs.getString(5));
                    facturar.add(new Modelo_Facturar(this.id_Factura, this.cliente, this.Detalles, this.total, this.estado, this.id_Registro));
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

    public void insertar() {
        try {
            Connection sql = con.getConexion();
            Statement stmt = sql.createStatement();
            PreparedStatement insert_Factura = sql.prepareStatement("INSERT INTO Facturas(Valor_Total, Descripcion, Id_Cliente, Id_Registro) VALUES(?,?,?,?)");
            insert_Factura.setFloat(1, this.total);
            insert_Factura.setString(2, this.Detalles);
            insert_Factura.setLong(3, this.id_Cliente);
            insert_Factura.setLong(4, this.id_Registro);
            insert_Factura.executeUpdate();
            ResultSet rs = stmt.executeQuery("SELECT TOP 1 Id_Factura FROM Facturas ORDER BY Id_Factura ASC");
            if (rs.next()) {
                this.id_Factura = rs.getLong(1);
                JOptionPane.showMessageDialog(null, "POR FAVOR PAGUE CON EL SIGUIENTE ID DE FACTURA " + this.id_Factura, "EXITOSO", 1);
            }
            sql.close();
            stmt.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean facturar() {
        try {
            Connection sql = con.getConexion();
            Statement stmt = sql.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Estado FROM Facturas WHERE Estado = 'Pagado' AND Id_Factura = " + this.id_Factura);
            if(rs.next())
            {
                JOptionPane.showMessageDialog(null, "LA FACTURA YA SE ENCUENTRA PAGADA", "Advertencia", 3);
                return true;
            }
            PreparedStatement updateEstado = sql.prepareStatement("UPDATE Facturas SET Estado = 'Pagado' WHERE Id_Factura =" + this.id_Factura);
            if (updateEstado.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "EL PAGO FUE REALIZADO EXITOSAMENTE", "Advertencia", 3);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "POR FAVOR VALIDE QUE EL ID DE LA FACTURA SEA CORRECTO", "Advertencia", 3);
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public ArrayList<Modelo_Facturar> getFacturar() {
        return facturar;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public long getId_Registro() {
        return id_Registro;
    }

    public void setId_Registro(long id_Registro) {
        this.id_Registro = id_Registro;
    }

    public String getDetalles() {
        return Detalles;
    }

    public void setDetalles(String Detalles) {
        this.Detalles = Detalles;
    }

    public long getId_Cliente() {
        return id_Cliente;
    }

    public void setId_Cliente(long id_Cliente) {
        this.id_Cliente = id_Cliente;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public long getId_Factura() {
        return id_Factura;
    }

    public void setId_Factura(long id_Factura) {
        this.id_Factura = id_Factura;
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
