/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;



import Presentacion.Main;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author LyonDj
 */
public class GenerarReportes {
    
    //Hacer reportes empleados productos platillos combos clientes y creo que factura
    
    
    /*
    public void ReporteEstudiantes(int estado){
    
    try {
    
    JasperReport reporte = (JasperReport) 
    
    JRLoader.loadObject("src/Reportes/EstudiantesReporte.jasper");
    
    Map parametro= new HashMap();

    parametro.put("estado", estado);
            
    JasperPrint j= JasperFillManager.fillReport(reporte, parametro, Main.hc.con);
    JasperViewer jv = new JasperViewer(j,false);
    
    jv.setTitle("Reporte Usuarios");
    jv.setVisible(true);
    
    }catch (Exception e){
        
        JOptionPane.showMessageDialog(null, "Error al mostrar el reporte "+e);
    
    
    }
            
    
    
    
    
    }
    
    */
    
    
    public void ReporteEstudiantes(){
    
    try {
    
    //JasperReport reporte = (JasperReport) JRLoader.loadObject("reporte1.jasper");
    //JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, null, conexion);
        
    JasperReport reporte = (JasperReport) 
    
    JRLoader.loadObject("src/Reportes/EstudiantesReporte.jasper");
    
    //Map parametro= new HashMap();

    //parametro.put("estado", estado);
    
    JasperPrint j= JasperFillManager.fillReport(reporte, null, Main.hc.con);
    JasperViewer jv = new JasperViewer(j,false);
    
    jv.setTitle("Reporte Usuarios");
    jv.setVisible(true);
    
    }catch (Exception e){
        
        JOptionPane.showMessageDialog(null, "Error al mostrar el reporte "+e);
    
    
    }
            
    
    
    
    
    }
    
    public void ReporteInstructor(){
    
    try {
    
    //JasperReport reporte = (JasperReport) JRLoader.loadObject("reporte1.jasper");
    //JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, null, conexion);
        
    JasperReport reporte = (JasperReport) 
    
    JRLoader.loadObject("src/Reportes/InstructorReporte.jasper");
    
    //Map parametro= new HashMap();

    //parametro.put("estado", estado);
    
    JasperPrint j= JasperFillManager.fillReport(reporte, null, Main.hc.con);
    JasperViewer jv = new JasperViewer(j,false);
    
    jv.setTitle("Reporte Instructores");
    jv.setVisible(true);
    
    }catch (Exception e){
        
        JOptionPane.showMessageDialog(null, "Error al mostrar el reporte "+e);
    
    
    }
    
    }
            
    public void ReporteCurso(){
    
    try {
    
    //JasperReport reporte = (JasperReport) JRLoader.loadObject("reporte1.jasper");
    //JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, null, conexion);
        
    JasperReport reporte = (JasperReport) 
    
    JRLoader.loadObject("src/Reportes/CursoReporte.jasper");
    
    //Map parametro= new HashMap();

    //parametro.put("estado", estado);
    
    JasperPrint j= JasperFillManager.fillReport(reporte, null, Main.hc.con);
    JasperViewer jv = new JasperViewer(j,false);
    
    jv.setTitle("Reporte Curso");
    jv.setVisible(true);
    
    }catch (Exception e){
        
        JOptionPane.showMessageDialog(null, "Error al mostrar el reporte "+e);
    
    
    }
    
    
    
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
