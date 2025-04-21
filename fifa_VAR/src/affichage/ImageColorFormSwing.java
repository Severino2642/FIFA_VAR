package affichage;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ImageColorFormSwing extends JFrame {
    private JLabel imageLabel;
    private JPanel colorPreviewPanel;
    private Color color1, color2, color3;

    public ImageColorFormSwing() {
        setTitle("Formulaire Image et Couleurs");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Bouton pour choisir une image
        JButton chooseImageButton = new JButton("Choisir une image");
        imageLabel = new JLabel("Aucune image sélectionnée", SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(400, 300));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        chooseImageButton.addActionListener(e -> chooseImage());

        // Boutons pour choisir les couleurs
        JButton chooseColor1Button = new JButton("Choisir Couleur l'equipe 1");
        JButton chooseColor2Button = new JButton("Choisir Couleur de l'equipe 2");
        JButton chooseColor3Button = new JButton("Choisir Couleur du Ballon");

        colorPreviewPanel = new JPanel();
        colorPreviewPanel.setLayout(new GridLayout(1, 3, 10, 10));
        colorPreviewPanel.setPreferredSize(new Dimension(400, 50));
        colorPreviewPanel.setBorder(BorderFactory.createTitledBorder("Aperçu des couleurs"));

        chooseColor1Button.addActionListener(e -> color1 = chooseColor());
        chooseColor2Button.addActionListener(e -> color2 = chooseColor());
        chooseColor3Button.addActionListener(e -> color3 = chooseColor());

        // Bouton pour valider
        JButton validateButton = new JButton("Valider");
        validateButton.addActionListener(e -> validateForm());

        // Ajouter les composants au panel principal
        mainPanel.add(chooseImageButton);
        mainPanel.add(imageLabel);
        mainPanel.add(chooseColor1Button);
        mainPanel.add(chooseColor2Button);
        mainPanel.add(chooseColor3Button);
        mainPanel.add(colorPreviewPanel);
        mainPanel.add(validateButton);

        // Ajouter au JFrame
        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Permet de choisir une image et l'affiche dans le JLabel.
     */
    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg", "png", "gif"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
            Image image = imageIcon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(image));
            imageLabel.setText(""); // Retirer le texte par défaut
        }
    }

    /**
     * Permet de choisir une couleur à l'aide de JColorChooser.
     *
     * @return La couleur choisie.
     */
    private Color chooseColor() {
        Color chosenColor = JColorChooser.showDialog(this, "Choisir une couleur", Color.WHITE);
        updateColorPreview();
        return chosenColor;
    }

    /**
     * Met à jour l'aperçu des couleurs choisies.
     */
    private void updateColorPreview() {
        colorPreviewPanel.removeAll();
        if (color1 != null) {
            JPanel panel1 = new JPanel();
            panel1.setBackground(color1);
            colorPreviewPanel.add(panel1);
        }
        if (color2 != null) {
            JPanel panel2 = new JPanel();
            panel2.setBackground(color2);
            colorPreviewPanel.add(panel2);
        }
        if (color3 != null) {
            JPanel panel3 = new JPanel();
            panel3.setBackground(color3);
            colorPreviewPanel.add(panel3);
        }
        colorPreviewPanel.revalidate();
        colorPreviewPanel.repaint();
    }

    /**
     * Valide le formulaire et affiche les informations choisies.
     */
    private void validateForm() {
        if (imageLabel.getIcon() == null) {
            JOptionPane.showMessageDialog(this, "Veuillez choisir une image.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (color1 == null || color2 == null || color3 == null) {
            JOptionPane.showMessageDialog(this, "Veuillez choisir trois couleurs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "Formulaire validé avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImageColorFormSwing form = new ImageColorFormSwing();
            form.setVisible(true);
        });
    }
}

