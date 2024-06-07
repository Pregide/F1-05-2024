package App;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import App.exception.NoTravelFoundException;
import fr.ulille.but.sae_s2_2024.AlgorithmeKPCC;
import fr.ulille.but.sae_s2_2024.Chemin;
import fr.ulille.but.sae_s2_2024.Lieu;
import fr.ulille.but.sae_s2_2024.ModaliteTransport;
import fr.ulille.but.sae_s2_2024.MultiGrapheOrienteValue;
import fr.ulille.but.sae_s2_2024.Trancon;
import graph.MonLieu;
import graph.MonTroncon;

public class TestVoyageur {
    Voyageur v;
    Lieu l1, l2, l3, l4;
    Trancon t1, t2, t3, t4;
    MultiGrapheOrienteValue graph;
    List<Chemin> chemins;

    @BeforeEach
    public void initialise(){
        v = new Voyageur(null, null);

        l1 = new MonLieu("Lille");
        l2 = new MonLieu("Douai");
        l3 = new MonLieu("Arras");
        l4 = new MonLieu("Amiens");

        t1 = new MonTroncon(ModaliteTransport.TRAIN, l1, l2, 0.5, 10, 10);
        t2 = new MonTroncon(ModaliteTransport.AVION, l2, l3, 20, 10, 100);
        t3 = new MonTroncon(ModaliteTransport.BUS, l3, l4, 5, 15, 5);
        t4 = new MonTroncon(ModaliteTransport.TRAIN, l1, l4, 10, 30, 20);

        graph = new MultiGrapheOrienteValue();
        graph.ajouterSommet(l1);
        graph.ajouterSommet(l2);
        graph.ajouterSommet(l3);
        graph.ajouterSommet(l4);
        graph.ajouterArete(t1, 10);
        graph.ajouterArete(t2, 20);
        graph.ajouterArete(t3, 30);
        graph.ajouterArete(t4, 40);

        chemins = AlgorithmeKPCC.kpcc(graph, l1, l4, 5);
    }

    @Test
    public void testTrajetValid(){
        ArrayList<ModaliteTransport> listModa = new ArrayList<>();
        listModa.add(ModaliteTransport.AVION);
        listModa.add(ModaliteTransport.TRAIN);
        listModa.add(ModaliteTransport.BUS);

        for (Chemin chemin : chemins) {
            assertTrue(v.trajetValid(chemin, listModa));
        }

        listModa.remove(ModaliteTransport.TRAIN);

        for (Chemin chemin : chemins) {
            assertFalse(v.trajetValid(chemin, listModa));
        }
    }

    @Test
    public void testTranscription(){
        assertEquals("Lille ---TRAIN--> Amiens, Poids: 40.0", v.transcription(chemins.get(0)));
        assertEquals("Lille ---TRAIN--> Douai ---AVION--> Arras ---BUS--> Amiens, Poids: 60.0", v.transcription(chemins.get(1)));
    }

    @Test
    public void testTrajet(){
        String attempt = "Lille ---TRAIN--> Amiens, Poids: 40.0\nLille ---TRAIN--> Douai ---AVION--> Arras ---BUS--> Amiens, Poids: 60.0\n";
        ArrayList<ModaliteTransport> modaliteTransports = new ArrayList<>();
        v.setdepart(l1);
        v.setArrive(l4);

        for (ModaliteTransport modaliteTransport : ModaliteTransport.values()) {
            modaliteTransports.add(modaliteTransport);
        }
        
        try {
            assertEquals(attempt, v.trajet(graph, modaliteTransports, 2));
        } catch (NoTravelFoundException e){}

        boolean noTravel = false;
        try {
            v.trajet(graph, new ArrayList<ModaliteTransport>(), 5);
        } catch (NoTravelFoundException e){
            noTravel = true;
        }
        assertTrue(noTravel);
    }
}
