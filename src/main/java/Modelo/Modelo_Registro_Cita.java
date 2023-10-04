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
public class Modelo_Registro_Cita {

    private String tiempo; //duraccion cita
    private String nombre_Mascota;
    private long id_Cliente;
    private String cliente;
    private String motivo_Consulta;
    private String sintomas;
    private String tratamiento_A_Seguir;
    private String cie10_Principal;
    private String cie10_Secundario;
    private String cie10_Adicional;
    private String tipo_Cita;
    private String adicciones;
    private String estado;
    private long adiccionP;
    private long id_Agenda;
    private long id_Registro;
    private ArrayList<Modelo_Registro_Cita> registrar = new ArrayList<Modelo_Registro_Cita>();
    private Conexion con = new Conexion();

    public Modelo_Registro_Cita(long id_Registro, long id_Agenda, String nombre_Mascota, String cliente, String cie10_Principal, String cie10_Secundario, String cie10_Adicional, String tiempo, String motivo_Consulta, String sintomas, String tratamiento_A_Seguir, String adicciones, String estado) {
        this.tiempo = tiempo;
        this.nombre_Mascota = nombre_Mascota;
        this.cliente = cliente;
        this.motivo_Consulta = motivo_Consulta;
        this.sintomas = sintomas;
        this.tratamiento_A_Seguir = tratamiento_A_Seguir;
        this.cie10_Principal = cie10_Principal;
        this.cie10_Secundario = cie10_Secundario;
        this.cie10_Adicional = cie10_Adicional;
        this.adicciones = adicciones;
        this.estado = estado;
        this.id_Agenda = id_Agenda;
        this.id_Registro = id_Registro;
    }

    public Modelo_Registro_Cita() {
    }

    public String validar_adicciones(String adicion) {
        try {
            Connection sql = con.getConexion();
            Statement stmt = sql.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT A.precio, A.Nombre FROM Adiciones A WHERE A.Nombre = '" + adicion + "'");
            if (rs.next()) {
                this.adiccionP = rs.getLong(1);
                String resul = rs.getString(2) + " $" + this.adiccionP + ",";
                rs.close();
                stmt.close();
                sql.close();
                return (resul);
            }
        } catch (Exception e) {
            System.out.println("No encontro nada");
        }

        return "nada";

    }

    public boolean insertar() {
        int val = validarDatos();
        try {
            Connection sql = con.getConexion();

            switch (val) {
                case 0: {
                    sql.close();
                    return false;
                }
                case 1: { //cie principal
                    PreparedStatement insertRegistro = sql.prepareStatement("INSERT INTO Registros_Cita(Duracion_Cita, Motivo_Consulta, Sintomas, Adicciones, Tratamiento_Aseguir, Id_Dueño, Id_Cita, Cie10_Principal) VALUES (?,?,?,?,?,?,?,?)");
                    insertRegistro.setString(1, this.tiempo);
                    insertRegistro.setString(2, this.motivo_Consulta);
                    insertRegistro.setString(3, this.sintomas);
                    insertRegistro.setString(4, this.adicciones);
                    insertRegistro.setString(5, this.tratamiento_A_Seguir);
                    insertRegistro.setLong(6, this.id_Cliente);
                    insertRegistro.setLong(7, this.id_Agenda);
                    insertRegistro.setString(8, this.cie10_Principal);
                    insertRegistro.executeUpdate();
                    insertRegistro.close();
                    Statement stmt = sql.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT TOP 1 Id_Registro FROM Registros_Cita ORDER BY Id_Registro ASC");
                    if (rs.next()) {
                        this.id_Registro = rs.getLong(1);
                        JOptionPane.showMessageDialog(null, "EL REGISTRO SE INGRESO EXITOSAMENTE, EL ID DE SU REGISTRO ES " + this.id_Registro, "EXITOSO", 1);
                    }
                    stmt.close();
                    rs.close();
                    sql.close();
                    return true;
                }
                case 2: { //cie principal +secun
                    PreparedStatement insertRegistro = sql.prepareStatement("INSERT INTO Registros_Cita(Duracion_Cita, Motivo_Consulta, Sintomas, Adicciones, Tratamiento_Aseguir, Id_Dueño, Id_Cita, Cie10_Principal, Cie10_Secundario) VALUES (?,?,?,?,?,?,?,?,?)");

                    insertRegistro.setString(1, this.tiempo);
                    insertRegistro.setString(2, this.motivo_Consulta);
                    insertRegistro.setString(3, this.sintomas);
                    insertRegistro.setString(4, this.adicciones);
                    insertRegistro.setString(5, this.tratamiento_A_Seguir);
                    insertRegistro.setLong(6, this.id_Cliente);
                    insertRegistro.setLong(7, this.id_Agenda);
                    insertRegistro.setString(8, this.cie10_Principal);
                    insertRegistro.setString(9, this.cie10_Secundario);
                    insertRegistro.executeUpdate();
                    insertRegistro.close();
                    Statement stmt = sql.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT Id_Registro FROM Registros_Cita ORDER BY Id_Registro ASC LIMIT 1");
                    if (rs.next()) {
                        id_Registro = rs.getLong(1);
                        JOptionPane.showMessageDialog(null, "EL REGISTRO SE INGRESO EXITOSAMENTE, EL ID DE SU REGISTRO ES " + this.id_Registro, "EXITOSO", 1);
                    }
                    stmt.close();
                    rs.close();
                    sql.close();
                    return true;
                }
                case 3: { //cie principal + adic +secun
                    PreparedStatement insertRegistro = sql.prepareStatement("INSERT INTO Registros_Cita(Duracion_Cita, Motivo_Consulta, Sintomas, Adicciones, Tratamiento_Aseguir, Id_Dueño, Id_Cita, Cie10_Principal, Cie10_Secundario, Cie10_Adicional) VALUES (?,?,?,?,?,?,?,?,?,?)");
                    insertRegistro.setString(1, this.tiempo);
                    insertRegistro.setString(2, this.motivo_Consulta);
                    insertRegistro.setString(3, this.sintomas);
                    insertRegistro.setString(4, this.adicciones);
                    insertRegistro.setString(5, this.tratamiento_A_Seguir);
                    insertRegistro.setLong(6, this.id_Cliente);
                    insertRegistro.setLong(7, this.id_Agenda);
                    insertRegistro.setString(8, this.cie10_Principal);
                    insertRegistro.setString(9, this.cie10_Secundario);
                    insertRegistro.setString(10, this.cie10_Adicional);
                    insertRegistro.executeUpdate();
                    insertRegistro.close();
                    Statement stmt = sql.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT Id_Registro FROM Registros_Cita ORDER BY Id_Registro ASC LIMIT 1");
                    if (rs.next()) {
                        id_Registro = rs.getLong(1);
                        JOptionPane.showMessageDialog(null, "EL REGISTRO SE INGRESO EXITOSAMENTE, EL ID DE SU REGISTRO ES " + this.id_Registro, "EXITOSO", 1);
                    }
                    stmt.close();
                    rs.close();
                    sql.close();
                    return true;
                }
                default: {
                    sql.close();
                    return false;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "INTENTELO DE NUEVO", "ERROR", 0);
            System.out.println(e);
        }

        return true;
    }

    public int validarDatos() {
        int cie = 0;
        try {
            Connection sql = con.getConexion();
            Statement stmt = sql.createStatement();

            if (!(this.cie10_Secundario.equals(""))) {
                ResultSet rs3 = stmt.executeQuery("SELECT C.Cie10 FROM Cies10 C WHERE C.Cie10 ='" + this.cie10_Secundario + "'");
                if (rs3.next()) {
                    rs3.close();
                    cie = cie + 1;
                } else {
                    JOptionPane.showMessageDialog(null, "POR FAVOR VALIDE DE QUE EL CIE10 SECUNDARIO ESTE CORRECTO", "Advertencia", 3);
                    return 0;
                }
            }
            if (!(this.cie10_Adicional.equals(""))) {
                ResultSet rs4 = stmt.executeQuery("SELECT C.Cie10 FROM Cies10 C WHERE C.Cie10 ='" + this.cie10_Adicional + "'");
                if (rs4.next()) {
                    rs4.close();
                    cie = cie + 1;
                } else {
                    JOptionPane.showMessageDialog(null, "POR FAVOR VALIDE DE QUE EL CIE10 ADICIONAL ESTE CORRECTO", "Advertencia", 3);
                    return 0;
                }
            }
            ResultSet rs2 = stmt.executeQuery("SELECT C.Cie10 FROM Cies10 C WHERE C.Cie10 ='" + this.cie10_Principal + "'");
            if (!(rs2.next())) {
                JOptionPane.showMessageDialog(null, "POR FAVOR VALIDE DE QUE EL CIE10 PRINCIPAL ESTE CORRECTO", "Advertencia", 3);
            } else {
                ResultSet rs;
                rs = stmt.executeQuery("SELECT A.Id_Agenda FROM Agendamientos A WHERE (SELECT TOP 1 M.Id_Mascota FROM Mascotas M WHERE M.Nombre = '" + this.nombre_Mascota.toUpperCase() + "' AND (M.Id_Dueño = " + this.id_Cliente + " OR M.Id_Dueño2 = " + this.id_Cliente + ")) = A.Id_Mascota AND A.Tipo_Cita = '" + this.tipo_Cita + "' AND A.Fecha_Cita = '2023-07-07'"); //AND YEAR(A.Fecha_Cita) = YEAR(GETDATE())  AND MONTH(A.Fecha_Cita) = MONTH(GETDATE()) AND DAY(A.Fecha_Cita) = DAY(GETDATE())
                if (!(rs.next())) {
                    JOptionPane.showMessageDialog(null, "POR FAVOR VALIDE DE QUE EL ID del cliente, EL NOMBRE DE LA MASCOTA O EL TIPO DE CITA QUE ELIGIO AL AGENDAR ESTEN CORECTOS", "Advertencia", 3);
                } else {
                    this.id_Agenda = rs.getLong(1);
                    rs.close();
                    rs = stmt.executeQuery("SELECT Id_Cita FROM Registros_Cita WHERE Id_Cita = " + getId_Agenda());
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null, "YA SE ENCUENTRA REGISTRADA ESTA CITA ", "Advertencia", 3);
                        return 0;
                    }
                    sql.close();
                    stmt.close();
                    rs.close();
                    rs2.close();
                    return cie + 1;
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return 0;
    }

    public Object[] objeto() {
        Object[] objeto = new Object[]{this.id_Registro, this.id_Agenda, this.nombre_Mascota, this.cliente, this.cie10_Principal, this.cie10_Secundario, this.cie10_Adicional, this.tiempo, this.motivo_Consulta, this.sintomas, this.tratamiento_A_Seguir, this.adicciones, this.estado};
        return objeto;
    }

    public boolean consulta() {
        try {

            Connection sql = con.getConexion();
            Statement stmt = sql.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT R.Id_Registro, R.Id_Cita, M.Nombre, C.Nombres, R.Cie10_Principal, R.Cie10_Secundario, R.Cie10_Adicional, R.Duracion_Cita, R.Motivo_Consulta, R.Sintomas, R.Tratamiento_Aseguir, R.Adicciones, R.Estado FROM Registros_Cita R, Clientes C, Mascotas M, Agendamientos A WHERE C.Id_Cliente =" + this.id_Cliente + " AND A.Id_Agenda = " + this.id_Agenda + " AND A.Id_Mascota = M.Id_Mascota AND R.Id_Cita= " + this.id_Agenda + " AND R.Id_Dueño = " + this.id_Cliente);
            if (rs.getRow() == 0) {
                rs.close();
                stmt.close();
                sql.close();
                return false;
            } else {
                while (rs.next()) {
                    setId_Registro(rs.getLong(1));
                    setId_Agenda(rs.getLong(2));
                    setNombre_Mascota(rs.getString(3));
                    setCliente(rs.getString(4));
                    setCie10_Principal(rs.getString(5));
                    setCie10_Secundario(rs.getString(6));
                    setCie10_Adicional(rs.getString(7));
                    setTiempo(rs.getString(8));
                    setMotivo_Consulta(rs.getString(9));
                    setSintomas(rs.getString(10));
                    setTratamiento_A_Seguir(rs.getString(11));
                    setAdicciones(rs.getString(12));
                    setEstado(rs.getString(13));
                    registrar.add(new Modelo_Registro_Cita(this.id_Registro, this.id_Agenda, this.nombre_Mascota, this.cliente, this.cie10_Principal, this.cie10_Secundario, this.cie10_Adicional, this.tiempo, this.motivo_Consulta, this.sintomas, this.tratamiento_A_Seguir, this.adicciones, this.estado));
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

    public ArrayList<Modelo_Registro_Cita> getRegistrar() {
        return registrar;
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

    public long getId_Agenda() {
        return id_Agenda;
    }

    public void setId_Agenda(long id_Agenda) {
        this.id_Agenda = id_Agenda;
    }

    public long getAdiccionP() {
        return adiccionP;
    }

    public void setAdiccionP(long adiccionP) {
        this.adiccionP = adiccionP;
    }

    public String getAdicciones() {
        return adicciones;
    }

    public void setAdicciones(String adicciones) {
        this.adicciones = adicciones;
    }

    public String getCie10_Adicional() {
        return cie10_Adicional;
    }

    public void setCie10_Adicional(String cie10_Adicional) {
        this.cie10_Adicional = cie10_Adicional;
    }

    public String getTipo_Cita() {
        return tipo_Cita;
    }

    public void setTipo_Cita(String tipo_Cita) {
        this.tipo_Cita = tipo_Cita;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getNombre_Mascota() {
        return nombre_Mascota;
    }

    public void setNombre_Mascota(String nombre_Mascota) {
        this.nombre_Mascota = nombre_Mascota;
    }

    public long getId_Cliente() {
        return id_Cliente;
    }

    public void setId_Cliente(long id_Cliente) {
        this.id_Cliente = id_Cliente;
    }

    public String getMotivo_Consulta() {
        return motivo_Consulta;
    }

    public void setMotivo_Consulta(String motivo_Consulta) {
        this.motivo_Consulta = motivo_Consulta;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public String getTratamiento_A_Seguir() {
        return tratamiento_A_Seguir;
    }

    public void setTratamiento_A_Seguir(String tratamiento_A_Seguir) {
        this.tratamiento_A_Seguir = tratamiento_A_Seguir;
    }

    public String getCie10_Principal() {
        return cie10_Principal;
    }

    public void setCie10_Principal(String cie10_Principal) {
        this.cie10_Principal = cie10_Principal;
    }

    public String getCie10_Secundario() {
        return cie10_Secundario;
    }

    public void setCie10_Secundario(String cie10_Secundario) {
        this.cie10_Secundario = cie10_Secundario;
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
