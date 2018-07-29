package Control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LyonDj
 */

public class Validar {
 
    public void ValidarSoloLetras(JTextField campo){
        
        campo.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e){
            char c = e.getKeyChar();
            
            if (Character.isDigit(c)){//verifica si es numero
                e.consume();//No permite ingreso de numeros
                
                System.out.println("car: " + c);
            }
            }
    
        });

    }
    
    
    public void ValidarSoloNumeros(JTextField campo){
        
        campo.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e){
            char c = e.getKeyChar();
            
            if (!Character.isDigit(c)){//verifica si es numero
                e.consume();//No permite ingreso de numeros
                System.out.println("car: " + c);
            }
            }
    
        });

                }
    
    public void LimitarCaracteres(JTextField campo, int cantidad){
        
        campo.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e){
            char c = e.getKeyChar();
            int tam = campo.getText().length();
            
            if (tam>=cantidad){//verifica si es numero
                e.consume();//No permite ingreso de numeros
                System.out.println("car: " + c);
            }
            }
    
        });

                }
    
    public boolean ValidarFormatoCorreo(String correo) {

        Pattern pat = null;
        Matcher mat = null;
        pat = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");

        mat = pat.matcher(correo);

        if (mat.find()) {
            return true;

        } else {
            return false;
        }
    }
    
    public void centrar_datos(int columna, JTable tabla){
 
            DefaultTableCellRenderer modelocentrar = new DefaultTableCellRenderer();
            modelocentrar.setHorizontalAlignment(SwingConstants.CENTER);
            tabla.getColumnModel().getColumn(columna).setCellRenderer(modelocentrar); 
      }
    
}