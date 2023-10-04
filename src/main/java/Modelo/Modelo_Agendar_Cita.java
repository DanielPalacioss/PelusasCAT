/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.Date;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author aris-
 */
public class Modelo_Agendar_Cita {

    private long id_Cliente;
    private String tipo_Cita;
    private String nombre_Mascota;
    private Date fecha_Cita;
    private String doctor;
    private Time hora_Cita;
    private String hora_cita;
    private long id_Mascota;
    private long id_Agenda;
    private String Cliente;
    private String Estado;
    private Conexion con = new Conexion();
    private ArrayList<Modelo_Agendar_Cita> agendarC = new ArrayList<Modelo_Agendar_Cita>();

    public Modelo_Agendar_Cita(String tipo_Cita, String nombre_Mascota, Date fecha_Cita, String doctor, String hora_cita, long id_Agenda, String Cliente, String Estado) {
        this.tipo_Cita = tipo_Cita;
        this.nombre_Mascota = nombre_Mascota;
        this.fecha_Cita = fecha_Cita;
        this.doctor = doctor;
        this.hora_cita = hora_cita;
        this.id_Agenda = id_Agenda;
        this.Cliente = Cliente;
        this.Estado = Estado;
    }

    public Modelo_Agendar_Cita() {
    }

    public boolean insertar() {
        try {
            Connection sql = con.getConexion();
            PreparedStatement insertAgenda = sql.prepareStatement("INSERT INTO Agendamientos (Fecha_Cita, HoraDeLa_Cita, Doctor, Tipo_Cita, Id_Mascota, Id_Dueño) VALUES(?,?,?,?,?,?)");
            Statement stmt = sql.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT A.Id_Agenda FROM Agendamientos A WHERE (SELECT TOP 1 M.Id_Mascota FROM Mascotas M WHERE M.Nombre = '" + this.nombre_Mascota.toUpperCase() + "' AND (M.Id_Dueño = " + this.id_Cliente + " OR M.Id_Dueño2 = " + this.id_Cliente + ")) = A.Id_Mascota AND A.Tipo_Cita = '" + this.tipo_Cita + "' AND A.Fecha_Cita = '"+ new java.sql.Date(this.fecha_Cita.getTime()).toString()+"'");
            if(rs.next())
            {
                JOptionPane.showMessageDialog(null, "YA TIENE AGENDADA CITA PARA EL DIA "+ new java.sql.Date(this.fecha_Cita.getTime()).toString()+" CON EL MISMO TIPO DE CITA", "Error", 0);
                return false;
            }
            rs.close();
            rs = stmt.executeQuery("SELECT M.Id_Mascota FROM Mascotas M, Clientes C WHERE (M.Id_Dueño = " + this.id_Cliente + " OR M.Id_Dueño2 = " + this.id_Cliente + ") AND M.Nombre = '" + this.nombre_Mascota.toUpperCase() + "'");
            if (rs.next()) {
                this.id_Mascota = rs.getInt("Id_Mascota");
                insertAgenda.setDate(1, new java.sql.Date(this.fecha_Cita.getTime()));
                insertAgenda.setTime(2, this.hora_Cita);
                insertAgenda.setString(3, this.doctor);
                insertAgenda.setString(4, this.tipo_Cita);
                insertAgenda.setLong(5, this.id_Mascota);
                insertAgenda.setLong(6, this.id_Cliente);
                insertAgenda.executeUpdate();
                insertAgenda.close();
                rs.close();
                JOptionPane.showMessageDialog(null, "LA AGENDA SE INGRESO EXITOSAMENTE", "EXITOSO", 1);
                rs = stmt.executeQuery("SELECT TOP 1 Id_Agenda FROM Agendamientos ORDER BY Id_Agenda ASC");
                if (rs.next()) {
                    this.id_Agenda = rs.getInt(1);
                    JOptionPane.showMessageDialog(null, "LA AGENDA SE INGRESO EXITOSAMENTE, EL ID DE SU CITA ES " + this.id_Agenda, "EXITOSO", 1);
                }
                rs.close();
                stmt.close();
                sql.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "POR FAVOR VALIDAR QUE EL NOMBRE DE LA MASCOTA Y ID_CLIENTE"
                        + " ESTE CORRECTAMENTE ESCRITO", "Advertencia", 2);
                return false;
            }

        } catch (Exception cone) {
            System.out.println(cone);
            return false;
        }

    }

    public int validarEntero(String Id) {
        try {
            long id = Long.parseLong(Id);
            return 1;
        } catch (NumberFormatException e) {
            return 0;
        }

    }

    public Object[] objeto() {
        Object[] objeto = new Object[]{this.id_Agenda, this.nombre_Mascota, this.Cliente, this.tipo_Cita, this.hora_cita, this.fecha_Cita.toString(), this.doctor, this.Estado};
        return objeto;
    }

    public boolean consulta() {
        try {

            Connection sql = con.getConexion();
            Statement stmt = sql.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT  A.Id_Agenda,A.Id_Mascota, A.Id_Dueño,A.Tipo_Cita,A.HoraDeLa_Cita,A.Fecha_Cita,A.Doctor,A.Estado,.Nombres,M.Nombre FROM Agendamientos A, Clientes C,Mascotas M WHERE A.Id_Dueño = " + this.id_Cliente + " AND A.Tipo_Cita = '" + this.tipo_Cita + "' AND A.Fecha_Cita = " + (new java.sql.Date(this.fecha_Cita.getTime())) + " AND C.Id_Cliente = A.Id_Dueño AND M.Id_Mascota = A.Id_Mascota");/*SELECT
  A.Id_Agenda,
  A.Id_Mascota,
  A.Id_Dueño,
  A.Tipo_Cita,
  A.HoraDeLa_Cita,
  A.Fecha_Cita,
  A.Doctor,
  A.Estado,
  C.Nombres,
  M.Nombre
FROM
  Agendamientos A,
  Clientes C,
  Mascotas M
WHERE
  A.Id_Dueño = " + this.id_Cliente + "
  AND A.Tipo_Cita = '" + this.tipo_Cita + "'
  AND A.Fecha_Cita = " + (new java.sql.Date(this.fecha_Cita.getTime())) + "
  AND C.Id_Cliente = A.Id_Dueño
  AND M.Id_Mascota = A.Id_Mascota*/
            if (rs.getRow() != 0) {
                rs.close();
                stmt.close();
                sql.close();
                return false;
            } else {
                while (rs.next()) {
                    setId_Agenda(rs.getInt(1));
                    setNombre_Mascota(rs.getString(10));
                    setCliente(rs.getString(9));
                    setTipo_Cita(rs.getString(4));
                    setHora_cita((rs.getTime(5).toLocalTime().toString())); //conversion de time de sql a tiempo de java y luego se convierte a string
                    setFecha_Cita(new java.util.Date(rs.getDate(6).getTime()));
                    setDoctor(rs.getString(7));
                    setEstado(rs.getString(8));
                    this.agendarC.add(new Modelo_Agendar_Cita(this.tipo_Cita, this.nombre_Mascota, this.fecha_Cita, this.doctor, this.hora_cita, this.id_Agenda, this.Cliente, this.Estado));
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

    public ArrayList<Modelo_Agendar_Cita> getAgendarC() {
        return agendarC;
    }

    public String getHora_cita() {
        return hora_cita;
    }

    public void setHora_cita(String hora_cita) {
        this.hora_cita = hora_cita;
    }

    public long getId_Mascota() {
        return id_Mascota;
    }

    public void setId_Mascota(long id_Mascota) {
        this.id_Mascota = id_Mascota;
    }

    public long getId_Agenda() {
        return id_Agenda;
    }

    public void setId_Agenda(long id_Agenda) {
        this.id_Agenda = id_Agenda;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String Cliente) {
        this.Cliente = Cliente;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public Time getHora_Cita() {
        return hora_Cita;
    }

    public void setHora_Cita(Time hora_Cita) {
        this.hora_Cita = hora_Cita;
    }

    public long getId_Cliente() {
        return id_Cliente;
    }

    public void setId_Cliente(long id_Cliente) {
        this.id_Cliente = id_Cliente;
    }

    public String getTipo_Cita() {
        return tipo_Cita;
    }

    public void setTipo_Cita(String tipo_Cita) {
        this.tipo_Cita = tipo_Cita;
    }

    public String getNombre_Mascota() {
        return nombre_Mascota;
    }

    public void setNombre_Mascota(String nombre_Mascota) {
        this.nombre_Mascota = nombre_Mascota;
    }

    public Date getFecha_Cita() {
        return fecha_Cita;
    }

    public void setFecha_Cita(Date fecha_Cita) {
        this.fecha_Cita = fecha_Cita;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

}
