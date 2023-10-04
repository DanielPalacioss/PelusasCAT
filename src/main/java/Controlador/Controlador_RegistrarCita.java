/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Modelo_Facturar;
import Modelo.Modelo_Registro_Cita;
import Vista.Vista_Registrar_Cita;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author aris-
 */
public class Controlador_RegistrarCita implements ActionListener {

    private Modelo.Modelo_Registro_Cita modeloR;
    private Modelo.Modelo_Facturar modeloF;
    private Vista.Vista_Registrar_Cita vista;
    private String tiempo;
    private int hora;
    private int minutos;

    public Controlador_RegistrarCita(Modelo_Registro_Cita modeloR, Vista_Registrar_Cita vista, Modelo_Facturar modeloF) {
        this.modeloR = modeloR;
        this.vista = vista;
        this.modeloF = modeloF;
        this.vista.RegistrarCitaBt.addActionListener(this);
        this.vista.adiccionalesBt.addActionListener(this);
    }

    public boolean validar_Hora() {

        try {
            SimpleDateFormat dateformat = new SimpleDateFormat("hh:mm a");
            String horaFinal = dateformat.format(this.vista.Hora_FinalSpin.getValue());
            String horaInicio = dateformat.format(this.vista.Hora_InicioSpin.getValue());
            Date horafinal = dateformat.parse(horaFinal);
            Date horainicio = dateformat.parse(horaInicio);
            long diferencia = horafinal.getTime() - horainicio.getTime();
            int horas = (int) (diferencia / 3600000);
            int minutos = (int) ((diferencia % 3600000) / 60000);
            String tiempo = String.format("%02d:%02d", horas, minutos);
            JOptionPane.showMessageDialog(null, "Tiempo transcurrido " + tiempo);
            if (horas == 0 && minutos == 0) {
                JOptionPane.showMessageDialog(null, "POR FAVOR INGRESE HORA DE INICIO Y FINAL CORRECTAS", "Error", 0);
                return false;
            } else if (horas < 0 || minutos < 0) {
                JOptionPane.showMessageDialog(null, "POR FAVOR INGRESE HORA DE INICIO Y FINAL CORRECTAS", "Error", 0);
                return false;
            } else {
                this.tiempo = tiempo;
                this.hora = horas;
                this.minutos = minutos;
                return true;
            }
        } catch (ParseException ex) {
            System.out.println(ex);
        }
        return false;
    }

    public void adicionales() {
        if (this.vista.adiccionalBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "POR FAVOR SELECCIONE UNO DE LOS ADICCIONALES", "Error", 0);
        } else {
            if (this.modeloF.getDetalles() == null) {
                this.modeloF.setDetalles(this.modeloR.validar_adicciones(this.vista.adiccionalBox.getSelectedItem().toString()));
                this.modeloF.setTotal(this.modeloR.getAdiccionP());
            } else {
                this.modeloF.setDetalles(this.modeloF.getDetalles() + ",\n" + this.modeloR.validar_adicciones(this.vista.adiccionalBox.getSelectedItem().toString().replaceAll(" ", "")));
                this.modeloF.setTotal(this.modeloF.getTotal() + this.modeloR.getAdiccionP());
            }

            if (this.modeloR.getAdicciones() == null) {
                this.modeloR.setAdicciones(this.vista.adiccionalBox.getSelectedItem().toString());
            } else {
                this.modeloR.setAdicciones(this.modeloR.getAdicciones() + ", " + this.vista.adiccionalBox.getSelectedItem().toString().replaceAll(" ", ""));

            }

        }
    }

    public boolean validacionesR() {
        if (this.vista.nombreTex.getText().replaceAll(" ", "").equals("")) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESE EL NOMBRE DE LA MASCOTA", "Error", 0);
            return false;
        } else if (this.vista.id_ClienteTex.getText().replaceAll(" ", "").equals("")) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESE EL ID DEL CLIENTE", "Error", 0);
            return false;
        } else if (!(this.modeloR.validarEntero(String.valueOf(this.vista.id_ClienteTex.getText().replaceAll(" ", ""))))) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESE SOLO NUMEROS EN EL CAMPO ID", "Error", 0);
            return false;
        } else if (this.vista.cie10PTex.getText().replaceAll(" ", "").equals("")) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESE EL CIE10 PRINCIPAL", "Error", 0);
            return false;
        } else if (this.vista.Tipo_CitaBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "POR FAVOR SELECCIONE EL TIPO DE CITA", "Error", 0);
            return false;
        } else if (this.vista.motivoConsultaAr.getText().replaceAll(" ", "").equals("")) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESE EL MOTIVO DE LA CONSULTA", "Error", 0);
            return false;
        } else if (this.vista.sintomasAr.getText().replaceAll(" ", "").equals("")) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESE LOS SINTOMAS DE LA MASCOTA", "Error", 0);
            return false;
        } else if (this.vista.tratamientoAr.getText().replaceAll(" ", "").equals("")) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESE EL TRATAMIENTO A SEGUIR", "Error", 0);
            return false;
        } else if (!(validar_Hora())) {
            return false;
        } else {
            this.modeloR.setId_Cliente(Integer.parseInt(this.vista.id_ClienteTex.getText().replaceAll(" ", "")));
            this.modeloR.setTiempo(this.tiempo);
            this.modeloR.setCie10_Principal(this.vista.cie10PTex.getText().replaceAll(" ", "").toUpperCase());
            this.modeloR.setNombre_Mascota(this.vista.nombreTex.getText().replaceAll(" ", "").toUpperCase());
            this.modeloR.setTipo_Cita(this.vista.Tipo_CitaBox.getSelectedItem().toString());
            this.modeloR.setMotivo_Consulta(this.vista.motivoConsultaAr.getText());
            this.modeloR.setSintomas(this.vista.sintomasAr.getText());
            this.modeloR.setTratamiento_A_Seguir(this.vista.tratamientoAr.getText());
            if (!(this.vista.cie10STex.getText().replaceAll(" ", "").equals(""))) {
                this.modeloR.setCie10_Secundario(this.vista.cie10STex.getText().replaceAll(" ", "").toUpperCase());
            } else {
                this.modeloR.setCie10_Secundario("");
            }
            if (!(this.vista.cie10ATex.getText().replaceAll(" ", "").equals(""))) {
                this.modeloR.setCie10_Adicional(this.vista.cie10ATex.getText().replaceAll(" ", "").toUpperCase());
            } else {
                this.modeloR.setCie10_Adicional("");
            }

            return true;
        }

    }

    public void cobroHora() {

        if (modeloF.getDetalles() == null) {
            if (this.hora > 0) {
                this.modeloF.setTotal(modeloF.getTotal() + (this.hora * 15000));
                this.modeloF.setDetalles(modeloF.getDetalles() + "Horas " + this.hora + " $" + (this.hora * 15000));
            }
            if (this.minutos > 0) {
                this.modeloF.setTotal(modeloF.getTotal() + ((this.minutos / 60) * 15000));
                this.modeloF.setDetalles(modeloF.getDetalles() + "Minutos " + this.minutos + " $" + ((this.minutos / 60) * 15000));
            }
        } else {
            if (this.hora > 0) {
                this.modeloF.setTotal(modeloF.getTotal() + (this.hora * 15000));
                this.modeloF.setDetalles(modeloF.getDetalles() + " Horas " + this.hora + " $" + (this.hora * 15000));
            }
            if (this.minutos > 0) {
                this.modeloF.setTotal(modeloF.getTotal() + ((this.minutos / 60) * 15000));
                this.modeloF.setDetalles(modeloF.getDetalles() + " Minutos " + this.minutos + " $" + ((this.minutos / 60) * 15000));
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(vista.RegistrarCitaBt)) {
            if (validacionesR()) {
                if (JOptionPane.showConfirmDialog(null, "SEGURO QUE DESEA REGISTRAR LA CITA CON ESOS DATOS?", "ADVERTENCIA", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (modeloR.insertar()) {
                        //insertar factura
                        cobroHora();
                        modeloF.setId_Cliente(modeloR.getId_Cliente());
                        modeloF.setId_Registro(modeloR.getId_Registro());
                        modeloF.insertar();
                        Limpiar();
                    }

                }
            }
        }
        if (e.getSource().equals(vista.adiccionalesBt)) {
            if (JOptionPane.showConfirmDialog(null, "SEGURO QUE DESEA AGREGAR UN ADICCIONAL?", "ADVERTENCIA", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                adicionales();
            }
        }
    }

    public void Limpiar() {
        try {
            Date HoraDef = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).parse("08:00 AM");
            this.vista.id_ClienteTex.setText("");
            this.vista.cie10PTex.setText("");
            this.vista.cie10STex.setText("");
            this.vista.cie10ATex.setText("");
            this.vista.nombreTex.setText("");
            this.vista.Tipo_CitaBox.setSelectedIndex(0);
            this.vista.motivoConsultaAr.setText("");
            this.vista.sintomasAr.setText("");
            this.vista.Hora_InicioSpin.setValue(HoraDef);
            this.vista.Hora_FinalSpin.setValue(HoraDef);
            this.vista.tratamientoAr.setText("");
        } catch (Exception e) {
        }

    }

    public Vista_Registrar_Cita getVista() {
        return vista;
    }

    public void MostrarVista() {
        vista.setVisible(true);
    }

    public void CerrarVista() {
        vista.dispose();
    }

}
