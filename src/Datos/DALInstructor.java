/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import static Datos.DALAlumno.Date_a_String_fecha;
import Presentacion.Main;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LyonDj
 */
public class DALInstructor {
    
    Conexion con = Main.hc;
    CargarDatos c = new CargarDatos();
    
    public void mostrarListaTodos(DefaultTableModel model, JTable tabla) {

        //Funcion que muestra en el result set (fila) la informacion del sql
        try {
            
            //String sql = " select idusuario, dni, nombres , apellidos ,correo from "
            //        + "usuario order by nombres asc";
            
            String sql = "select idInstructor, ciInstructor, NombreInstructor, ApellidoInstructor, Especialidad "
                    + "from instructor where EstadoInstructor=1 order by NombreInstructor asc;";
            
            ResultSet rs = con.ejecutarSQLSelect(sql);

            c.cargarTabla(5, rs, model, tabla);

        } catch (Exception e) {
            System.out.println("Error al cargar tabla DAL: " + e);
        }
    }
    
    public void insertarDatos(Instructor i) {

        try {

            //String sql = "INSERT INTO usuario( dni, nombres, apellidos, correo, telefono, usuario, clave, fecha, foto)\n"
            //        + "    VALUES ( ?, ?, ?, ?, ?, ?, ?,'" + Date_a_String_fecha(u.getFecha()) + "', ?);";
            
            String sql = "INSERT INTO instructor (ciInstructor, NombreInstructor, ApellidoInstructor, Especialidad, CorreoInstructor, TlfInstructor)\n" +
"           VALUES ( ?, ?, ?, ?, ?, ?);";
            
            
            PreparedStatement ps = con.con.prepareStatement(sql);
            ps.setString(1, i.getCi());
            ps.setString(2, i.getNombres());
            ps.setString(3, i.getApellidos());
            ps.setString(4, i.getEspecialidad());
            ps.setString(5, i.getCorreo());
            ps.setString(6, i.getTelefono());
            
            
            boolean ejecucion = con.ejecutarSQL(ps);
            if (ejecucion == true) {

                JOptionPane.showMessageDialog(null, "Instructor Correctamente Registrado");

            } else if (ejecucion == false) {

                JOptionPane.showMessageDialog(null, "Error al insertar Instructor");
                System.out.print("Error al insertar dialog");
            }

        } catch (Exception e) {

            System.out.print("Error al insertar: " + e);

        }

    }
    
    
    public Object[] consultarPorID(int id){
       
        Object [] datos = new Object[6];
        
        try{
            
            //String sql=  "select  dni, nombres, apellidos, correo, telefono, usuario, clave, fecha, foto from usuario where idusuario= ? ";
            String sql=  "select ciInstructor, NombreInstructor, ApellidoInstructor, CorreoInstructor, TlfInstructor, Especialidad from instructor where idInstructor= ? ";
            PreparedStatement ps= con.con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs= ps.executeQuery();
            
            while (rs.next()){
                datos[0] = rs.getString(1); //cedula
                datos[1] = rs.getString(2); //nombres
                datos[2] = rs.getString(3); //apellidos
                datos[3] = rs.getString(4); //correo
                datos[4] = rs.getString(5); //telefono
                datos[5] = rs.getString(6); //especialidad
                //datos[6] = rs.getInt(7);   //estado
   
            }
        }catch(Exception e){
            System.out.println("Error al consultar: "+ e);
     
        }
        return datos;
    }
    
    public void modificarDatos(Instructor i) {

        try {

            //String sql = "INSERT INTO usuario( dni, nombres, apellidos, correo, telefono, usuario, clave, fecha, foto)\n"
            //        + "    VALUES ( ?, ?, ?, ?, ?, ?, ?,'" + Date_a_String_fecha(u.getFecha()) + "', ?);";
            String sql = "UPDATE instructor SET ciInstructor = ?,NombreInstructor = ?,ApellidoInstructor = ?,CorreoInstructor = ?,\n"
                    + "TlfInstructor = ? , Especialidad =  ?\n"
                    + "  WHERE idInstructor = ?;";

            PreparedStatement ps = con.con.prepareStatement(sql);
            ps.setString(1, i.getCi());
            ps.setString(2, i.getNombres());
            ps.setString(3, i.getApellidos());
            ps.setString(4, i.getCorreo());
            ps.setString(5, i.getTelefono());
            ps.setString(6, i.getEspecialidad());
  
            ps.setInt(7, i.getIdinstructor());

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
    
    public void eliminarInstructor(Instructor i) {

        try {

            String sql = "UPDATE instructor SET EstadoInstructor=0 WHERE idInstructor = ?;";

            PreparedStatement ps = con.con.prepareStatement(sql);

            ps.setInt(1, i.getIdinstructor());

            boolean ejecucion = con.ejecutarSQL(ps);
            if (ejecucion == true) {

                JOptionPane.showMessageDialog(null, "Usuario Correctamente Eliminado");

            } else if (ejecucion == false) {

                JOptionPane.showMessageDialog(null, "Error al Eliminar");
                System.out.print("Error al insertar dialog");
            }

        } catch (Exception e) {

            System.out.print("Error al insertar: " + e);

        }

    }
    
    public void buscarLista(DefaultTableModel model, JTable tabla, String dato) {

        //Funcion que muestra en el result set (fila) la informacion del sql
        try {
            
            String sql = "select idInstructor, ciInstructor, NombreInstructor, ApellidoInstructor, Especialidad from instructor where EstadoInstructor=1 and (NombreInstructor like '%"+dato+"%' or ApellidoInstructor like '%"+dato+"%') order by NombreInstructor asc";

            //String sql = "select idusuario, dni, nombres , apellidos ,correo from usuario where estado=1 and (nombres like '%"+dato+"%' or apellidos like '%"+dato+"%') order by nombres asc";
            ResultSet rs = con.ejecutarSQLSelect(sql);

            c.cargarTabla(5, rs, model, tabla);

        } catch (Exception e) {
            System.out.println("Error al cargar tabla DAL: " + e);
        }
    }
    
    
}
