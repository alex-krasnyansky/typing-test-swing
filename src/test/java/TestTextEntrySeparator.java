import org.akrasnyansky.typetest.recognizer.TextEntry;
import org.akrasnyansky.typetest.recognizer.TextEntrySeparator;
import org.akrasnyansky.typetest.recognizer.TextEntryWord;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestTextEntrySeparator {

    @Test
    public void testCreation() {
        // good
        TextEntrySeparator sep1 = new TextEntrySeparator(".", 2);
        assertEquals(1, sep1.getLength());
        assertEquals(2, sep1.getPositionStart());
        assertEquals(3, sep1.getPositionEnd());
        assertEquals(".", sep1.getString());
        assertEquals(TextEntry.TYPE_SEPARATOR, sep1.getType());
        // space
        TextEntrySeparator sep2 = new TextEntrySeparator(" ", 44);
        assertEquals(1, sep2.getLength());
        assertEquals(44, sep2.getPositionStart());
        assertEquals(45, sep2.getPositionEnd());
        assertEquals(" ", sep2.getString());
    }


    @Test(expected = IllegalArgumentException.class)
    public void textCreationException() {
        new TextEntrySeparator("  ", 18);
    }

    @Test(expected = IllegalArgumentException.class)
    public void textCreationExceptionPosition() {
        new TextEntrySeparator(")", -6);
    }

    @Test
    public void testDiffs() {
        TextEntrySeparator sep1 = new TextEntrySeparator("-", 90);
        TextEntrySeparator sep2 = new TextEntrySeparator("-", 105);
        TextEntrySeparator sep3 = new TextEntrySeparator("@", 3);

        // diff list void
        assertEquals(0, sep1.getDiffMeasure(sep1));

        assertEquals(0, sep1.getDiffMeasure(sep2));

        assertEquals(0, sep2.getDiffMeasure(sep1));
        // diff list real
        assertEquals(1, sep1.getDiffMeasure(sep3));

        assertEquals(1, sep3.getDiffMeasure(sep2));

        // diffs with other things
        assertEquals(1, sep2.getDiffMeasure(null));

        assertEquals(1, sep2.getDiffMeasure(new TextEntryWord("-", 105)));
    }

    @Test
    public void testDiffAdvanced() {
        TextEntrySeparator sepQuoteSimple = new TextEntrySeparator("\"", 88);
        TextEntrySeparator sepQuoteRusLeft = new TextEntrySeparator("«", 9);
        TextEntrySeparator sepQuoteRusRight = new TextEntrySeparator("»", 1009);

        assertEquals(0, sepQuoteRusLeft.getDiffMeasure(sepQuoteSimple));

        assertEquals(0, sepQuoteSimple.getDiffMeasure(sepQuoteRusRight));

        assertEquals(1, sepQuoteRusLeft.getDiffMeasure(sepQuoteRusRight));
    }

}
