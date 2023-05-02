package Milestone6;

import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;


public class GUI extends JFrame {
    public static void photrographer(Container frame0){
        JPanel phtgrphr = new JPanel();

        JLabel name = new JLabel("Photographer: ");
        JComboBox comboN = new JComboBox();
        comboN.addItem("uno");
        comboN.addItem("dos");
        comboN.addItem("tres");
        phtgrphr.add(name);
        phtgrphr.add(comboN);

        frame0.add(phtgrphr);
    }
    public static void date(Container frame0){
        JPanel dateLabel = new JPanel();
        JLabel text = new JLabel("Photos after  ");
        JXDatePicker date =  new JXDatePicker();
        dateLabel.add(text);
        dateLabel.add(date);
        frame0.add(dateLabel);
    }
    public static void imgsName(Container frame0){
        JPanel names = new JPanel();


        JList imgList=new JList();
        imgList.setPreferredSize(new Dimension(350,125));
        DefaultListModel element = new DefaultListModel();
        for (int index = 0; index < 20; index++) {
            element.addElement("Elemento " + index);
        }
        imgList.setModel(element);

        JScrollPane scrollLista = new JScrollPane(imgList);
        imgList.setLayoutOrientation(JList.VERTICAL);




        names.add(imgList);
        frame0.add(names);
    }
    public static void abajoderecha(Container frame0){
        String src = "vangogh1.jpg";
        JPanel img= new JPanel();
        JLabel imgs = new JLabel();
        imgs.setPreferredSize(new Dimension(300,125));

        Image photo = new ImageIcon("img/milestone6/" + src).getImage();
        photo = photo.getScaledInstance(250,200,Image.SCALE_SMOOTH);
        ImageIcon loogooo = new ImageIcon(photo);
        imgs.setIcon(loogooo);
        img.add(imgs);
        frame0.add(img);
    }

    /*=========================================================================================================
            Creation of the frame and call to the methods.*/
    public static void ShowGUI(){
        JFrame frame0 = new GUI();
        frame0.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame0.setLayout(new GridLayout(2,2));
        frame0.setPreferredSize(new Dimension(700,300));

        photrographer(frame0);
        date(frame0);
        imgsName(frame0);
        abajoderecha(frame0);



        frame0.pack();
        frame0.setLocationRelativeTo(null);
        frame0.setVisible(true);
    }
    public void getPhotographers(){

    }
    public static void main(String[] args) {

        ShowGUI();
    }
}
