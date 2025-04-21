package annexe;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.awt.*;
import java.util.Arrays;

public class ColorConverter {
    static {
        // Charger la bibliothèque native OpenCV
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static Scalar [] generateScalar(Color color) {
        // Couleur RGB
        Scalar rgbColor = new Scalar(color.getRed(), color.getGreen(), color.getBlue());

        // Convertir la couleur RGB en HSV
        Mat rgbMat = new Mat(1, 1, CvType.CV_8UC3, rgbColor);
        Mat hsvMat = new Mat();
        Imgproc.cvtColor(rgbMat, hsvMat, Imgproc.COLOR_RGB2HSV);

        // Extraire les valeurs HSV
        double[] hsvValues = hsvMat.get(0, 0);

        // Afficher les valeurs HSV
        System.out.println("HSV: " + Arrays.toString(hsvValues));

        // Définir les valeurs Scalar min et max
        Scalar minScalar = new Scalar(hsvValues[0] - 10, 50, 50);
        Scalar maxScalar = new Scalar(hsvValues[0] + 10, 255, 255);

        // Afficher les valeurs Scalar min et max
        System.out.println("Scalar min: " + minScalar);
        System.out.println("Scalar max: " + maxScalar);
        return new Scalar[]{minScalar, maxScalar};
    }

    public static void main(String[] args) {
        // Couleur RGB
        Scalar rgbColor = new Scalar(255, 255, 255);

        // Convertir la couleur RGB en HSV
        Mat rgbMat = new Mat(1, 1, CvType.CV_8UC3, rgbColor);
        Mat hsvMat = new Mat();
        Imgproc.cvtColor(rgbMat, hsvMat, Imgproc.COLOR_RGB2HSV);

        // Extraire les valeurs HSV
        double[] hsvValues = hsvMat.get(0, 0);

        // Afficher les valeurs HSV
        System.out.println("HSV: " + Arrays.toString(hsvValues));

        // Définir les valeurs Scalar min et max
        Scalar minScalar = new Scalar(hsvValues[0] - 10, 50, 50);
        Scalar maxScalar = new Scalar(hsvValues[0] + 10, 255, 255);

        // Afficher les valeurs Scalar min et max
        System.out.println("Scalar min: " + minScalar);
        System.out.println("Scalar max: " + maxScalar);
    }
}
