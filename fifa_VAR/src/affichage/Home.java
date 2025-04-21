package affichage;

import analyseur.ImageAnalyseur;
import analyseur.Var;
import annexe.PlageCouleur;
import entity.Ballon;
import entity.Equipe;
import entity.Joueur;
import entity.Terrain;
import listener.ColorListener;
import listener.ComboBoxListener;
import listener.ImageListener;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Home extends JFrame {
    JPanel mainPanel;
    Var var;
    ImageIcon image;
    String imagePath;
    PlageCouleur [] plageCouleurs;
    boolean isHorizontal = true;
    public Home() {
        setPlageCouleurs(new PlageCouleur().generate());
        setVar();
        setTitle("Home");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel principal
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // choix d'image
        mainPanel.add(this.generateInputImage(), BorderLayout.CENTER);

        JPanel panelColor = new JPanel();
        panelColor.setLayout(new BoxLayout(panelColor, BoxLayout.Y_AXIS));
        // choix des couleur
        panelColor.add(this.generateInputColor("equipe:0","Choisir le couleur de l'equipe 1"),BorderLayout.NORTH);
        panelColor.add(this.generateInputColor("equipe:1","Choisir le couleur de l'equipe 2"),BorderLayout.SOUTH);
        panelColor.add(this.generateComboBoxColor("ballon","Choisir le couleur du ballon"),BorderLayout.CENTER);
        mainPanel.add(panelColor);

        mainPanel.add(this.generateComboBoxFormat("format","isHorizontal"));

        // Bouton pour valider
        JButton validateButton = new JButton("Analyser");
        validateButton.addActionListener(e -> validateForm());

        mainPanel.add(validateButton, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public PlageCouleur[] getPlageCouleurs() {
        return plageCouleurs;
    }

    public void setPlageCouleurs(PlageCouleur[] plageCouleurs) {
        this.plageCouleurs = plageCouleurs;
    }

    public Var getVar() {
        return var;
    }

    public void setVar(Var var) {
        this.var = var;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    public void setHorizontal(boolean horizontal) {
        isHorizontal = horizontal;
    }

    public JPanel generateInputImage (){
        JPanel imagePanel = new JPanel();
        JButton bt = new JButton("Choisir une image");
        JLabel imageLabel = new JLabel("Aucune image sélectionnée", SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(400, 300));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        bt.addActionListener(new ImageListener(imageLabel,this));
        imagePanel.add(bt, BorderLayout.CENTER);
        imagePanel.add(imageLabel,BorderLayout.WEST);
        return imagePanel;
    }

    public JPanel generateInputColor (String bt_name,String bt_title){

        JPanel colorPanel = new JPanel();
        colorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JButton bt = new JButton(bt_title);
        bt.setName(bt_name);

        JPanel colorLabel = new JPanel();
        colorLabel.setPreferredSize(new Dimension(20, 20));
        colorLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        bt.addActionListener(new ColorListener(colorLabel,this));
        colorPanel.add(bt, BorderLayout.CENTER);
        colorPanel.add(colorLabel,BorderLayout.EAST);
        return colorPanel;
    }

    public JPanel generateComboBoxColor (String bt_name,String bt_title){

        JPanel colorPanel = new JPanel();
        colorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Label pour indiquer l'objectif
        JLabel label = new JLabel(bt_title, SwingConstants.CENTER);
        colorPanel.add(label,BorderLayout.NORTH);

        // Liste déroulante (JComboBox)
        List<String> options = new ArrayList<>();
        options.add("Aucun");
        for (PlageCouleur p : this.getPlageCouleurs()) {
            options.add(p.getNom());
        }

        JComboBox<String> comboBox = new JComboBox<>(options.toArray(new String[]{}));
        comboBox.setName(bt_name);
        comboBox.addActionListener(new ComboBoxListener(comboBox,this));
        colorPanel.add(comboBox,BorderLayout.SOUTH);

        return colorPanel;
    }

    public JPanel generateComboBoxFormat (String bt_name,String bt_title){

        JPanel colorPanel = new JPanel();
        colorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Label pour indiquer l'objectif
        JLabel label = new JLabel(bt_title, SwingConstants.CENTER);
        colorPanel.add(label,BorderLayout.NORTH);

        // Liste déroulante (JComboBox)
        List<String> options = new ArrayList<>();
        options.add("true");
        options.add("false");

        JComboBox<String> comboBox = new JComboBox<>(options.toArray(new String[]{}));
        comboBox.setName(bt_name);
        comboBox.addActionListener(new ComboBoxListener(comboBox,this));
        colorPanel.add(comboBox,BorderLayout.SOUTH);

        return colorPanel;
    }


    public void setVar(){
        this.var = new Var();
        List<Equipe> equipes = new ArrayList<>();
        equipes.add(new Equipe(0));
        equipes.add(new Equipe(1));
        this.var.setEquipes(equipes);
        this.var.setBallon(new Ballon());
        this.var.setTerrain(new Terrain());
    }

    private void validateForm() {
        if (this.getImage()==null) {
            JOptionPane.showMessageDialog(this, "Veuillez choisir une image.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (int i=0 ; i<this.getVar().getEquipes().size();i++) {
            Equipe equipe = this.getVar().getEquipes().get(i);
            if (equipe.getColor()==null) {
                JOptionPane.showMessageDialog(this, "Veuillez choisir le couleur de l'equipe "+(i+1), "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        if (this.getVar().getBallon().getColor()==null) {
            JOptionPane.showMessageDialog(this, "Veuillez choisir le couleur du ballon", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "Formulaire validé avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
        for (int i=0 ; i<this.getVar().getEquipes().size();i++) {
            Equipe equipe = this.getVar().getEquipes().get(i);
            try {
                equipe.updateJoueur(this.getImagePath());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        try {
            this.getVar().getBallon().updatePosition(this.getImagePath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.getVar().getTerrain().setLongueur(this.getImage().getIconWidth());
        this.getVar().getTerrain().setLargeur(this.getImage().getIconHeight());
        if (!this.isHorizontal()){
            this.getVar().getTerrain().setHorizontal(false);
        }

        this.getVar().initializeEquipe();
        this.getVar().findMbappe();

        try {
            String new_path = new ImageAnalyseur().writeResult(this.getImagePath(),this);
            this.displayImage(new_path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void displayImage(String imagePath) {
        ImageIcon imageIcon = new ImageIcon(new File(imagePath).getAbsolutePath());
        JFrame frame = new JFrame();
        frame.setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel imagePanel = new JPanel();
        imagePanel.setPreferredSize(new Dimension(image.getIconWidth(), image.getIconHeight()));
        imagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel imageLabel = new JLabel();
        Image image = imageIcon.getImage().getScaledInstance(imageIcon.getIconWidth(), imageIcon.getIconHeight(), Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(image));
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        imagePanel.setVisible(true);

        frame.add(imagePanel);
        frame.setVisible(true);
    }
}
