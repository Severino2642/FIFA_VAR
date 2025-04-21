package entity;

import java.awt.*;

public class Terrain {
    double longueur;
    double largeur;
    boolean isHorizontal = true;

    public Terrain() {
    }

    public Terrain(double longueur, double largeur) {
        this.longueur = longueur;
        this.largeur = largeur;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    public void setHorizontal(boolean horizontal) {
        isHorizontal = horizontal;
    }

    public double getLongueur() {
        return longueur;
    }

    public void setLongueur(double longueur) {
        this.longueur = longueur;
    }

    public double getLargeur() {
        return largeur;
    }

    public void setLargeur(double largeur) {
        this.largeur = largeur;
    }

    public Point getCampBut(boolean isGauche) {
        if (isHorizontal){
            int y = (int) largeur/2;
            int x = 0;
            if (!isGauche) {
                x = (int) longueur;
            }
            return new Point(x, y);
        }
        else {
            int y = 0;
            int x = (int) longueur/2;
            if (!isGauche) {
                y = (int) largeur;
            }
            return new Point(x, y);
        }
    }
}
