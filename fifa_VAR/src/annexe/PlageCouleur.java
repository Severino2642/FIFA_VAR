package annexe;

import org.opencv.core.Scalar;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlageCouleur {
    int id;
    String nom;
    Color color;
    List<Scalar> scalars = new ArrayList<Scalar>();

    public PlageCouleur() {
    }

    public PlageCouleur(Color color) {
        this.setColor(color);
    }

    public PlageCouleur(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public PlageCouleur(int id, String nom, List<Scalar> scalars) {
        this.id = id;
        this.nom = nom;
        this.scalars = scalars;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        scalars = List.of(ColorConverter.generateScalar(color));
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Scalar> getScalars() {
        if (color!=null && scalars.size()<0) {
            scalars = List.of(ColorConverter.generateScalar(color));
        }
        return scalars;
    }

    public void setScalars(List<Scalar> scalars) {
        this.scalars = scalars;
    }

    public PlageCouleur [] generate (){
        List<PlageCouleur> plages = new ArrayList<PlageCouleur>();

        plages.add(new PlageCouleur(1, "bleu ciel", List.of(new Scalar[]{
                new Scalar(90, 50, 150),
                new Scalar(130, 200, 255)
        })));

        plages.add(new PlageCouleur(2, "rouge", List.of(new Scalar[]{
                new Scalar(0,150,150),
                new Scalar(5,255,255)
        })));

        plages.add(new PlageCouleur(3, "noir", List.of(new Scalar[]{
                new Scalar(0,0,0),
                new Scalar(180,255,50)
        })));

        return plages.toArray(new PlageCouleur[]{});
    }

    public PlageCouleur findByNom(String nom,PlageCouleur [] plages) {
        for (PlageCouleur plageCouleur : plages) {
            if (plageCouleur.getNom().equals(nom)) {
                return plageCouleur;
            }
        }
        return null;
    }
}
