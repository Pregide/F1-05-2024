package App;

import java.util.List;

import fr.ulille.but.sae_s2_2024.AlgorithmeKPCC;
import fr.ulille.but.sae_s2_2024.Chemin;
import fr.ulille.but.sae_s2_2024.Lieu;
import fr.ulille.but.sae_s2_2024.MultiGrapheOrienteValue;

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

    public String trajet(MultiGrapheOrienteValue graphe, int nbTrajetDemande) throws NoTravelFindException{
        List<Chemin> chemin = AlgorithmeKPCC.kpcc(graphe, depart, arrive, nbTrajetDemande);
        if(chemin.size() > 0){
            String res = "";
            for (Chemin trajet : chemin) {
                res += trajet + "\n";
            }
            return res;
        } 
        throw new NoTravelFindException();
    }

    /*
     * symboliser un trajet de la façon suivante :
     * 
     * Marseille--Train-->Paris, Paris--Avion-->Lille, Lille--Train-->Londre
     * 
     * Le voyageur utilise le train de Marseille a Paris (on s'en fou par quel autre ville il passe) puis fait une correspondence pour prendre un avion direction 
     * Lille et une autre correspondence pour terminé son trajet vers Londre en Train
     */
}
