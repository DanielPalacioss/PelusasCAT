/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Modelo.Modelo_Agendar_Cita;
import Vista.Vista_Agendar_Cita;
import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author aris-
 */
public class Controlador_AgendarCita implements ActionListener {

    private Vista_Agendar_Cita vista;
    private Modelo_Agendar_Cita modelo;
    private Time hora;

    public Controlador_AgendarCita(Vista_Agendar_Cita vista, Modelo_Agendar_Cita modelo) {
        this.vista = vista;
        this.modelo = modelo;
        vista.AgendarBt.addActionListener(this);
    }

    public boolean convertirHora() {

        try {
            SimpleDateFormat validarHora = new SimpleDateFormat("hh:mm a");
            String Horap = validarHora.format(this.vista.Hora_CitaSpin.getValue());
            Date Horapp = validarHora.parse(Horap);
            Calendar cal = Calendar.getInstance();
            cal.setTime(Horapp);
            int horaV = cal.get(Calendar.HOUR_OF_DAY);

            // Validar la hora
            if (horaV >= 8 && horaV <= 18) {
                this.hora = new Time(Horapp.getTime());
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
            return true;
        }
    }

    public boolean validarFecha() {

        try {
            LocalDate fechaActual = LocalDate.now();
            Instant instant = vista.fecha_Date.getDate().toInstant();
            LocalDate fechaAValidar = instant.atZone(ZoneId.systemDefault()).toLocalDate();

            if (fechaAValidar.isBefore(fechaActual) || fechaAValidar.isEqual(fechaActual)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }

    public boolean validaciones() {
        try {
            SimpleDateFormat validarFecha = new SimpleDateFormat("y-MM-d");
            String probar = validarFecha.format(this.vista.fecha_Date.getDate());

        } catch (Exception a) {
            JOptionPane.showMessageDialog(null, "POR FAVOR SELECCIONE UNA FECHA PARA LA CITA", "Error", 0);
            return false;
        }
        if (this.vista.IdDueñoTex.getText().replaceAll(" ", "").equals("")) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESAR UN VALOR EN ID DEL DUEÑO", "Error", 0);
            return false;
        } else if (this.modelo.validarEntero(this.vista.IdDueñoTex.getText().replaceAll(" ", "")) == 0) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESAR SOLO NUMERO EN ID_CLIENTE", "Error", 0);
            return false;
        } else if (validarFecha()) {
            JOptionPane.showMessageDialog(null, "LA FECHA DE LA CITA NO PUEDE SER IGUAL O INFERIOR AL DIA EN CURSO", "Error", 0);
            return false;
        } else if (this.vista.NombreMTex.getText().replaceAll(" ", "").equals("")) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESAR EL NOMBRE DE LA MASCOTA", "Error", 0);
            return false;
        } else if (this.vista.Tipo_CitaBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "POR FAVOR SELECCIONE EL TIPO DE CITA", "Error", 0);
            return false;
        } else if (convertirHora()) {//pilas hora spin
            JOptionPane.showMessageDialog(null, "LA HORA DEBE ESTAR ENTRE LAS 8Am Y LAS 6Pm", "Advertencia", 3);
            return false;
        } else if (this.vista.doctorBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "POR FAVOR SELECCIONE EL DOCTOR", "Error", 0);
            return false;
        } else {
            this.modelo.setNombre_Mascota(this.vista.NombreMTex.getText().toUpperCase());
            this.modelo.setDoctor(String.valueOf(this.vista.doctorBox.getSelectedItem()));
            this.modelo.setId_Cliente(Integer.parseInt(this.vista.IdDueñoTex.getText().replaceAll(" ", "")));
            this.modelo.setFecha_Cita(this.vista.fecha_Date.getDate());
            this.modelo.setHora_Cita(this.hora);
            this.modelo.setTipo_Cita(this.vista.Tipo_CitaBox.getSelectedItem().toString());
            return true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.vista.AgendarBt)) {

            if (validaciones()) {
                if (JOptionPane.showConfirmDialog(null, "SEGURO QUE DESEA AGENDAR?", "ADVERTENCIA", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (this.modelo.insertar()) {
                        Limpiar();
                    }
                }

            }

        }
    }

    public void Limpiar() {
        try {
            Date HoraDef = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).parse("08:00 AM");
            this.vista.NombreMTex.setText("");
            this.vista.doctorBox.setSelectedIndex(0);
            this.vista.IdDueñoTex.setText("");
            this.vista.fecha_Date.setDate(null);
            this.vista.Hora_CitaSpin.setValue(HoraDef);
            this.vista.Tipo_CitaBox.setSelectedIndex(0);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void MostrarVista() {
        this.vista.setVisible(true);
    }

    public void CerrarVista() {
        this.vista.dispose();
    }
}
