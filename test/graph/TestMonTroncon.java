package graph;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
// import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.ulille.but.sae_s2_2024.Lieu;
import fr.ulille.but.sae_s2_2024.ModaliteTransport;
import fr.ulille.but.sae_s2_2024.Trancon;

public class TestMonTroncon {
    Lieu l1, l2, l3;
    Trancon t1, t2;

    @BeforeEach
    public void initialise(){
        l1 = new MonLieu("Lille");
        l2 = new MonLieu("Douai");
        l3 = new MonLieu("Arras");

        t1 = new MonTroncon(ModaliteTransport.TRAIN, l1, l2, 0, 0, 0);
        t2 = new MonTroncon(ModaliteTransport.TRAIN, l2, l3, 0, 0, 0);
    }

    @Test
    public void testEquals(){
        Trancon tb1, tb2, tb3;
        tb1 = new MonTroncon(ModaliteTransport.TRAIN, l1, l2, 0, 0, 0);
        tb2 = new MonTroncon(ModaliteTransport.AVION, l2, l3, 0, 0, 0);
        tb3 = new MonTroncon(ModaliteTransport.TRAIN, l1, l3, 0, 0, 0);

        assertFalse(t1.equals(null));
        assertFalse(t1.equals(t2));
        assertFalse(t1.equals(tb3));
        assertFalse(t2.equals(tb2));

        assertTrue(t1.equals(tb1));
        assertTrue(t1.equals(t1));
    }

    // @Test
    // public void testToString(){
    //     String attempt = "TRAIN-->Lille,Douai";
    //     assertEquals(attempt, t1);
    // }
}
