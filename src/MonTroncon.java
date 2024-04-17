import fr.ulille.but.sae_s2_2024.Lieu;
import fr.ulille.but.sae_s2_2024.ModaliteTransport;
import fr.ulille.but.sae_s2_2024.Trancon;

public class MonTroncon implements Trancon {
    private ModaliteTransport modalite;
    private final MonLieu LIEU1, LIEU2;

    public MonTroncon(ModaliteTransport modalite, MonLieu v1, MonLieu v2){
        this.modalite=modalite;
        this.LIEU1=v1;
        this.LIEU2=v2;
    }

    public MonTroncon inv(){
        return new MonTroncon(modalite, LIEU2, LIEU1);
    }

    public String toString(){
        return this.modalite + "-->" + this.LIEU1 +","+this.LIEU2;
    }

    @Override
    public Lieu getDepart() {
        return this.LIEU1;
    }

    @Override
    public Lieu getArrivee() {
        return this.LIEU2;
    }

    @Override
    public ModaliteTransport getModalite() {
        return this.modalite;
    }
}
