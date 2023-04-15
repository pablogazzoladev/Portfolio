package com.pablogazzola.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Properties;



public class Estudiantes extends JFrame{
    private JPanel Panel;
    private JTextField idText;
    private JTextField nombreText;
    private JTextField apellidoText;
    private JTextField edadText;
    private JTextField telefonoText;
    private JTextField carreraText;
    private JButton ingresarBt;
    private JButton consultarBt;
    private JList lista;

    Connection con;

    PreparedStatement ps;
    Statement st;
    ResultSet r;

    DefaultListModel mod = new DefaultListModel<>();

    public Estudiantes() {
        consultarBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    listar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        ingresarBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    insertar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void listar() throws SQLException {
        conectar();
        lista.setModel(mod);
        st = con.createStatement();
        r = st.executeQuery("SELECT idestudiante,nombre,apellido FROM estudiante");
        mod.removeAllElements();
        while(r.next()){
            mod.addElement(r.getString(1)+" "+r.getString(2)+" "+r.getString(3));
        }
    }

    public void insertar() throws SQLException {
        conectar();
        ps = con.prepareStatement("INSERT INTO estudiante VALUES(?,?,?,?,?,?)" );
        ps.setInt(1,Integer.parseInt(idText.getText()));
        ps.setString(2,nombreText.getText());
        ps.setString(3,apellidoText.getText());
        ps.setInt(4,Integer.parseInt(edadText.getText()));
        ps.setString(5,telefonoText.getText());
        ps.setString(6,carreraText.getText());
        if (ps.executeUpdate()>0){
            lista.setModel(mod);
            mod.removeAllElements();
            mod.addElement("Inserción exitosa");

            idText.setText("");
            nombreText.setText("");
            apellidoText.setText("");
            edadText.setText("");
            telefonoText.setText("");
            carreraText.setText("");
        }
    }

    public static void main(String[] args) {
        Estudiantes f = new Estudiantes();
        f.setContentPane(new Estudiantes().Panel);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.pack();
    }

    public void conectar(){
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/learning","root","sisifo82");
            System.out.println("Conexión exitosa");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
