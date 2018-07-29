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

/**
 *
 * @author LyonDj
 */
public class DALAdmin {
    
    Conexion con = Main.hc;
    
    String contra, usuario;
    
    public String BuscarContraseña(){
        
        try {
            
            String sql = "select contraseña from admin;";
            ResultSet rs = con.ejecutarSQLSelect(sql);
            
            //PreparedStatement ps= con.con.prepareStatement(sql);
            //ResultSet rs= ps.executeQuery();
            
            if(rs.next()){
            //datos[0] = rs.getString(1);
            contra = rs.getString(1);
            }
            
        } catch (Exception e) {
            System.out.println("Error al cargar contraseña: " + e);
        }
        
        
        //return datos[0].toString();
        return contra;
    }
    
    public boolean ExisteContraseña(){
        boolean existe = false;
        
        try {
            
            String sql = "select contraseña from admin;";
            ResultSet rs = con.ejecutarSQLSelect(sql);
            
            //PreparedStatement ps= con.con.prepareStatement(sql);
            //ResultSet rs= ps.executeQuery();
            
            if(rs.next()){
            //datos[0] = rs.getString(1);
            existe =true;
            }else
                existe=false;
            
        } catch (Exception e) {
            System.out.println("Error al leer contraseña" + e);
        }

        return existe;
    }
    
    
    public boolean ExisteUsuario(){
        boolean existe = false;
        
        try {
            
            String sql = "select usuario from admin;";
            ResultSet rs = con.ejecutarSQLSelect(sql);
            
            //PreparedStatement ps= con.con.prepareStatement(sql);
            //ResultSet rs= ps.executeQuery();
            
            if(rs.next()){
            //datos[0] = rs.getString(1);
            existe =true;
            }else
                existe=false;
            
        } catch (Exception e) {
            System.out.println("Error al leer contraseña" + e);
        }

        return existe;
    }
    public String BuscarUsuario(){
        
        try {
            
            String sql = "select usuario from admin;";
            ResultSet rs = con.ejecutarSQLSelect(sql);
            
            //PreparedStatement ps= con.con.prepareStatement(sql);
            //ResultSet rs= ps.executeQuery();
            
            if(rs.next()){
            //datos[0] = rs.getString(1);
            usuario = rs.getString(1);
            }
            
        } catch (Exception e) {
            System.out.println("Error al cargar contraseña: " + e);
        }
        
        
        //return datos[0].toString();
        return usuario;
    }
    
    public void insertarDatos(Admin a) {

        try {

            //String sql = "INSERT INTO usuario( dni, nombres, apellidos, correo, telefono, usuario, clave, fecha, foto)\n"
            //        + "    VALUES ( ?, ?, ?, ?, ?, ?, ?,'" + Date_a_String_fecha(u.getFecha()) + "', ?);";
            
            String sql = "INSERT INTO admin (usuario, contraseña) VALUES ( ?, ?);";
            
            
            PreparedStatement ps = con.con.prepareStatement(sql);
            ps.setString(1, a.usuario);
            ps.setString(2, a.getContra());
 
            boolean ejecucion = con.ejecutarSQL(ps);
            if (ejecucion == true) {

                JOptionPane.showMessageDialog(null, "Tu contraseña y usuario se guardo correctamente.");

            } else if (ejecucion == false) {

                JOptionPane.showMessageDialog(null, "Error al insertar login");
                //System.out.print("Error al insertar dialog");
            }

        } catch (Exception e) {

            System.out.print("Error al insertar login: " + e);

        }

    }
    
    
    public void updateDatos(String usuario, String contraseña) {

        try {

            //String sql = "INSERT INTO usuario( dni, nombres, apellidos, correo, telefono, usuario, clave, fecha, foto)\n"
            //        + "    VALUES ( ?, ?, ?, ?, ?, ?, ?,'" + Date_a_String_fecha(u.getFecha()) + "', ?);";
            
            String sql = "update admin set contraseña = ?, usuario = ?;";
            
            
            PreparedStatement ps = con.con.prepareStatement(sql);
            ps.setString(1, contraseña);
            ps.setString(2, usuario);
 
            boolean ejecucion = con.ejecutarSQL(ps);
            if (ejecucion == true) {

                JOptionPane.showMessageDialog(null, "Tu contraseña y usuario se guardo correctamente.");

            } else if (ejecucion == false) {

                JOptionPane.showMessageDialog(null, "Error al insertar login");
                //System.out.print("Error al insertar dialog");
            }

        } catch (Exception e) {

            System.out.print("Error al insertar login: " + e);

        }

    }
}
