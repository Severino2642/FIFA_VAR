package annexe;

import java.awt.*;

public class Position {
    double diametre;
    Point coordonne;

    public Position(double diametre, Point coordonne) {
        this.diametre = diametre;
        this.coordonne = coordonne;
    }

    public double getDiametre() {
        return diametre;
    }

    public void setDiametre(double diametre) {
        this.diametre = diametre;
    }

    public Point getCoordonne() {
        return coordonne;
    }

    public void setCoordonne(Point coordonne) {
        this.coordonne = coordonne;
    }

    public static double distanceBetween(Position p1, Position p2) {
        double ecart = p1.getCoordonne().distance(p2.getCoordonne());
        double distanceTotal  = ecart - (p1.getDiametre()/2 + p2.getDiametre()/2);
        return Math.max(0,distanceTotal);
    }

    public double getRayon (){
        return this.diametre/2;
    }
}
