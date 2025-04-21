package entity;

import analyseur.ImageAnalyseur;
import annexe.PlageCouleur;
import annexe.Position;
import org.opencv.core.Scalar;

import java.awt.*;

public class Ballon {
    Position position;
    PlageCouleur color;

    public Ballon() {
    }

    public Ballon(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    public PlageCouleur getColor() {
        return color;
    }

    public void setColor(PlageCouleur color) {
        this.color = color;
    }

    public void updatePosition(String filePath) throws Exception {
        Scalar [] scalars = this.getColor().getScalars().toArray(new Scalar[]{});

        Ballon b = new ImageAnalyseur().findBallon(filePath,scalars[0],scalars[1]);
        if (b!=null){
            this.position = b.getPosition();
        }
        else {
            throw new Exception("Le Ballon n'existe pas");
        }
    }
}
