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
import Vista.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author aris-
 */
public class Controlador_Inicio implements ActionListener {

    //INICIALIZAR AGENDA
    Modelo_Agendar_Cita modeloAgendarCita = new Modelo_Agendar_Cita();
    Vista_Agendar_Cita vistaAgendarCita = new Vista_Agendar_Cita();
    //INICIALIZAR CLIENTE
    Modelo_Cliente modeloCliente = new Modelo_Cliente();
    Vista_Ingreso_Clientes vistaIngresoCliente = new Vista_Ingreso_Clientes();
    //INICIALIZAR FACTURA
    Modelo_Facturar modeloFacturar = new Modelo_Facturar();
    Vista_Facturar vistaFacturar = new Vista_Facturar();
    //INICIALIZAR MASCOTA
    Modelo_Mascota modeloMascota = new Modelo_Mascota();
    Vista_Ingreso_Mascota vistaIngresoMascota = new Vista_Ingreso_Mascota();
    //INICIALIZAR REGISTRO DE CITA
    Modelo_Registro_Cita modeloRegistroCita = new Modelo_Registro_Cita();
    Vista_Registrar_Cita vistaRegistrarCita = new Vista_Registrar_Cita();
    //INICIALIZAR VISTA CONSULTA
    Vista_Consultar vistaConsultar = new Vista_Consultar();
    //INICIALIZAR VISTA INICIO
    Vista_Inicio vistaInicio = new Vista_Inicio();

    //INICIALIZAR CONTROLADORES
    Controlador_AgendarCita controladorAgendarCita = new Controlador_AgendarCita(vistaAgendarCita, modeloAgendarCita);
    Controlador_Consulta controladorConsulta = new Controlador_Consulta(modeloAgendarCita, modeloCliente, modeloFacturar, modeloMascota, modeloRegistroCita, vistaConsultar);
    Controlador_Facturar controladorFactura = new Controlador_Facturar(vistaFacturar, modeloFacturar);
    Controlador_IngresoCliente controladorCliente = new Controlador_IngresoCliente(modeloCliente, vistaIngresoCliente);
    Controlador_IngresoMascota controladorMascota = new Controlador_IngresoMascota(modeloMascota, vistaIngresoMascota);
    Controlador_RegistrarCita controladorRegistrarCita = new Controlador_RegistrarCita(modeloRegistroCita, vistaRegistrarCita, modeloFacturar);

    public Controlador_Inicio(Vista_Inicio vistaInicio) {
        this.vistaInicio = vistaInicio;
        this.vistaInicio.AgendarCitaBt.addActionListener(this);
        this.vistaInicio.ConsultarBt.addActionListener(this);
        this.vistaInicio.FacturarBt.addActionListener(this);
        this.vistaInicio.RegistrarCitaBt.addActionListener(this);
        this.vistaInicio.RegistrarClienteBt.addActionListener(this);
        this.vistaInicio.RegistrarMascotaBt.addActionListener(this);
        this.vistaIngresoCliente.VolverClienteBt.addActionListener(this);
        this.vistaIngresoMascota.VolverMascotaBt.addActionListener(this);
        this.vistaFacturar.VolverBt.addActionListener(this);
        this.vistaAgendarCita.VolverBt.addActionListener(this);
        this.vistaRegistrarCita.VolverBt.addActionListener(this);
        this.vistaInicio.SalirBt.addActionListener(this);
    }

    //ESCUCHA DE CADA BOTON
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.vistaInicio.AgendarCitaBt)) {
            CerrarVista();
            this.controladorAgendarCita.MostrarVista();
        }
        if (e.getSource().equals(this.vistaInicio.ConsultarBt)) {
            CerrarVista();
            this.controladorConsulta.MostrarVista();
        }
        if (e.getSource().equals(this.vistaInicio.FacturarBt)) {
            CerrarVista();
            this.controladorFactura.MostrarVista();
        }
        if (e.getSource().equals(this.vistaInicio.RegistrarCitaBt)) {
            CerrarVista();
            this.controladorRegistrarCita.MostrarVista();
        }
        if (e.getSource().equals(this.vistaInicio.RegistrarClienteBt)) {
            CerrarVista();
            this.controladorCliente.MostrarVista();
        }
        if (e.getSource().equals(this.vistaInicio.RegistrarMascotaBt)) {
            CerrarVista();
            this.controladorMascota.MostrarVista();
        }
        if (e.getSource().equals(this.vistaInicio.SalirBt)) {
            if (JOptionPane.showConfirmDialog(null, "SEGURO QUE DESEA CERRAR LA APLICACIÓN?", "ADVERTENCIA", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
        if (e.getSource().equals(this.vistaIngresoCliente.VolverClienteBt)) {
            this.controladorCliente.CerrarVista();
            MostrarVista();
        }
        if (e.getSource().equals(this.vistaIngresoMascota.VolverMascotaBt)) {
            this.controladorMascota.CerrarVista();
            MostrarVista();
        }
        if (e.getSource().equals(this.vistaAgendarCita.VolverBt)) {
            this.controladorAgendarCita.CerrarVista();
            MostrarVista();
        }
        if (e.getSource().equals(this.vistaRegistrarCita.VolverBt)) {
            this.controladorRegistrarCita.CerrarVista();
            MostrarVista();
        }
        if (e.getSource().equals(this.vistaFacturar.VolverBt)) {
            this.controladorFactura.CerrarVista();
            MostrarVista();
        }
    }

    public void MostrarVista() {
        vistaInicio.setVisible(true);
    }

    public void CerrarVista() {
        vistaInicio.dispose();
    }

}
