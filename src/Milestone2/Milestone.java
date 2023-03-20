package Milestone2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Milestone {
    static JFrame frame0;
    static JTextArea textArea;
    static JComboBox combo;

    public static void west(Container frame0) {
        JPanel westPanel = new JPanel();
        westPanel.setLayout(new FlowLayout());
        combo = new JComboBox();
        combo.setPreferredSize(new Dimension(195,30));
        combo.addItem("python.txt");
        combo.addItem("java.txt");
        combo.addItem("c.txt");
        combo.addItem("noExist.txt");

        combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textArea.setText("");
                FileReader reader = null;
                try {
                    reader = new FileReader("text/" + combo.getSelectedItem().toString());
                } catch (FileNotFoundException e) {
                    JOptionPane.showMessageDialog(null, "File not found!", "Alert", JOptionPane.ERROR_MESSAGE);
                    //System.out.println("file not found");
                }
                try {
                    textArea.read(reader, "text/" + combo.getSelectedItem().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        JButton but1= new JButton("Clear");
        but1.setPreferredSize(new Dimension(100,30));
        but1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textArea.setText("");
            }
        });

        westPanel.add(combo);
        westPanel.add(but1);
        frame0.add("West", westPanel);
    }

    public static void east(Container frame0) {
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setBorder(new EmptyBorder(5,5,5,5));
        textArea.setEditable(false);
        textArea.setPreferredSize(new Dimension(300,300));


        FileReader reader = null;
        try {
            reader = new FileReader("text/python.txt");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File not found!", "Alert", JOptionPane.ERROR_MESSAGE);
        }
        try {
            textArea.read(reader, "text/python.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(300,150));


        frame0.add("East", scrollPane);
    }

    public static void south (Container southPanel) {
        JButton but1= new JButton("Close");
        but1.setPreferredSize(new Dimension(100,30));
        but1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
                //this.frame.dispose();
            }
        });

        southPanel.add(but1);
    }

    public static void GUI() {
        frame0 = new JFrame("Test events: files");
        frame0.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame0.setLayout(new BorderLayout());
        frame0.setPreferredSize(new Dimension(900,450));


        JPanel southP = new JPanel();
        southP.setBorder(new EmptyBorder(10,400,10,10));
        frame0.add("South", southP);

        west(frame0);
        east(frame0);
        south(southP);

        frame0.pack();
        frame0.setLocationRelativeTo(null);
        frame0.setVisible(true);
    }

    public static void main(String[] args) {
        GUI();
    }
}


