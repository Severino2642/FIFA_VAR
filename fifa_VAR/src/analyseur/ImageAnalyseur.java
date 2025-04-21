package analyseur;

import affichage.Home;
import annexe.Position;
import entity.Ballon;
import entity.Equipe;
import entity.Joueur;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ImageAnalyseur {
    static {
        // Charger la bibliothèque native OpenCV
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public List<Joueur> findJoueurs(String inputPath,Scalar lowerBlue,Scalar upperBlue) throws Exception {
        List<Joueur> joueurs = new ArrayList<Joueur>();
        // Charger l'image
        Mat image = Imgcodecs.imread(inputPath);

        if (image.empty()) {
            throw new Exception("Erreur : Impossible de charger l'image !");
        }

        System.out.println("Image chargée avec succès : " + inputPath);

        // Convertir l'image en espace colorimétrique HSV
        Mat hsvImage = new Mat();
        Imgproc.cvtColor(image, hsvImage, Imgproc.COLOR_BGR2HSV);

        // Créer un masque pour les pixels bleus
        Mat blueMask = new Mat();
        Core.inRange(hsvImage, lowerBlue, upperBlue, blueMask);

        // Trouver les contours dans le masque
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(blueMask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // Parcourir les contours et calculer les cercles
        int id = 0;
        for (MatOfPoint contour : contours) {
            // Calculer le cercle minimal englobant
            Point center = new Point();
            float[] radius = new float[1];
            MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
            Imgproc.minEnclosingCircle(contour2f, center, radius);

            // Ajouter le cercle à la liste
            Position position = new Position(radius[0]*2,new java.awt.Point((int) center.x, (int) center.y));
            joueurs.add(new Joueur(id, position));

            // Dessiner le cercle sur l'image originale
            Imgproc.circle(image, center, (int) radius[0], new Scalar(0, 255, 0), 2); // Cercle vert
            Imgproc.circle(image, center, 3, new Scalar(0, 0, 255), -1); // Centre en rouge
            id++;
        }

        // Sauvegarder l'image avec les cercles dessinés
        String outputPath = "output_with_circles.jpg";
        Imgcodecs.imwrite(outputPath, image);
        System.out.println("Image avec cercles sauvegardée : " + outputPath);

        // Afficher les cercles détectés
        System.out.println("Cercles détectés : "+joueurs.size());
        for (Joueur circle : joueurs) {
            System.out.println("Centre : (" + circle.getPosition().getCoordonne().getX()+ ", " + circle.getPosition().getCoordonne().getY() + "), Diametre : " + circle.getPosition().getDiametre());
        }

        return joueurs;
    }

    public Ballon findBallon(String inputPath, Scalar lowerBlue, Scalar upperBlue) throws Exception {
        Ballon result = null;
        // Charger l'image
        Mat image = Imgcodecs.imread(inputPath);

        if (image.empty()) {
            throw new Exception("Erreur : Impossible de charger l'image !");
        }

        System.out.println("Image chargée avec succès : " + inputPath);

        // Convertir l'image en espace colorimétrique HSV
        Mat hsvImage = new Mat();
        Imgproc.cvtColor(image, hsvImage, Imgproc.COLOR_BGR2HSV);

        // Créer un masque pour les pixels bleus
        Mat blueMask = new Mat();
        Core.inRange(hsvImage, lowerBlue, upperBlue, blueMask);

        // Trouver les contours dans le masque
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(blueMask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // Parcourir les contours et calculer les cercles
        for (MatOfPoint contour : contours) {
            // Calculer le cercle minimal englobant
            Point center = new Point();
            float[] radius = new float[1];
            MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
            Imgproc.minEnclosingCircle(contour2f, center, radius);

            // Ajouter le cercle à la liste
            Position position = new Position(radius[0]*2,new java.awt.Point((int) center.x, (int) center.y));
            result = new Ballon(position);

            // Dessiner le cercle sur l'image originale
            Imgproc.circle(image, center, (int) radius[0], new Scalar(0, 255, 0), 2); // Cercle vert
            Imgproc.circle(image, center, 3, new Scalar(0, 0, 255), -1); // Centre en rouge
        }

        // Sauvegarder l'image avec les cercles dessinés
        String outputPath = "output_with_circles.jpg";
        Imgcodecs.imwrite(outputPath, image);
        System.out.println("Image avec cercles sauvegardée : " + outputPath);

        // Afficher les cercles détectés
        System.out.println("Cercles détectés :");
            System.out.println("Centre : (" + result.getPosition().getCoordonne().getX()+ ", " + result.getPosition().getCoordonne().getY() + "), Diametre : " + result.getPosition().getDiametre());
        return result;
    }

    public static Scalar[] generateScalar(Color color, int tolerance) {
        // OpenCV utilise le format BGR, donc nous récupérons les composantes dans cet ordre
        int blue = color.getBlue();
        int green = color.getGreen();
        int red = color.getRed();

        // Calculer les limites inférieures
        int lowerBlue = Math.max(0, blue - tolerance);
        int lowerGreen = Math.max(0, green - tolerance);
        int lowerRed = Math.max(0, red - tolerance);

        // Calculer les limites supérieures
        int upperBlue = Math.min(255, blue + tolerance);
        int upperGreen = Math.min(255, green + tolerance);
        int upperRed = Math.min(255, red + tolerance);

        // Créer les scalaires pour la plage de couleurs
        Scalar lowerBound = new Scalar(lowerBlue, lowerGreen, lowerRed);
        Scalar upperBound = new Scalar(upperBlue, upperGreen, upperRed);

        return new Scalar[]{lowerBound, upperBound};
    }

    public void writeEquipeResult(Mat image, Var var) throws Exception {

        // Convertir l'image en espace colorimétrique HSV
        Mat hsvImage = new Mat();
        Imgproc.cvtColor(image, hsvImage, Imgproc.COLOR_BGR2HSV);


        // Parcourir les contours et calculer les cercles
        int font = Imgproc.FONT_HERSHEY_SIMPLEX;
        double fontSize = 0.4;
        int fontWeight = 1;
        Scalar textColor = new Scalar(0, 0, 0);
        int id = 0;
        for (Equipe equipe : var.getEquipes()) {
            for (Joueur joueur:equipe.getJoueurs()) {
                Point center = new Point(joueur.getPosition().getCoordonne().getX(), joueur.getPosition().getCoordonne().getY());
                String statut = "J";
                if (joueur.isGK()){
                    statut = "GK";
                }
                statut += joueur.getIdEquipe()+""+joueur.getId();
                Imgproc.putText(image,statut,center,font,fontSize,textColor,fontWeight);
                Scalar scalar = new Scalar(0, 13, 255);
                if (joueur.getIdEquipe()==1){
                    scalar = new Scalar(255, 0, 79);
                }
                if (joueur.isPossession()){
                    scalar = var.getBallon().getColor().getScalars().get(0);
                }
                Imgproc.circle(image, center,  (int) joueur.getPosition().getDiametre()/2,scalar , 2); // Cercle vert
                id++;
            }
        }
    }

    public String writeResult(String inputPath, Home home) throws Exception {
        // Charger l'image
        Mat image = Imgcodecs.imread(inputPath);

        if (image.empty()) {
            throw new Exception("Erreur : Impossible de charger l'image !");
        }

        System.out.println("Image chargée avec succès : " + inputPath);

        // Convertir l'image en espace colorimétrique HSV
        Mat hsvImage = new Mat();
        Imgproc.cvtColor(image, hsvImage, Imgproc.COLOR_BGR2HSV);


        // Parcourir les contours et calculer les cercles
        int font = Imgproc.FONT_HERSHEY_SIMPLEX;
        double fontSize = 0.5;
        int fontWeight = 1;
        Scalar textColor = new Scalar(0, 0, 0);
        int id = 0;
        this.drawLineOfJoueur(image,home.getVar().getlastDefender(),home.getVar());
        writeEquipeResult(image,home.getVar());
        Joueur dribbleur = home.getVar().findDribleur();
        for (Joueur joueur:home.getVar().getPlayerChecked()) {
            Point center = new Point(joueur.getPosition().getCoordonne().getX(), joueur.getPosition().getCoordonne().getY());

            // Dessiner le cercle sur l'image originale
            Imgproc.putText(image,joueur.getStatus(),new Point(center.x-joueur.getPosition().getRayon(), center.y),font,fontSize,textColor,fontWeight);
            Scalar scalar = new Scalar(0, 255, 0);
            if (joueur.isMbappe()){
                scalar = new Scalar(5, 255, 255);
            }
            else {
                this.drawIndicateurPasse(image,dribbleur,joueur);
            }
            Imgproc.circle(image, center,  (int) joueur.getPosition().getRayon(),scalar , 2); // Cercle vert
    //                Imgproc.circle(image, center, 3, new Scalar(0, 0, 255), -1); // Centre en rouge

            id++;
        }

        // Sauvegarder l'image avec les cercles dessinés
        String outputPath = "var_result.jpg";
        Imgcodecs.imwrite(outputPath, image);
        System.out.println("Image sauvegardée : " + outputPath);
        return outputPath;
    }

    public void drawLineOfJoueur (Mat image,Joueur joueur,Var var) throws Exception {
        if (var.getTerrain().isHorizontal()){
            double x = joueur.getPosition().getCoordonne().getX();
            if (var.getEquipes().get(joueur.getIdEquipe()).isGauche()){
                x -= (joueur.getPosition().getRayon());
            }
            else {
                x += (joueur.getPosition().getRayon());
            }

            Point pt1 = new Point(x, 0);
            Point pt2 = new Point(x, var.getTerrain().getLargeur());
            Imgproc.line(image, pt1, pt2, new Scalar(5, 255, 255), 1);
        }
        else {
            double y = joueur.getPosition().getCoordonne().getY();
            if (var.getEquipes().get(joueur.getIdEquipe()).isGauche()){
                y -= (joueur.getPosition().getRayon());
            }
            else {
                y += (joueur.getPosition().getRayon());
            }

            Point pt1 = new Point(0,y );
            Point pt2 = new Point(var.getTerrain().getLongueur(), y);
            Imgproc.line(image, pt1, pt2, new Scalar(5, 255, 255), 1);
        }

    }

    public void drawIndicateurPasse (Mat image,Joueur j1,Joueur j2) throws Exception {

        Point pt1 = new Point(j1.getPosition().getCoordonne().getX(), j1.getPosition().getCoordonne().getY());
        Point pt2 = new Point(j2.getPosition().getCoordonne().getX(), j2.getPosition().getCoordonne().getY());
        Imgproc.arrowedLine(image, pt1, pt2, new Scalar(0,0,0), 2);
    }
}
