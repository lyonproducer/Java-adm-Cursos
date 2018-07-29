/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Datos.Cursos;
import Datos.DALCurso;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LyonDj
 */
public class BLLCurso {
    
    DALCurso dalc = new DALCurso(); 
    
    public void mostrarListaTodos(DefaultTableModel model, JTable tabla){
      
        dalc.mostrarListaTodos(model, tabla); 
    } 
    
    public Object[] consultarEspecialidad(){
        
        return dalc.consultarEspecialidad();
    }
    
    public int consultarCantidadEspecialidad(){
        
        return dalc.consultarCantidadEspecialidad();
    }
    
    public Object[] consultarInstructor(String especialidad){
        
        return dalc.consultarInstructor(especialidad);
    }
    
    public int consultarCantidadInstructores(String especialidad){
        
        return dalc.consultarCantidadInstructores(especialidad);
        
    }
    
    public Object[] consultarHorasDisponibles(String dia){
        
        return dalc.consultarHorasDisponibles(dia);
    }
    
    public Object[] consultarAulasDisponibles(String dia, String hora){
        
        return dalc.consultarAulasDisponibles(dia,hora);
    }
    
    public Object []consultarIdInstructor(String nombre){
        
        return dalc.consultarIdInstructor(nombre);
    }
    
    public void insertarDatos(Cursos c) {
        
        dalc.insertarDatos(c);
        
    }
    
    public Object[] consultarPorID(int id){
        
        return dalc.consultarPorID(id);
        
    }
    
    public void modificarDatos(Cursos c){
        
        dalc.modificarDatos(c);
        
    }
    
    public void eliminarCurso(Cursos c){
        
        dalc.eliminarCurso(c);
        
        
    }
}
