/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LyonDj
 */
public class CargarDatos {
    
    //Funcion que carga la tabla con la informacion del result set por colummna y fila
    public void cargarTabla(int columnas, ResultSet rs, DefaultTableModel model, JTable tabla){
        
        try{
            Object[] filas = new Object[columnas];
            
            //Recorremos las filas
            while(rs.next()){
                //recorremos las columnas
                for(int i=0;i<columnas;i++){
                    
                    filas[i]=rs.getObject(i+1);
                    
                }
                model.addRow(filas);
                
            }
            tabla.updateUI();
            rs.close();
            
        }catch (Exception e){
            System.out.println("Error al cargar tabla: "+ e);
            
            
        }
        
    }
    
}
