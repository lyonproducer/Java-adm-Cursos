/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

import Datos.Conexion;
import Reportes.GenerarReportes;

/**
 *
 * @author LyonDj
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static Conexion hc;
    
    
    public static void main(String[] args) {
        
        try{
            hc= new Conexion();
            System.out.println("Conectado");
            
            //IU_GestorCurso g = new IU_GestorCurso();
            //g.setVisible(true);
            
            //IU_GestorAlumno a = new IU_GestorAlumno();
            //a.setVisible(true);
            
            Login log = new Login();
            log.setVisible(true);
            
        }catch(Exception e){
            System.out.println("Error al iniciar: "+e);
        }
        
        
    }
    
}
