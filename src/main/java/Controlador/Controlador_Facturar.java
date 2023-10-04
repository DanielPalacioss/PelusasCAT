/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Modelo_Facturar;
import Vista.Vista_Facturar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author aris-
 */
public class Controlador_Facturar implements ActionListener {

    Vista_Facturar vista;
    Modelo_Facturar modelo;

    public Controlador_Facturar(Vista_Facturar vista, Modelo_Facturar modelo) {
        this.vista = vista;
        this.modelo = modelo;
        vista.FacturarBt.addActionListener(this);
    }

    public boolean validaciones() {
        if (vista.IdFactura.getText().replaceAll(" ", "").equals("")) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESE EL ID DEL DUEÑO PRINCIPAL", "Error", 0);
            return false;
        } else if (!(modelo.validarEntero(vista.IdFactura.getText().replace(" ", "")))) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESE SOLO NUMEROS EN EL ID DEL DUEÑO PRINCIPAL", "Error", 0);
            return false;
        } else {
            modelo.setId_Factura(Integer.parseInt(vista.IdFactura.getText().replaceAll(" ", "")));
            return true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(vista.FacturarBt)) {
            if (validaciones()) {
                if (modelo.facturar()) {
                    Limpiar();
                }
            }
        }
    }

    public void Limpiar() {
        this.vista.IdFactura.setText("");
    }

    public Vista_Facturar getVista() {
        return vista;
    }

    public void MostrarVista() {
        vista.setVisible(true);
    }

    public void CerrarVista() {
        vista.dispose();
    }
}
