import org.akrasnyansky.typetest.recognizer.CompareHelper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestCompareHelper {

    @Test
    public void testInit() {
        assertEquals(6, CompareHelper.QUOTES_OPENING.size());
        assertEquals(6, CompareHelper.QUOTES_CLOSING.size());
        assertEquals(6, CompareHelper.HYPHENS.size());
        assertEquals(2, CompareHelper.SPACES.size());
        assertEquals(2, CompareHelper.LINE_SEPARATORS.size());
    }

}
