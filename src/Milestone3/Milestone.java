package Milestone3;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Milestone extends JFrame{
    static JFrame frame0;
    static JTextArea textArea;
    static JComboBox combo;

    /*=========================================================================================================
        Elements that will be at the left of the frame.*/
    public static void west(Container frame0) {
        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        westPanel.setBorder(new EmptyBorder(20,20,20,0));
        westPanel.setPreferredSize(new Dimension(300,200));

        JPanel conb= new JPanel();
        JPanel img= new JPanel();
        JPanel check = new JPanel();

        combo = new JComboBox();
        combo.setSize(new Dimension(100,50));
        combo.setBorder(new EmptyBorder(0,0,10,0));
        combo.addItem("1.jpeg");
        combo.addItem("2.jpg");
        combo.addItem("3.jpeg");
        combo.addItem("4.jpeg");
        JLabel imgs = new JLabel();
        imgs.setIcon(new ImageIcon("img/1.jpeg"));
        combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                imgs.setIcon(new ImageIcon("img/" + combo.getSelectedItem().toString()));
            }
        });
        JCheckBox saveOrNot = new JCheckBox("Save your comment");
        conb.add(combo);
        img.add(imgs);
        check.add(saveOrNot);
        westPanel.add(conb);
        westPanel.add(img);
        westPanel.add(check);
        frame0.add("West", westPanel);
    }

    public static void middle(Container frame0) {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        centerPanel.setBorder(new EmptyBorder(20,0,20,20));
        centerPanel.setPreferredSize(new Dimension(250,200));

        JPanel text = new JPanel();
        text.setBorder(new EmptyBorder(390,10,10,10));

        TextField comment= new TextField(30);
        comment.setSize(new Dimension(45, 10));

        text.add(comment);
        centerPanel.add(text);
        frame0.add("Center", centerPanel);
    }

    /*=========================================================================================================
        Elements that will be at the bottom of the frame.*/
    public static void south (Container frame0) {
        JPanel southP = new JPanel();
        southP.setBorder(new EmptyBorder(10,400,10,10));

        JButton but1= new JButton("Close");
        but1.setPreferredSize(new Dimension(100,30));
        but1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        southP.add(but1);
        frame0.add("South", southP);
    }

    /*=========================================================================================================
            Creation of the frame and call to the methods.*/
    public static void GUI() {
        frame0 = new JFrame("Test events: files");
        frame0.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame0.setLayout(new BorderLayout());
        frame0.setPreferredSize(new Dimension(600,600));


        west(frame0);
        middle(frame0);
        south(frame0);

        frame0.pack();
        frame0.setLocationRelativeTo(null);
        frame0.setVisible(true);
    }
    /*=========================================================================================================
            Creation of the LogIn.*/
    public static void logIn() {
        String pass =  "anboto";
        String password = JOptionPane.showInputDialog
                (null, "Input password", "KAI-FOOD", JOptionPane.QUESTION_MESSAGE );
        if(password.equals("anboto"))
        {
            GUI();
        }
        else {
            JOptionPane.showMessageDialog
                    (null, "invalid user id and password", "KAI-FOOD", JOptionPane.ERROR_MESSAGE);
        }

    }
    public static void main(String[] args) {
        logIn();
        /*GUI();*/
    }
}

