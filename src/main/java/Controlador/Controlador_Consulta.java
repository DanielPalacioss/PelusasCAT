/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Modelo_Agendar_Cita;
import Modelo.Modelo_Cliente;
import Modelo.Modelo_Facturar;
import Modelo.Modelo_Mascota;
import Modelo.Modelo_Registro_Cita;
import Vista.Vista_Consultar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author aris-
 */
public class Controlador_Consulta implements ActionListener {

    private Modelo_Agendar_Cita modeloA;
    private Modelo_Cliente modeloC;
    private Modelo_Facturar modeloF;
    private Modelo_Mascota modeloM;
    private Modelo_Registro_Cita modeloR;
    private boolean cerrar;
    private Vista_Consultar vista;

    public Controlador_Consulta(Modelo_Agendar_Cita modeloA, Modelo_Cliente modeloC, Modelo_Facturar modeloF, Modelo_Mascota modeloM, Modelo_Registro_Cita modeloR, Vista_Consultar vista) {
        this.modeloA = modeloA;
        this.modeloC = modeloC;
        this.modeloF = modeloF;
        this.modeloM = modeloM;
        this.modeloR = modeloR;
        this.vista = vista;
        this.vista.consultarAgendamientosBt.addActionListener(this);
        this.vista.consultarClientesBt.addActionListener(this);
        this.vista.consultar_FacturasBt.addActionListener(this);
        this.vista.consultar_MascotasBt.addActionListener(this);
        this.vista.consultar_RegistrosBt.addActionListener(this);
    }

    public boolean validarA() {
        try {
            SimpleDateFormat validarFecha = new SimpleDateFormat("y-MM-d");
            String probar = validarFecha.format(this.vista.fecha_CitaAgendamientos.getDate());

        } catch (Exception a) {
            JOptionPane.showMessageDialog(null, "POR FAVOR SELECCIONE UNA FECHA PARA LA CITA", "Error", 0);
            return false;
        }
        if (this.vista.id_ClienteAgendamientosTex.getText().replaceAll(" ", "").equals("")) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESAR UN VALOR EN ID DEL CLIENTE", "Error", 0);
            return false;
        } else if (this.modeloA.validarEntero(this.vista.id_ClienteAgendamientosTex.getText().replaceAll(" ", "")) == 0) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESAR SOLO NUMERO EN ID_CLIENTE", "Error", 0);
            return false;
        } else if (this.vista.tipo_CitaAgendamientosBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "POR FAVOR SELECCIONE EL TIPO DE CITA", "Error", 0);
            return false;
        } else {
            modeloA.setId_Cliente(Long.parseLong(this.vista.id_ClienteAgendamientosTex.getText().replaceAll(" ", "")));
            modeloA.setTipo_Cita(this.vista.tipo_CitaAgendamientosBox.getSelectedItem().toString());
            modeloA.setFecha_Cita(vista.fecha_CitaAgendamientos.getDate());
            return true;
        }

    }

    public void consultaA() {
        DefaultTableModel modelo = (DefaultTableModel) vista.tbAgendamientos.getModel();
        modelo.setRowCount(0);
        try {
            for (Modelo_Agendar_Cita agenda : modeloA.getAgendarC()) {
                modelo.addRow(agenda.objeto());
            }
            vista.tbAgendamientos.setModel(modelo);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean validarC() {
        if (this.vista.id_ClienteClientesTex.getText().replaceAll(" ", "").equals("")) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESAR UN VALOR EN ID DEL CLIENTE", "Error", 0);
            return false;
        } else if (!(this.modeloC.validarEntero(this.vista.id_ClienteClientesTex.getText().replaceAll(" ", "")))) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESAR SOLO NUMERO EN ID_CLIENTE", "Error", 0);
            return false;
        } else {
            this.modeloC.setId(Long.parseLong(this.vista.id_ClienteClientesTex.getText().replaceAll(" ", "")));
            return true;
        }
    }

    public void consultaC() {
        DefaultTableModel modelo = (DefaultTableModel) this.vista.tbCliente.getModel();
        modelo.setRowCount(0);
        try {
            for (Modelo_Cliente cliente : modeloC.getCliente()) {
                modelo.addRow(cliente.objeto());
            }
            
            vista.tbCliente.setModel(modelo);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean validarF() {
        if (this.vista.id_FacturaFacturasTex.getText().replaceAll(" ", "").equals("")) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESAR UN VALOR EN ID DE LA FACTURA", "Error", 0);
            return false;
        } else if (!(this.modeloF.validarEntero(this.vista.id_FacturaFacturasTex.getText().replaceAll(" ", "")))) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESAR SOLO NUMERO EN ID_FACTURA", "Error", 0);
            return false;
        } else {
            System.out.println("envio");
            this.modeloF.setId_Factura(Long.parseLong(this.vista.id_FacturaFacturasTex.getText().replaceAll(" ", "")));
            return true;
        }
    }

    public void consultaF() {
        DefaultTableModel modelo = (DefaultTableModel) vista.tbFactura.getModel();
        modelo.setRowCount(0);
        try {
            for (Modelo_Facturar factura : modeloF.getFacturar()) {
                modelo.addRow(factura.objeto());
            }
            vista.tbFactura.setModel(modelo);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean validarM() {
        if (this.vista.id_ClienteMascotasTex.getText().replaceAll(" ", "").equals("")) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESAR UN VALOR EN ID DEL CLIENTE", "Error", 0);
            return false;
        } else if (!(this.modeloM.validarEntero(this.vista.id_ClienteMascotasTex.getText().replaceAll(" ", "")))) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESAR SOLO NUMERO EN ID_CLIENTE", "Error", 0);
            return false;
        } else if (this.vista.nombreMascota_MascotasTex.getText().replaceAll(" ", "").equals("")) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESAR UN VALOR EN MASCOTA", "Error", 0);
            return false;
        } else {
            this.modeloM.setId_Cliente(Long.parseLong(this.vista.id_ClienteMascotasTex.getText().replaceAll(" ", "")));
            this.modeloM.setNombre(this.vista.nombreMascota_MascotasTex.getText().replaceAll(" ", "").toUpperCase());
            return true;
        }
    }

    public void consultaM() {
        DefaultTableModel modelo = (DefaultTableModel) vista.tbMascotas.getModel();
        modelo.setRowCount(0);
        try {
            for (Modelo_Mascota mascota : modeloM.getMascota()) {
                modelo.addRow(mascota.objeto());
            }
            vista.tbMascotas.setModel(modelo);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean validarR() {
        if (this.vista.id_ClienteRegistrosTex.getText().replaceAll(" ", "").equals("")) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESAR UN VALOR EN ID DEL CLIETNE", "Error", 0);
            return false;
        } else if (!(this.modeloR.validarEntero(this.vista.id_ClienteRegistrosTex.getText().replaceAll(" ", "")))) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESAR SOLO NUMERO EN ID_CLIENTE", "Error", 0);
            return false;
        } else if (this.vista.id_AgendaRegistrosTex.getText().replaceAll(" ", "").equals("")) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESAR UN VALOR EN ID DE LA AGENDA", "Error", 0);
            return false;
        } else if (!(this.modeloR.validarEntero(this.vista.id_AgendaRegistrosTex.getText().replaceAll(" ", "")))) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESAR SOLO NUMERO EN ID_AGENDA", "Error", 0);
            return false;
        } else {
            this.modeloR.setId_Cliente(Long.parseLong(this.vista.id_ClienteRegistrosTex.getText().replaceAll(" ", "")));
            this.modeloR.setId_Agenda(Long.parseLong(this.vista.id_AgendaRegistrosTex.getText().replaceAll(" ", "")));
            return true;
        }

    }

    public void consultaR() {
        DefaultTableModel modelo = (DefaultTableModel) vista.tbRegistro.getModel();
        modelo.setRowCount(0);
        try {
            for (Modelo_Registro_Cita agenda : modeloR.getRegistrar()) {
                modelo.addRow(agenda.objeto());
            }
            vista.tbRegistro.setModel(modelo);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(vista.consultarAgendamientosBt)) //consulta Agenda
        {
            validarA();
            if (modeloA.consulta()) {
                consultaA();
            } else {
                JOptionPane.showMessageDialog(null, "VALIDE DE QUE LOS DATOS INGRESADOS SEAN CORRECTOS O EXISTAN", "Advetencia", 3);
            }
        }

        if (e.getSource().equals(vista.consultar_RegistrosBt)) //consulta Registro
        {
            validarR();
            if (modeloR.consulta()) {
                consultaR();
            } else {
                JOptionPane.showMessageDialog(null, "VALIDE DE QUE LOS DATOS INGRESADOS SEAN CORRECTOS O EXISTAN", "Advetencia", 3);
            }
        }

        if (e.getSource().equals(vista.consultarClientesBt)) //consulta Cliente
        {
            if (validarC()) {
                if (modeloC.consulta()) {
                    consultaC();
                } else {
                    JOptionPane.showMessageDialog(null, "VALIDE DE QUE LOS DATOS INGRESADOS SEAN CORRECTOS O EXISTAN", "Advetencia", 3);
                }
            }

        }

        if (e.getSource().equals(vista.consultar_FacturasBt)) //consulta Factura
        {
            validarF();
            if (modeloF.consulta()) {
                consultaF();
            } else {
                JOptionPane.showMessageDialog(null, "VALIDE DE QUE LOS DATOS INGRESADOS SEAN CORRECTOS O EXISTAN", "Advetencia", 3);
            }
        }

        if (e.getSource().equals(vista.consultar_MascotasBt)) //consulta Mascota
        {
            validarM();
            if (modeloM.consulta()) {
                consultaM();
            } else {
                JOptionPane.showMessageDialog(null, "VALIDE DE QUE LOS DATOS INGRESADOS SEAN CORRECTOS O EXISTAN", "Advetencia", 3);
            }
        }

    }

    public Vista_Consultar getVista() {
        return vista;
    }

    public void MostrarVista() {
        vista.setVisible(true);
    }

    public void CerrarVista() {
        vista.dispose();
    }
}
