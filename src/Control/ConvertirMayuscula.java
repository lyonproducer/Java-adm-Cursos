/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author LyonDj
 */
public class ConvertirMayuscula extends PlainDocument{
    
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException{
    
        super.insertString(offset, str.toUpperCase(), attr);
        
    }
    
}
