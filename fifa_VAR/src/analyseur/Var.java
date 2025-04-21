package analyseur;

import annexe.Position;
import entity.Ballon;
import entity.Equipe;
import entity.Joueur;
import entity.Terrain;
import org.opencv.core.Scalar;

import java.util.ArrayList;
import java.util.List;

public class Var {
    List<Equipe> equipes = new ArrayList<>();
    Ballon ballon;
    Terrain terrain;


    public Var() {
    }

    public Var(List<Equipe> equipes, Ballon ballon,Terrain terrain) {
        this.equipes = equipes;
        this.ballon = ballon;
        this.terrain = terrain;
        this.initializeEquipe();
    }

    public List<Equipe> getEquipes() {
        return equipes;
    }

    public void setEquipes(List<Equipe> equipes) {
        this.equipes = equipes;
    }

    public Ballon getBallon() {
        return ballon;
    }

    public void setBallon(Ballon ballon) {
        this.ballon = ballon;
    }

    public void AddEquipe(Equipe equipe) {
        this.equipes.add(equipe);
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public Joueur findDribleur(){
        Joueur joueur = this.getEquipes().get(0).getJoueurs().get(0);
        double min = Position.distanceBetween(this.getEquipes().get(0).getJoueurs().get(0).getPosition(), ballon.getPosition()) ;
        for (Equipe equipe : this.getEquipes()) {
            for (Joueur j : equipe.getJoueurs()) {
                if (Position.distanceBetween(j.getPosition(), ballon.getPosition()) < min) {
                    min = Position.distanceBetween(j.getPosition(), ballon.getPosition());
                    joueur = j;

                }
            }
        }
        joueur.setPossession(true);
        System.out.println("Dribleur :"+joueur.getIdEquipe()+""+joueur.getId());
        return joueur;
    }

    public Joueur getJoueurDistant(Joueur joueur,boolean isGauche) {
        for (Equipe equipe : this.getEquipes()) {
            if (this.getTerrain().isHorizontal()){
                for (Joueur j : equipe.getJoueurs()) {
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
            }
            else {
                for (Joueur j : equipe.getJoueurs()) {
                    if (isGauche) {
                        if (j.getPosition().getCoordonne().getY() < joueur.getPosition().getCoordonne().getY()) {
                            joueur = j;
                        }
                    }
                    else {
                        if (j.getPosition().getCoordonne().getY() > joueur.getPosition().getCoordonne().getY()) {
                            joueur = j;
                        }
                    }
                }
            }
        }
        return joueur;
    }

    public Joueur findJoueurProche(Position position){
        Joueur joueur = this.getEquipes().get(0).getJoueurs().get(0);
        double min = Position.distanceBetween(this.getEquipes().get(0).getJoueurs().get(0).getPosition(), position) ;
        for (Equipe equipe : this.getEquipes()) {
            for (Joueur j : equipe.getJoueurs()) {
                if (Position.distanceBetween(j.getPosition(), position) < min) {
                    min = Position.distanceBetween(j.getPosition(), position);
                    joueur = j;

                }
            }
        }
        return joueur;
    }

    public Equipe findTeamByisAttack(boolean isAttack){
        for (Equipe equipe : this.getEquipes()) {
            if (equipe.isAttack() == isAttack){
                return equipe;
            }
        }
        return null;
    }

    public Joueur[] getPlayerChecked(){
        Equipe team = findTeamByisAttack(true);
        Joueur joueur = team.findPossesseur();
        List<Joueur> result = new ArrayList<>();
        for (Joueur j : team.getJoueurs()) {
            if (this.getTerrain().isHorizontal()){
                if (team.isGauche()){
                    if (j.getPosition().getCoordonne().getX() > joueur.getPosition().getCoordonne().getX()) {
                        result.add(j);
                    }
                }
                else {
                    if (j.getPosition().getCoordonne().getX() < joueur.getPosition().getCoordonne().getX()) {
                        result.add(j);
                    }
                }
            }
            else {
                if (team.isGauche()){
                    if (j.getPosition().getCoordonne().getY() > joueur.getPosition().getCoordonne().getY()) {
                        result.add(j);
                    }
                }
                else {
                    if (j.getPosition().getCoordonne().getY() < joueur.getPosition().getCoordonne().getY()) {
                        result.add(j);
                    }
                }
            }

        }
        return result.toArray(new Joueur[]{});
    }

    public Joueur getlastDefender(){
        Equipe equipe = findTeamByisAttack(false);
        Joueur gk = equipe.findGK();
        Joueur last = equipe.findNotGK();
        if (this.getTerrain().isHorizontal()){
            double min  = Math.abs(gk.getPosition().getCoordonne().getX() - last.getPosition().getCoordonne().getX());
            for (Joueur j : equipe.getJoueurs()) {
                if (Math.abs(gk.getPosition().getCoordonne().getX() - j.getPosition().getCoordonne().getX()) < min && !j.isGK()){
                    min = Math.abs(gk.getPosition().getCoordonne().getX() - j.getPosition().getCoordonne().getX());
                    last = j;
                }
            }
        }
        else {
            double min  = Math.abs(gk.getPosition().getCoordonne().getY() - last.getPosition().getCoordonne().getY());
            for (Joueur j : equipe.getJoueurs()) {
                if (Math.abs(gk.getPosition().getCoordonne().getY() - j.getPosition().getCoordonne().getY()) < min && !j.isGK()){
                    min = Math.abs(gk.getPosition().getCoordonne().getY() - j.getPosition().getCoordonne().getY());
                    last = j;
                }
            }
        }
        System.out.println("Last : "+last.getIdEquipe()+"||"+last.getId());
        return last;
    }

    public Joueur[] findMbappe(){
        Equipe equipe = this.findTeamByisAttack(false);
        Joueur lastDefender = this.getlastDefender();
        List<Joueur> result = new ArrayList<>();
        for (Joueur joueur : this.getPlayerChecked()){
            if (this.getTerrain().isHorizontal()){
                if (equipe.isGauche()){
                    if (joueur.getPosition().getCoordonne().getX()<lastDefender.getPosition().getCoordonne().getX()){
                        joueur.setMbappe(true);
                        result.add(joueur);
                    }
                }
                else{
                    if (joueur.getPosition().getCoordonne().getX()>lastDefender.getPosition().getCoordonne().getX()){
                        joueur.setMbappe(true);
                        result.add(joueur);
                    }
                }
            }
            else {
                if (equipe.isGauche()){
                    if (joueur.getPosition().getCoordonne().getY()<lastDefender.getPosition().getCoordonne().getY()){
                        joueur.setMbappe(true);
                        result.add(joueur);
                    }
                }
                else{
                    if (joueur.getPosition().getCoordonne().getY()>lastDefender.getPosition().getCoordonne().getY()){
                        joueur.setMbappe(true);
                        result.add(joueur);
                    }
                }
            }
        }
        return result.toArray(new Joueur[]{});
    }

    public Var generate (String filePath,Terrain terrain) throws Exception {
        List<Equipe> equipes = new Equipe().makeEquipe(filePath);
        Ballon ballon = new ImageAnalyseur().findBallon(filePath,new Scalar(0,0,0),new Scalar(180,255,50));
        return new Var(equipes,ballon,terrain);
    }

    public void initializeEquipe(){
        for (Equipe equipe : this.getEquipes()) {
            equipe.setGauche(false);
            equipe.setAttack(false);
            equipe.initializeJoueur();
        }
        Joueur joueur = this.findDribleur();

        for (Equipe equipe : equipes) {
            for (Joueur j : equipe.getJoueurs()) {
                if (j.getPosition().getCoordonne().getX() == joueur.getPosition().getCoordonne().getX() && j.getPosition().getCoordonne().getY() == joueur.getPosition().getCoordonne().getY()){
                    equipe.setAttack(true);
                }
            }
        }

        // initialization du gardient de but
        Joueur gk_gauche = this.findJoueurProche(new Position(1,this.getTerrain().getCampBut(true)));
        Joueur gk_droite = this.findJoueurProche(new Position(1,this.getTerrain().getCampBut(false)));
        gk_gauche.setGK(true);
        gk_droite.setGK(true);
        System.out.println("GK Gauche :"+gk_gauche.getIdEquipe()+""+gk_gauche.getId());
        System.out.println("GK Droite :"+gk_droite.getIdEquipe()+""+gk_droite.getId());


        // initialization des coter
        Equipe equipe1 = this.findTeamByisAttack(true);
        Equipe equipe2 = this.findTeamByisAttack(false);
        if (equipe1.isMonJoueur(gk_gauche)){
            equipe1.setGauche(true);
            equipe2.setGauche(false);
        }
        if (equipe1.isMonJoueur(gk_droite)){
            equipe1.setGauche(false);
            equipe2.setGauche(true);
        }

    }
}
