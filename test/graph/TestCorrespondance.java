package graph;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.ulille.but.sae_s2_2024.ModaliteTransport;
import graph.exception.IncompatibleModalityException;

public class TestCorrespondance {
    Correspondance c;

    @BeforeEach
    public void initialise(){
        try {
            c = new Correspondance(new MonLieu("Lille"), ModaliteTransport.TRAIN, ModaliteTransport.AVION, 0, 0, 0);
        } catch (IncompatibleModalityException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEquals(){
        assertFalse(c.equals(null));
        assertTrue(c.equals(c));
        try{
            assertTrue(c.equals(new Correspondance(new MonLieu("Lille"), ModaliteTransport.TRAIN, ModaliteTransport.AVION, 0, 0, 0)));
            assertFalse(c.equals(new Correspondance(new MonLieu("Paris"), ModaliteTransport.TRAIN, ModaliteTransport.AVION, 0, 0, 0)));
        } catch (IncompatibleModalityException e){}
    }
}
