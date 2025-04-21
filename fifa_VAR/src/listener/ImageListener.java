package listener;

import affichage.Home;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ImageListener implements ActionListener {
    JLabel label;
    Home home;

    public ImageListener() {
    }

    public ImageListener(JLabel label, Home home) {
        this.label = label;
        this.home = home;
    }

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        chooseImage();
    }

    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg", "png", "gif"));
        int result = fileChooser.showOpenDialog(this.getHome());
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
            Image image = imageIcon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
            this.getLabel().setIcon(new ImageIcon(image));
            this.getLabel().setText(""); // Retirer le texte par défaut
            this.getHome().setImage(imageIcon);
            this.getHome().setImagePath(selectedFile.getAbsolutePath());
            System.out.println(this.getHome().getImage().getIconWidth()+" x "+this.getHome().getImage().getIconHeight());
        }
    }
}
