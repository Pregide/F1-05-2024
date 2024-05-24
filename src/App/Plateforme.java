package App;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.ulille.but.sae_s2_2024.AlgorithmeKPCC;
import fr.ulille.but.sae_s2_2024.Chemin;
import fr.ulille.but.sae_s2_2024.Lieu;
import fr.ulille.but.sae_s2_2024.ModaliteTransport;
import fr.ulille.but.sae_s2_2024.MultiGrapheOrienteValue;
import fr.ulille.but.sae_s2_2024.Trancon;
import graph.MonLieu;
import graph.MonTroncon;
import graph.TypeCout;
import java.util.Scanner;  

public class Plateforme {
    private final static int IDX_TEMPS = 5;
    private final static int IDX_POLLUTION = 4;
    private final static int IDX_PRIX = 3;
    private final static int IDX_MODALITE = 2;
    private final static int IDX_DESTINATION = 1;
    private final static int IDX_DEPART = 0;
    private final static String RESSOURCE = "res/";

    private final static int MAX_TIME_DURA = 180;
    private final ArrayList<Lieu> lieux = new ArrayList<Lieu>();
    private final ArrayList<Trancon> troncons = new ArrayList<Trancon>();
    private final MultiGrapheOrienteValue graphe = new MultiGrapheOrienteValue();

    public Plateforme(String data){
        ventilation(scan(data));
        addData();
    }

    public int getSizeLieux(){return lieux.size();}
    public int getSizeTroncons(){return troncons.size();}
    public Lieu getLieu(int idx){return lieux.get(idx);}
    public MultiGrapheOrienteValue getGraphe(){return graphe;}

    
    private String[] scan(String path){
        try (Scanner sc = new Scanner(new File(RESSOURCE + path))){
            sc.useDelimiter(";");
            String[] data=new String[]{};
            int i=0;
            while (sc.hasNext())  
            {  
                data[i]+=sc.next();
                i++;
            } 
            return data;
        } catch(FileNotFoundException e) {
            System.out.println("File not found: "); e.printStackTrace();
        } 
        return null;
    }

    private boolean isValid(String[] data){
        for (int i=0;i<data.length;++i){
            if (data[i].charAt(0) == '-' || data[i].equals("null")){
                return false;
            }
        }
        return true;
    }

    /**
     * Séparation des données récupérer dans une ArrayList de tableau de String, chaque entré est un tajet et le tableau de cette entré répertorie la ville de départ, la ville d'arrivé, le moyen de transport, la pollution, le temps et le prix de ce trajet
     * 
     * @param data l'ensemble des données récupérer
     * @return retourne un tableau de 2 dimensions contenant toute les valeurs
     */
    public ArrayList<String[]> ventilation(String[] data) {
        if(data != null){
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
        return null;
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
        Lieu dest = listLieux(tab[IDX_DESTINATION]);
        ModaliteTransport modalite = ModaliteTransport.valueOf(tab[IDX_MODALITE].toUpperCase());
        double co2 = Double.parseDouble(tab[IDX_POLLUTION]);
        double temps = Double.parseDouble(tab[IDX_TEMPS]);
        double prix = Double.parseDouble(tab[IDX_PRIX]);

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

    public String toString(Voyageur v, int nbTrajetDemande){
        List<Chemin> chemin = AlgorithmeKPCC.kpcc(graphe, v.getdepart(), v.getArrive(), nbTrajetDemande);
        String res = "";
        for (Chemin trajet : chemin) {
            res += trajet + "\n";
        }
        return res;
    }
}
