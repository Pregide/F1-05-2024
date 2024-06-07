package App;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.ulille.but.sae_s2_2024.Trancon;
import graph.TypeCout;

public class TestPlateforme {
    Plateforme p;
    ArrayList<String> given;

    @BeforeEach
    public void initial(){
        given = new ArrayList<>();
        p = new Plateforme(given);
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

        p.changeCritère(TypeCout.CO2);
        for (Trancon tr : p.getGraphe().aretes()) {
            assertEquals(1, p.getGraphe().getPoidsArete(tr));
        }
        
        p.changeCritère(TypeCout.TEMPS);
        for (Trancon tr : p.getGraphe().aretes()) {
            assertEquals(2, p.getGraphe().getPoidsArete(tr));
        }

        p.changeCritère(TypeCout.PRIX);
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
}