package App;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        String[][] res = new String[][]{{"a", "b", "Train", "10", "20", "30"},
                                        {"a", "b", "Avion", "40", "50", "60"},
                                        {"c", "d", "Bus", "70", "80", "90"}};

        assertArrayEquals(res, p.ventilation(new String[]{"a;b;Train;10;20;30",
                                                            "a;b;Avion;40;50;60",
                                                            "c;d;Bus;70;80;90"}));
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

        p.ventilation(new String[]{"a;b;Train;10;20;30",
                                    "a;b;Avion;40;50;60"});
        assertEquals(4, p.getSizeTroncons());
    }
}
