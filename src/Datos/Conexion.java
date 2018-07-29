/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author LyonDj
 */
public class Conexion implements Serializable{
    
    public Connection con=null;
    
    public Conexion(){
        
        con=Conexion.realizaConexion();
    }

    public Connection getCon() {
        return con;
    }
    
    public static Connection realizaConexion() {
        
        Connection c=null;
        
        try {
            
            //Class.forName("org.postgresql.Driver");
            Class.forName("com.mysql.jdbc.Driver");
            //c=DriverManager.getConnection("jdbc:postgresql://localhost:5432/SomosSistemas",
            //        "postgres","leo123");
            
            c=DriverManager.getConnection("jdbc:mysql://localhost:3306/cursos",
                    "root","leo123");
            
            System.out.println("Conectado correctamente");
            
        }catch (SQLException e){
            System.out.println("error: " +e);
            
            
        } catch (ClassNotFoundException ex) {
            System.out.println("error2: " +ex);
           
        }       
                
              return c;  
        
    }
    
    public boolean ejecutarSQL(PreparedStatement sentencia){
        
        try{
            sentencia.execute();
            sentencia.close();
            return true;
            
        }catch(Exception e){
            System.out.println("error al ejecutar " +e);
            return false;
        }  
    }
    
    public ResultSet ejecutarSQLSelect(String sql){
        
        ResultSet resultado;
        
        try{
            
            PreparedStatement sentencia=con.prepareStatement(sql);
            resultado=sentencia.executeQuery();
            return resultado;
            
        }catch(SQLException e){
            System.out.println("error al ejecutar consulta " +e);
            return null;
        }
    }
    
    
    
    
    
    
    
    
}
