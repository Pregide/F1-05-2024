package App;

import fr.ulille.but.sae_s2_2024.Lieu;
import graph.MonLieu;

public class Voyageur {
    private MonLieu depart;
    private MonLieu arrive;
    private final int NUMUSER;
    private static int cpt=0;

    public Voyageur(MonLieu depart, MonLieu arrive){
        this.depart=depart;
        this.arrive=arrive;
        this.NUMUSER=Voyageur.cpt;
        Voyageur.cpt++;
    }

    public Lieu getdepart() {return depart;}
    public Lieu getArrive() {return arrive;}
    public int getNUMUSER() {return NUMUSER;}
    public void setdepart(MonLieu depart) {this.depart = depart;}
    public void setArrive(MonLieu arrive) {this.arrive = arrive;}
}
