/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;


import Datos.DALInstructor;
import Datos.Instructor;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LyonDj
 */
public class BLLInstructor {
    
    DALInstructor dali = new DALInstructor();
    
    public void mostrarListaTodos(DefaultTableModel model, JTable tabla){
        
        dali.mostrarListaTodos(model, tabla);
        
    }
    
        public void insertarDatos(Instructor i){
            
            dali.insertarDatos(i);
        }
        
        public Object[] consultarPorID(int id){
            
            return dali.consultarPorID(id);
        }
        
        public void modificarDatos(Instructor i){
            
            dali.modificarDatos(i);
            
        }
        
        public void eliminarInstructor(Instructor i){
            
            dali.eliminarInstructor(i);
        }
        
        public void buscarLista(DefaultTableModel model, JTable tabla, String dato){
            
            dali.buscarLista(model, tabla, dato);
        }
    
    
}
