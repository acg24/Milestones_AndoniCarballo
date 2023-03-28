package Milestone3;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Milestone {
    static JFrame frame0;
    static JTextArea textArea;
    static JComboBox combo;

    public static void west(Container frame0) {
        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        westPanel.setBorder(new EmptyBorder(20,20,20,20));
        westPanel.setPreferredSize(new Dimension(200,200));
        combo = new JComboBox();
        combo.setSize(new Dimension(195,100));
        combo.setBorder(new EmptyBorder(0,0,20,0));
        combo.addItem("1.jpeg");
        combo.addItem("2.jpg");
        combo.addItem("3.jpeg");
        combo.addItem("4.jpeg");

        combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                File originalFile = new File("img/1.jpeg");
                FileInputStream inStream=null;
                try {
                    inStream = new FileInputStream(originalFile);

                    byte buffer [] = new byte[512];
                    int value =0;
                    while (value!=-1){
                        value = inStream.read(buffer);
                    }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    if (inStream != null) {
                        try {
                            inStream.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                /*textArea.setText("");
                FileReader reader = null;
                try {
                    reader = new FileReader("img/" + combo.getSelectedItem().toString());
                } catch (FileNotFoundException e) {
                    JOptionPane.showMessageDialog(null, "File not found!", "Alert", JOptionPane.ERROR_MESSAGE);
                    //System.out.println("file not found");
                }
                try {
                    textArea.read(reader, "img/" + combo.getSelectedItem().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }
        });



        textArea = new JTextArea();
        textArea.setLineWrap(true);

        textArea.setEditable(false);
        textArea.setPreferredSize(new Dimension(200,100));


        FileReader reader = null;
        try {
            reader = new FileReader("img/1.jpeg");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File not found!", "Alert", JOptionPane.ERROR_MESSAGE);
        }
        try {
            textArea.read(reader, "img/1.jpeg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        westPanel.add(combo);
        westPanel.add(textArea);
        frame0.add("West", westPanel);
    }

    public static void east(Container frame0) {}

    public static void south (Container frame0) {
        JPanel southP = new JPanel();
        southP.setBorder(new EmptyBorder(10,400,10,10));

        JButton but1= new JButton("Close");
        but1.setPreferredSize(new Dimension(100,30));
        but1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
                //this.frame.dispose();
            }
        });

        southP.add(but1);
        frame0.add("South", southP);
    }

    public static void GUI() {
        frame0 = new JFrame("Test events: files");
        frame0.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame0.setLayout(new BorderLayout());
        frame0.setPreferredSize(new Dimension(500,600));




        west(frame0);

        south(frame0);

        frame0.pack();
        frame0.setLocationRelativeTo(null);
        frame0.setVisible(true);
    }

    public static void main(String[] args) {
        GUI();
    }
}

