package Milestone6;


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
    dbconnector conn;
    private JComboBox combo;
    private DefaultComboBoxModel comboModel;
    private JXDatePicker datePicker;
    private DefaultListModel listModel;
    private ImageIcon img;
    private JLabel pickLabel;

    public void addComponentsToPanel1(Container panel1) {
        JLabel label1 = new JLabel("Photographer: ");
        comboModel = new DefaultComboBoxModel<String>();
        List<Milestone6.Photographer> myPhotographers = new ArrayList<Milestone6.Photographer>() ;
        myPhotographers = conn.getPhotographers();
        Iterator<Photographer> it = myPhotographers.iterator();
        while(it.hasNext()){
            String name = it.next().getName();
            comboModel.addElement(name);
        }
        combo = new JComboBox(comboModel);
        combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                addPictures();
            }
        });
        panel1.add(label1);
        panel1.add(combo);
    }

    public void addComponentsToPanel2(Container panel2) {
        JLabel label2 = new JLabel("Photos after: ");
        datePicker = new JXDatePicker();
        datePicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPictures();
            }
        });
    }

    public void addPictures() {
        listModel.removeAllElements();
        List<Milestone6.Picture> myPictures = conn.getPictures(combo.getSelectedIndex(), datePicker.getDate());
        Iterator<Milestone6.Picture> it = myPictures.iterator();
        while(it.hasNext()) {
            listModel.addElement(it.next().getTitle());
        }
    }



    public void addComponentsToPanel3(Container panel3) {
        listModel = new DefaultListModel<String>();
        JList list = new JList(listModel);
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    List<Picture> myPictures = conn.getPictures(combo.getSelectedIndex(), datePicker.getDate());
                    System.out.println(myPictures.get(list.getSelectedIndex()).getFile());
                    img = new ImageIcon("img/milestone6/" + myPictures.get(list.getSelectedIndex()).getFile());
                    img.setImage(img.getImage().getScaledInstance(200,100,1));
                    pickLabel.setIcon(img);
                }
            }
        });
        addPictures();
        JScrollPane scrollPane = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(270,100));
        panel3.add(scrollPane);
    }

    public void addComponentsToPanel4(Container panel4) {

        img = new ImageIcon("nothing");
        img.setImage(img.getImage().getScaledInstance(200,100,1));
        pickLabel = new JLabel();
        pickLabel.setIcon(img);

        panel4.add(pickLabel);
    }

    public GUI() {
        Container p = this.getContentPane();
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                conn.closeCon();
            }
        });
        this.setLayout(new GridLayout(2, 2));
        this.setPreferredSize(new Dimension(600,200));

        conn = new dbconnector();

        JPanel panel1 = new JPanel();
        panel1.setPreferredSize(new Dimension(300,100));
        panel1.setBorder(new EmptyBorder(10,20,10,10));
        addComponentsToPanel1(panel1);
        this.add(panel1);

        JPanel panel2 = new JPanel();
        panel2.setPreferredSize(new Dimension(300,100));
        panel2.setBorder(new EmptyBorder(10,10,10,20));
        addComponentsToPanel2(panel2);
        this.add(panel2);

        JPanel panel3 =new JPanel();
        panel3.setPreferredSize(new Dimension(300,100));
        panel3.setBorder(new EmptyBorder(10,20,20,10));
        addComponentsToPanel3(panel3);
        this.add(panel3);

        JPanel panel4 = new JPanel();
        panel4.setPreferredSize(new Dimension(300,100));
        panel4.setBorder(new EmptyBorder(10,20,20,10));
        addComponentsToPanel4(panel4);
        this.add(panel4);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        Milestone6.GUI myPictureViewer = new Milestone6.GUI();
    }
}
