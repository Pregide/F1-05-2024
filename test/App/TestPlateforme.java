package App;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.ulille.but.sae_s2_2024.Lieu;
import graph.MonLieu;

public class TestPlateforme {
    Plateforme p;

    @BeforeEach
    public void initial(){
        p = new Plateforme(new String[]{});
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
        Lieu l1 = new MonLieu("Arras");
        Lieu l2 = new MonLieu("Douai");
        Lieu l3 = new MonLieu("Lille");

        assertEquals(0, p.getSizeLieux());

        p.listLieux(l1, l2);
        assertEquals(2, p.getSizeLieux());

        p.listLieux(l1, l3);
        assertEquals(3, p.getSizeLieux());

        p.listLieux(l3, l1);
        assertEquals(3, p.getSizeLieux());
    }

    @Test
    public void testListTroncon(){
        assertEquals(0, p.getSizeTroncons());

        p.ventilation(new String[]{"Lion;Boiry;Train;10;20;30",
        "Lion;Boiry;Avion;40;50;60",
        "Courcheveille;Dijon;Bus;70;80;90"});
        assertEquals(6, p.getSizeTroncons());
    }
}