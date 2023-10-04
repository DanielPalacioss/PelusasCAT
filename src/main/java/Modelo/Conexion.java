/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author aris-
 */
public class Conexion {

    public static Connection getConexion() {
        String conexionUrl = "jdbc:sqlserver://localhost:1433;"
                + "databaseName=Pelusas_Cat;" + "user=sa;" + "password=6012;"
                + "loginTimeout=30; trustServerCertificate=true";
        try {
            Connection con = DriverManager.getConnection(conexionUrl);
            System.out.println("conectado");
            return con;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "LA CONEXION A LA BASE DE DATOS NO ESTA DISPONIBLE",
                     "Advertencia", JOptionPane.WARNING_MESSAGE);
            System.out.println(ex.toString());
            return null;
        }
    }

}
