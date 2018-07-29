/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

import Control.BLLAlumno;
import Control.ConvertirMayuscula;
import Control.Validar;
import Datos.Alumno;
import Reportes.GenerarReportes;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LyonDj
 */
public class IU_GestorAlumno extends javax.swing.JFrame {

    Validar v = new Validar();
    DefaultTableModel modelo_tabla;

    //bll usuario conecta la clase Dal Usuario con la clase Cargar datos.
    BLLAlumno bll = new BLLAlumno();

    FileInputStream fis;
    int longitudBytes, apretafoto = 0,id =0;
    boolean consultar = false;

    Alumno u = new Alumno();

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public IU_GestorAlumno() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setTitle("Gestion de Alumnos");
        metodosInicio();
        setIcon();

        //configurar tabla
        modelo_tabla = new DefaultTableModel() {
            //para que las filas no sean editables
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }

        };
        
        iniciarTabla(modelo_tabla, TablaDatos);
        
        /*
        //añade configuracion a la tabla.
        TablaDatos.setModel(modelo_tabla);
        modelo_tabla.addColumn("ID");
        modelo_tabla.addColumn("Cedula");
        modelo_tabla.addColumn("Nombre");
        modelo_tabla.addColumn("Apellidos");
        modelo_tabla.addColumn("Correo");
        
        
        bll.mostrarListaTodos(modelo_tabla, TablaDatos);

        //metodo para que las columnas sean fijas (no se muevan)
        TablaDatos.getTableHeader().setReorderingAllowed(false);
        */
    }
    
    public void llenarComboCurso(){
        
    jComboCurso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { }));
    Object [] datos= bll.consultarEspecialidad();

    for (int i=0; i<bll.consultarCantidadEspecialidad();i++ ){
        
        jComboCurso.addItem(datos[i].toString());
        System.out.println("Cargando dato: " + datos[i].toString());
    }
    
    }
    
    public void iniciarTabla(DefaultTableModel model, JTable tabla){
        
        TablaDatos.setModel(modelo_tabla);
        modelo_tabla.addColumn("ID");
        modelo_tabla.addColumn("Cedula");
        modelo_tabla.addColumn("Nombre");
        modelo_tabla.addColumn("Apellidos");
        modelo_tabla.addColumn("Curso");

        bll.mostrarListaTodos(modelo_tabla, TablaDatos);

        //metodo para que las columnas sean fijas (no se muevan)
        TablaDatos.getTableHeader().setReorderingAllowed(false);
        TablaDatos.getColumnModel().getColumn(0).setMaxWidth(10);
        TablaDatos.getColumnModel().getColumn(0).setMinWidth(10);
        //jTableInstructor.getColumnModel().getColumn(0).setPreferredWidth(0);
        
        TablaDatos.getColumnModel().getColumn(1).setMinWidth(100);
        TablaDatos.getColumnModel().getColumn(1).setMaxWidth(100);
        v.centrar_datos(1, TablaDatos);
        //jTableInstructor.getColumnModel().getColumn(1).setPreferredWidth(60);
    }
    
    
    
    
    public void metodosInicio() {

        v.ValidarSoloLetras(txtnombre);
        v.ValidarSoloLetras(txtapellido);
        v.ValidarSoloNumeros(txtdni);
        v.ValidarSoloNumeros(txttelefono);
        v.LimitarCaracteres(txtdni, 8);
        v.LimitarCaracteres(txtnombre, 100);
        v.LimitarCaracteres(txtapellido, 100);
        v.LimitarCaracteres(txttelefono, 20);
        v.LimitarCaracteres(txtcorreo, 100);
        
        txtnombre.setDocument(new ConvertirMayuscula());
        txtapellido.setDocument(new ConvertirMayuscula());
        txtbuscar.setDocument(new ConvertirMayuscula());
        llenarComboCurso();

    }

    /*
    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)(\\.[A-Za-z]{2,})$";

    public boolean m_validarEmail(String email) {
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }
    
    */
    
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

    public void ValidarIngreso() {

        String dni = txtdni.getText().trim();
        int tam = dni.length();
        String nom = txtnombre.getText().trim();
        String apellido = txtapellido.getText().trim();
        String telefono = txttelefono.getText().trim();
        

        Date fecha = jDateFecha.getDate();

        boolean estado = ValidarFormatoCorreo(txtcorreo.getText());
        //boolean estado = m_validarEmail(txtcorreo.getText());
        
        if (tam != 8 || nom.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || fecha == null || estado == false) {

            Guardar.setEnabled(false);

        } else {
            Guardar.setEnabled(true);
        }

    }

    public void CargarFoto() {

        JFileChooser j = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("JPG & PNG", "jpg", "png");
        j.setFileFilter(filtro);

        int estado = j.showOpenDialog(null);

        if (estado == JFileChooser.APPROVE_OPTION) {

            try {
                fis = new FileInputStream(j.getSelectedFile());
                this.longitudBytes = (int) j.getSelectedFile().length();

                try {

                    Foto.setIcon(null);
                    Image icono = ImageIO.read(j.getSelectedFile()).getScaledInstance(Foto.getWidth(),
                            Foto.getHeight(), Foto.getWidth());

                    Foto.setIcon(new ImageIcon(icono));
                    Foto.updateUI();
                    //apreta foto dice si se ah subido la foto.
                    apretafoto = 1;
                    System.out.println("Longitud de Bytes: " + longitudBytes);

                } catch (IOException e) {

                    System.out.println("Error al cargar foto IO: " + e);
                }

            } catch (FileNotFoundException e) {
                System.out.println("Error al cargar file: " + e);
            }
        }

    }

    public void BotonGuardar() {

        String ci = txtdni.getText().trim();
        String nombre = txtnombre.getText().trim();
        String apellido = txtapellido.getText().trim();
        String correo = txtcorreo.getText().trim();
        String telefono = txttelefono.getText().trim();
        String curso = jComboCurso.getSelectedItem().toString();
        Date fecha = jDateFecha.getDate();
        
        Object [] datos =bll.consultarIdCurso(curso);
       
        //int estado =0;
        
        u.setIdalumno(id);
        u.setDni(ci);
        u.setNombres(nombre);
        u.setApellidos(apellido);
        u.setCorreo(correo);
        u.setTelefono(telefono);
        u.setCurso(curso);
        u.setFecha(fecha);
        u.setFis(fis);
        u.setLongitudBytes(longitudBytes);
        u.setId_Curso((int) datos[0]);
        //u.setEstado(estado);

        if (consultar == false) {
            //insertar
            bll.insertarDatos(u);
            limpiarCampos();

        } else if (consultar == true) {
            //modificar

            if (apretafoto == 0) {

                bll.modificarDatosSinFoto(u);

            } else if (apretafoto == 1) {
                bll.modificarDatosConFoto(u);

            }
            limpiarCampos();
        }

    }

    public void actualizarTabla() {

        while (modelo_tabla.getRowCount() > 0) {
            modelo_tabla.removeRow(0);
        }
        
        if(jRadioInscritos.isSelected()==true){
            bll.mostrarListaInscritos(modelo_tabla, TablaDatos);  
        }else
            bll.mostrarListaTodos(modelo_tabla, TablaDatos);
        
    }

    public void limpiarCampos() {

        txtdni.setText(null);
        txtnombre.setText(null);
        txtapellido.setText(null);
        txtcorreo.setText(null);
        txttelefono.setText(null);
        
        
        jDateFecha.setDate(null);
        
        Guardar.setEnabled(false);
        Eliminar.setEnabled(false);
        botonInscribir.setEnabled(false);
        
        consultar = false;
        apretafoto = 0;
        actualizarTabla();

        Foto.setIcon(null);

    }

    public void buscar() {

        String dato = txtbuscar.getText();
        System.out.println("buscador dice " + dato);

        if (dato.isEmpty()) {
            actualizarTabla();
            System.out.println("no hay nada en el buscador");
            
        } else if (!dato.isEmpty()) {

            while (modelo_tabla.getRowCount() > 0) {
                modelo_tabla.removeRow(0);
            }
            
            if(jRadioInscritos.isSelected()==true){
            bll.buscarLista(modelo_tabla, TablaDatos, dato);  
        }else
            bll.buscarListaNoIncritos(modelo_tabla, TablaDatos, dato);
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

        MousePanel = new jcMousePanel.jcMousePanel();
        panel = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaDatos = new javax.swing.JTable();
        labelBuscar = new javax.swing.JLabel();
        txtbuscar = new javax.swing.JTextField();
        jRadioInscritos = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        Foto = new javax.swing.JLabel();
        Subirfoto = new javax.swing.JButton();
        txtnombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtapellido = new javax.swing.JTextField();
        txtdni = new javax.swing.JTextField();
        txttelefono = new javax.swing.JTextField();
        txtcorreo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jDateFecha = new com.toedter.calendar.JDateChooser();
        Guardar = new javax.swing.JButton();
        Eliminar = new javax.swing.JButton();
        Nuevo = new javax.swing.JButton();
        botonInscribir = new javax.swing.JButton();
        jComboCurso = new javax.swing.JComboBox<>();
        botonAtras = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(650, 500));

        MousePanel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Fondos/fondo Alumnos.jpg"))); // NOI18N
        MousePanel.setPreferredSize(new java.awt.Dimension(650, 500));

        panel.setBackground(new java.awt.Color(243, 232, 156));
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelMouseClicked(evt);
            }
        });
        panel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                panelPropertyChange(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(243, 232, 156));

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
        TablaDatos.setOpaque(false);
        TablaDatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaDatosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TablaDatos);

        labelBuscar.setText("Buscar");

        txtbuscar.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtbuscarCaretUpdate(evt);
            }
        });

        jRadioInscritos.setText("Solo Inscritos");
        jRadioInscritos.setContentAreaFilled(false);
        jRadioInscritos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioInscritosActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("Nota: Para modificar un alumno, selecione en la tabla y realize doble click sobre el alumno que desea modificar y le abrira el paner gestor con toda la informacion cargada del alumno seleccionado.");
        jTextArea1.setOpaque(false);
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(labelBuscar)
                .addGap(36, 36, 36)
                .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jRadioInscritos)
                .addContainerGap(105, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addGap(20, 20, 20))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelBuscar)
                    .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioInscritos))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panel.addTab("Lista de Participantes", jPanel2);

        jPanel1.setBackground(new java.awt.Color(243, 232, 156));

        Foto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 255, 204)));

        Subirfoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Persona.png"))); // NOI18N
        Subirfoto.setText("Subir Foto");
        Subirfoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubirfotoActionPerformed(evt);
            }
        });

        txtnombre.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtnombreCaretUpdate(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Apellidos");

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Telefono");

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Correo");

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Curso");

        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("C.I");

        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Nombres");

        txtapellido.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtapellidoCaretUpdate(evt);
            }
        });

        txtdni.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtdniCaretUpdate(evt);
            }
        });
        txtdni.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtdniPropertyChange(evt);
            }
        });

        txttelefono.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txttelefonoCaretUpdate(evt);
            }
        });

        txtcorreo.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtcorreoCaretUpdate(evt);
            }
        });

        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Fecha de Nacimiento");

        jDateFecha.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateFechaPropertyChange(evt);
            }
        });

        Guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Botones/boton guardar 1.png"))); // NOI18N
        Guardar.setBorderPainted(false);
        Guardar.setContentAreaFilled(false);
        Guardar.setEnabled(false);
        Guardar.setFocusPainted(false);
        Guardar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Botones/boton guardar 2.png"))); // NOI18N
        Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarActionPerformed(evt);
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

        botonInscribir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Botones/boton inscribir 1.png"))); // NOI18N
        botonInscribir.setBorderPainted(false);
        botonInscribir.setContentAreaFilled(false);
        botonInscribir.setEnabled(false);
        botonInscribir.setFocusPainted(false);
        botonInscribir.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Botones/boton inscribir 2.png"))); // NOI18N
        botonInscribir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonInscribirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtdni, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtapellido, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtcorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jDateFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(106, 106, 106))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(Foto, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Subirfoto, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27)
                                .addComponent(jLabel8)
                                .addGap(67, 67, 67))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(Nuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(botonInscribir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addComponent(Guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(Eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Guardar)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(2, 2, 2)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(22, 22, 22)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(14, 14, 14)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jComboCurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtdni, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(txtapellido, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(16, 16, 16)
                                        .addComponent(txtcorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(7, 7, 7)
                                        .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(Foto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(Subirfoto))
                            .addComponent(jDateFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Eliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(botonInscribir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Nuevo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel.addTab("Gestor", jPanel1);

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

        javax.swing.GroupLayout MousePanelLayout = new javax.swing.GroupLayout(MousePanel);
        MousePanel.setLayout(MousePanelLayout);
        MousePanelLayout.setHorizontalGroup(
            MousePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MousePanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(botonAtras, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        MousePanelLayout.setVerticalGroup(
            MousePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MousePanelLayout.createSequentialGroup()
                .addContainerGap(59, Short.MAX_VALUE)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(botonAtras, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(MousePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MousePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAtrasActionPerformed
        IU_MainMenu Menu = new IU_MainMenu();
        Menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_botonAtrasActionPerformed

    private void NuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuevoActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_NuevoActionPerformed

    private void EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarActionPerformed
        
        int respuesta = JOptionPane.showConfirmDialog(null,"¿Realmente desea eliminar este Usuario?");

        u.setIdalumno(id);

        if (respuesta==0){
            bll.eliminarUsuario(u);
            limpiarCampos();
        }

    }//GEN-LAST:event_EliminarActionPerformed

    private void GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarActionPerformed
        BotonGuardar();
    }//GEN-LAST:event_GuardarActionPerformed

    private void jDateFechaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateFechaPropertyChange
        ValidarIngreso();
    }//GEN-LAST:event_jDateFechaPropertyChange

    private void txtcorreoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtcorreoCaretUpdate
        ValidarIngreso();
    }//GEN-LAST:event_txtcorreoCaretUpdate

    private void txttelefonoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txttelefonoCaretUpdate
        ValidarIngreso();
    }//GEN-LAST:event_txttelefonoCaretUpdate

    private void txtdniCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtdniCaretUpdate
        ValidarIngreso();        // TODO add your handling code here:
    }//GEN-LAST:event_txtdniCaretUpdate

    private void txtapellidoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtapellidoCaretUpdate
        ValidarIngreso();        // TODO add your handling code here:
    }//GEN-LAST:event_txtapellidoCaretUpdate

    private void txtnombreCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtnombreCaretUpdate
        ValidarIngreso();
    }//GEN-LAST:event_txtnombreCaretUpdate

    private void SubirfotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubirfotoActionPerformed

        CargarFoto();
    }//GEN-LAST:event_SubirfotoActionPerformed

    private void jRadioInscritosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioInscritosActionPerformed
        
        actualizarTabla();
        
            
            
        
    }//GEN-LAST:event_jRadioInscritosActionPerformed

    private void txtbuscarCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtbuscarCaretUpdate
        buscar();

    }//GEN-LAST:event_txtbuscarCaretUpdate

    private void TablaDatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaDatosMouseClicked
        if(evt.getClickCount()==2){
            
            
            consultar=true;
            int fila=TablaDatos.getSelectedRow();
            id =(int) TablaDatos.getValueAt(fila,0);
            Object [] datos= bll.consultarPorID(id, Foto);
            
            
            if (bll.BuscarEstado(id)== 1){
                botonInscribir.setEnabled(false);
                Eliminar.setEnabled(true);
            }else{
                botonInscribir.setEnabled(true);
                Eliminar.setEnabled(false);
            }
            
            
            txtdni.setText(datos[0].toString());
            txtnombre.setText(datos[1].toString());
            txtapellido.setText(datos[2].toString());
            txtcorreo.setText(datos[3].toString());
            txttelefono.setText(datos[4].toString());
            jDateFecha.setDate((Date)datos[5]);
            
            //jComboCurso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { }));
            //jComboCurso.addItem(u.getCurso());
            
            //System.out.println("El estado del usuario es. " + datos[9]);
            System.out.println("El estado del usuario es. " + bll.BuscarEstado(id));
            
            try{

                Foto.setIcon((Icon) datos[6]);
            }catch (Exception e){

            }

            panel.setSelectedIndex(1);
            
            
        }
    }//GEN-LAST:event_TablaDatosMouseClicked

    private void botonInscribirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonInscribirActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(null,"¿Realmente desea inscribir este participante?");

        u.setIdalumno(id);

        if (respuesta==0){
            bll.InscribirUsuario(u);
            limpiarCampos();
            
        }
    }//GEN-LAST:event_botonInscribirActionPerformed

    private void panelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelMouseClicked
        
    }//GEN-LAST:event_panelMouseClicked

    private void panelPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_panelPropertyChange
        
    }//GEN-LAST:event_panelPropertyChange

    private void txtdniPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtdniPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdniPropertyChange

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
            java.util.logging.Logger.getLogger(IU_GestorAlumno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IU_GestorAlumno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IU_GestorAlumno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IU_GestorAlumno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IU_GestorAlumno().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Eliminar;
    private javax.swing.JLabel Foto;
    private javax.swing.JButton Guardar;
    private jcMousePanel.jcMousePanel MousePanel;
    private javax.swing.JButton Nuevo;
    private javax.swing.JButton Subirfoto;
    private javax.swing.JTable TablaDatos;
    private javax.swing.JButton botonAtras;
    private javax.swing.JButton botonInscribir;
    private javax.swing.JComboBox<String> jComboCurso;
    private com.toedter.calendar.JDateChooser jDateFecha;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioInscritos;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel labelBuscar;
    private javax.swing.JTabbedPane panel;
    private javax.swing.JTextField txtapellido;
    private javax.swing.JTextField txtbuscar;
    private javax.swing.JTextField txtcorreo;
    private javax.swing.JTextField txtdni;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txttelefono;
    // End of variables declaration//GEN-END:variables

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Iconos/compose.png")));
    }
}
