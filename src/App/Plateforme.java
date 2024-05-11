package App;

import java.util.ArrayList;

import fr.ulille.but.sae_s2_2024.Lieu;
import fr.ulille.but.sae_s2_2024.ModaliteTransport;
import fr.ulille.but.sae_s2_2024.MultiGrapheOrienteValue;
import graph.MonLieu;
import graph.MonTroncon;

public class Plateforme {
    private final ArrayList<Lieu> lieux = new ArrayList<Lieu>();
    private final ArrayList<MonTroncon> troncons = new ArrayList<MonTroncon>();
    private final MultiGrapheOrienteValue graphe = new MultiGrapheOrienteValue();


    public Plateforme(String[] data){
        addData(ventilation(data));
    }

    public int getSizeLieux(){return lieux.size();}
    public int getSizeTroncons(){return troncons.size();}

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
    public String[][] ventilation(String[] data) {
        String[][] res = new String[data.length][6];
        for (int i = 0; i < data.length; i++) {
            String[] parts = data[i].split(";");
            if (isValid(parts)) {
                if (parts.length == 6) {
                    res[i] = parts;
                    MonLieu dep = new MonLieu(parts[0]);
                    MonLieu dest = new MonLieu(parts[1]);
                    listLieux(dep, dest);
                    listTroncon(dep, dest, parts);
                } else {
                    res[i] = new String[] {"Données insuffisantes après split"};
                }
            } else {
                res[i] = new String[] {"Une des valeurs de cette ligne est invalide"};
            }
        }
        return res;
    }

    /**
     * Listage des Lieux, si le lieu n'existe pas dans la list actuel nous l'ajoutons sinon nous ne faisons rien
     * 
     * @param dep Un lieu de départ
     * @param dest Un lieu de destination
     */
    public void listLieux(Lieu dep, Lieu dest){
        if(!lieux.contains(dep)){lieux.add(dep);}
        if(!lieux.contains(dest)){lieux.add(dest);}
    }

    /**
     * Listage des Troncons, pour les deux villes un troncon est créer dans un sens puis dans l'autre 
     * 
     * @param dep Lieu de départ
     * @param dest Lieu de destination
     * @param tab Toute les informations sur un troncon
     */
    public void listTroncon(Lieu dep, Lieu dest, String[] tab){
        troncons.add(new MonTroncon(ModaliteTransport.valueOf(tab[2].toUpperCase()), dep, dest, Double.parseDouble(tab[3]), Double.parseDouble(tab[4]), Double.parseDouble(tab[5])));
        troncons.add(new MonTroncon(ModaliteTransport.valueOf(tab[2].toUpperCase()), dest, dep, Double.parseDouble(tab[3]), Double.parseDouble(tab[4]), Double.parseDouble(tab[5])));
    }

    /**
     * 
     * @param data
     */
    private void addData(String[][] data){
        for(Lieu lieu : lieux){
            graphe.ajouterSommet(lieu);
        }
        for(MonTroncon troncon : troncons){
            graphe.ajouterArete(troncon, troncon.getCoutCo2());
        }
    }

    

    public String toString(){
        return "";
    }
}
