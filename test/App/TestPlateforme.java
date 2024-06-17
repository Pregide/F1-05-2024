package App;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.ulille.but.sae_s2_2024.AlgorithmeKPCC;
import fr.ulille.but.sae_s2_2024.Chemin;
import fr.ulille.but.sae_s2_2024.Lieu;
import fr.ulille.but.sae_s2_2024.ModaliteTransport;
import fr.ulille.but.sae_s2_2024.MultiGrapheOrienteValue;
import fr.ulille.but.sae_s2_2024.Trancon;
import graph.MonLieu;
import graph.MonTroncon;
import graph.TypeCout;

public class TestPlateforme {
    Plateforme p;
    ArrayList<String> given;
    Lieu l1, l2, l3, l4;
    Trancon t1, t2, t3, t4;
    MultiGrapheOrienteValue graph;
    List<Chemin> chemins;

    @BeforeEach
    public void initial(){
        given = new ArrayList<>();
        p = new Plateforme(given);

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
    public void testInitNull(){
        boolean nullPointer = false;

        try {
            p = new Plateforme(null);
        } catch (NullPointerException e){
            nullPointer = true;
        }

        assertTrue(nullPointer);
    }

    @Test
    public void testVentilation(){
        assertEquals(0, p.getSizeTroncons());

        given.add("a;b;Train;30;20;10");
        given.add("t;t;t;z;z;z");
        p.ventilation(given);

        assertEquals(2, p.getSizeTroncons());
    }

    @Test
    public void testIsValid() {
        //Test avec valeur négative
        assertEquals(0, p.getSizeTroncons());
        given.add("a;b;Train;-1;2;3");
        p.ventilation(given);
        assertEquals(0, p.getSizeTroncons());

        //Test avec valeur null
        assertEquals(0, p.getSizeTroncons());
        given.add("a;b;Train;null;2;3");
        p.ventilation(given);
        assertEquals(0, p.getSizeTroncons());

        //Test avec champ manquant
        assertEquals(0, p.getSizeTroncons());
        given.add("a;b;Train;1;2");
        p.ventilation(given);
        assertEquals(0, p.getSizeTroncons());

        //Test avec champ supplémentaire
        assertEquals(0, p.getSizeTroncons());
        given.add("a;b;Train;1;2;3;4");
        p.ventilation(given);
        assertEquals(0, p.getSizeTroncons());
    }

    @Test
    public void testListLieu(){
        Plateforme.lieux.clear();
        assertEquals(0, p.getSizeLieux());
        p.listLieux("Arras");
        assertEquals(1, p.getSizeLieux());
        p.listLieux("Arras");
        assertEquals(1, p.getSizeLieux());
    }

    @Test
    public void testListTroncon(){
        assertEquals(0, p.getSizeTroncons());
        assertEquals(0, p.getSizeCorrespondances());

        given.add("Lion;Boiry;Train;10;20;30");
        given.add("Lion;Boiry;Avion;40;50;60");
        given.add("Courcheveille;Dijon;Bus;70;80;90");
        given.add("Lion;Train;Avion;30;20;10");
        
        p.ventilation(given);

        assertEquals(6, p.getSizeTroncons());
        assertEquals(1, p.getSizeCorrespondances());
    }

    @Test
    public void testAddData(){

        assertEquals(0, p.getSizeLieux());
        assertEquals(0, p.getSizeTroncons());

        given.add("a;b;Train;10;11;12");

        p.ventilation(given);
        assertEquals(2, p.getSizeLieux());
        assertEquals(2, p.getSizeTroncons());

        given.add("a;c;Train;13;14;15");
        p = new Plateforme(given);
        assertEquals(3, p.getSizeLieux());
        assertEquals(4, p.getSizeTroncons());
    }

    @Test
    public void testChangeCritère(){
        given.add("a;b;Train;1;2;3");
        p.ventilation(given);
        ArrayList<TypeCout> listCrit = new ArrayList<>();

        listCrit.add(TypeCout.CO2);
        p.changeCritère(listCrit);
        for (Trancon tr : p.getGraphe().aretes()) {
            assertEquals(1, p.getGraphe().getPoidsArete(tr));
        }
        
        listCrit.remove(TypeCout.CO2);
        listCrit.add(TypeCout.TEMPS);
        p.changeCritère(listCrit);
        for (Trancon tr : p.getGraphe().aretes()) {
            assertEquals(2, p.getGraphe().getPoidsArete(tr));
        }

        listCrit.remove(TypeCout.TEMPS);
        listCrit.add(TypeCout.PRIX);
        p.changeCritère(listCrit);
        for (Trancon tr : p.getGraphe().aretes()) {
            assertEquals(3, p.getGraphe().getPoidsArete(tr));
        }
    }

    @Test
    public void testIsModality(){
        assertTrue(p.isModality("Train"));
        assertTrue(p.isModality("train"));
        assertTrue(p.isModality("TRAIN"));
        assertFalse(p.isModality("Camion"));
        assertFalse(p.isModality("train."));
        assertFalse(p.isModality("trai"));
    }

    @Test
    public void testTranscription(){
        assertEquals("Lille ---TRAIN--> Amiens, Poids: 40.0", p.transcription(chemins.get(0)));
        assertEquals("Lille ---TRAIN--> Douai ---AVION--> Arras ---BUS--> Amiens, Poids: 60.0", p.transcription(chemins.get(1)));
    }

    @Test
    public void testTrajetValid(){
        ArrayList<ModaliteTransport> listModa = new ArrayList<>();
        listModa.add(ModaliteTransport.AVION);
        listModa.add(ModaliteTransport.TRAIN);
        listModa.add(ModaliteTransport.BUS);

        for (Chemin chemin : chemins) {
            assertTrue(p.trajetValid(chemin, listModa, 1000));
        }

        for (Chemin chemin : chemins) {
            assertFalse(p.trajetValid(chemin, listModa, 2));
        }

        listModa.remove(ModaliteTransport.TRAIN);

        for (Chemin chemin : chemins) {
            assertFalse(p.trajetValid(chemin, listModa, 1000));
        }
    }

    // @Test
    // public void testTrajet(){
    //     String attempt = "Lille ---TRAIN--> Amiens, Poids: 40.0" + System.getProperty("line.separator") + "Lille ---TRAIN--> Douai ---AVION--> Arras ---BUS--> Amiens, Poids: 60.0";
    //     ArrayList<ModaliteTransport> modaliteTransports = new ArrayList<>();

    //     for (ModaliteTransport modaliteTransport : ModaliteTransport.values()) {
    //         modaliteTransports.add(modaliteTransport);
    //     }
        
    //     try {
    //         assertEquals(attempt, p.trajet(graph, modaliteTransports, 2));
    //     } catch (NoTravelFoundException e){}

    //     boolean noTravel = false;
    //     try {
    //         v.trajet(graph, new ArrayList<ModaliteTransport>(), 5);
    //     } catch (NoTravelFoundException e){
    //         noTravel = true;
    //     }
    //     assertTrue(noTravel);
    // }
}