package graph;

import java.util.HashMap;
import java.util.Map;

import fr.ulille.but.sae_s2_2024.Lieu;
import fr.ulille.but.sae_s2_2024.ModaliteTransport;
import fr.ulille.but.sae_s2_2024.Trancon;

public class MonTroncon implements Trancon {
    private final ModaliteTransport MODALITE;
    private final Lieu DEPART, ARRIVEE;
    private final Map<TypeCout, Double> COUT = new HashMap<TypeCout, Double>();

    public MonTroncon(ModaliteTransport modalite, Lieu depart, Lieu arrivee, double co2, double time, double price){
        this.MODALITE = modalite;
        this.DEPART = depart;
        this.ARRIVEE = arrivee;
        this.COUT.put(TypeCout.CO2, co2);
        this.COUT.put(TypeCout.TEMPS, time);
        this.COUT.put(TypeCout.PRIX, price);
    }

    public boolean equals(Object o){
        if(o == this) return true;
        if(o == null) return false;
        if(o.getClass() == MonTroncon.class){
            MonTroncon tr = (MonTroncon) o;
            return DEPART.equals(tr.DEPART) && ARRIVEE.equals(tr.ARRIVEE) && MODALITE.equals(tr.MODALITE);
        }
        return false;
    }

    public String toString(){
        return MODALITE + "-->" + DEPART + "," + ARRIVEE;
    }

    public Lieu getDepart() {
        return DEPART;
    }

    public Lieu getArrivee() {
        return ARRIVEE;
    }

    public ModaliteTransport getModalite() {
        return MODALITE;
    }

    public double getCout(TypeCout cout){
        return COUT.get(cout);
    }
}
