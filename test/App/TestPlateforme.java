package App;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPlateforme {
    Plateforme p;

    @BeforeEach
    public void initial(){
        Plateforme p = new Plateforme("villeA;villeB;Train;60;1.7;80"+
                                      "villeB;villeD;Train;22;2.4;40"+
                                      "villeA;villeC;Train;42;1.4;50"+
                                      "villeB;villeC;Train;14;1.4;60"+
                                      "villeC;villeD;Avion;110;150;22"+
                                      "villeC;villeD;Train;65;1.2;90");
    }

    @Test
    public void testVentilation(){
        ArrayList<String[]> expected = new ArrayList<String[]>();
        expected.add(new String[]{"a", "b", "Train", "10", "20", "30"});
        expected.add(new String[]{"a", "b", "Avion", "40", "50", "60"});
        expected.add(new String[]{"c", "d", "Bus", "70", "80", "90"});

        ArrayList<String[]> test = p.ventilation(new String[]{"a;b;Train;10;20;30",
                                                        "a;b;Avion;40;50;60",
                                                        "c;d;Bus;70;80;90"});

        for(int i=0; i<expected.size(); i++){
            assertArrayEquals(expected.get(i), test.get(i));
        }

        assertNull(p.ventilation(null));
    }

    @Test
    public void testIsValid() {
        ArrayList<String[]> expected =new ArrayList<String[]>();

        String[] inputData = {
            "a;null;Train;10;20;30",
            "a;b;Avion;40;50;60",
            "c;d;Bus;70;80;90"
        };

        expected.add(new String[]{"a", "b", "Avion", "40", "50", "60"});
        expected.add(new String[]{"c", "d", "Bus", "70", "80", "90"});

        for(int i=0; i<expected.size(); i++){
            assertArrayEquals(expected.get(i), p.ventilation(inputData).get(i));
        }

        String[] inputData2 = {
            "a;-10;Train;10;20;30",
            "a;b;Avion;40;50;60",
            "c;d;Bus;70;80"
        };
    
        expected.clear();
        expected.add(new String[]{"a", "b", "Avion", "40", "50", "60"});

        for(int i=0; i<expected.size(); i++){
            assertArrayEquals(expected.get(i), p.ventilation(inputData2).get(i));
        }

        String[] inputData3 = {
            "a;-10;Train;10;20;30",
            "a;b;Avion;40;50;60",
            "c;d;Bus;70;80;0;10"
        };

        expected.clear();
        expected.add(new String[]{"a", "b", "Avion", "40", "50", "60"});

        for(int i=0; i<expected.size(); i++){
            assertArrayEquals(expected.get(i), p.ventilation(inputData3).get(i));
        }
    }

    @Test
    public void testListLieu(){
        p = new Plateforme(null);
        assertEquals(0, p.getSizeLieux());
        p.listLieux("Arras");
        assertEquals(1, p.getSizeLieux());
        p.listLieux("Arras");
        assertEquals(1, p.getSizeLieux());
    }

    @Test
    public void testListTroncon(){
        p = new Plateforme(null);
        assertEquals(0, p.getSizeTroncons());

        p.ventilation(new String[]{"Lion;Boiry;Train;10;20;30",
        "Lion;Boiry;Avion;40;50;60",
        "Courcheveille;Dijon;Bus;70;80;90"});
        
        assertEquals(6, p.getSizeTroncons());
    }

    @Test
    public void testAddData(){

        assertEquals(0, p.getGraphe().sommets().size());
        assertEquals(0, p.getGraphe().aretes().size());

        p = new Plateforme(new String[]{"a;b;Train;10;11;12"});
        assertEquals(2, p.getGraphe().sommets().size());
        assertEquals(2, p.getGraphe().aretes().size());

        p = new Plateforme(new String[]{"a;b;Train;10;11;12", "a;c;Train;13;14;15"});
        assertEquals(3, p.getGraphe().sommets().size());
        assertEquals(4, p.getGraphe().aretes().size());
    }

    @Test
    public void testChangeCritère(){}

    @Test
    public void testAfficheListLieu(){}
}