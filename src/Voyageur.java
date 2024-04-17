public class Voyageur {

    private final int ID;
    private static int cpt;
    private final String NOM;

    Voyageur(String nom){
        ++cpt;
        this.ID=cpt;
        this.NOM=nom;
    }

    public int getID(){
        return this.ID;
    }

    public String getNom(){
        return this.NOM;
    }
}
