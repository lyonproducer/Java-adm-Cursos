/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

import Control.BLLCurso;
import Control.Validar;
import Datos.Cursos;
import java.awt.Toolkit;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LyonDj
 */
public class IU_GestorCurso extends javax.swing.JFrame {

    Validar v = new Validar();
    DefaultTableModel modelo_tabla_Curso;
    
    boolean consultar = false;
    String CursoSeleccionado, DiaSeleccionado;
    int comboValidar=0;
    
    BLLCurso bll = new BLLCurso();
    
    int id =0;
    
    Cursos c = new Cursos();
    
    public IU_GestorCurso() {
        initComponents();
        
        this.setLocationRelativeTo(null);
        this.setTitle("Gestor de Cursos");
        setIcon();
        llenarComboCurso();
        
        
        modelo_tabla_Curso = new DefaultTableModel() {
            //para que las filas no sean editables
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }

        };
        
        iniciarTabla(modelo_tabla_Curso, TablaDatos);
        
        
    }
    
    
    public void llenarComboCurso(){
        
    jComboCurso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { }));
    Object [] datos= bll.consultarEspecialidad();

    for (int i=0; i<bll.consultarCantidadEspecialidad();i++ ){
        
        jComboCurso.addItem(datos[i].toString());
        System.out.println("Cargando dato: " + datos[i].toString());
    }
    
    }
    
   
    public void llenarComboHora(String dia){
        
        System.out.println("\n-------------------------------------------\n");
        jComboHora.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { }));
        Object [] datos=bll.consultarHorasDisponibles(dia);
        
        //jComboHora.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"7:00-9:00","9:00-11:00","13:00-15:00","15:00-17:00"}));
        
        String horas[]={"7:00-9:00","9:00-11:00","13:00-15:00","15:00-17:00"};
        int a=0;
        
        try {

        for (int i=0; i<4;i++){
            
            //System.out.println("Cargando dato: " + datos[i].toString());
            System.out.println("Cargando hora: " + horas[i]);
            
            if  (datos[a]!= null){
                
                if(horas[i].equals(datos[a])){
              
                System.out.println("La hora esta ocupada");
                a++;
                }else{
            
                    jComboHora.addItem(horas[i]);
                    System.out.println("La hora no esta ocupada y se añadio");            
                }               
            }else{
            
            jComboHora.addItem(horas[i]);
            System.out.println("La hora no esta ocupada y se añadio");           
            }     
        }
        
        }catch (Exception e){
            
            System.out.println("Error: "+ e);
        }
        
        llenarComboAula();
    }
    
    public void llenarComboAula(){
        
        System.out.println("\n-------------------------------------------\n");
        
        jComboAula.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { }));
        Object [] datos=bll.consultarAulasDisponibles(jComboDia.getSelectedItem().toString(),jComboHora.getSelectedItem().toString());
        
        //jComboHora.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"7:00-9:00","9:00-11:00","13:00-15:00","15:00-17:00"}));
        
        String aulas[]={"A-10","A-20","A-30","A-40", "A-50", "A-60"};
        int a=0;
        
        
        try {

        for (int i=0; i<6;i++){
            
            //System.out.println("Cargando dato: " + datos[i].toString());
            System.out.println("Cargando Aula: " + aulas[i]);
            
            if  (datos[a]!= null){
                
                if(aulas[i].equals(datos[a])){
              
                System.out.println("El aula esta ocupada");
                a++;
                }else{
            
                    jComboAula.addItem(aulas[i]);
                    System.out.println("El aula no esta ocupada y se añadio");            
                }               
            }else{
            
            jComboAula.addItem(aulas[i]);
            System.out.println("El aula no esta ocupada y se añadio");           
            }     
        }
        
        }catch (Exception e){
            
            System.out.println("Error: "+ e);
        }
        
        Registrar.setEnabled(true);
        
    }
    
    
    
    public void llenarComboInstructor(String especialidad){
        
        jComboInstructor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { }));
        Object [] datos= bll.consultarInstructor(especialidad);

        for (int i=0; i<bll.consultarCantidadInstructores(especialidad);i++ ){
        
            jComboInstructor.addItem(datos[i].toString());
            System.out.println("Cargando dato: " + datos[i].toString());
    }
        
        
    }
    
    public void iniciarTabla(DefaultTableModel model, JTable tabla){
        
        TablaDatos.setModel(modelo_tabla_Curso);
        modelo_tabla_Curso.addColumn("ID");
        modelo_tabla_Curso.addColumn("Curso");
        modelo_tabla_Curso.addColumn("Dia");
        modelo_tabla_Curso.addColumn("Hora");
        modelo_tabla_Curso.addColumn("Aula");
        modelo_tabla_Curso.addColumn("Instructor");
        
        bll.mostrarListaTodos(modelo_tabla_Curso, TablaDatos);

        //metodo para que las columnas sean fijas (no se muevan)
        TablaDatos.getTableHeader().setReorderingAllowed(false);
        TablaDatos.getColumnModel().getColumn(0).setMaxWidth(30);
        TablaDatos.getColumnModel().getColumn(0).setMinWidth(30);
        //TablaDatos.getColumnModel().getColumn(0).setPreferredWidth(30);
        
        //TablaDatos.getColumnModel().getColumn(1).setMinWidth(100);
        //TablaDatos.getColumnModel().getColumn(1).setMaxWidth(100);
        v.centrar_datos(0, TablaDatos);
        //jTableInstructor.getColumnModel().getColumn(1).setPreferredWidth(60);
    }
    
    public void actualizarTabla() {

        while (modelo_tabla_Curso.getRowCount() > 0) {
            modelo_tabla_Curso.removeRow(0);
        }
        
        bll.mostrarListaTodos(modelo_tabla_Curso, TablaDatos);   
    }
    
    public void botonGuardar(){

        String curso = jComboCurso.getSelectedItem().toString();
        String instructor = jComboInstructor.getSelectedItem().toString();
        String dia = jComboDia.getSelectedItem().toString();
        String hora = jComboHora.getSelectedItem().toString();
        String aula = jComboAula.getSelectedItem().toString();
        
        Object [] datos =bll.consultarIdInstructor(instructor);
        //int estado =0;
        
        //System.out.println("el id del profesor es: " + datos[0]);
        
        c.setNombreCurso(curso);
        c.setIdInstructor((int) datos[0]);
        c.setDia(dia);
        c.setAula(aula);
        c.setHora(hora);
        //c.setIdCurso((int)datos[0]);
        
        System.out.println("Nuevos Datos: ");
        System.out.println("Nombre: " + c.getNombreCurso());
        System.out.println("Dia: " + c.getDia());
        System.out.println("Hora: " + c.getHora());
        System.out.println("Aula: " + c.getAula());
        System.out.println("id Curso " + c.getIdCurso());
        
        //u.setEstado(estado);

        if (consultar == false) {
            //insertar
            bll.insertarDatos(c);
            limpiarCampos();

        } else if (consultar == true) {
            //modificar
            
            //System.out.println("Nuevos Datos: ");
            
            try{
                
            
            
            bll.modificarDatos(c);
            limpiarCampos();
            
            }catch(Exception e){
                
                System.out.println(e);
                
            }
            
            
            
        }
        
        actualizarTabla();
        
    }
    
    public void limpiarCampos(){
        System.out.println("\n-------------------------------------------------------------------\n");
        llenarComboCurso();
        jComboDia.removeAllItems();
        jComboHora.removeAllItems();
        jComboInstructor.removeAllItems();
        jComboAula.removeAllItems();
        Registrar.setEnabled(false);
        Eliminar.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jcMousePanel1 = new jcMousePanel.jcMousePanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaDatos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jComboCurso = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jComboInstructor = new javax.swing.JComboBox<>();
        Registrar = new javax.swing.JButton();
        Eliminar = new javax.swing.JButton();
        Nuevo = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jComboDia = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jComboHora = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jComboAula = new javax.swing.JComboBox<>();
        botonAtras = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jcMousePanel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Fondos/fondo Cursos.jpg"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(180, 243, 167));

        TablaDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        TablaDatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaDatosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TablaDatos);

        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Curso");

        jComboCurso.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboCursoItemStateChanged(evt);
            }
        });
        jComboCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboCursoActionPerformed(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Instructor");

        jComboInstructor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboInstructorItemStateChanged(evt);
            }
        });

        Registrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Botones/boton guardar 1.png"))); // NOI18N
        Registrar.setBorderPainted(false);
        Registrar.setContentAreaFilled(false);
        Registrar.setEnabled(false);
        Registrar.setFocusPainted(false);
        Registrar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Botones/boton guardar 2.png"))); // NOI18N
        Registrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegistrarActionPerformed(evt);
            }
        });

        Eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Botones/boton eliminar 1.png"))); // NOI18N
        Eliminar.setBorderPainted(false);
        Eliminar.setContentAreaFilled(false);
        Eliminar.setEnabled(false);
        Eliminar.setFocusPainted(false);
        Eliminar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Botones/boton eliminar 2.png"))); // NOI18N
        Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarActionPerformed(evt);
            }
        });

        Nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Botones/boton nuevo 1.png"))); // NOI18N
        Nuevo.setBorderPainted(false);
        Nuevo.setContentAreaFilled(false);
        Nuevo.setFocusPainted(false);
        Nuevo.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Botones/boton nuevo 2.png"))); // NOI18N
        Nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevoActionPerformed(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Dias");

        jComboDia.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboDiaItemStateChanged(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Horas");

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Aula:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Nuevo)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jComboDia, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboAula, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(63, 63, 63)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboInstructor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboHora, 0, 191, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Registrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Eliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboCurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboInstructor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboAula)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Registrar)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(Eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5))
                    .addComponent(Nuevo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        botonAtras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Botones/boton atras 1.png"))); // NOI18N
        botonAtras.setBorderPainted(false);
        botonAtras.setContentAreaFilled(false);
        botonAtras.setFocusPainted(false);
        botonAtras.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Botones/boton atras 2.png"))); // NOI18N
        botonAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAtrasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jcMousePanel1Layout = new javax.swing.GroupLayout(jcMousePanel1);
        jcMousePanel1.setLayout(jcMousePanel1Layout);
        jcMousePanel1Layout.setHorizontalGroup(
            jcMousePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jcMousePanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(botonAtras, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jcMousePanel1Layout.setVerticalGroup(
            jcMousePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jcMousePanel1Layout.createSequentialGroup()
                .addContainerGap(88, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(botonAtras, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jcMousePanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jcMousePanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAtrasActionPerformed
        
        IU_MainMenu m = new IU_MainMenu();
        m.setVisible(true);
        
        this.dispose();
    }//GEN-LAST:event_botonAtrasActionPerformed

    private void jComboCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboCursoActionPerformed
        
        //comboValidar =0;
        CursoSeleccionado=jComboCurso.getSelectedItem().toString();
         
        if(comboValidar>1){
           
        System.out.println(CursoSeleccionado);
        System.out.println(comboValidar);
        
        llenarComboInstructor(CursoSeleccionado);
        comboValidar= comboValidar-2;     
        jComboDia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"Lunes","Martes","Miercoles","Jueves","Viernes"}));
        
        if(consultar==true){
            
            Eliminar.setEnabled(true);
        }
        
        } 
        //System.out.println(jComboCurso.getSelectedItem().toString());
        comboValidar++;
        
    }//GEN-LAST:event_jComboCursoActionPerformed

    private void RegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegistrarActionPerformed
    
        botonGuardar();
        
        
    }//GEN-LAST:event_RegistrarActionPerformed

    private void jComboCursoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboCursoItemStateChanged
        
        /*
        //comboValidar =0;
        CursoSeleccionado=jComboCurso.getSelectedItem().toString();
         
        if(comboValidar>1){
           
            
        System.out.println(CursoSeleccionado);
        System.out.println(comboValidar);
        
        llenarComboInstructor(CursoSeleccionado);
        comboValidar= comboValidar-2;     
        jComboDia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"Lunes","Martes","Miercoles","Jueves","Viernes"}));
        
        } 
        //System.out.println(jComboCurso.getSelectedItem().toString());
        comboValidar++;
        */
        
    }//GEN-LAST:event_jComboCursoItemStateChanged

    private void jComboDiaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboDiaItemStateChanged
     
        try{ 
            
        DiaSeleccionado=jComboDia.getSelectedItem().toString();
        
        if(comboValidar>1){
           
        System.out.println("Seleccionado dia: " + DiaSeleccionado);
    
        llenarComboHora(DiaSeleccionado);
        comboValidar= comboValidar-2;     
   
        } 
         
        }catch(Exception e){
           //System.out.println("El error es: " + e);     
                
        }
        
        comboValidar++;
        
        
    }//GEN-LAST:event_jComboDiaItemStateChanged

    private void NuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuevoActionPerformed
        limpiarCampos();
        
    }//GEN-LAST:event_NuevoActionPerformed

    private void TablaDatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaDatosMouseClicked
        if(evt.getClickCount()==2){
            
            Eliminar.setEnabled(false);
            
            consultar=true;
            int fila=TablaDatos.getSelectedRow();
            id =(int) TablaDatos.getValueAt(fila,0);
            c.setIdCurso(id);
            Object [] datos= bll.consultarPorID(id);
            
            jComboCurso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { }));
            jComboCurso.addItem(datos[0].toString());

            //System.out.println("El estado del usuario es. " + datos[9]);
            //System.out.println("El estado del usuario es. " + bll.BuscarEstado(id));
            
        }
    }//GEN-LAST:event_TablaDatosMouseClicked

    private void jComboInstructorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboInstructorItemStateChanged
        if(consultar==true){
            Eliminar.setEnabled(true);
        }       
    }//GEN-LAST:event_jComboInstructorItemStateChanged

    public void botonEliminar(){
 
            System.out.println("EL id a eliminar es: " + c.getIdCurso());
            bll.eliminarCurso(c);
            actualizarTabla();
            limpiarCampos();

    }
    
    
    private void EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarActionPerformed
               botonEliminar();
    }//GEN-LAST:event_EliminarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(IU_GestorCurso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IU_GestorCurso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IU_GestorCurso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IU_GestorCurso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IU_GestorCurso().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Eliminar;
    private javax.swing.JButton Nuevo;
    private javax.swing.JButton Registrar;
    private javax.swing.JTable TablaDatos;
    private javax.swing.JButton botonAtras;
    private javax.swing.JComboBox<String> jComboAula;
    private javax.swing.JComboBox<String> jComboCurso;
    private javax.swing.JComboBox<String> jComboDia;
    private javax.swing.JComboBox<String> jComboHora;
    private javax.swing.JComboBox<String> jComboInstructor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private jcMousePanel.jcMousePanel jcMousePanel1;
    // End of variables declaration//GEN-END:variables

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Iconos/compose.png")));
    }
}
