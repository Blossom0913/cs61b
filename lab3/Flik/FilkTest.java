// Example JUnit test for Flik
import static org.junit.Assert.*;
import org.junit.Test;

public class FilkTest {
    @Test
    public void testIsSameNumber() {
        assertTrue(Flik.isSameNumber(128, 128)); // This will fail!
        assertTrue(Flik.isSameNumber(100, 100)); // This may pass!
    }
}