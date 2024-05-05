package graph;

import fr.ulille.but.sae_s2_2024.Lieu;

public class MonLieu implements Lieu{
    private String nom;
    
    public MonLieu(String nom){
        this.nom=nom;
    }

    public boolean equals(Object obj){
        if(obj == null) return false;
        if(obj == this) return true;
        if(obj.getClass() == MonLieu.class){
            MonLieu tr = (MonLieu) obj;
            return nom.equals(tr.getNom());
        }
        return false;
    }

    public String getNom(){
        return nom;
    }

    public String toString(){
        return this.nom;
    }
}
