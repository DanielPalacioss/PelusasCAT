/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Modelo_Cliente;
import Vista.Vista_Ingreso_Clientes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author aris-
 */
public class Controlador_IngresoCliente implements ActionListener {

    private Modelo_Cliente modelo;
    private Vista_Ingreso_Clientes vista;

    public Controlador_IngresoCliente(Modelo_Cliente modelo, Vista_Ingreso_Clientes vista) {
        this.modelo = modelo;
        this.vista = vista;
        this.vista.IngresarClienteBt.addActionListener(this);
    }

    public boolean validaciones() {
        if (this.vista.NombreTex.getText().replaceAll(" ", "").equals("")) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESE EL NOMBRE DEL CLIENTE", "Error", 0);
            return false;
        } else if (this.vista.ApellidosTex.getText().replaceAll(" ", "").equals("")) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESE EL APELLIDO DEL CLIENTE", "Error", 0);
            return false;
        } else if (!(this.modelo.validarEntero(this.vista.TelefonoTex.getText().replaceAll(" ", "")))) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESE SOLO NUMEROS EN EL CAMPO TELEFONO", "Error", 0);
            return false;
        }else if (this.vista.IdText.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESE UN NUMERO VALIDO EN EL CAMPO ID", "Error", 0);
            return false;
        }  else if (this.vista.TelefonoTex.getText().length()<10) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESE UN NUMERO DE TELEFONO VALIDO", "Error", 0);
            return false;
        }  else if (this.vista.Tipo_idBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "POR FAVOR SELECCIONE UN TIPO DE IDENTIFICACION", "Error", 0);
            return false;
        } else if (!(this.modelo.validarEntero(this.vista.IdText.getText().replaceAll(" ", "")))) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESE SOLO NUMEROS EN EL CAMPO ID", "Error", 0);
            return false;
        } else if (!(this.vista.EmailTex.getText().contains("@") && this.vista.EmailTex.getText().contains(".com"))) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESE UN CORREO ELECTRONICO VALIDO", "Error", 0);
            return false;
        }  else {
            this.modelo.setNombres(String.valueOf(this.vista.NombreTex.getText().toUpperCase()));
            this.modelo.setApellidos(String.valueOf(this.vista.ApellidosTex.getText().toUpperCase()));
            this.modelo.setTelefono(Long.parseLong(this.vista.TelefonoTex.getText().replaceAll(" ", "")));
            this.modelo.setId(Long.parseLong(this.vista.IdText.getText().replaceAll(" ", "")));
            this.modelo.setTipo_Id(this.vista.Tipo_idBox.getSelectedItem().toString());
            if (this.vista.DireccionTex.getText().replaceAll(" ", "").equals("")) {
                this.modelo.setDireccion("Sin direccion");
            } else {
                this.modelo.setDireccion(vista.DireccionTex.getText().toUpperCase());
            }
            if (this.vista.BarrioTex.getText().replaceAll(" ", "").equals("")) {
                this.modelo.setBarrio("Sin barrio");
            } else {
                this.modelo.setBarrio(vista.BarrioTex.getText().toUpperCase());
            }
            if (this.vista.EmailTex.getText().replaceAll(" ", "").equals("")) {
                this.modelo.setEmail("clientesincorreo@sincorreo.com");
            } else {//validacion de si es un correo tenes que hacerla mas arriba
                this.modelo.setEmail(vista.EmailTex.getText().replaceAll(" ", "").toLowerCase());
            }
            if (this.vista.GrupFamiliarTex.getText().replaceAll(" ", "").equals("")) {
                this.modelo.setNombre_GrupoFamiliar("Sin grupo familiar");
            } else {
                this.modelo.setNombre_GrupoFamiliar(vista.GrupFamiliarTex.getText().toUpperCase());
            }
            return true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.vista.IngresarClienteBt)) {

            if (validaciones()) {
                if (JOptionPane.showConfirmDialog(null, "SEGURO QUE DESEA AGREGAR EL CLIENTE CON ESOS DATOS?", "ADVERTENCIA",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if(this.modelo.insertar())
                    {
                        Limpiar();
                    }
                }
            }
        }
    }
    
    public void Limpiar()
    {
        this.vista.NombreTex.setText("");
        this.vista.ApellidosTex.setText("");
        this.vista.TelefonoTex.setText("");
        this.vista.IdText.setText("");
        this.vista.Tipo_idBox.setSelectedIndex(0);
        this.vista.DireccionTex.setText("");
        this.vista.BarrioTex.setText("");
        this.vista.EmailTex.setText("");
        this.vista.GrupFamiliarTex.setText("");
    }
    
    public void MostrarVista() {
        vista.setVisible(true);
    }

    public void CerrarVista() {
        vista.dispose();
    }
}
