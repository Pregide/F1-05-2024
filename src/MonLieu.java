import fr.ulille.but.sae_s2_2024.Lieu;

public class MonLieu implements Lieu{
    private String nom;
    public MonLieu(String nom){
        this.nom=nom;
    }

    public String toString(){
        return this.nom;
    }
}
