/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

import Control.BLLInstructor;
import Control.ConvertirMayuscula;
import Control.Validar;
import Datos.Instructor;
import java.awt.Toolkit;
import java.util.Date;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LyonDj
 */
public class IU_GestorInstructor extends javax.swing.JFrame {

    Validar v = new Validar();
    DefaultTableModel modelo_tabla_Instructor;
    
    boolean consultar = false;
    
    BLLInstructor bll = new BLLInstructor();
    
    int id =0;
    
    Instructor i = new Instructor();
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public IU_GestorInstructor() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setTitle("Gestion de Instructores");
        metodosInicio();
        
        
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Iconos/compose.png")));
    
                
        modelo_tabla_Instructor = new DefaultTableModel() {
            //para que las filas no sean editables
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }

        };
        
        iniciarTabla(modelo_tabla_Instructor, jTableInstructor);
        
        }
    
    public void iniciarTabla(DefaultTableModel model, JTable tabla){
        
        jTableInstructor.setModel(modelo_tabla_Instructor);
        modelo_tabla_Instructor.addColumn("ID");
        modelo_tabla_Instructor.addColumn("Cedula");
        modelo_tabla_Instructor.addColumn("Nombre");
        modelo_tabla_Instructor.addColumn("Apellidos");
        modelo_tabla_Instructor.addColumn("Especialidad");

        bll.mostrarListaTodos(modelo_tabla_Instructor, jTableInstructor);

        //metodo para que las columnas sean fijas (no se muevan)
        jTableInstructor.getTableHeader().setReorderingAllowed(false);
        jTableInstructor.getColumnModel().getColumn(0).setMaxWidth(20);
        jTableInstructor.getColumnModel().getColumn(0).setMinWidth(20);
        //jTableInstructor.getColumnModel().getColumn(0).setPreferredWidth(0);
        
        jTableInstructor.getColumnModel().getColumn(1).setMinWidth(100);
        jTableInstructor.getColumnModel().getColumn(1).setMaxWidth(100);
        v.centrar_datos(1, jTableInstructor);
        v.centrar_datos(0, jTableInstructor);
        //jTableInstructor.getColumnModel().getColumn(1).setPreferredWidth(60);
    }
    
    
    public void metodosInicio() {

        v.ValidarSoloLetras(jTextNombre);
        v.ValidarSoloLetras(jTextApellido);
        v.ValidarSoloLetras(jTextEspecialidad);
        v.ValidarSoloNumeros(jTextCedula);
        v.ValidarSoloNumeros(jTextTelefono);
        v.LimitarCaracteres(jTextCedula, 8);
        v.LimitarCaracteres(jTextNombre, 30);
        v.LimitarCaracteres(jTextApellido, 30);
        v.LimitarCaracteres(jTextTelefono, 12);
        v.LimitarCaracteres(jTextEspecialidad, 30);
        v.LimitarCaracteres(jTextCorreo, 100);
        

        jTextNombre.setDocument(new ConvertirMayuscula());
        jTextApellido.setDocument(new ConvertirMayuscula());
        jTextBuscar.setDocument(new ConvertirMayuscula());
        jTextEspecialidad.setDocument(new ConvertirMayuscula());

    }
    
    public void BotonGuardar() {

        String ci = jTextCedula.getText().trim();
        String nombre = jTextNombre.getText().trim();
        String apellido = jTextApellido.getText().trim();
        String correo = jTextCorreo.getText().trim();
        String telefono = jTextTelefono.getText().trim();
        String especialidad = jTextEspecialidad.getText().trim();
       
        //int estado =0;
        
        i.setIdinstructor(id);
        i.setCi(ci);
        i.setNombres(nombre);
        i.setApellidos(apellido);
        i.setCorreo(correo);
        i.setTelefono(telefono);
        i.setEspecialidad(especialidad);
        //u.setEstado(estado);

        if (consultar == false) {
            //insertar
            bll.insertarDatos(i);
            limpiarCampos();

        } else if (consultar == true) {
            //modificar

            bll.modificarDatos(i);
            limpiarCampos();
            
        }

    }
    
    public void limpiarCampos() {

        jTextNombre.setText(null);
        jTextApellido.setText(null);
        jTextCedula.setText(null);
        jTextCorreo.setText(null);
        jTextTelefono.setText(null);
        jTextEspecialidad.setText(null);
        
        botonGuardar.setEnabled(false);
        botonEliminar.setEnabled(false);
        //botonInscribir.setEnabled(false);
        
        consultar = false;
        actualizarTabla();

    }
    
    public void actualizarTabla() {

        while (modelo_tabla_Instructor.getRowCount() > 0) {
            modelo_tabla_Instructor.removeRow(0);
        }
        
        bll.mostrarListaTodos(modelo_tabla_Instructor, jTableInstructor);   
    }
    
    
    public void ValidarIngreso() {

        String ci = jTextCedula.getText().trim();
        int tam = ci.length();
        String nom = jTextNombre.getText().trim();
        String apellido = jTextApellido.getText().trim();
        String telefono = jTextTelefono.getText().trim();
        String especialidad = jTextEspecialidad.getText();

        boolean estado = v.ValidarFormatoCorreo(jTextCorreo.getText());
        
        
        if ( tam != 8 || nom.isEmpty() || apellido.isEmpty()  || telefono.isEmpty() || estado == false || especialidad.isEmpty()){
            botonGuardar.setEnabled(false);
      
        }else botonGuardar.setEnabled(true);
        
        
        /*
        if (tam != 8 || nom.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || especialidad.isEmpty()
                || estado == false) {

            botonGuardar.setEnabled(false);

        } else {
            botonGuardar.setEnabled(true);
        }
        */
        
    }
    
    public void buscar() {
        
        String dato = jTextBuscar.getText();
        System.out.println("buscador dice " + dato);

        if (dato.isEmpty()) {
            actualizarTabla();
            System.out.println("no hay nada en el buscador");
            
        } else if (!dato.isEmpty()) {

            while (modelo_tabla_Instructor.getRowCount() > 0) {
                modelo_tabla_Instructor.removeRow(0);
            }
            
            bll.buscarLista(modelo_tabla_Instructor, jTableInstructor, dato);
          
    }
        
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
        Panel = new javax.swing.JTabbedPane();
        jPanelLista = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableInstructor = new javax.swing.JTable();
        jTextBuscar = new javax.swing.JTextField();
        jLabelBuscar = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanelGestor = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextCedula = new javax.swing.JTextField();
        botonNuevo = new javax.swing.JButton();
        botonGuardar = new javax.swing.JButton();
        botonEliminar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jTextCorreo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextTelefono = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextApellido = new javax.swing.JTextField();
        jTextNombre = new javax.swing.JTextField();
        jTextEspecialidad = new javax.swing.JTextField();
        botonSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(650, 500));

        jcMousePanel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Fondos/Instructor 2.jpg"))); // NOI18N

        Panel.setFocusable(false);
        Panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelMouseClicked(evt);
            }
        });
        Panel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                PanelPropertyChange(evt);
            }
        });

        jPanelLista.setBackground(new java.awt.Color(202, 221, 236));

        jScrollPane1.setBackground(new java.awt.Color(202, 221, 236));
        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseClicked(evt);
            }
        });

        jTableInstructor.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableInstructor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableInstructorMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableInstructor);

        jTextBuscar.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextBuscarCaretUpdate(evt);
            }
        });

        jLabelBuscar.setForeground(new java.awt.Color(0, 0, 0));
        jLabelBuscar.setText("Buscar");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("Nota: Para modificar un instructor, selecione en la tabla y realize doble click sobre el instructor que desea modificar y le abrira el paner gestor con toda la informacion cargada del instructor seleccionado.");
        jTextArea1.setAutoscrolls(false);
        jTextArea1.setOpaque(false);
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanelListaLayout = new javax.swing.GroupLayout(jPanelLista);
        jPanelLista.setLayout(jPanelListaLayout);
        jPanelListaLayout.setHorizontalGroup(
            jPanelListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelListaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanelListaLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabelBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 160, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanelListaLayout.setVerticalGroup(
            jPanelListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelListaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelBuscar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83))
        );

        Panel.addTab("Lista de Instructores", jPanelLista);

        jPanelGestor.setBackground(new java.awt.Color(202, 221, 236));
        jPanelGestor.setFocusable(false);
        jPanelGestor.setPreferredSize(new java.awt.Dimension(650, 359));

        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Nombres:");

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Apellidos:");

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Cedula:");

        jTextCedula.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextCedulaCaretUpdate(evt);
            }
        });

        botonNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Botones/boton nuevo 1.png"))); // NOI18N
        botonNuevo.setBorderPainted(false);
        botonNuevo.setContentAreaFilled(false);
        botonNuevo.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Botones/boton nuevo 2.png"))); // NOI18N
        botonNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonNuevoActionPerformed(evt);
            }
        });

        botonGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Botones/boton guardar 1.png"))); // NOI18N
        botonGuardar.setBorderPainted(false);
        botonGuardar.setContentAreaFilled(false);
        botonGuardar.setEnabled(false);
        botonGuardar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Botones/boton guardar 2.png"))); // NOI18N
        botonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGuardarActionPerformed(evt);
            }
        });

        botonEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Botones/boton eliminar 1.png"))); // NOI18N
        botonEliminar.setBorderPainted(false);
        botonEliminar.setContentAreaFilled(false);
        botonEliminar.setEnabled(false);
        botonEliminar.setFocusPainted(false);
        botonEliminar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Botones/boton eliminar 2.png"))); // NOI18N
        botonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarActionPerformed(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Especialidad:");

        jTextCorreo.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextCorreoCaretUpdate(evt);
            }
        });

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Correo:");

        jTextTelefono.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextTelefonoCaretUpdate(evt);
            }
        });

        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Telefono:");

        jTextApellido.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextApellidoCaretUpdate(evt);
            }
        });

        jTextNombre.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextNombreCaretUpdate(evt);
            }
        });

        jTextEspecialidad.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextEspecialidadCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout jPanelGestorLayout = new javax.swing.GroupLayout(jPanelGestor);
        jPanelGestor.setLayout(jPanelGestorLayout);
        jPanelGestorLayout.setHorizontalGroup(
            jPanelGestorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGestorLayout.createSequentialGroup()
                .addGroup(jPanelGestorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelGestorLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelGestorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelGestorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE))
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelGestorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextCedula)
                            .addComponent(jTextNombre)
                            .addComponent(jTextEspecialidad, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanelGestorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanelGestorLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(botonNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanelGestorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelGestorLayout.createSequentialGroup()
                        .addComponent(botonGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelGestorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jTextApellido, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                        .addComponent(jTextCorreo, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextTelefono, javax.swing.GroupLayout.Alignment.LEADING)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelGestorLayout.setVerticalGroup(
            jPanelGestorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGestorLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanelGestorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(jPanelGestorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanelGestorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addGroup(jPanelGestorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botonNuevo)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelGestorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(botonEliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonGuardar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(35, 35, 35))
        );

        Panel.addTab("Gestor", jPanelGestor);

        botonSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Botones/boton atras 1.png"))); // NOI18N
        botonSalir.setBorderPainted(false);
        botonSalir.setContentAreaFilled(false);
        botonSalir.setFocusPainted(false);
        botonSalir.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Botones/boton atras 2.png"))); // NOI18N
        botonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jcMousePanel1Layout = new javax.swing.GroupLayout(jcMousePanel1);
        jcMousePanel1.setLayout(jcMousePanel1Layout);
        jcMousePanel1Layout.setHorizontalGroup(
            jcMousePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jcMousePanel1Layout.createSequentialGroup()
                .addGap(0, 521, Short.MAX_VALUE)
                .addComponent(botonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jcMousePanel1Layout.setVerticalGroup(
            jcMousePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jcMousePanel1Layout.createSequentialGroup()
                .addContainerGap(87, Short.MAX_VALUE)
                .addComponent(Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(botonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jcMousePanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jcMousePanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalirActionPerformed
        IU_MainMenu Menu = new IU_MainMenu();
        Menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_botonSalirActionPerformed

    private void botonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGuardarActionPerformed
        BotonGuardar();
        
        
    }//GEN-LAST:event_botonGuardarActionPerformed

    private void jTextNombreCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextNombreCaretUpdate
        ValidarIngreso();
    }//GEN-LAST:event_jTextNombreCaretUpdate

    private void jTextApellidoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextApellidoCaretUpdate
        ValidarIngreso();
    }//GEN-LAST:event_jTextApellidoCaretUpdate

    private void jTextCedulaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextCedulaCaretUpdate
        ValidarIngreso();
    }//GEN-LAST:event_jTextCedulaCaretUpdate

    private void jTextCorreoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextCorreoCaretUpdate
        ValidarIngreso();
    }//GEN-LAST:event_jTextCorreoCaretUpdate

    private void jTextEspecialidadCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextEspecialidadCaretUpdate
        ValidarIngreso();
    }//GEN-LAST:event_jTextEspecialidadCaretUpdate

    private void jTextTelefonoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextTelefonoCaretUpdate
        ValidarIngreso();
    }//GEN-LAST:event_jTextTelefonoCaretUpdate

    private void botonNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNuevoActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_botonNuevoActionPerformed

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane1MouseClicked

    private void jTableInstructorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableInstructorMouseClicked
        if(evt.getClickCount()==2){
            
            botonEliminar.setEnabled(true);
            
            consultar=true;
            int fila=jTableInstructor.getSelectedRow();
            id =(int) jTableInstructor.getValueAt(fila,0);
            Object [] datos= bll.consultarPorID(id);

            jTextCedula.setText(datos[0].toString());
            jTextNombre.setText(datos[1].toString());
            jTextApellido.setText(datos[2].toString());
            jTextCorreo.setText(datos[3].toString());
            jTextTelefono.setText(datos[4].toString());
            jTextEspecialidad.setText(datos[5].toString());
            
            //System.out.println("El estado del usuario es. " + datos[9]);
            //System.out.println("El estado del usuario es. " + bll.BuscarEstado(id));

            Panel.setSelectedIndex(1);
            
            
        }
    }//GEN-LAST:event_jTableInstructorMouseClicked

    private void botonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(null,"¿Realmente desea eliminar este Usuario?");

        i.setIdinstructor(id);

        if (respuesta==0){
            bll.eliminarInstructor(i);
            limpiarCampos();
        }
    }//GEN-LAST:event_botonEliminarActionPerformed

    private void PanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelMouseClicked
      
    }//GEN-LAST:event_PanelMouseClicked

    private void PanelPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_PanelPropertyChange
        
    }//GEN-LAST:event_PanelPropertyChange

    private void jTextBuscarCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextBuscarCaretUpdate
        buscar();
    }//GEN-LAST:event_jTextBuscarCaretUpdate

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
            java.util.logging.Logger.getLogger(IU_GestorInstructor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IU_GestorInstructor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IU_GestorInstructor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IU_GestorInstructor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IU_GestorInstructor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane Panel;
    private javax.swing.JButton botonEliminar;
    private javax.swing.JButton botonGuardar;
    private javax.swing.JButton botonNuevo;
    private javax.swing.JButton botonSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelBuscar;
    private javax.swing.JPanel jPanelGestor;
    private javax.swing.JPanel jPanelLista;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableInstructor;
    private javax.swing.JTextField jTextApellido;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextBuscar;
    private javax.swing.JTextField jTextCedula;
    private javax.swing.JTextField jTextCorreo;
    private javax.swing.JTextField jTextEspecialidad;
    private javax.swing.JTextField jTextNombre;
    private javax.swing.JTextField jTextTelefono;
    private jcMousePanel.jcMousePanel jcMousePanel1;
    // End of variables declaration//GEN-END:variables


}
