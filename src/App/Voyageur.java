package App;

import java.util.List;

import App.exception.NoTravelFoundException;
import fr.ulille.but.sae_s2_2024.AlgorithmeKPCC;
import fr.ulille.but.sae_s2_2024.Chemin;
import fr.ulille.but.sae_s2_2024.Lieu;
import fr.ulille.but.sae_s2_2024.ModaliteTransport;
import fr.ulille.but.sae_s2_2024.MultiGrapheOrienteValue;
import fr.ulille.but.sae_s2_2024.Trancon;

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

    public String trajet(MultiGrapheOrienteValue graphe, ModaliteTransport moda, int nbTrajetDemande) throws NoTravelFoundException{
        List<Chemin> chemin = AlgorithmeKPCC.kpcc(graphe, depart, arrive, nbTrajetDemande);
        if(chemin.size() > 0){
            String res = "";
            for (Chemin trajet : chemin) {
                res += transcription(trajet) + "\n";
            }
            if(res.length() > 0){
                return res;
            }            
        } 
        throw new NoTravelFoundException();
    }

    public String transcription(Chemin chemin){
        String res = "";
        ModaliteTransport lastModa = null;
        Lieu lastLieu = null;
        for (Trancon tr : chemin.aretes()){
            lastLieu = tr.getArrivee();
            if(lastModa != tr.getModalite()){
                lastModa = tr.getModalite();
                res += tr.getDepart() + " ---" + lastModa + "--> ";
            }
        }
        res +=  lastLieu + ", Poids: " + chemin.poids();
        return res;
    }
}
