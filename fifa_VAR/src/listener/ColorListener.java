package listener;

import affichage.Home;
import analyseur.ImageAnalyseur;
import annexe.PlageCouleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ColorListener implements ActionListener {

    JPanel panel;
    Home home;

    public ColorListener(JPanel panel, Home home) {
        this.panel = panel;
        this.home = home;
    }

    public ColorListener() {
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton bt = (JButton) e.getSource();
        Color color = chooseColor();
        if (color != null) {
            if (bt.getName().contains("equipe")) {
                String [] tab = bt.getName().split(":");
                int id = Integer.parseInt(tab[1]);
                this.getHome().getVar().getEquipes().get(id).setColor(new PlageCouleur(color));
            }
            if (bt.getName().compareToIgnoreCase("ballon")==0) {
                this.getHome().getVar().getBallon().setColor(new PlageCouleur(color));
            }
            updateColorPreview(color);
        }
        else {
            JOptionPane.showMessageDialog(this.getHome(), "Couleur non valide", "Alerte", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Color chooseColor() {
        Color chosenColor = JColorChooser.showDialog(this.getHome(), "Choisir une couleur", Color.WHITE);
        return chosenColor;
    }

    /**
     * Met à jour l'aperçu des couleurs choisies.
     */
    private void updateColorPreview(Color color) {
        panel.setBackground(color);
    }
}
