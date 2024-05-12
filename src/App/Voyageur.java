package App;

import fr.ulille.but.sae_s2_2024.Lieu;

public class Voyageur {
    private Lieu depart;
    private Lieu arrive;
    private final int NUMUSER;
    private static int cpt=0;

    public Voyageur(Lieu depart, Lieu arrive){
        this.depart=depart;
        this.arrive=arrive;
        this.NUMUSER=Voyageur.cpt;
        Voyageur.cpt++;
    }

    public Lieu getdepart() {return depart;}
    public Lieu getArrive() {return arrive;}
    public int getNUMUSER() {return NUMUSER;}
    public void setdepart(Lieu depart) {this.depart = depart;}
    public void setArrive(Lieu arrive) {this.arrive = arrive;}
}
