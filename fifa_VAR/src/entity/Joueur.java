package entity;

import annexe.Position;

public class Joueur {
    int id;
    int idEquipe;
    Position position;
    boolean isMbappe = false;
    boolean isGK = false;
    boolean possession = false;
    boolean isAttack = false;


    public Joueur() {
    }

    public Joueur(int id, Position position) {
        this.id = id;
        this.position = position;
    }

    public Joueur(int id, Position position, boolean isMbappe) {
        this.id = id;
        this.position = position;
        this.isMbappe = isMbappe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isMbappe() {
        return isMbappe;
    }

    public void setMbappe(boolean mbappe) {
        isMbappe = mbappe;
    }

    public boolean isGK() {
        return isGK;
    }

    public void setGK(boolean GK) {
        isGK = GK;
    }

    public boolean isPossession() {
        return possession;
    }

    public void setPossession(boolean possession) {
        this.possession = possession;
    }

    public boolean isAttack() {
        return isAttack;
    }

    public void setAttack(boolean attack) {
        isAttack = attack;
    }

    public int getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(int idEquipe) {
        this.idEquipe = idEquipe;
    }

    public String getStatus (){
        String status = "IN";
        if(this.isMbappe()){
            status = "OUT";
        }
        return status;
    }
}
