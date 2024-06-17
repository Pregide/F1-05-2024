package App;

/*
 * TO DO V2:
 * - Correspondences 
 * 
 * TO DO V3:
 * - Critère (si un seul critère, si 2 critères, si tout les critères) pour la V3
 */
import java.util.ArrayList;
import java.util.List;

import App.exception.NoTravelFoundException;
import fr.ulille.but.sae_s2_2024.AlgorithmeKPCC;
import fr.ulille.but.sae_s2_2024.Chemin;
import fr.ulille.but.sae_s2_2024.Lieu;
import fr.ulille.but.sae_s2_2024.ModaliteTransport;
import fr.ulille.but.sae_s2_2024.MultiGrapheOrienteValue;
import fr.ulille.but.sae_s2_2024.Trancon;
import graph.Correspondance;
import graph.MonLieu;
import graph.MonTroncon;
import graph.TypeCout;
import graph.exception.IncompatibleModalityException;  

public class Plateforme {
    private final static int IDX_DEPART = 0;
    private final static int IDX_DESTINATION = 1;
    private final static int IDX_MODALITE = 2;
    private final static int IDX_TEMPS = 3;
    private final static int IDX_POLLUTION = 4;
    private final static int IDX_PRIX = 5;

    public final static ArrayList<Lieu> lieux = new ArrayList<Lieu>();
    private final ArrayList<Trancon> troncons;
    private final ArrayList<Correspondance> correspondances;
    private final MultiGrapheOrienteValue graphe;

    public Plateforme(ArrayList<String> data) {
        this.troncons = new ArrayList<Trancon>();
        this.correspondances = new ArrayList<Correspondance>();
        this.graphe = new MultiGrapheOrienteValue();
        ventilation(data);
        addData();
    }

    public int getSizeLieux(){return lieux.size();}
    public int getSizeTroncons(){return troncons.size();}
    public int getSizeCorrespondances(){return correspondances.size();}
    public Lieu getLieu(int idx){return lieux.get(idx);}
    public MultiGrapheOrienteValue getGraphe(){return graphe;}

    private boolean isValid(String[] data){
        if(data.length != 6) return false;
        if(data[IDX_DEPART].equals("") || data[IDX_DEPART].equals("null")) return false;
        if(data[IDX_DESTINATION].equals("") || data[IDX_DESTINATION].equals("null")) return false;
        if(!isModality(data[IDX_MODALITE])) return false;
        
        try {
            if (Double.parseDouble(data[IDX_POLLUTION]) < 0) return false;
            if (Double.parseDouble(data[IDX_TEMPS]) < 0) return false;
            if (Double.parseDouble(data[IDX_PRIX]) < 0) return false;
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Séparation des données récupérer dans une ArrayList de tableau de String, chaque entré est un tajet et le tableau de cette entré répertorie la ville de départ, la ville d'arrivé, le moyen de transport, la pollution, le temps et le prix de ce trajet
     * 
     * @param data l'ensemble des données récupérer
     * @return retourne un tableau de 2 dimensions contenant toute les valeurs
     */
    public void ventilation(ArrayList<String> data){
        for (String chaine : data) {
            String[] parts = chaine.split(";");
            if (isValid(parts)) {
                listTroncon(parts);
            }
        }
    }

    /**
     * Listage des Lieux, si le lieu n'existe pas dans la list actuel nous l'ajoutons sinon nous ne faisons rien
     * 
     * @param lieu Nom d'un lieu 
     * @return Retourne une instance de MonLieu si le Lieu n'existe pas sinon retourne le Lieu déjà connu a partir de la liste lieux
     */
    public Lieu listLieux(String lieu){
        Lieu tmp = new MonLieu(lieu);
        if(lieux.contains(tmp)){
            return lieux.get(lieux.indexOf(tmp));
        }
        lieux.add(tmp);
        return tmp;
    }

    /**
     * Listage des Troncons, pour les deux villes un troncon est créer dans un sens puis dans l'autre 
     * 
     * @param tab Toute les informations sur un troncon
     */
    public void listTroncon(String[] tab){
        Lieu dep = listLieux(tab[IDX_DEPART]);
        ModaliteTransport modalite = ModaliteTransport.valueOf(tab[IDX_MODALITE].toUpperCase());
        double co2 = Double.parseDouble(tab[IDX_POLLUTION]);
        double temps = Double.parseDouble(tab[IDX_TEMPS]);
        double prix = Double.parseDouble(tab[IDX_PRIX]);

        if(!isModality(tab[IDX_DESTINATION])){
            Lieu dest = listLieux(tab[IDX_DESTINATION]);

            Trancon tmp = new MonTroncon(modalite, dep, dest, co2, temps, prix);
            if(!troncons.contains(tmp)){
                troncons.add(tmp);
                troncons.add(new MonTroncon(modalite, dest, dep, co2, temps, prix));
            }
        } else {
            ModaliteTransport moda2 = ModaliteTransport.valueOf(tab[IDX_DESTINATION].toUpperCase());
            try {
                correspondances.add(new Correspondance(dep, moda2, modalite, co2, temps, prix));
            } catch (IncompatibleModalityException e) {}
        }
    }

    public boolean isModality(String str){
        try {
            ModaliteTransport.valueOf(str.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private void addData(){
        for(Lieu lieu : lieux){
            graphe.ajouterSommet(lieu);
        }
        for(Trancon troncon : troncons){
            graphe.ajouterArete(troncon, ((MonTroncon) troncon).getCout(TypeCout.CO2));
        }
    }

    /**
     * Change le poids de toute les aretes du graphe selon le critère souhaiter de l'énumération TypeCout
     * 
     * @param cout Critère de séléction
     */
    public void changeCritère(List<TypeCout> crit){
        for (Trancon tr : graphe.aretes()) {
            graphe.modifierPoidsArete(tr, (((MonTroncon) tr).getCout(crit.get(0))*0.6 + ((MonTroncon) tr).getCout(crit.get(1))*0.3 + ((MonTroncon) tr).getCout(crit.get(2)))*0.1);
        }
    } //change a partir de la liste de cout mettre le premier critère a *0.6 le 2e a 0.3 et le 3e a 0.1

    /**
     * Affiche la liste de tous les Lieux connus
     * 
     * @return Retourne la liste des lieux associé à un numéro 
     */
    public String afficheListLieu(){
        String res = "";
        int i = 1;
        for (Lieu lieu : lieux) {
            res += i + ". " + lieu + "\n";
            i++;
        }
        return res;
    }

    public ArrayList<String> trajet(Lieu depart, Lieu arrive, List<ModaliteTransport> listModa, int nbTrajetDemande, int limit) throws NoTravelFoundException{
        List<Chemin> chemin = AlgorithmeKPCC.kpcc(graphe, depart, arrive, nbTrajetDemande);
        if(chemin.size() > 0){
            ArrayList<String> res = new ArrayList<>();
            for (Chemin trajet : chemin) {
                if(trajetValid(trajet, listModa, limit)) res.add(transcription(trajet));
            }
            if(res.size() > 0) return res;
        } 
        throw new NoTravelFoundException("No Travel Possible");
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

    public boolean trajetValid(Chemin chem, List<ModaliteTransport> listModa, int limit){
        for (Trancon tr : chem.aretes()) {
            if(!listModa.contains(tr.getModalite()) || chem.poids() > limit) return false;
        }
        return true;
    }
}
