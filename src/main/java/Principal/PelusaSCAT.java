/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Principal;

import Controlador.Controlador_Inicio;
import Vista.Vista_Inicio;

/**
 *
 * @author aris-
 */
public class PelusaSCAT {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Vista_Inicio vista = new Vista_Inicio();
        Controlador_Inicio controladorInicio = new Controlador_Inicio(vista);
        controladorInicio.MostrarVista();
    }

}
