/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Datos.DALAlumno;
import Datos.Alumno;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LyonDj
 */
public class BLLAlumno {
    //clase que se encarga de conectar la clase dal usuario con la clase cargar datos.
    //para asi llamarla en una sola.
    
    DALAlumno dal = new DALAlumno();
    
     public void mostrarListaInscritos(DefaultTableModel model, JTable tabla){
         dal.mostrarListaInscritos(model, tabla);
         
     }
     
     public void mostrarListaTodos(DefaultTableModel model, JTable tabla){
         dal.mostrarListaTodos(model, tabla);
         
     }
     
     public void insertarDatos(Alumno u){
         dal.insertarDatos(u);
     }
     
     
     public void modificarDatosSinFoto(Alumno u){
         dal.modificarDatosSinFoto(u);
     } 
     
     public void modificarDatosConFoto(Alumno u){
         dal.modificarDatosConFoto(u);
     } 
    
     
     public void buscarLista(DefaultTableModel model, JTable tabla, String dato){
         dal.buscarLista(model, tabla, dato);   
     } 
     
     public void buscarListaNoIncritos(DefaultTableModel model, JTable tabla, String dato){
         dal.buscarListaNoInscritos(model, tabla, dato);   
     } 
     
     public Object[] consultarPorID(int id, JLabel lblfoto){
         return dal.consultarPorID(id, lblfoto);
     }
     
     public void eliminarUsuario(Alumno u) {
         dal.eliminarUsuario(u);
     }
     
     public void InscribirUsuario(Alumno u) {
         dal.InscribirUsuario(u);
     }
     
     public int BuscarEstado(int id){
         return dal.BuscarEstado(id);
     }
     
     public int consultarCantidadEspecialidad(){
         return dal.consultarCantidadEspecialidad();
     }
     
     public Object[] consultarEspecialidad(){
         return dal.consultarEspecialidad();
     }
     
     public Object []consultarIdCurso(String curso){
         return dal.consultarIdCurso(curso);
     }
}
