package App;

import java.util.ArrayList;

import fr.ulille.but.sae_s2_2024.Lieu;
import fr.ulille.but.sae_s2_2024.ModaliteTransport;
import fr.ulille.but.sae_s2_2024.MultiGrapheOrienteValue;
import fr.ulille.but.sae_s2_2024.Trancon;
import graph.MonLieu;
import graph.MonTroncon;
import graph.TypeCout;

public class Plateforme {
    private final static int MAX_TIME_DURA = 180;
    private final ArrayList<Lieu> lieux = new ArrayList<Lieu>();
    private final ArrayList<Trancon> troncons = new ArrayList<Trancon>();
    private final MultiGrapheOrienteValue graphe = new MultiGrapheOrienteValue();

    public Plateforme(String[] data){
        ventilation(data);
        addData();
    }

    public int getSizeLieux(){return lieux.size();}
    public int getSizeTroncons(){return troncons.size();}
    public Lieu getLieu(int idx){return lieux.get(idx);}
    public MultiGrapheOrienteValue getGraphe(){return graphe;}

    /**
     * prend un tableau de donnée d'une dimmension et vérifie si toutes les données sont valides à pour la ventilation
     * 
     * @param data les données à vérifier
     * @return retourne si les données sont valides ou non
     */
    private boolean isValid(String[] data){
        for (int i=0;i<data.length;++i){
            if (data[i].charAt(0) == '-' || data[i].equals("null")){
                return false;
            }
        }
        return true;
    }

    /**
     * Séparation des données récupérer dans un tableau a 2 dimmansions
     * 
     * @param data l'ensemble des données récupérer
     * @return retourne un tableau de 2 dimensions contenant toute les valeurs
     */
    public ArrayList<String[]> ventilation(String[] data) {
        ArrayList<String[]> res = new ArrayList<String[]>();
        for (String chaine : data) {
            String[] parts = chaine.split(";");
            if (parts.length == 6 && isValid(parts)) {
                res.add(parts);
                listTroncon(parts);
            }
        }
        return res;
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
        Lieu dep = listLieux(tab[0]);
        Lieu dest = listLieux(tab[1]);
        ModaliteTransport modalite = ModaliteTransport.valueOf(tab[2].toUpperCase());
        double co2 = Double.parseDouble(tab[3]);
        double temps = Double.parseDouble(tab[4]);
        double prix = Double.parseDouble(tab[5]);

        troncons.add(new MonTroncon(modalite, dep, dest, co2, temps, prix));
        troncons.add(new MonTroncon(modalite, dest, dep, co2, temps, prix));
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
    public void changeCritère(TypeCout cout){
        for (Trancon tr : graphe.aretes()) {
            graphe.modifierPoidsArete(tr, ((MonTroncon) tr).getCout(cout));
        }
    } 

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

    public String toString(){
        return "";
    }
}
