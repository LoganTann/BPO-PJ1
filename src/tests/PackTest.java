import appli.Pack;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PackTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testConstants() {
        assertEquals(Pack.DEFAULT_FIRST_CARD, 1);
        assertEquals(Pack.DEFAULT_LAST_CARD, 60);
    }

    @Test
    public void getPack() {
        Pack packDefault = new Pack();
        Pack packLitterals = new Pack(1, 60);
        assertNotNull(packDefault.getPack());
        assertNotNull(packLitterals.getPack());
    }

    @Test
    public void testDefaultConstructor() {
        Pack packDefault = new Pack();
        Pack packLitterals = new Pack(1, 60);

        assertArrayEquals(
            packDefault.getPack().toArray(new Integer[0]),
            packLitterals.getPack().toArray(new Integer[0])
        );
    }

    @Test
    public void removeCard() {

    }

    @Test
    public void exists() {
    }
}