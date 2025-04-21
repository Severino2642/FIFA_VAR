package affichage;

import analyseur.Var;
import entity.Joueur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel {
    int longueur, largeur;
    ImageIcon image;
    Var var;

    public ImagePanel(ImageIcon image) {
        this.longueur = image.getIconWidth();
        this.largeur = image.getIconHeight();
        this.image = image;
        this.setPreferredSize(new Dimension(image.getIconWidth(), image.getIconHeight()));
        this.setImage(image);
    }

    public ImagePanel (){
    }

    public int getLongueur() {
        return longueur;
    }

    public void setLongueur(int longueur) {
        this.longueur = longueur;
    }

    public int getLargeur() {
        return largeur;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }

    public Var getVar() {
        return var;
    }

    public void setVar(Var var) {
        this.var = var;
    }

    @Override
    protected void paintComponent(Graphics g) {

        // Dessiner un rectangle rouge
//        g.setColor(Color.gray);
//        g.fillRect(0, 0, this.getLongueur(), this.getLargeur());
        g.drawImage(image.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
        System.out.println("W : "+this.getLongueur());
        System.out.println("H : "+this.getLargeur());
        g.setFont(new Font("Arial", Font.BOLD, 10));

//        int x = (int) this.getVar().getTerrain().getCampBut(true).getX();
//        int y = (int) this.getVar().getTerrain().getCampBut(true).getY();
//        g.setColor(Color.cyan);
//        g.fillOval(x,y,15,15);
//
//        x = (int) this.getVar().getTerrain().getCampBut(false).getX();
//        y = (int) this.getVar().getTerrain().getCampBut(false).getY();
//        g.setColor(Color.green);
//        g.fillOval(x,y,15,15);

        this.draw_players(g);
//        g.setColor(Color.RED);
//        g.drawString("Tay", (int) (this.getLongueur()/2), (int)(this.getLargeur()/2));
    }

    public void draw_players (Graphics g) {
        Joueur [] joueurs = this.getVar().findMbappe();
        for (Joueur joueur : joueurs) {
            int x = (int) joueur.getPosition().getCoordonne().getX();
            int y = (int) joueur.getPosition().getCoordonne().getY();
            g.drawString("OUT", x, y);
//            System.out.println("OUT :" +joueur.getIdEquipe()+" : "+joueur.getId()+" | "+joueur.getPosition().getCoordonne().getX());
        }

        for (Joueur joueur : this.getVar().getPlayerChecked()) {
            if (!joueur.isMbappe()){
                int x = (int) joueur.getPosition().getCoordonne().getX();
                int y = (int) joueur.getPosition().getCoordonne().getY();
                g.drawString("IN", x, y);
//                System.out.println("IN :" +joueur.getIdEquipe()+" : "+joueur.getId()+" | "+joueur.getPosition().getCoordonne().getX());

            }
        }

        int id = 0;
//        for (Equipe equipe : this.getVar().getEquipes()) {
//            g.setColor(Color.red);
//            if (id==0){
//                g.setColor(Color.blue);
//            }
//            for (Joueur joueur : equipe.getJoueurs()) {
//                int x = (int) joueur.getPosition().getCoordonne().getX();
//                int y = (int) joueur.getPosition().getCoordonne().getY();
//                if (id==0){
//                    g.setColor(Color.blue);
//                }
//                if (id>0){
//                    g.setColor(Color.red);
//                }
//                if (joueur.isPossession()){
//                    g.setColor(Color.ORANGE);
//                }
//                if (joueur.isGK()){
//                    g.setColor(Color.magenta);
//                }
//                g.fillOval(x, y, (int) joueur.getPosition().getDiametre(), (int) joueur.getPosition().getDiametre());
////                g.drawString(""+joueur.getPosition().getCoordonne().getX(), x, y);
//
//            }
//            id++;
//        }
        int x = (int) this.getVar().getBallon().getPosition().getCoordonne().getX();
        int y = (int) this.getVar().getBallon().getPosition().getCoordonne().getY();
        g.setColor(Color.black);
//        g.fillOval(x, y, (int) this.getVar().getBallon().getPosition().getDiametre(), (int) this.getVar().getBallon().getPosition().getDiametre());
    }

    public void savePanelAsImage(String filePath) throws IOException {
        // Créer une image BufferedImage de la taille du panel
        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);

        // Dessiner le contenu du JPanel sur l'image
        Graphics2D g2d = image.createGraphics();
        this.printAll(g2d);
        g2d.dispose();

        // Écrire l'image dans un fichier
        File file = new File(filePath);
        ImageIO.write(image, "png", file);
        System.out.println("Le JPanel a été sauvegardé en tant qu'image : " + file.getAbsolutePath());
    }
}
