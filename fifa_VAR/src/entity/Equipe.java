package entity;

import analyseur.ImageAnalyseur;
import annexe.PlageCouleur;
import annexe.Position;
import org.opencv.core.Scalar;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Equipe {
    int id;
    List<Joueur> joueurs = new ArrayList<>();
    boolean isAttack = false;
    boolean isGauche = false;
    PlageCouleur color;

    public Equipe(int id) {
        this.id = id;
    }

    public Equipe(int id, List<Joueur> joueurs) {
        this.id = id;
        for (Joueur joueur : joueurs) {
            joueur.setIdEquipe(this.id);
        }
        this.joueurs = joueurs;
    }

    public Equipe() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Joueur> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(List<Joueur> joueurs) {
        this.joueurs = joueurs;
    }

    public boolean isAttack() {
        return isAttack;
    }

    public void setAttack(boolean attack) {
        isAttack = attack;
    }

    public boolean isGauche() {
        return isGauche;
    }

    public void setGauche(boolean gauche) {
        isGauche = gauche;
    }

    public void addJoueurs(Joueur joueur) {
        joueur.setAttack(this.isAttack());
        joueurs.add(joueur);
    }

    public PlageCouleur getColor() {
        return color;
    }

    public void setColor(PlageCouleur color) {
        this.color = color;
    }

    public Equipe [] generateEquipe(){
        Equipe [] equipes = new Equipe[2];
        for (int i = 0; i < equipes.length; i++) {
            int nbJoueur = 0;
            List<Joueur> joueurs = new ArrayList<Joueur>();
            while (nbJoueur<11){
                Random rand = new Random();
                Position p = new Position(rand.nextInt(5),new Point(rand.nextInt(1000),rand.nextInt(1000)));
                Joueur j = new Joueur(nbJoueur,p, rand.nextBoolean());
                joueurs.add(j);
                nbJoueur++;
            }
            equipes[i] = new Equipe();
            equipes[i].setId(i);
            equipes[i].setJoueurs(joueurs);
        }
        return equipes;
    }

    public List<Equipe> makeEquipe(String filePath) throws Exception {
        List<Equipe> equipes = new ArrayList<>();
        List<Scalar[]> scalars = new ArrayList<>();
        Scalar[] team1 = new Scalar[2];
//        team1[0] = new Scalar(100, 150, 50);
//        team1[1] = new Scalar(140, 255, 255);
        team1[0] = new Scalar(90, 50, 150);
        team1[1] = new Scalar(130, 200, 255);
        Scalar[] team2 = new Scalar[2];
        team2[0] = new Scalar(0,150,150);
        team2[1] = new Scalar(5,255,255);
        scalars.add(team1);
        scalars.add(team2);
        ImageAnalyseur imageAnalyseur = new ImageAnalyseur();
        int id = 0;
        for (Scalar [] sc : scalars){
            List<Joueur> joueurs = imageAnalyseur.findJoueurs(filePath,sc[0],sc[1]);
            equipes.add(new Equipe(id,joueurs));
            id++;
        }
        return equipes;
    }

    public Joueur findGK() {
        for (Joueur joueur : this.getJoueurs()) {
            if (joueur.isGK()){
                return joueur;
            }
        }
        return null;
    }
    public Joueur findNotGK() {
        for (Joueur joueur : this.getJoueurs()) {
            if (!joueur.isGK()){
                return joueur;
            }
        }
        return null;
    }

    public Joueur findPossesseur() {
        for (Joueur joueur : this.getJoueurs()) {
            if (joueur.isPossession()){
                return joueur;
            }
        }
        return null;
    }

    public void initializeJoueur() {
        for (Joueur joueur : this.getJoueurs()) {
            joueur.setIdEquipe(this.getId());
            joueur.setAttack(this.isAttack());
            joueur.setMbappe(false);
        }
    }

    public Joueur getJoueurDistant(Joueur joueur,boolean isGauche) {
        for (Joueur j : this.getJoueurs()) {
            if (isGauche) {
                if (j.getPosition().getCoordonne().getX() < joueur.getPosition().getCoordonne().getX()) {
                    joueur = j;
                }
            }
            else {
                if (j.getPosition().getCoordonne().getX() > joueur.getPosition().getCoordonne().getX()) {
                    joueur = j;
                }
            }
        }
        return joueur;
    }

    public boolean isMonJoueur (Joueur joueur) {
        if (joueur.getIdEquipe() == this.getId()) {
            return true;
        }
        return false;
    }

    public void initialiserJoueur() {
        for (Joueur joueur : this.getJoueurs()) {
            joueur.setIdEquipe(this.getId());
            joueur.setMbappe(false);
            joueur.setPossession(false);
            joueur.setAttack(this.isAttack());
            joueur.setGK(false);
        }
    }

    public void updateJoueur(String filePath) throws Exception {
        Scalar [] sc = this.getColor().getScalars().toArray(new Scalar[]{});
        this.setJoueurs(new ImageAnalyseur().findJoueurs(filePath,sc[0],sc[1]));
        this.initializeJoueur();
    }

}
