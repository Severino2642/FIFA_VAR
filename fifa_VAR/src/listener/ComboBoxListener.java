package listener;

import affichage.Home;
import annexe.PlageCouleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ComboBoxListener implements ActionListener {
    JComboBox<String> comboBox;
    Home home;
    public ComboBoxListener (){

    }
    public ComboBoxListener(JComboBox<String> comboBox, Home home) {
        this.comboBox = comboBox;
        this.home = home;
    }

    public JComboBox<String> getComboBox() {
        return comboBox;
    }

    public void setComboBox(JComboBox<String> comboBox) {
        this.comboBox = comboBox;
    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedOption = (String) comboBox.getSelectedItem();
        JComboBox<String> bt = (JComboBox<String>) e.getSource();
        PlageCouleur plageCouleur = new PlageCouleur().findByNom(selectedOption,this.home.getPlageCouleurs());
        if (bt.getName().compareToIgnoreCase("format")==0) {
            this.getHome().setHorizontal(Boolean.valueOf(selectedOption));
        }
        else {
            if (plageCouleur!=null){
                if (bt.getName().contains("equipe")) {
                    String [] tab = bt.getName().split(":");
                    int id = Integer.parseInt(tab[1]);
                    this.getHome().getVar().getEquipes().get(id).setColor(plageCouleur);
//                JOptionPane.showMessageDialog(this.getHome(), "Couleur de l'equipe" +(id+1)+" valide", "Alerte", JOptionPane.INFORMATION_MESSAGE);
                }
                if (bt.getName().compareToIgnoreCase("ballon")==0) {
                    this.getHome().getVar().getBallon().setColor(plageCouleur);
//                JOptionPane.showMessageDialog(this.getHome(), "Couleur du ballon valide", "Alerte", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(this.getHome(), "Couleur non valide", "Alerte", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
