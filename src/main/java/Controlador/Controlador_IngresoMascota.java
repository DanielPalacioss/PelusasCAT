/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Modelo.Modelo_Mascota;
import Vista.Vista_Ingreso_Mascota;
import javax.swing.JOptionPane;

/**
 *
 * @author aris-
 */
public class Controlador_IngresoMascota implements ActionListener {
    
    private Modelo_Mascota modelo;
    private Vista_Ingreso_Mascota vista;
    
    public Controlador_IngresoMascota(Modelo_Mascota modelo, Vista_Ingreso_Mascota vista) {
        this.modelo = modelo;
        this.vista = vista;
        this.vista.IngresarMascotaBt.addActionListener(this);
    }
    
    public boolean validaciones() {
        if (!(vista.IdDueño2Tex.getText().replaceAll(" ", "").equals(""))) {
            if (!(modelo.validarEntero(vista.IdDueño2Tex.getText().replaceAll(" ", "")))) {
                JOptionPane.showMessageDialog(null, "POR FAVOR INGRESE SOLO NUMEROS EN EL ID DEL DUEÑO SECUNDARIO", "Error", 0);
                return false;
            }
        }
        if (vista.IdDueñoTex.getText().replaceAll(" ", "").equals("")) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESE EL ID DEL DUEÑO PRINCIPAL", "Error", 0);
            return false;
        } else if (!(modelo.validarEntero(vista.IdDueñoTex.getText().replaceAll(" ", "")))) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESE SOLO NUMEROS EN EL ID DEL DUEÑO PRINCIPAL", "Error", 0);
            return false;
        } else if ((int) vista.PesoSpin.getValue() < 1) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESE UN PESO MAYOR A 0", "Error", 0);
            return false;
        } else if (vista.NombreMTex.getText().replaceAll(" ", "").equals("")) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESE EL NOMBRE DE LA MASCOTA", "Error", 0);
            return false;
        } else {
            modelo.setNombre(this.vista.NombreMTex.getText().toUpperCase());
            modelo.setId_Cliente(Long.parseLong(this.vista.IdDueñoTex.getText()));
            if (!(this.vista.IdDueño2Tex.getText().replaceAll(" ", "").equals(""))) {
                System.out.println("se ingreso");
                modelo.setId_ClienteS(Long.parseLong(vista.IdDueño2Tex.getText().replaceAll(" ", "")));
            }
            modelo.setPeso(this.vista.PesoSpin.getValue().toString() + " Kg");
            if (this.vista.RazaTex.getText().replaceAll(" ", "").equals("")) {
                modelo.setRaza("SIN RAZA");
            } else {
                modelo.setRaza(vista.RazaTex.getText().toUpperCase());
            }
            if (this.vista.CaracteristicasAr.getText().replaceAll(" ", "").equals("")) {
                modelo.setCaracteristicas("Sin caracteristicas");
            } else {
                modelo.setCaracteristicas(vista.CaracteristicasAr.getText());
            }
            
            return true;
        }
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(vista.IngresarMascotaBt)) {
            if (validaciones()) {
                if (JOptionPane.showConfirmDialog(null, "SEGURO QUE DESEA INGRESAR UNA MASCOTA CON ESOS DATOS?", "ADVERTENCIA", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if(modelo.insertar())
                    {
                        Limpiar();
                    }
                }
            }
            
        }
        
    }
    
    public void Limpiar() {
        this.vista.NombreMTex.setText("");
        this.vista.IdDueñoTex.setText("");
        this.vista.IdDueño2Tex.setText("");
        this.vista.PesoSpin.setValue(0);
        this.vista.RazaTex.setText("");
        this.vista.CaracteristicasAr.setText("");
    }
    
    public Vista_Ingreso_Mascota getVista() {
        return vista;
    }
    
    public void MostrarVista() {
        vista.setVisible(true);
    }
    
    public void CerrarVista() {
        vista.dispose();
    }
}
