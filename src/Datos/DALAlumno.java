/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Presentacion.Main;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class DALAlumno {

    Conexion con = Main.hc;
    CargarDatos c = new CargarDatos();

    public void mostrarListaInscritos(DefaultTableModel model, JTable tabla) {

        //Funcion que muestra en el result set (fila) la informacion del sql
        try {
            
            //String sql = " select idusuario, dni, nombres , apellidos ,correo from "
            //        + "usuario where estado=1 order by nombres asc";
            
            String sql = "select idalumno, ciAlumno, Nombre, Apellido, (select NombreCurso from curso where idCurso = id_Curso) from alumno where Estado=1 order by Nombre asc;";

            //String sql = "select idusuario, dni, nombres , apellidos ,correo from usuario where estado=1 and (nombres like '%D%') order by nombres asc";
            ResultSet rs = con.ejecutarSQLSelect(sql);

            c.cargarTabla(5, rs, model, tabla);

        } catch (Exception e) {
            System.out.println("Error al cargar tabla DAL: " + e);
        }
    }
    
    public void mostrarListaTodos(DefaultTableModel model, JTable tabla) {

        //Funcion que muestra en el result set (fila) la informacion del sql
        try {
            
            String sql = "select idalumno, ciAlumno, Nombre, Apellido, (select NombreCurso from curso where idCurso = id_Curso) from alumno order by Nombre asc;";

            
            //String sql = "select idusuario, dni, nombres , apellidos ,correo from usuario where estado=1 and (nombres like '%D%') order by nombres asc";
            ResultSet rs = con.ejecutarSQLSelect(sql);

            c.cargarTabla(5, rs, model, tabla);

        } catch (Exception e) {
            System.out.println("Error al cargar tabla DAL: " + e);
        }
    }
    
    public int consultarCantidadEspecialidad(){
        int i=0,a=0;
        
        try{
           
            //String sql=  "select distinct Especialidad from instructor";
            //String sql=  "SELECT distinct Especialidad FROM instructor AS EI LEFT JOIN curso AS I ON Especialidad = NombreCurso WHERE I.NombreCurso IS NULL";
            String sql=  "SELECT NombreCurso from curso where EstadoCurso=1 ;";
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
    
    public Object[] consultarEspecialidad(){
        int i=0,a=0;
        Object [] datos = new Object[100];
        
        try{

            String sql= "SELECT NombreCurso from curso where EstadoCurso=1 ;";
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

    public void buscarLista(DefaultTableModel model, JTable tabla, String dato) {

        //Funcion que muestra en el result set (fila) la informacion del sql
        try {
            
            String sql = "select idalumno, ciAlumno, Nombre , Apellido ,(select NombreCurso from curso where idCurso = id_Curso) from alumno where Estado=1 and (Nombre like '%"+dato+"%' or Apellido like '%"+dato+"%') order by Nombre asc";

            //String sql = "select idusuario, dni, nombres , apellidos ,correo from usuario where estado=1 and (nombres like '%"+dato+"%' or apellidos like '%"+dato+"%') order by nombres asc";
            ResultSet rs = con.ejecutarSQLSelect(sql);

            c.cargarTabla(5, rs, model, tabla);

        } catch (Exception e) {
            System.out.println("Error al cargar tabla DAL: " + e);
        }
    }
    
    public void buscarListaNoInscritos(DefaultTableModel model, JTable tabla, String dato) {

        //Funcion que muestra en el result set (fila) la informacion del sql
        try {
            
            String sql = "select idalumno, ciAlumno, Nombre , Apellido ,(select NombreCurso from curso where idCurso = id_Curso) from alumno where (Nombre like '%"+dato+"%' or Apellido like '%"+dato+"%') order by Nombre asc;";

            //String sql = "select idusuario, dni, nombres , apellidos ,correo from usuario where estado=1 and (nombres like '%"+dato+"%' or apellidos like '%"+dato+"%') order by nombres asc";
            ResultSet rs = con.ejecutarSQLSelect(sql);

            c.cargarTabla(5, rs, model, tabla);

        } catch (Exception e) {
            System.out.println("Error al cargar tabla DAL: " + e);
        }
    }

    public static String Date_a_String_fecha(Date fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fec_string = sdf.format(fecha);
        return fec_string;
    }

    public void insertarDatos(Alumno u) {

        try {

            //String sql = "INSERT INTO usuario( dni, nombres, apellidos, correo, telefono, usuario, clave, fecha, foto)\n"
            //        + "    VALUES ( ?, ?, ?, ?, ?, ?, ?,'" + Date_a_String_fecha(u.getFecha()) + "', ?);";
            
            String sql = "INSERT INTO alumno (ciAlumno, Nombre, Apellido, Correo, Telefono, FechaNacimiento, foto, id_Curso)\n"
            + "VALUES ( ?, ?, ? , ?, ?,'" + Date_a_String_fecha(u.getFecha()) +"', ?, ?)";

            PreparedStatement ps = con.con.prepareStatement(sql);
            ps.setString(1, u.getDni());
            ps.setString(2, u.getNombres());
            ps.setString(3, u.getApellidos());
            ps.setString(4, u.getCorreo());
            ps.setString(5, u.getTelefono());
            ps.setBinaryStream(6, u.getFis(), u.getLongitudBytes());
            ps.setInt(7, u.getId_Curso());
            boolean ejecucion = con.ejecutarSQL(ps);
            if (ejecucion == true) {

                JOptionPane.showMessageDialog(null, "Usuario Correctamente Registrado");

            } else if (ejecucion == false) {

                JOptionPane.showMessageDialog(null, "Error al insertar Usuario");
                System.out.print("Error al insertar dialog");
            }

        } catch (Exception e) {

            System.out.print("Error al insertar: " + e);

        }

    }

    public void modificarDatosSinFoto(Alumno u) {

        try {

            //String sql = "INSERT INTO usuario( dni, nombres, apellidos, correo, telefono, usuario, clave, fecha, foto)\n"
            //        + "    VALUES ( ?, ?, ?, ?, ?, ?, ?,'" + Date_a_String_fecha(u.getFecha()) + "', ?);";
            String sql = "UPDATE alumno SET ciAlumno = ?,Nombre = ?,Apellido = ?,Telefono = ?,"
                    + "Correo = ?, FechaNacimiento ='"+Date_a_String_fecha(u.getFecha())+"', id_Curso = ? WHERE idalumno = ?;";

            PreparedStatement ps = con.con.prepareStatement(sql);
            ps.setString(1, u.getDni());
            ps.setString(2, u.getNombres());
            ps.setString(3, u.getApellidos());
            ps.setString(4, u.getTelefono());
            ps.setString(5, u.getCorreo());
            ps.setInt(6, u.getId_Curso());
         
            //ps.setBinaryStream(8, u.getFis(), u.getLongitudBytes());

            ps.setInt(7, u.getIdalumno());

            boolean ejecucion = con.ejecutarSQL(ps);
            if (ejecucion == true) {

                JOptionPane.showMessageDialog(null, "Usuario Correctamente Actualizado");

            } else if (ejecucion == false) {

                JOptionPane.showMessageDialog(null, "Error al Modificar");
                System.out.print("Error al insertar dialog");
            }

        } catch (Exception e) {

            System.out.print("Error al insertar: " + e);

        }

    }

    public void modificarDatosConFoto(Alumno u) {

        try {

            //String sql = "INSERT INTO usuario( dni, nombres, apellidos, correo, telefono, usuario, clave, fecha, foto)\n"
            //        + "    VALUES ( ?, ?, ?, ?, ?, ?, ?,'" + Date_a_String_fecha(u.getFecha()) + "', ?);";
            //String sql = "UPDATE usuario SET dni = ?,nombres = ?,apellidos = ?,correo = ?,\n"
            //        + "telefono = ?,usuario =  ?,clave =  ?,\n"
            //        + "foto= ?,fecha = '" + Date_a_String_fecha(u.getFecha()) + "'  WHERE idusuario = ?;";
            
            String sql = "UPDATE alumno SET ciAlumno = ?,Nombre = ?,Apellido = ?,Telefono = ?,"
                    + "Correo = ?, FechaNacimiento ='"+Date_a_String_fecha(u.getFecha())+"', foto=? ,id_Curso = ? WHERE idalumno = ?;";

            PreparedStatement ps = con.con.prepareStatement(sql);
            ps.setString(1, u.getDni());
            ps.setString(2, u.getNombres());
            ps.setString(3, u.getApellidos());
            ps.setString(4, u.getTelefono());
            ps.setString(5, u.getCorreo());
            ps.setBinaryStream(6, u.getFis(), u.getLongitudBytes());
            ps.setInt(7, u.getId_Curso());
            ps.setInt(8, u.getIdalumno());

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
    
    public Object[] consultarPorID(int id, JLabel lblfoto){
       
        Object [] datos = new Object[10]; 
        ImageIcon foto;
        InputStream is;
        
        try{
            
            //String sql=  "select  dni, nombres, apellidos, correo, telefono, usuario, clave, fecha, foto from usuario where idusuario= ? ";
            //String sql=  "select  dni, nombres, apellidos, correo, telefono, usuario, clave, fecha, foto, estado from usuario where idusuario= ? ";
            String sql=  "select ciAlumno, Nombre, Apellido, Correo, Telefono, FechaNacimiento, Foto, Estado from alumno where idalumno= ? ";
            PreparedStatement ps= con.con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs= ps.executeQuery();
            
            
            while (rs.next()){
                datos[0] = rs.getString(1); //dni
                datos[1] = rs.getString(2); //nombres
                datos[2] = rs.getString(3); //apellidos
                datos[3] = rs.getString(4); //correo
                datos[4] = rs.getString(5); //telefono
                datos[5] = rs.getDate(6); //fecha
                is = rs.getBinaryStream(7);
                datos[7] = rs.getInt(8);
                
                BufferedImage bi = ImageIO.read(is);
                foto = new ImageIcon(bi);
                Image img = foto.getImage();
                Image newimg = img.getScaledInstance(lblfoto.getWidth() ,lblfoto.getHeight(), java.awt.Image.SCALE_SMOOTH);
                
                ImageIcon newicon = new ImageIcon(newimg);
                datos[6] = newicon;
            }
        }catch(Exception e){
            System.out.println("Error al consultar: "+ e);
     
        }
        return datos;
    }

    
    public void eliminarUsuario(Alumno u) {

        try {

            String sql = "UPDATE alumno SET Estado=0 WHERE idalumno = ?;";

            PreparedStatement ps = con.con.prepareStatement(sql);

            ps.setInt(1, u.getIdalumno());

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
    
    
    public void InscribirUsuario(Alumno u) {

        try {

            String sql = "UPDATE alumno SET Estado=1 WHERE idalumno = ?;";

            PreparedStatement ps = con.con.prepareStatement(sql);

            ps.setInt(1, u.getIdalumno());

            boolean ejecucion = con.ejecutarSQL(ps);
            if (ejecucion == true) {

                JOptionPane.showMessageDialog(null, "Participante Correctamente Inscrito");

            } else if (ejecucion == false) {

                JOptionPane.showMessageDialog(null, "Error al Inscribir");
                //System.out.print("Error al insertar dialog");
            }

        } catch (Exception e) {

            System.out.print("Error al insertar (inscribir): " + e);

        }

    }
    
    public int BuscarEstado(int id){
        
        int estado=6;
        try {
            
            String sql = "select Estado from alumno WHERE idalumno = ?;";
            
            PreparedStatement ps= con.con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs= ps.executeQuery();
            
            
            /*
            String sql = "select estado from usuario WHERE idusuario = 10;";
            ResultSet rs = con.ejecutarSQLSelect(sql);
            */
            
            
            if(rs.next()){
            //datos[0] = rs.getString(1);
            //estado = Integer.valueOf(rs.getString(1));
            //estado = rs.getInt(1);
            estado = rs.getInt(1);
            }
            
        } catch (Exception e) {
            System.out.println("Error al cargar contraseña: " + e);
        }
        
        
        //return datos[0].toString();
        return estado;
    }
    
    
    public Object []consultarIdCurso(String curso){
        int i=0,a=0;
        Object [] datos = new Object[100];
        
        try{
            
            //String sql=  "select  dni, nombres, apellidos, correo, telefono, usuario, clave, fecha, foto from usuario where idusuario= ? ";
            String sql=  "select idCurso from curso where NombreCurso = '"+curso+"';";
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
}
