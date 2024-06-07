package graph;

import java.util.HashMap;

import fr.ulille.but.sae_s2_2024.Lieu;
import fr.ulille.but.sae_s2_2024.ModaliteTransport;
import graph.exception.IncompatibleModalityException;

public class Correspondance implements Lieu {
    private final Lieu VILLE;
    private final ModaliteTransport PROV;
    private final ModaliteTransport SUIE;
    private final HashMap<TypeCout, Double> COUT;

    public Correspondance(Lieu ville, ModaliteTransport prov, ModaliteTransport suie, double co2, double temps, double prix) throws IncompatibleModalityException {
        if(prov == suie) throw new IncompatibleModalityException();
        this.VILLE = ville;
        this.PROV = prov;
        this.SUIE = suie;
        this.COUT = new HashMap<>();
        this.COUT.put(TypeCout.CO2, co2);
        this.COUT.put(TypeCout.TEMPS, temps);
        this.COUT.put(TypeCout.PRIX, prix);
    }

    public Lieu getVILLE() {
        return VILLE;
    }

    public ModaliteTransport getPROV() {
        return PROV;
    }

    public ModaliteTransport getSUIE() {
        return SUIE;
    }

    public double getCO2(){
        return COUT.get(TypeCout.CO2);
    }

    public double getTemps(){
        return COUT.get(TypeCout.TEMPS);
    }

    public double getPrix(){
        return COUT.get(TypeCout.PRIX);
    }

    public boolean equals(Object o){
        if (o == null) return false;
        if (o == this) return true;
        if (o.getClass() == Correspondance.class){
            Correspondance tmp = (Correspondance) o;
            return VILLE.equals(tmp.VILLE) && PROV == tmp.PROV && SUIE == tmp.SUIE;
        }
        return false;
    }

    public String toString(){
        return PROV.toString() + " --" + VILLE + "-> " + SUIE.toString();
    }
}
