package Milestone7;

import org.jdesktop.swingx.JXDatePicker;



import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class GUI extends JFrame {
    DBConnector myPhotographerManager;
    private JButton awardButton;
    private JButton removeButton;
    private JComboBox comboBox;
    private DefaultComboBoxModel comboBoxModel;
    private JXDatePicker datePicker;
    private DefaultListModel listModel;
    private ImageIcon image;
    private JLabel pickLabel;

    public void addComponentsToPanel1(JFrame frame) {
        awardButton = new JButton("AWARD");
        awardButton.setPreferredSize(new Dimension(200, 50));
        awardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String inputValue = JOptionPane.showInputDialog("Minimum number of visits for getting a prize: ");
                myPhotographerManager.award(Integer.parseInt(inputValue));
            }
        });
        frame.add(awardButton);
    }

    public void addComponentsToPanel2(JFrame frame) {
        removeButton = new JButton("REMOVE");
        removeButton.setPreferredSize(new Dimension(200, 50));
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {myPhotographerManager.remove();}
        });
        frame.add(removeButton);
    }

    public void addComponentsToPanel3(JPanel panel3) {
        JLabel label3 = new JLabel("Photographer: ");
        comboBoxModel = new DefaultComboBoxModel<String>();
        List<Photographer> myPhotographers = new ArrayList<Photographer>() ;
        myPhotographers = myPhotographerManager.getPhotographers();
        Iterator<Photographer> it = myPhotographers.iterator();
        while(it.hasNext()){
            String name = it.next().getName();
            comboBoxModel.addElement(name);
        }
        comboBox = new JComboBox(comboBoxModel);
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                addPictures();
            }
        });
        panel3.add(label3);
        panel3.add(comboBox);
    }

    public void addComponentsToPanel4(Container panel4) {
        JLabel label4 = new JLabel("Photos after: ");
        datePicker = new JXDatePicker();
        datePicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                addPictures();
            }
        });
        panel4.add(datePicker);
    }

    public void addPictures() {
        listModel.removeAllElements();
        List<Picture> myPictures = myPhotographerManager.getPictures(comboBox.getSelectedIndex(), datePicker.getDate());
        Iterator<Picture> it = myPictures.iterator();
        while(it.hasNext()) {
            listModel.addElement(it.next().getTitle());
        }
    }



    public void addComponentsToPanel5(Container panel5) {
        listModel = new DefaultListModel<String>();
        JList list = new JList(listModel);
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    List<Picture> myPictures = myPhotographerManager.getPictures(comboBox.getSelectedIndex(), datePicker.getDate());
                    System.out.println(myPictures.get(list.getSelectedIndex()).getFile());
                    image = new ImageIcon("img/milestone6/" + myPictures.get(list.getSelectedIndex()).getFile());
                    image.setImage(image.getImage().getScaledInstance(200,100,1));
                    pickLabel.setIcon(image);
                }
            }
        });
        addPictures();
        JScrollPane scrollPane = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(270,100));
        panel5.add(scrollPane);
    }

    public void addComponentsToPanel6(Container panel6) {

        image = new ImageIcon("nothing");
        image.setImage(image.getImage().getScaledInstance(200,100,1));
        pickLabel = new JLabel();
        pickLabel.setIcon(image);

        panel6.add(pickLabel);
    }

    public GUI() {
        Container p = this.getContentPane();
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                myPhotographerManager.closeCon();
            }
        });
        this.setLayout(new GridLayout(3, 2));
        this.setPreferredSize(new Dimension(600,350));

        myPhotographerManager = new DBConnector();

        addComponentsToPanel1(this);

        addComponentsToPanel2(this);

        JPanel panel3 = new JPanel();
        panel3.setPreferredSize(new Dimension(300,100));
        panel3.setBorder(new EmptyBorder(10,20,10,10));
        addComponentsToPanel3(panel3);
        this.add(panel3);

        JPanel panel4 = new JPanel();
        panel4.setPreferredSize(new Dimension(300,100));
        panel4.setBorder(new EmptyBorder(10,10,10,20));
        addComponentsToPanel4(panel4);
        this.add(panel4);

        JPanel panel5 = new JPanel();
        panel5.setPreferredSize(new Dimension(300,100));
        panel5.setBorder(new EmptyBorder(10,20,20,10));
        addComponentsToPanel5(panel5);
        this.add(panel5);

        JPanel panel6 = new JPanel();
        panel6.setPreferredSize(new Dimension(300,100));
        panel6.setBorder(new EmptyBorder(10,20,20,10));
        addComponentsToPanel6(panel6);
        this.add(panel6);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        GUI myPictureViewer = new GUI();
    }
}
