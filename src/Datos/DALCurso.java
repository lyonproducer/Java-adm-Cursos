/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Presentacion.Main;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LyonDj
 */
public class DALCurso {
    
    Conexion con = Main.hc;
    CargarDatos c = new CargarDatos();
    
    
    public void mostrarListaTodos(DefaultTableModel model, JTable tabla) {

        //Funcion que muestra en el result set (fila) la informacion del sql
        try {
            
            //String sql = " select idusuario, dni, nombres , apellidos ,correo from "
            //        + "usuario order by nombres asc";
            
            String sql= "select idCurso, NombreCurso, dia, hora, Aula, (select NombreInstructor from instructor where idInstructor = id_Instructor) from curso;";
            
            //String sql = "select idusuario, dni, nombres , apellidos ,correo from usuario where estado=1 and (nombres like '%D%') order by nombres asc";
            ResultSet rs = con.ejecutarSQLSelect(sql);

            c.cargarTabla(6, rs, model, tabla);

        } catch (Exception e) {
            System.out.println("Error al cargar tabla DAL: " + e);
        }
    }
    
    public Object[] consultarEspecialidad(){
        int i=0,a=0;
        Object [] datos = new Object[100];
        
        try{
            
            //String sql=  "select  dni, nombres, apellidos, correo, telefono, usuario, clave, fecha, foto from usuario where idusuario= ? ";
            //String sql=  "select Especialidad from instructor;";
            //String sql=  "SELECT distinct Especialidad FROM instructor AS EI LEFT JOIN curso AS I ON Especialidad = NombreCurso WHERE I.NombreCurso IS NULL";
            String sql=  "SELECT distinct Especialidad FROM instructor LEFT JOIN curso AS I ON Especialidad = NombreCurso WHERE I.NombreCurso IS NULL AND EstadoInstructor=1;";
            ResultSet rs = con.ejecutarSQLSelect(sql);

            while (rs.next()){
                
                //datos[i] = rs.getString(i+1);
                //System.out.println("Cargando dato: " + datos[i].toString());
                for(i=0;i<1;i++){
                    
                    datos[a]=rs.getObject(i+1);
                    a++;
                    //System.out.println("Cargando dato: " + datos[i].toString());
                }
  
            }

        }catch(Exception e){
            System.out.println("Error al consultar: "+ e);
     
        }
        return datos;
    }
    
    public int consultarCantidadEspecialidad(){
        int i=0,a=0;
        
        try{
           
            //String sql=  "select distinct Especialidad from instructor";
            //String sql=  "SELECT distinct Especialidad FROM instructor AS EI LEFT JOIN curso AS I ON Especialidad = NombreCurso WHERE I.NombreCurso IS NULL";
            String sql=  "SELECT distinct Especialidad FROM instructor LEFT JOIN curso AS I ON Especialidad = NombreCurso WHERE I.NombreCurso IS NULL AND EstadoInstructor=1;";
            ResultSet rs = con.ejecutarSQLSelect(sql);
            
            
            while (rs.next()){
                
                for(i=0;i<1;i++){

                    a++;                    
                }
  
            }

        }catch(Exception e){
            System.out.println("Error al consultar: "+ e);
     
        }
        return a;
    }
    
        public Object[] consultarInstructor(String especialidad){
        int i=0,a=0;
        Object [] datos = new Object[100];
        
        try{
            
            //String sql=  "select  dni, nombres, apellidos, correo, telefono, usuario, clave, fecha, foto from usuario where idusuario= ? ";
            String sql=  "select NombreInstructor from instructor where Especialidad = '"+especialidad+"';";
            //PreparedStatement ps= con.con.prepareStatement(sql);
            //ps.setInt(1, id);
            //ResultSet rs= ps.executeQuery();
            ResultSet rs = con.ejecutarSQLSelect(sql);
            
            
            while (rs.next()){
                
                //datos[i] = rs.getString(i+1);
                //System.out.println("Cargando dato: " + datos[i].toString());
                for(i=0;i<1;i++){
                    
                    datos[a]=rs.getObject(i+1);
                    a++;
                    //System.out.println("Cargando dato: " + datos[i].toString());
                }
  
            }

        }catch(Exception e){
            System.out.println("Error al consultar: "+ e);
     
        }
        return datos;
    }
    
    public int consultarCantidadInstructores(String especialidad){
        int i=0,a=0;
        
        try{
           
            String sql=  "select NombreInstructor from instructor where Especialidad = '"+especialidad+"';";
            ResultSet rs = con.ejecutarSQLSelect(sql);

            while (rs.next()){
                
                for(i=0;i<1;i++){

                    a++;                    
                }
  
            }

        }catch(Exception e){
            System.out.println("Error al consultar: "+ e);
     
        }
        return a;
    }
    
    public Object[] consultarHorasDisponibles(String dia){
        int i=0,a=0;
        Object [] datos = new Object[100];
        
        try{
            
            //String sql=  "select  dni, nombres, apellidos, correo, telefono, usuario, clave, fecha, foto from usuario where idusuario= ? ";
            String sql=  "select hora from curso where dia = '"+dia+"';";
            ResultSet rs = con.ejecutarSQLSelect(sql);
            
            
            while (rs.next()){
                
                //datos[i] = rs.getString(i+1);
                //System.out.println("Cargando dato: " + datos[i].toString());
                for(i=0;i<1;i++){
                    
                    datos[a]=rs.getObject(i+1);
                    a++;
                    //System.out.println("Cargando dato: " + datos[i].toString());
                }
  
            }

        }catch(Exception e){
            System.out.println("Error al consultar: "+ e);
     
        }
        return datos;
    }
    
    public Object[] consultarAulasDisponibles(String dia, String hora){
        int i=0,a=0;
        Object [] datos = new Object[100];
        
        try{
            //String sql=  "select  dni, nombres, apellidos, correo, telefono, usuario, clave, fecha, foto from usuario where idusuario= ? ";
            //String sql=  "Select distinct Aula from curso;";
            String sql=  "Select distinct Aula from curso where dia = '"+dia+"' and hora = '"+hora+"';";
            ResultSet rs = con.ejecutarSQLSelect(sql);
            while (rs.next()){
                
                //datos[i] = rs.getString(i+1);
                //System.out.println("Cargando dato: " + datos[i].toString());
                for(i=0;i<1;i++){
                    
                    datos[a]=rs.getObject(i+1);
                    a++;
                    //System.out.println("Cargando dato: " + datos[a].toString());
                }
  
            }

        }catch(Exception e){
            System.out.println("Error al consultar: "+ e);
     
        }
        return datos;
    }
    
    public Object []consultarIdInstructor(String nombre){
        int i=0,a=0;
        Object [] datos = new Object[100];
        
        try{
            
            //String sql=  "select  dni, nombres, apellidos, correo, telefono, usuario, clave, fecha, foto from usuario where idusuario= ? ";
            String sql=  "select idInstructor from instructor where NombreInstructor = '"+nombre+"';";
            ResultSet rs = con.ejecutarSQLSelect(sql);
            
            if(rs.next()){
                
                datos[0] =rs.getObject(1);
                //System.out.println("Cargando dato: " + datos[0].toString());
                
            }
            
        }catch(Exception e){
            System.out.println("Error al consultar: "+ e);
     
        }
        return datos;
    }
    
    public void insertarDatos(Cursos c) {

        try {

            //String sql = "INSERT INTO usuario( dni, nombres, apellidos, correo, telefono, usuario, clave, fecha, foto)\n"
            //        + "    VALUES ( ?, ?, ?, ?, ?, ?, ?,'" + Date_a_String_fecha(u.getFecha()) + "', ?);";
            
            String sql = "INSERT INTO curso (NombreCurso, dia, hora , Aula, id_Instructor)\n" +
            "    VALUES  (?, ? , ? , ?, ?);";
            
            
            PreparedStatement ps = con.con.prepareStatement(sql);
            //ps.setInt(1, c.getIdInstructor());
            ps.setString(1, c.getNombreCurso());
            ps.setString(2, c.getDia());
            ps.setString(3, c.getHora());
            ps.setString(4, c.getAula());
            ps.setInt(5, c.getIdInstructor());
            
            boolean ejecucion = con.ejecutarSQL(ps);
            if (ejecucion == true) {

                JOptionPane.showMessageDialog(null, "Curso Correctamente Registrado");

            } else if (ejecucion == false) {

                JOptionPane.showMessageDialog(null, "Error al insertar Curso");
                System.out.print("Error al insertar dialog");
            }

        } catch (Exception e) {

            System.out.print("Error al insertar: " + e);

        }

    }
    
    public Object[] consultarPorID(int id){
       
        Object [] datos = new Object[5];
        
        try{
            
            //String sql=  "select  dni, nombres, apellidos, correo, telefono, usuario, clave, fecha, foto from usuario where idusuario= ? ";
            //String sql=  "select ciInstructor, NombreInstructor, ApellidoInstructor, CorreoInstructor, TlfInstructor, Especialidad from instructor where idInstructor= ? ";
            String sql=  "select NombreCurso, dia, hora, Aula, id_Instructor from curso where IdCurso= ?;";
            PreparedStatement ps= con.con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs= ps.executeQuery();
            
            while (rs.next()){
                datos[0] = rs.getString(1); //Nombre
                datos[1] = rs.getString(2); //dia
                datos[2] = rs.getString(3); //hora
                datos[3] = rs.getString(4); //aula
                datos[4] = rs.getString(5); //id_Instructor
            }
        }catch(Exception e){
            System.out.println("Error al consultar: "+ e);
     
        }
        return datos;
    }
    
    
    public void modificarDatos(Cursos c) {

        try {

            //String sql = "INSERT INTO usuario( dni, nombres, apellidos, correo, telefono, usuario, clave, fecha, foto)\n"
            //        + "    VALUES ( ?, ?, ?, ?, ?, ?, ?,'" + Date_a_String_fecha(u.getFecha()) + "', ?);";
            
            
            String sql = "UPDATE curso SET NombreCurso = ?,dia = ?,hora = ?,aula = ? WHERE idCurso = ?;";

            PreparedStatement ps = con.con.prepareStatement(sql);
            
            ps.setString(1, c.getNombreCurso());
            ps.setString(2, c.getDia());
            ps.setString(3, c.getHora());
            ps.setString(4, c.getAula());
            ps.setInt(5, c.getIdCurso());
            
            

            boolean ejecucion = con.ejecutarSQL(ps);
            
            if (ejecucion == true) {

                JOptionPane.showMessageDialog(null, "Usuario Correctamente Actualizado");

            } else if (ejecucion == false) {

                JOptionPane.showMessageDialog(null, "Error al modificar");
                System.out.print("Error al insertar dialog");
            }

        } catch (Exception e) {

            System.out.print("Error al insertar: " + e);

        }

    }
    
    public void eliminarCurso(Cursos c) {

        try {

            String sql = "delete from curso where idCurso= ?;";

            PreparedStatement ps = con.con.prepareStatement(sql);
            ps.setInt(1, c.getIdCurso());

            boolean ejecucion = con.ejecutarSQL(ps);
            if (ejecucion == true) {

                JOptionPane.showMessageDialog(null, "Curso Correctamente Eliminado");

            } else if (ejecucion == false) {

                JOptionPane.showMessageDialog(null, "Error al Eliminar");
                System.out.print("Error al insertar dialog");
            }

        } catch (Exception e) {

            System.out.print("Error al eliminar: " + e);

        }

    }
}
