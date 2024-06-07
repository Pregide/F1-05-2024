package graph;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.ulille.but.sae_s2_2024.Lieu;

public class TestMonLieu {
    Lieu l1, l2;

    @BeforeEach
    public void initialise(){
        l1 = new MonLieu("Lille");
        l2 = new MonLieu("Douai");
    }

    @Test
    public void testEquals(){
        Lieu l3 = new MonLieu("Lille");

        assertTrue(l1.equals(l3));
        assertFalse(l1.equals(l2));
        assertFalse(l1.equals(null));
        assertTrue(l1.equals(l1));
    }
}
