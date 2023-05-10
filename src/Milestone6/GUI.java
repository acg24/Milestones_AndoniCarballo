package Milestone6;
import org.jdesktop.swingx.JXDatePicker;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class GUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private static JComboBox<String> photographersComboBox;
    private static JXDatePicker datePicker;
    private JList<Picture> pictureList;
    private JLabel pictureLabel;

    public GUI(List<Photographer> photographers, List<Picture> pictures) {
        super("Picture Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the components
        photographersComboBox = new JComboBox<>(photographers.toArray(new String[0]));
        photographersComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                updatePictureList(dbconnector.getPictures(photographersComboBox.getSelectedIndex(), (Date) datePicker.getDate()));
            }
        });
        datePicker = new JXDatePicker();
        pictureList = new JList<>(new DefaultListModel<>());
        pictureList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pictureList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                Picture selectedPicture = pictureList.getSelectedValue();
                if (selectedPicture != null) {
                    pictureLabel.setIcon(new ImageIcon(String.valueOf(selectedPicture.getPhotographerId())));
                }
            }
        });
        pictureLabel = new JLabel();
        JPanel contentPane = new JPanel(new GridLayout(2, 2));
        contentPane.add(photographersComboBox);
        contentPane.add(datePicker);
        contentPane.add(new JScrollPane(pictureList));
        contentPane.add(pictureLabel);
        getContentPane().add(contentPane, BorderLayout.CENTER);
        updatePictureList(pictures);
        pack();
        setVisible(true);
    }

    private void updatePictureList(List<Picture> pictures) {
        DefaultListModel<Picture> model = (DefaultListModel<Picture>) pictureList.getModel();
        model.clear();
        for (Picture picture : pictures) {
            if (picture.getPhotographer().equals(photographersComboBox.getSelectedItem())
                    && picture.getDate().equals(datePicker.getDate())) {
                model.addElement(picture);
            }
        }
        if (model.getSize() > 0) {
            pictureList.setSelectedIndex(0);
        }
    }

    public static void main(String[] args) throws SQLException {
            new GUI(dbconnector.getPhotographers(), dbconnector.getPictures(photographersComboBox.getSelectedIndex(), (Date) datePicker.getDate()));
    }
}
