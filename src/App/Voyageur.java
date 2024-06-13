package App;

import java.util.List;

import fr.ulille.but.sae_s2_2024.Chemin;

public class Voyageur {
    private final int NUMUSER;
    private static int cpt=0;
    private List<Chemin> trajets;

    public Voyageur(){
        this.NUMUSER=Voyageur.cpt;
        Voyageur.cpt++;
    }

    public int getNUMUSER() {return NUMUSER;}
    public List<Chemin> getTrajets(){return trajets;}

    public void addTrajet(Chemin ch){
        trajets.add(ch);
    }
}
